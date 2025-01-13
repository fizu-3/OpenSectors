package net.fcode;

import net.fcode.command.ChannelCommand;
import net.fcode.configuration.impl.DatabaseConfiguration;
import net.fcode.configuration.impl.MessagesConfiguration;
import net.fcode.configuration.impl.SectorConfiguration;
import net.fcode.configuration.service.ConfigurationService;
import net.fcode.helper.ChatHelper;
import net.fcode.sector.Sector;
import net.fcode.user.cache.UserCache;
import net.fcode.inventory.api.GuiActionHandler;
import net.fcode.listener.*;
import net.fcode.listener.redis.PacketSectorConfigurationResponseListener;
import net.fcode.listener.redis.PacketSectorInformationUpdateListener;
import net.fcode.listener.redis.PacketUserSynchronizeDataListener;
import net.fcode.packet.impl.SectorConfigurationRequestPacket;
import net.fcode.runnable.ActionbarNotificationRunnable;
import net.fcode.runnable.SectorBorderUpdateRunnable;
import net.fcode.runnable.SectorInformationUpdateRunnable;
import net.fcode.scoreboard.SpawnScoreboard;
import net.fcode.scoreboard.api.Assemble;
import net.fcode.sector.SectorCache;
import net.fcode.sector.SectorType;
import net.fcode.transfer.PlayerTransferService;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public final class SectorsPlugin extends JavaPlugin {

    private MessengerService messengerService;
    private PlayerTransferService transferService;

    private SectorCache sectorCache;
    private UserCache userCache;

    private MessagesConfiguration messagesConfiguration;

    @Override
    public void onEnable() {
        ConfigurationService configurationService = new ConfigurationService(this.getDataFolder().getAbsolutePath());

        SectorConfiguration sectorConfiguration = configurationService.loadConfiguration(new SectorConfiguration());
        DatabaseConfiguration databaseConfiguration = configurationService.loadConfiguration(new DatabaseConfiguration());

        messagesConfiguration = configurationService.loadConfiguration(new MessagesConfiguration());

        this.sectorCache = new SectorCache();
        this.sectorCache.setCurrentSector(sectorConfiguration.getCurrentSector());

        this.messengerService = new MessengerService(databaseConfiguration.getRedisHost(), databaseConfiguration.getRedisPort(), databaseConfiguration.getRedisPassword());
        this.messengerService.setPacketSender(this.sectorCache.getCurrentSectorID());

        this.messengerService.subscribe(this.sectorCache.getCurrentSectorID(),new PacketSectorConfigurationResponseListener(this));
        this.messengerService.subscribe(this.sectorCache.getCurrentSectorID(),new PacketUserSynchronizeDataListener(this.messengerService,this.userCache));
        this.messengerService.subscribe("sectors",new PacketSectorInformationUpdateListener(this.sectorCache));

        this.transferService = new PlayerTransferService(this);
        this.userCache = new UserCache();

        initListeners();

        new SectorInformationUpdateRunnable(this).runTaskTimerAsynchronously(this,50L,50L);
        new ActionbarNotificationRunnable(this).runTaskTimer(this,1L,1L);
        new SectorBorderUpdateRunnable(this).runTaskTimer(this,65L,65L);

        this.messengerService.publish("proxy",new SectorConfigurationRequestPacket());


    }

    @Override
    public void onDisable() {
        if(this.sectorCache.getCurrentSector().getSectorType().equals(SectorType.SPAWN)) {
            Optional<Sector> optionalSector = this.sectorCache.getSpawnSector();

            for(Player player : this.getServer().getOnlinePlayers()) {
                if(optionalSector.isEmpty()) {
                    player.kickPlayer(ChatHelper.colored(this.messagesConfiguration.getSpawnSectorNotFoundMessage()));
                    continue;
                }

                this.userCache.findUserByUUID(player.getUniqueId()).ifPresent(user -> this.transferService.connect(player,user,optionalSector.get()));

            }
        }

        if (Objects.nonNull(this.messengerService)) {
            this.messengerService.shutdown();
        }
    }

    public void registerSector() {
        if(this.sectorCache.getCurrentSector() == null) {
            this.getServer().shutdown();
            return;
        }

        if(!this.sectorCache.getCurrentSector().getSectorType().equals(SectorType.SPAWN)) return;

        Assemble assemble = new Assemble(this,new SpawnScoreboard(this.sectorCache,this.messagesConfiguration));
        assemble.setTicks(5);
        assemble.setup();

        this.getCommand("channel").setExecutor(new ChannelCommand(this.userCache,this.sectorCache,this.transferService,this.messagesConfiguration));
    }

    public PlayerTransferService getTransferService() {
        return transferService;
    }

    public UserCache getUserCache() {
        return userCache;
    }

    public MessengerService getMessengerService() {
        return messengerService;
    }

    public SectorCache getSectorCache() {
        return sectorCache;
    }

    private void initListeners() {
        Arrays.stream(new Listener[] {
                new GuiActionHandler(),
                new PlayerJoinListener(this),
                new PlayerRespawnListener(this),
                new PlayerDeathListener(this),
                new PlayerSectorInteractListener(this),
                new PlayerLoginListener(this),
                new PlayerQuitListener(this.userCache),
                new PlayerMoveListener(this.sectorCache,this.userCache,this.transferService,this.messagesConfiguration),
                new PlayerTeleportListener(this.sectorCache,this.userCache,this.transferService,this.messagesConfiguration),
        }).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener,this));
    }

    public MessagesConfiguration getMessagesConfiguration() {
        return messagesConfiguration;
    }
}

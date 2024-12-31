package fi.fcode;

import fi.fcode.command.ChannelCommand;
import fi.fcode.configuration.ConfigurationHelper;
import fi.fcode.configuration.impl.configuration.ClientConfiguration;
import fi.fcode.data.cache.UserCache;
import fi.fcode.inventory.api.GuiActionHandler;
import fi.fcode.listener.*;
import fi.fcode.listener.redis.PacketSectorConfigurationResponseListener;
import fi.fcode.listener.redis.PacketSectorInformationUpdateListener;
import fi.fcode.listener.redis.PacketUserSynchronizeDataListener;
import fi.fcode.packet.impl.SectorConfigurationRequestPacket;
import fi.fcode.scoreboard.SpawnScoreboard;
import fi.fcode.scoreboard.api.Assemble;
import fi.fcode.sector.SectorCache;
import fi.fcode.sector.SectorType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public final class SectorsPlugin extends JavaPlugin {
    private static SectorsPlugin instance;
    private MessengerService messengerService;
    private SectorCache sectorCache;
    private UserCache userCache;
    @Override
    public void onEnable() {
        instance = this;
        ClientConfiguration clientConfiguration = ConfigurationHelper.load(new File(this.getDataFolder() + "/config.json"),ClientConfiguration.class);
        this.sectorCache = new SectorCache();
        this.sectorCache.setCurrentSector(clientConfiguration.currentSector);

        this.messengerService = new MessengerService(clientConfiguration.redis.redisHost,clientConfiguration.redis.redisPort,clientConfiguration.redis.redisPassword);
        this.messengerService.setPacketSender(this.sectorCache.getCurrentSectorID());

        this.messengerService.subscribe(this.sectorCache.getCurrentSectorID(),new PacketSectorConfigurationResponseListener());
        this.messengerService.subscribe("sectors",new PacketSectorInformationUpdateListener());
        this.messengerService.subscribe(this.sectorCache.getCurrentSectorID(),new PacketUserSynchronizeDataListener());

        this.userCache = new UserCache();

        initListeners();
        this.messengerService.publish("proxy",new SectorConfigurationRequestPacket());


    }

    @Override
    public void onDisable() {
        if (Objects.nonNull(this.messengerService)) {
            this.messengerService.shutdown();
        }
    }

    public void registerSector() {
        if(SectorsPlugin.getInstance().getSectorCache().getCurrentSector().getSectorType() == SectorType.SPAWN) {
            Assemble assemble = new Assemble(this,new SpawnScoreboard());
            assemble.setTicks(5);
            assemble.setup();
            this.getCommand("channel").setExecutor(new ChannelCommand());
        }
    }

    public UserCache getUserCache() {
        return userCache;
    }

    public static SectorsPlugin getInstance() {
        return instance;
    }

    public MessengerService getMessengerService() {
        return messengerService;
    }

    public SectorCache getSectorCache() {
        return sectorCache;
    }

    private void initListeners() {
        Arrays.stream(new Listener[] {
                new PlayerMoveListener(),
                new PlayerJoinListener(),
                new GuiActionHandler(),
                new PlayerQuitListener(),
                new PlayerSectorInteractListener(),
                new PlayerTeleportListener(),
                new PlayerLoginListener(),
                new PlayerDeathListener()
        }).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener,this));
    }
}

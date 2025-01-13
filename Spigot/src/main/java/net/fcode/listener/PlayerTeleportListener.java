package net.fcode.listener;

import net.fcode.configuration.impl.MessagesConfiguration;
import net.fcode.user.User;
import net.fcode.user.cache.UserCache;
import net.fcode.helper.ChatHelper;
import net.fcode.transfer.PlayerTransferService;
import net.fcode.sector.Sector;
import net.fcode.sector.SectorCache;
import net.fcode.sector.SectorType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Optional;

public class PlayerTeleportListener implements Listener {

    private final UserCache userCache;

    private final SectorCache sectorCache;

    private final PlayerTransferService transferService;

    private final MessagesConfiguration messagesConfiguration;

    public PlayerTeleportListener(SectorCache sectorCache, UserCache userCache, PlayerTransferService transferService, MessagesConfiguration messagesConfiguration) {
        this.sectorCache = sectorCache;
        this.userCache = userCache;
        this.transferService = transferService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) return;

        if(!event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) return;

        Player player = event.getPlayer();
        Location locationTo = event.getTo();

        Optional<User> userOptional = this.userCache.findUserByUUID(player.getUniqueId());

        if(userOptional.isEmpty()) {
            return;
        }

        this.sectorCache.getSectorByLocation(locationTo).ifPresent(sector -> {
            if (sector.getSectorType().equals(SectorType.SPAWN)) {
                sector = this.sectorCache.getSpawnSector().orElse(sector);
            }

            if (!sector.isOnline()) {
                player.sendMessage(ChatHelper.colored(this.messagesConfiguration.getSectorIsOfflineMessage()));
                return;
            }

            User user = userOptional.get();

            if (user.isRedirecting()) return;

            user.setRedirecting(true);

            this.transferService.connect(player, user, sector);
        });
    }
}
package fi.fcode.listeners;

import fi.fcode.SectorsPlugin;
import fi.fcode.data.User;
import fi.fcode.helpers.ChatHelper;
import fi.fcode.helpers.PlayerTransferHelper;
import fi.fcode.sector.Sector;
import fi.fcode.sector.SectorType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Optional;

public class PlayerTeleportListener implements Listener {


    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        Location locationTo = event.getTo();


        Optional<Sector> sectorOptional = SectorsPlugin.getInstance().getSectorCache().getSectorByLocation(locationTo);

        Optional<User> userOptional = SectorsPlugin.getInstance().getUserCache().getUser(player.getUniqueId());

        if(!sectorOptional.isPresent() || !userOptional.isPresent()) {
            return;
        }
        sectorOptional.ifPresent(sector -> {
            if(sector.getSectorType().equals(SectorType.SPAWN)) {
                sector = SectorsPlugin.getInstance().getSectorCache().getSpawnSector().get();
            }
            if(!sector.isOnline()) {
                player.sendMessage(ChatHelper.colored("&4Sektor " + sector.getId() + " z jakim probujesz sie polaczyc jest niedostepny"));
                return;
            }
            User user = userOptional.get();
            if(!user.isRedirecting()) {
                user.setRedirecting(true);
                PlayerTransferHelper.connect(user, sector);
            }
        });
    }
}
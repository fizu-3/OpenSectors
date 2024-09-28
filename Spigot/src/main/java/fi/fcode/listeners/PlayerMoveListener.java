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
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location goToLocation = event.getTo();

        Optional<User> userOptional = SectorsPlugin.getInstance().getUserCache().getUser(player.getUniqueId());
        if(!userOptional.isPresent()) {
            return;
        }
        Optional<Sector> sectorOptional = SectorsPlugin.getInstance().getSectorCache().getSectorByLocation(goToLocation);

        if(!sectorOptional.isPresent()) {
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
            PlayerTransferHelper.connect(userOptional.get(), sector);
        });

    }
}

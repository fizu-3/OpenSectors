package fi.fcode.listener;

import fi.fcode.SectorsPlugin;
import fi.fcode.data.User;
import fi.fcode.helper.ChatHelper;
import fi.fcode.helper.PlayerTransferHelper;
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

        Optional<User> userOptional = SectorsPlugin.getInstance().getUserCache().findUserByUUID(player.getUniqueId());

        Optional<Sector> sectorOptional = SectorsPlugin.getInstance().getSectorCache().getSectorByLocation(goToLocation);

        if(userOptional.isEmpty()) {
            return;
        }

        sectorOptional.ifPresent(sector -> {
            if(sector.getSectorType().equals(SectorType.SPAWN)) {
                sector = SectorsPlugin.getInstance().getSectorCache().getSpawnSector().orElseThrow(() -> new RuntimeException("Spawn sector not founded!!!"));
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

package fi.fcode.listener;

import fi.fcode.SectorsPlugin;
import fi.fcode.data.User;
import fi.fcode.helper.ChatHelper;
import fi.fcode.helper.PlayerTransferHelper;
import fi.fcode.sector.Sector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Optional;

public class PlayerDeathListener implements Listener {


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);

        if (event.getEntity() == null) return;

        Player victim = event.getEntity();

        Optional<Sector> sectorOptional = SectorsPlugin.getInstance().getSectorCache().getSpawnSector();

        if (sectorOptional.isEmpty()) {
            victim.kickPlayer(ChatHelper.colored("&cBrak wolnego servera"));
            return;
        }

        Optional<User> userOptional = SectorsPlugin.getInstance().getUserCache().findUserByUUID(victim.getUniqueId());

        if (userOptional.isEmpty()) {
            victim.kickPlayer(ChatHelper.colored("&cCos sie rozpierdolilo pozdro dla ciebie"));
            return;
        }

        SectorsPlugin.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(SectorsPlugin.getInstance(), () -> {
            victim.spigot().respawn();
            PlayerTransferHelper.connect(userOptional.get(), sectorOptional.get());
        }, 2L);
    }
}

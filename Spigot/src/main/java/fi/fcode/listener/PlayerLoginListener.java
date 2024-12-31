package fi.fcode.listener;

import fi.fcode.SectorsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        SectorsPlugin.getInstance().getUserCache().findUserByUUID(player.getUniqueId()).ifPresentOrElse(user -> {
            Logger.getAnonymousLogger().log(Level.FINE,"User founded");
        }, () -> {
            SectorsPlugin.getInstance().getUserCache().createUser(player);
        });
    }
}

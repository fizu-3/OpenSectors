package fi.fcode.runnable;

import fi.fcode.SectorsPlugin;
import fi.fcode.helpers.ChatHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class ActionbarNotificationRunnable implements Runnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location location = player.getLocation();
            double distance = SectorsPlugin.getInstance().getSectorCache().distance(location);

            if (distance > 30.0) {
                continue;
            }
            ChatHelper.sendActionBarPlayer(player, ChatHelper.colored("&7Jesteś blisko sektora &2{DISTANCE}&7m")
                    .replace("{DISTANCE}", String.valueOf(Math.round(distance))));
        }
    }
}

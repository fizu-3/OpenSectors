package net.fcode.runnable;

import net.fcode.SectorsPlugin;
import net.fcode.helper.ChatHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class ActionbarNotificationRunnable extends BukkitRunnable {

    private final SectorsPlugin plugin;

    public ActionbarNotificationRunnable(SectorsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            Location location = player.getLocation();
            double distance = this.plugin.getSectorCache().distance(location);

            if (distance > 30.0) continue;

            ChatHelper.sendActionBarPlayer(player, ChatHelper.colored(this.plugin.getMessagesConfiguration().getActionbarBorderMessage())
                    .replace("{DISTANCE}", String.valueOf(Math.round(distance))));
        }
    }
}

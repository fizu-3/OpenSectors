package net.fcode.listener;

import net.fcode.user.cache.UserCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final UserCache userCache;

    public PlayerQuitListener(UserCache userCache) {
        this.userCache = userCache;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(null);

        this.userCache.removeUser(player.getUniqueId());
    }
}

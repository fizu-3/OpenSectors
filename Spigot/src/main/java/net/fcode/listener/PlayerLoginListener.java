package net.fcode.listener;

import net.fcode.SectorsPlugin;
import net.fcode.helper.ChatHelper;
import net.fcode.user.cache.UserCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerLoginListener implements Listener {

    private final SectorsPlugin plugin;

    public PlayerLoginListener(SectorsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if(this.plugin.getSectorCache().getSectorMap().isEmpty()) {
            event.setKickMessage(ChatHelper.colored(this.plugin.getMessagesConfiguration().getNoSectorsAvailableMessage()));
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            return;
        }

        this.plugin.getUserCache().findUserByUUID(player.getUniqueId()).ifPresentOrElse(user -> Logger.getAnonymousLogger().log(Level.FINE,"User found"), () -> this.plugin.getUserCache().createUser(player));
    }
}

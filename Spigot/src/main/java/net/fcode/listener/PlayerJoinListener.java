package net.fcode.listener;

import net.fcode.SectorsPlugin;
import net.fcode.helper.ChatHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final SectorsPlugin plugin;

    public PlayerJoinListener(SectorsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        this.plugin.getUserCache().findUserByUUID(player.getUniqueId()).ifPresent(user -> {
            user.loadData(player,this.plugin);

            player.sendMessage(ChatHelper.colored(this.plugin.getMessagesConfiguration().getPlayerDataLoadedMessage()));
        });

        player.sendTitle(ChatHelper.colored(this.plugin.getMessagesConfiguration().getConnectedInfoTitle()), ChatHelper.colored(this.plugin.getMessagesConfiguration().getConnectedInfoSubTitle()));
    }

}

package net.fcode.listener;

import net.fcode.SectorsPlugin;
import net.fcode.helper.ChatHelper;
import net.fcode.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Optional;

public class PlayerRespawnListener implements Listener {

    private final SectorsPlugin plugin;

    public PlayerRespawnListener(SectorsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        Optional<User> userOptional = this.plugin.getUserCache().findUserByUUID(player.getUniqueId());

        if(userOptional.isEmpty()) return;

        this.plugin.getSectorCache().getSpawnSector().ifPresentOrElse(sector -> this.plugin.getTransferService().connect(player,userOptional.get(),sector),() -> player.kickPlayer(ChatHelper.colored(this.plugin.getMessagesConfiguration().getSpawnSectorNotFoundMessage())));



    }
}

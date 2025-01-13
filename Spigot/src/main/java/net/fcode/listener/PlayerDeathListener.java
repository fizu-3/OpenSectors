package net.fcode.listener;

import net.fcode.SectorsPlugin;
import net.fcode.user.User;
import net.fcode.helper.ChatHelper;
import net.fcode.sector.Sector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Optional;

public class PlayerDeathListener implements Listener {

    private final SectorsPlugin plugin;

    public PlayerDeathListener(SectorsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);

        if (event.getEntity() == null) return;

        Player victim = event.getEntity();

        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> victim.spigot().respawn(), 2L);
    }
}

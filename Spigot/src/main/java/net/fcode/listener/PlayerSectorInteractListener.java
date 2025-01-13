package net.fcode.listener;

import net.fcode.SectorsPlugin;
import net.fcode.helper.ChatHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class PlayerSectorInteractListener implements Listener {

    private final SectorsPlugin plugin;

    public PlayerSectorInteractListener(SectorsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (this.plugin.getSectorCache().distance(location) <= 30) {
            event.setCancelled(true);

            player.sendMessage(ChatHelper.colored(this.plugin.getMessagesConfiguration().getCannotBreakBlockNearSectorMessage()));
            return;
        }

        this.plugin.getUserCache().findUserByUUID(player.getUniqueId()).ifPresent(user -> {
            if(!user.isRedirecting()) return;

            event.setCancelled(true);
        });

    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (this.plugin.getSectorCache().distance(location) <= 30) {
            event.setCancelled(true);

            player.sendMessage(ChatHelper.colored(this.plugin.getMessagesConfiguration().getCannotPlaceBlockNearSectorMessage()));
            return;
        }

        this.plugin.getUserCache().findUserByUUID(player.getUniqueId()).ifPresent(user -> {
            if(!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlockClicked().getLocation();

        if(event.getBlockClicked() == null) return;

        if (this.plugin.getSectorCache().distance(location) <= 30) {
            event.setCancelled(true);
            return;
        }

        this.plugin.getUserCache().findUserByUUID(player.getUniqueId()).ifPresent(user -> {
            if(!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlockClicked().getLocation();

        if(event.getBlockClicked() == null) return;

        if (this.plugin.getSectorCache().distance(location) <= 30) {
            event.setCancelled(true);
            return;
        }

        this.plugin.getUserCache().findUserByUUID(player.getUniqueId()).ifPresent(user -> {
            if(!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }


    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        Location location = event.getBlock().getLocation();

        if (this.plugin.getSectorCache().distance(location) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (this.plugin.getSectorCache().distance(event.getLocation()) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        if (this.plugin.getSectorCache().distance(location) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        this.plugin.getUserCache().findUserByUUID(event.getEntity().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onProjectileLaunch(final ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;

        this.plugin.getUserCache().findUserByUUID(event.getEntity().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        this.plugin.getUserCache().findUserByUUID(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onOpen(final InventoryOpenEvent event) {
        this.plugin.getUserCache().findUserByUUID(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        this.plugin.getUserCache().findUserByUUID(event.getWhoClicked().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onDrag(final InventoryDragEvent event) {
        this.plugin.getUserCache().findUserByUUID(event.getWhoClicked().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onInteract(final InventoryInteractEvent event) {
        this.plugin.getUserCache().findUserByUUID(event.getWhoClicked().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }
}
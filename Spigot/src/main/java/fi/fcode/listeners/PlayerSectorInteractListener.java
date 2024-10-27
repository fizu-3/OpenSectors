package fi.fcode.listeners;

import fi.fcode.SectorsPlugin;
import fi.fcode.data.User;
import fi.fcode.helpers.ChatHelper;
import fi.fcode.sector.SectorCache;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.util.Objects;
import java.util.Optional;

public class PlayerSectorInteractListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onCreate(final VehicleCreateEvent event) {
        final Vehicle vehicle = event.getVehicle();

        SectorCache sectorCache = SectorsPlugin.getInstance().getSectorCache();

        if (sectorCache.distance(vehicle.getLocation()) <= 30) {
            SectorsPlugin.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(SectorsPlugin.getInstance(), vehicle::remove);
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(final VehicleEnterEvent event) {
        final Vehicle vehicle = event.getVehicle();

        SectorCache sectorCache = SectorsPlugin.getInstance().getSectorCache();

        if (sectorCache.distance(vehicle.getLocation()) <= 30) {
            SectorsPlugin.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(SectorsPlugin.getInstance(), vehicle::remove);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        SectorCache sectorCache = SectorsPlugin.getInstance().getSectorCache();
        Location location = event.getBlock().getLocation();

        if (sectorCache.distance(location) <= 30) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            SectorsPlugin.getInstance().getUserCache().getUser(event.getEntity().getUniqueId()).ifPresent(user -> {
                if (user.isRedirecting()) {
                    event.setCancelled(true);
                }
            });
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLaunch(final ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            SectorsPlugin.getInstance().getUserCache().getUser(event.getEntity().getUniqueId()).ifPresent(user -> {
                if (user.isRedirecting()) {
                    event.setCancelled(true);
                }
            });
        }
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlace(final BlockPlaceEvent event) {
        SectorsPlugin.getInstance().getUserCache().getUser(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        SectorCache sectorCache = SectorsPlugin.getInstance().getSectorCache();
        Location location = event.getBlock().getLocation();

        if (sectorCache.distance(location) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        SectorCache sectorCache = SectorsPlugin.getInstance().getSectorCache();

        if (sectorCache.distance(event.getLocation()) <= 30) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        SectorsPlugin.getInstance().getUserCache().getUser(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        SectorCache sectorCache = SectorsPlugin.getInstance().getSectorCache();
        Location location = player.getLocation();


        if (sectorCache.distance(location) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onOpen(final InventoryOpenEvent event) {
        SectorsPlugin.getInstance().getUserCache().getUser(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent event) {
        SectorsPlugin.getInstance().getUserCache().getUser(event.getWhoClicked().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrag(final InventoryDragEvent event) {
        SectorsPlugin.getInstance().getUserCache().getUser(event.getWhoClicked().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final InventoryInteractEvent event) {
        SectorsPlugin.getInstance().getUserCache().getUser(event.getWhoClicked().getUniqueId()).ifPresent(user -> {
            if (user.isRedirecting()) {
                event.setCancelled(true);
            }
        });
    }
}
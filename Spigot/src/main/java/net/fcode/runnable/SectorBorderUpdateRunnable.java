package net.fcode.runnable;

import net.fcode.SectorsPlugin;
import net.fcode.helper.BorderHelper;
import net.fcode.sector.Sector;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SectorBorderUpdateRunnable extends BukkitRunnable {

    private final SectorsPlugin plugin;

    public SectorBorderUpdateRunnable(SectorsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Sector sector = this.plugin.getSectorCache().getCurrentSector();

        double sectorSize = Math.abs(sector.getMaxX() - sector.getMinX());

        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            BorderHelper.setWorldBorder(player, sectorSize, sector.getCenterX(), sector.getCenterZ());
        }
    }
}
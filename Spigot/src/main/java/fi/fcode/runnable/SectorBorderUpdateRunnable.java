package fi.fcode.runnable;

import fi.fcode.SectorsPlugin;
import fi.fcode.helper.BorderHelper;
import fi.fcode.sector.Sector;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SectorBorderUpdateRunnable implements Runnable {
    @Override
    public void run() {
        Sector sector = SectorsPlugin.getInstance().getSectorCache().getCurrentSector();
        double sectorSize = Math.abs(sector.getMaxX() - sector.getMinX());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            BorderHelper.setWorldBorder(player, sectorSize, sector.getCenterX(), sector.getCenterZ());
        }
    }
}
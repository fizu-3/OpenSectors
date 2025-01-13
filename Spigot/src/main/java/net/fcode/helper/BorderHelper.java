package net.fcode.helper;

import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BorderHelper {
    public static void setWorldBorder(Player player, double size, double centerX, double centerZ) {
        WorldServer worldServer = ((CraftWorld) player.getWorld()).getHandle();
        WorldBorder worldBorder = new WorldBorder();

        worldBorder.setWarningDistance(0);
        worldBorder.world = worldServer;
        worldBorder.setSize(size + 3);
        worldBorder.setCenter(centerX, centerZ);

        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(worldBorder,
                PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }
}

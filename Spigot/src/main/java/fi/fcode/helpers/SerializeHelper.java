package fi.fcode.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public final class SerializeHelper {

    public static String serializeLocation(final Location location) {
        try {
            return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":"
                    + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
        } catch (final Exception ex) {
            return "";
        }
    }

    public static Location deserializeLocation(final String serializedData) {
        try {
            final String[] split = serializedData.split(":");
            return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]),
                    Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]),
                    Float.parseFloat(split[5]));
        } catch (final Exception ex) {
            return null;
        }
    }


}
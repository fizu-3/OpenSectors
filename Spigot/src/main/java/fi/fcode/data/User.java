package fi.fcode.data;

import fi.fcode.helpers.SerializeHelper;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private String name,nbtString,lastLocation;
    private UUID uuid;
    private int gamemode,heldSlot;


    public User(Player player) {

        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.lastLocation = SerializeHelper.serializeLocation(player.getLocation());

        this.gamemode = player.getGameMode().getValue();
        this.heldSlot = player.getInventory().getHeldItemSlot();

        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        entityPlayer.b(nbtTagCompound);
        this.nbtString = nbtTagCompound.toString();
    }

    public void saveData(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.lastLocation = SerializeHelper.serializeLocation(player.getLocation());

        this.gamemode = player.getGameMode().getValue();
        this.heldSlot = player.getInventory().getHeldItemSlot();

        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        entityPlayer.b(nbtTagCompound);
        this.nbtString = nbtTagCompound.toString();
    }
    public int getHeldSlot() {
        return heldSlot;
    }

    public int getGamemode() {
        return gamemode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNbtString() {
        return nbtString;
    }

    public void setNbtString(String nbtString) {
        this.nbtString = nbtString;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

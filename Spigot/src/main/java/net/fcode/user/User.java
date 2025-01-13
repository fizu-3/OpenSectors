package net.fcode.user;

import net.fcode.SectorsPlugin;
import net.fcode.configuration.impl.MessagesConfiguration;
import net.fcode.event.PlayerLoadDataEvent;
import net.fcode.event.PlayerSaveDataEvent;
import net.fcode.helper.ChatHelper;
import net.fcode.helper.NbtConverterHelper;
import net.fcode.helper.SerializeHelper;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private final String name;
    private final UUID uuid;

    private String nbtString,lastLocation,gameMode;

    private int heldSlot;
    private long redirectTime;

    public User(Player player) {

        this.name = player.getName();
        this.uuid = player.getUniqueId();
    }

    public void loadData(Player player, SectorsPlugin plugin) {

        if(nbtString == null || this.lastLocation == null || this.gameMode == null) return;

        if(isRedirecting() && nbtString == null || lastLocation == null || getLastLocation() == null) {
            player.kickPlayer(ChatHelper.colored(plugin.getMessagesConfiguration().getPlayerDataNotFoundMessage()));
            return;
        }

        PlayerLoadDataEvent playerLoadDataEvent = new PlayerLoadDataEvent(player,this);
        plugin.getServer().getPluginManager().callEvent(playerLoadDataEvent);

        if(playerLoadDataEvent.isCancelled()) return;

        Location location = SerializeHelper.deserializeLocation(this.lastLocation);
        player.teleport(location);

        player.getInventory().setHeldItemSlot(this.heldSlot);
        player.setGameMode(GameMode.valueOf(this.gameMode));

        NBTTagCompound nbtTagCompound = NbtConverterHelper.convertStringToNBTCompound(this.nbtString);

        if(nbtTagCompound == null) {
            player.kickPlayer(ChatHelper.colored(plugin.getMessagesConfiguration().getPlayerDataNotFoundMessage()));
            return;
        }

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        entityPlayer.a(nbtTagCompound);
    }

    public void saveData(Player player,SectorsPlugin plugin) {
        PlayerSaveDataEvent playerSaveDataEvent = new PlayerSaveDataEvent(player,this);
        plugin.getServer().getPluginManager().callEvent(playerSaveDataEvent);

        if(playerSaveDataEvent.isCancelled()) return;

        this.lastLocation = SerializeHelper.serializeLocation(player.getLocation());

        this.gameMode = player.getGameMode().name();
        this.heldSlot = player.getInventory().getHeldItemSlot();

        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        final NBTTagCompound nbtTagCompound = new NBTTagCompound();

        entityPlayer.b(nbtTagCompound);

        this.nbtString = nbtTagCompound.toString();


    }

    public boolean isRedirecting() {
        return this.redirectTime + 5000L > System.currentTimeMillis();
    }

    public void setRedirecting(final boolean redirecting) {
        this.redirectTime = redirecting ? System.currentTimeMillis() : 0L;
    }

    public int getHeldSlot() {
        return heldSlot;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getName() {
        return name;
    }


    public String getNbtString() {
        return nbtString;
    }

    public String getLastLocation() {
        return lastLocation;
    }


    public UUID getUuid() {
        return uuid;
    }

}

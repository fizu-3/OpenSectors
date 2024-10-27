package fi.fcode.listeners;

import fi.fcode.SectorsPlugin;
import fi.fcode.data.User;
import fi.fcode.helpers.ChatHelper;
import fi.fcode.helpers.NbtConverterHelper;
import fi.fcode.helpers.PlayerTransferHelper;
import fi.fcode.helpers.SerializeHelper;
import fi.fcode.sector.Sector;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

public class PlayerJoinListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        Optional<User> userOptional = SectorsPlugin.getInstance().getUserCache().getUser(player.getUniqueId());

        if(!userOptional.isPresent()) {
            SectorsPlugin.getInstance().getUserCache().createUser(player);
        }

        userOptional.ifPresent(user -> {

            if(player.isDead()) {
                Optional<Sector> sectorOptional = SectorsPlugin.getInstance().getSectorCache().getSpawnSector();
                if(sectorOptional.isEmpty()) {
                    player.kickPlayer(ChatHelper.colored("&cBrak wolnego servera"));
                    return;
                }

                SectorsPlugin.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(SectorsPlugin.getInstance(), () -> {
                    player.spigot().respawn();
                    PlayerTransferHelper.connect(userOptional.get(),sectorOptional.get());
                }, 2L);
                return;
            }
            Location location = SerializeHelper.deserializeLocation(user.getLastLocation());

            player.teleport(location);

            player.getInventory().setHeldItemSlot(user.getHeldSlot());

            player.setGameMode(GameMode.getByValue(user.getGamemode()));

            final NBTTagCompound nbtTagCompound = NbtConverterHelper.convertStringToNBTCompound(user.getNbtString());

            if(nbtTagCompound == null) {
                player.kickPlayer("Synchronizacja jak na arivi czyli rozjebana essa wypierdalaj");
                return;
            }

            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

            entityPlayer.a(nbtTagCompound);

            player.sendMessage(ChatHelper.colored("&aOdebrano zajebisty pakiet synchronizacji itsow ziutasa"));
        });

        player.sendTitle("", ChatHelper.colored("&7Pomyslnie &a&npolaczono &7z sektorem &a&n" + SectorsPlugin.getInstance().getSectorCache().getCurrentSectorID()));
    }

}

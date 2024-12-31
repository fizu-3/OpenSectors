package fi.fcode.listener;

import fi.fcode.SectorsPlugin;
import fi.fcode.data.User;
import fi.fcode.helper.ChatHelper;
import fi.fcode.helper.NbtConverterHelper;
import fi.fcode.helper.PlayerTransferHelper;
import fi.fcode.helper.SerializeHelper;
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

        SectorsPlugin.getInstance().getUserCache().findUserByUUID(player.getUniqueId()).ifPresent(user -> {
            if(player.isDead()) {
                Optional<Sector> sectorOptional = SectorsPlugin.getInstance().getSectorCache().getSpawnSector();

                if(sectorOptional.isEmpty()) {
                    player.kickPlayer(ChatHelper.colored("&cBrak wolnego servera"));
                    return;
                }

                SectorsPlugin.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(SectorsPlugin.getInstance(), () -> {
                    player.spigot().respawn();
                    PlayerTransferHelper.connect(user,sectorOptional.get());
                }, 2L);
                return;
            }

            Location location = SerializeHelper.deserializeLocation(user.getLastLocation());

            player.teleport(location);

            player.getInventory().setHeldItemSlot(user.getHeldSlot());

            player.setGameMode(GameMode.getByValue(user.getGameMode()));

            NBTTagCompound nbtTagCompound = NbtConverterHelper.convertStringToNBTCompound(user.getNbtString());

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

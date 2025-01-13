package net.fcode.runnable;
import net.fcode.SectorsPlugin;
import net.fcode.packet.impl.SectorInformationUpdatePacket;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.scheduler.BukkitRunnable;

public class SectorInformationUpdateRunnable extends BukkitRunnable {

    private final SectorsPlugin plugin;

    public SectorInformationUpdateRunnable(SectorsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.plugin.getMessengerService().publish("sectors",new SectorInformationUpdatePacket(this.plugin.getServer().getOnlinePlayers().size(),MinecraftServer.getServer().recentTps[0]));
    }
}

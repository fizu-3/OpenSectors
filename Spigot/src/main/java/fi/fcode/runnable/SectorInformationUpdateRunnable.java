package fi.fcode.runnable;

import fi.fcode.SectorsPlugin;
import fi.fcode.packet.impl.SectorInformationUpdatePacket;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class SectorInformationUpdateRunnable implements Runnable {
    @Override
    public void run() {
        double tps = 0.0;
        for(double d : MinecraftServer.getServer().recentTps){
            tps = d;
        }

        SectorsPlugin.getInstance().getMessengerCache().publish("sectors",new SectorInformationUpdatePacket(SectorsPlugin.getInstance().getServer().getOnlinePlayers().size(),tps));
    }
}

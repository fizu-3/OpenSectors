package fi.fcode.handlers;

import fi.fcode.SectorsPlugin;
import fi.fcode.packet.impl.SectorConfigurationResponsePacket;
import fi.fcode.redis.PacketListener;
import fi.fcode.runnable.ActionbarNotificationRunnable;
import fi.fcode.runnable.SectorBorderUpdateRunnable;
import fi.fcode.runnable.SectorInformationUpdateRunnable;

public class PacketSectorConfigurationResponseListener extends PacketListener<SectorConfigurationResponsePacket> {
    public PacketSectorConfigurationResponseListener() {
        super(SectorConfigurationResponsePacket.class);
    }

    @Override
    public void handle(SectorConfigurationResponsePacket packet) {
        SectorsPlugin.getInstance().getSectorCache().loadSectors(packet.getSectors());


        SectorsPlugin.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(SectorsPlugin.getInstance(), new SectorInformationUpdateRunnable(), 0, 30L);
        SectorsPlugin.getInstance().getServer().getScheduler().runTaskTimer(SectorsPlugin.getInstance(),new ActionbarNotificationRunnable(),1L,1L);
        SectorsPlugin.getInstance().getServer().getScheduler().runTaskTimer(SectorsPlugin.getInstance(),new SectorBorderUpdateRunnable(),20L,20L);
        SectorsPlugin.getInstance().registerSector();
        SectorsPlugin.getInstance().getLogger().info("Zaladowano pomyslnie!!!");
    }
}

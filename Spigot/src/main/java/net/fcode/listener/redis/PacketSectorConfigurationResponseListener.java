package net.fcode.listener.redis;

import net.fcode.SectorsPlugin;
import net.fcode.packet.impl.SectorConfigurationResponsePacket;
import net.fcode.redis.PacketListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketSectorConfigurationResponseListener extends PacketListener<SectorConfigurationResponsePacket> {

    private final SectorsPlugin plugin;

    public PacketSectorConfigurationResponseListener(SectorsPlugin plugin) {
        super(SectorConfigurationResponsePacket.class);

        this.plugin = plugin;
    }

    @Override
    public void handle(SectorConfigurationResponsePacket packet) {
        this.plugin.getSectorCache().loadSectors(packet.getSectors());
        this.plugin.registerSector();

        Logger.getAnonymousLogger().log(Level.FINE, this.plugin.getSectorCache().getSectorMap().size() + " sectors loaded");
    }
}

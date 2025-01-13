package net.fcode.listener.redis;

import net.fcode.packet.impl.SectorInformationUpdatePacket;
import net.fcode.redis.PacketListener;
import net.fcode.sector.SectorCache;

public class PacketSectorInformationUpdateListener extends PacketListener<SectorInformationUpdatePacket> {

    private final SectorCache sectorCache;

    public PacketSectorInformationUpdateListener(SectorCache sectorCache) {
        super(SectorInformationUpdatePacket.class);

        this.sectorCache = sectorCache;
    }

    @Override
    public void handle(SectorInformationUpdatePacket packet) {
        this.sectorCache.getSector(packet.getSender()).ifPresent(sector -> {
            sector.setLastUpdate(System.currentTimeMillis());
            sector.setPlayers(packet.getPlayers());
            sector.setTps(packet.getTps());
        });
    }
}

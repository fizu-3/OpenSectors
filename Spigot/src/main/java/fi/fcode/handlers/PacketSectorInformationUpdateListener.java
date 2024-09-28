package fi.fcode.handlers;

import fi.fcode.SectorsPlugin;
import fi.fcode.packet.impl.SectorInformationUpdatePacket;
import fi.fcode.redis.PacketListener;

public class PacketSectorInformationUpdateListener extends PacketListener<SectorInformationUpdatePacket> {
    public PacketSectorInformationUpdateListener() {
        super(SectorInformationUpdatePacket.class);
    }

    @Override
    public void handle(SectorInformationUpdatePacket packet) {

        SectorsPlugin.getInstance().getSectorCache().getSector(packet.getSender()).ifPresent(sector -> {
            sector.setLastUpdate(System.currentTimeMillis());
            sector.setPlayers(packet.getPlayers());
            sector.setTps(packet.getTps());
        });
    }
}

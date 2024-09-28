package fi.fcode.packet.impl;

import fi.fcode.packet.Packet;
import fi.fcode.sector.Sector;

public class SectorConfigurationResponsePacket extends Packet {

    private final Sector[] sectors;

    public SectorConfigurationResponsePacket(Sector[] sectors) {
        this.sectors = sectors;
    }

    public Sector[] getSectors() {
        return sectors;
    }
}

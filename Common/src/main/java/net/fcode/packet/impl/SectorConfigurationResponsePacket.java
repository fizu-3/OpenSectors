package net.fcode.packet.impl;

import net.fcode.packet.Packet;
import net.fcode.sector.Sector;

public class SectorConfigurationResponsePacket extends Packet {

    private final Sector[] sectors;

    public SectorConfigurationResponsePacket(Sector[] sectors) {
        this.sectors = sectors;
    }

    public Sector[] getSectors() {
        return sectors;
    }
}

package fi.fcode.packet.impl;

import fi.fcode.packet.Packet;

public class PlayerConnectSectorPacket extends Packet {
    private String name;
    private String sectorName;

    public PlayerConnectSectorPacket(String name, String sectorName) {
        this.name = name;
        this.sectorName = sectorName;
    }

    public String getName() {
        return name;
    }

    public String getSectorName() {
        return sectorName;
    }
}

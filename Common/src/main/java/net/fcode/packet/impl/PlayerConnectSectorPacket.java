package net.fcode.packet.impl;

import net.fcode.packet.Packet;

public class PlayerConnectSectorPacket extends Packet {
    private final String name, sectorName;

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

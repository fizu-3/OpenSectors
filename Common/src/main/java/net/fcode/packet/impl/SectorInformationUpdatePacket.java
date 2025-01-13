package net.fcode.packet.impl;

import net.fcode.packet.Packet;

public class SectorInformationUpdatePacket extends Packet {

    private final int players;
    private final double tps;

    public SectorInformationUpdatePacket(int players, double tps) {
        this.players = players;
        this.tps = tps;
    }

    public int getPlayers() {
        return players;
    }

    public double getTps() {
        return tps;
    }
}

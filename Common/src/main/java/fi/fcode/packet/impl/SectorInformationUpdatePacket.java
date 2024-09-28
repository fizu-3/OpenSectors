package fi.fcode.packet.impl;

import fi.fcode.packet.Packet;

public class SectorInformationUpdatePacket extends Packet {


    private int players;
    private double tps;

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

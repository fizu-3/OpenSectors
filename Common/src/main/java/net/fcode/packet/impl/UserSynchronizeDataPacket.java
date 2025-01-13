package net.fcode.packet.impl;

import net.fcode.packet.Packet;

public class UserSynchronizeDataPacket extends Packet {

    private final String userJson,sectorName;

    public UserSynchronizeDataPacket(String userJson, String sectorName) {
        this.userJson = userJson;
        this.sectorName = sectorName;
    }


    public String getSectorName() {
        return sectorName;
    }

    public String getUserJson() {
        return userJson;
    }
}

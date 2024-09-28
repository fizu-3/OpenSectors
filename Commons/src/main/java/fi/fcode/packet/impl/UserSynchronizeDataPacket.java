package fi.fcode.packet.impl;

import fi.fcode.packet.Packet;

public class UserSynchronizeDataPacket extends Packet {

    private String userJson,sectorName;

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

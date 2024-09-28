package fi.fcode.packet;

import fi.fcode.MessengerCache;

import java.io.Serializable;

public class Packet implements Serializable {


    private final String sender;

    public Packet() {
        this.sender = MessengerCache.getInstance().getPacketSender();
    }

    public String getSender() {
        return sender;
    }
}
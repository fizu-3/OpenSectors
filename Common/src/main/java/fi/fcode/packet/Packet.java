package fi.fcode.packet;

import fi.fcode.MessengerService;

import java.io.Serializable;

public class Packet implements Serializable {


    private final String sender;

    public Packet() {
        this.sender = MessengerService.getInstance().getPacketSender();
    }

    public String getSender() {
        return sender;
    }
}
package net.fcode.listener;

import net.fcode.MessengerService;
import net.fcode.ProxyPlugin;
import net.fcode.packet.impl.SectorConfigurationRequestPacket;
import net.fcode.packet.impl.SectorConfigurationResponsePacket;
import net.fcode.redis.PacketListener;
import net.fcode.sector.Sector;
import net.fcode.sector.SectorCache;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketSectorConfigurationRequestListener extends PacketListener<SectorConfigurationRequestPacket> {

    private final SectorCache sectorCache;

    private final MessengerService messengerService;


    public PacketSectorConfigurationRequestListener(SectorCache sectorCache, MessengerService messengerService) {
        super(SectorConfigurationRequestPacket.class);

        this.sectorCache = sectorCache;
        this.messengerService = messengerService;
    }

    @Override
    public void handle(SectorConfigurationRequestPacket packet) {
        if(this.sectorCache.getSector(packet.getSender()).isEmpty()) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"No sector found for "+ packet.getSender());
            return;
        }

        this.messengerService.publish(packet.getSender(),new SectorConfigurationResponsePacket(this.sectorCache.getSectorMap().values().toArray(new Sector[0])));
    }
}

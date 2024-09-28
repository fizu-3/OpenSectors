package fi.fcode.listeners;

import fi.fcode.ProxyPlugin;
import fi.fcode.packet.impl.SectorConfigurationRequestPacket;
import fi.fcode.packet.impl.SectorConfigurationResponsePacket;
import fi.fcode.redis.PacketListener;
import fi.fcode.sector.Sector;

public class PacketSectorConfigurationRequestListener extends PacketListener<SectorConfigurationRequestPacket> {
    public PacketSectorConfigurationRequestListener() {
        super(SectorConfigurationRequestPacket.class);
    }

    @Override
    public void handle(SectorConfigurationRequestPacket packet) {
        if(!ProxyPlugin.getInstance().getSectorCache().getSector(packet.getSender()).isPresent()) {
            ProxyPlugin.getInstance().getLogger().info("Brak zaladowanego takiego serwera");
            return;
        }
        ProxyPlugin.getInstance().getLogger().info("Otrzmyalem prosbe o konfiguracje serwera od " + packet.getSender());
        ProxyPlugin.getInstance().getMessengerCache().publish(packet.getSender(),new SectorConfigurationResponsePacket(ProxyPlugin.getInstance().getSectorCache().getSectorMap().values().toArray(new Sector[0])));
    }
}

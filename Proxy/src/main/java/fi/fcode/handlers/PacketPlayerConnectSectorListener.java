package fi.fcode.handlers;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import fi.fcode.ProxyPlugin;
import fi.fcode.packet.impl.PlayerConnectSectorPacket;
import fi.fcode.redis.PacketListener;

import java.util.Optional;

public class PacketPlayerConnectSectorListener extends PacketListener<PlayerConnectSectorPacket> {
    public PacketPlayerConnectSectorListener() {
        super(PlayerConnectSectorPacket.class);
    }

    @Override
    public void handle(PlayerConnectSectorPacket packet) {
        Optional<Player> player = ProxyPlugin.getInstance().getServer().getPlayer(packet.getName());
        Optional<RegisteredServer> serverInfo = ProxyPlugin.getInstance().getServer().getServer(packet.getSectorName());

        if(player.isPresent() && serverInfo.isPresent()) {
            player.get().createConnectionRequest(serverInfo.get()).connect();
        }
    }
}

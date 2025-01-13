package net.fcode.listener;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.fcode.ProxyPlugin;
import net.fcode.packet.impl.PlayerConnectSectorPacket;
import net.fcode.redis.PacketListener;

import java.util.Optional;

public class PacketPlayerConnectSectorListener extends PacketListener<PlayerConnectSectorPacket> {

    private final ProxyPlugin plugin;

    public PacketPlayerConnectSectorListener(ProxyPlugin plugin) {
        super(PlayerConnectSectorPacket.class);

        this.plugin = plugin;
    }

    @Override
    public void handle(PlayerConnectSectorPacket packet) {
        Optional<Player> playerOptional = this.plugin.getServer().getPlayer(packet.getName());
        Optional<RegisteredServer> serverInfo = this.plugin.getServer().getServer(packet.getSectorName());

        playerOptional.ifPresent(player -> {

            if(serverInfo.isEmpty()) return;

            player.createConnectionRequest(serverInfo.get()).connect();
        });
    }
}

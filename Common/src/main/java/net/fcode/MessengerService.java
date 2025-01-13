package net.fcode;

import net.fcode.packet.Packet;
import net.fcode.redis.PacketListener;
import net.fcode.redis.codec.FSTCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.nustaq.serialization.FSTConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MessengerService {

    private static MessengerService instance;

    private final List<String> subscribedChannels;
    private final FSTConfiguration fstConfiguration;
    private final StatefulRedisPubSubConnection<String, Packet> pubSubConnection;
    private final StatefulRedisConnection<String, Packet> connection;

    private String packetSender;

    public MessengerService(String address, int port, String password) {
        instance = this;

        this.subscribedChannels = new ArrayList<>();

        this.fstConfiguration = FSTConfiguration.createDefaultConfiguration();

        RedisClient redisClient = RedisClient.create(RedisURI.builder()
                .withHost(address)
                .withPort(port)
                .withPassword(password)
                .build());

        FSTCodec fstCodec = new FSTCodec();

        this.pubSubConnection = redisClient.connectPubSub(fstCodec);
        this.connection = redisClient.connect(fstCodec);


    }

    public void shutdown() {
        this.pubSubConnection.sync().unsubscribe(getSubscribedChannels().toArray(new String[0]));
        this.pubSubConnection.close();
        this.connection.close();
    }

    public void publish(String channel, Packet packet) {
        this.connection.sync().publish(channel, packet);
    }

    public void subscribe(String channel, PacketListener<? extends Packet> listener) {
        this.pubSubConnection.addListener(listener);

        if (this.subscribedChannels.contains(channel)) return;

        this.pubSubConnection.sync().subscribe(channel);
        this.subscribedChannels.add(channel);
    }

    public List<String> getSubscribedChannels() {
        return subscribedChannels;
    }

    public String getPacketSender() {
        return packetSender;
    }

    public FSTConfiguration getFstConfiguration() {
        return fstConfiguration;
    }

    public void setPacketSender(String packetSender) {
        this.packetSender = packetSender;
    }

    public static MessengerService getInstance() {
        return instance;
    }
}
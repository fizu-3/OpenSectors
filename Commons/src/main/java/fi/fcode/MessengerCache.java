package fi.fcode;

import fi.fcode.packet.Packet;
import fi.fcode.redis.PacketListener;
import fi.fcode.redis.codec.FSTCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.nustaq.serialization.FSTConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MessengerCache {

    private List<String> subscribedChannels = new ArrayList<>();
    private static MessengerCache instance;
    private FSTConfiguration fstConfiguration;
    private RedisClient redisClient;
    private StatefulRedisPubSubConnection<String, Packet> pubSubConnection;
    private StatefulRedisConnection<String, String> databaseConnect;
    private StatefulRedisConnection<String, Packet> connection;
    private String packetSender;

    public MessengerCache(String address, int port, String password) {
        instance = this;
        this.fstConfiguration = FSTConfiguration.createDefaultConfiguration();
        this.redisClient = RedisClient.create(RedisURI.builder()
                .withHost(address)
                .withPort(port)
                .withPassword(password)
                .build());

        FSTCodec fstCodec = new FSTCodec();
        this.pubSubConnection = this.redisClient.connectPubSub(fstCodec);
        this.databaseConnect = this.redisClient.connect();
        this.connection = this.redisClient.connect(fstCodec);


    }


    public void shutdown() {
        this.getPubSubConnection().sync().unsubscribe(getSubscribedChannels().toArray(new String[0]));
        this.pubSubConnection.close();
        this.databaseConnect.close();
        this.connection.close();
    }

    public void publish(String channel, Packet packet) {
        getConnection().sync().publish(channel, packet);
    }


    public void subscribe(String channel, PacketListener<? extends Packet> listener) {
        getPubSubConnection().addListener(listener);

        if (!subscribedChannels.contains(channel)) {
            getPubSubConnection().sync().subscribe(channel);
            subscribedChannels.add(channel);
        }
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

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public StatefulRedisConnection<String, Packet> getConnection() {
        return connection;
    }

    public StatefulRedisPubSubConnection<String, Packet> getPubSubConnection() {
        return pubSubConnection;
    }

    public StatefulRedisConnection<String, String> getDatabaseConnect() {
        return databaseConnect;
    }

    public void setPacketSender(String packetSender) {
        this.packetSender = packetSender;
    }

    public static MessengerCache getInstance() {
        return instance;
    }
}
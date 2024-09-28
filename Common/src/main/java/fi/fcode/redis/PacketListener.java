package fi.fcode.redis;

import fi.fcode.packet.Packet;
import io.lettuce.core.pubsub.RedisPubSubListener;

public abstract class PacketListener<T extends Packet> implements RedisPubSubListener<String, Packet> {
        private Class<T> packetClass;

        public PacketListener(Class<T> packetClass) {
                this.packetClass = packetClass;
        }

        public abstract void handle(T packet);

        @Override
        public void message(String channel, Packet packet) {
                if (packet.getClass().isAssignableFrom(packetClass)) {
                        this.handle((T) packet);
                }
        }


        @Override
        public void message(String pattern, String channel, Packet message) {

        }

        @Override
        public void subscribed(String channel, long count) {

        }

        @Override
        public void psubscribed(String pattern, long count) {

        }

        @Override
        public void unsubscribed(String channel, long count) {

        }

        @Override
        public void punsubscribed(String pattern, long count) {

        }

}
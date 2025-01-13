package net.fcode.listener.redis;

import net.fcode.MessengerService;
import net.fcode.user.User;
import net.fcode.user.cache.UserCache;
import net.fcode.helper.GsonHelper;
import net.fcode.packet.impl.PlayerConnectSectorPacket;
import net.fcode.packet.impl.UserSynchronizeDataPacket;
import net.fcode.redis.PacketListener;

public class PacketUserSynchronizeDataListener extends PacketListener<UserSynchronizeDataPacket> {

    private final MessengerService messengerService;

    private final UserCache userCache;


    public PacketUserSynchronizeDataListener(MessengerService messengerService,UserCache userCache) {
        super(UserSynchronizeDataPacket.class);

        this.messengerService = messengerService;
        this.userCache = userCache;
    }

    @Override
    public void handle(UserSynchronizeDataPacket packet) {
        User user = GsonHelper.fromJson(packet.getUserJson(), User.class);

        this.userCache.getUsers().put(user.getUuid(), user);

        this.messengerService.publish("proxy", new PlayerConnectSectorPacket(user.getName(), packet.getSectorName()));
    }
}

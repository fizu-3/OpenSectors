package fi.fcode.listener.redis;

import fi.fcode.SectorsPlugin;
import fi.fcode.data.User;
import fi.fcode.helper.GsonHelper;
import fi.fcode.packet.impl.PlayerConnectSectorPacket;
import fi.fcode.packet.impl.UserSynchronizeDataPacket;
import fi.fcode.redis.PacketListener;

public class PacketUserSynchronizeDataListener extends PacketListener<UserSynchronizeDataPacket> {
    public PacketUserSynchronizeDataListener() {
        super(UserSynchronizeDataPacket.class);
    }

    @Override
    public void handle(UserSynchronizeDataPacket packet) {
        User user = GsonHelper.fromJson(packet.getUserJson(), User.class);

        SectorsPlugin.getInstance().getUserCache().getUsers().put(user.getUuid(), user);

        SectorsPlugin.getInstance().getMessengerService().publish("proxy", new PlayerConnectSectorPacket(user.getName(), packet.getSectorName()));
    }
}

package fi.fcode.listeners.redis;

import fi.fcode.SectorsPlugin;
import fi.fcode.data.User;
import fi.fcode.helpers.GsonHelper;
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

        if(SectorsPlugin.getInstance().getUserCache().getUsers().containsKey(user.getUuid())) {
            SectorsPlugin.getInstance().getUserCache().getUsers().replace(user.getUuid(), user);
        } else {
            SectorsPlugin.getInstance().getUserCache().getUsers().put(user.getUuid(), user);
        }
        SectorsPlugin.getInstance().getMessengerCache().publish("proxy", new PlayerConnectSectorPacket(user.getName(), packet.getSectorName()));
    }
}

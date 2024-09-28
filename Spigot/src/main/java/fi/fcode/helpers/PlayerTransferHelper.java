package fi.fcode.helpers;

import fi.fcode.SectorsPlugin;
import fi.fcode.data.User;
import fi.fcode.packet.impl.PlayerConnectSectorPacket;
import fi.fcode.packet.impl.UserSynchronizeDataPacket;
import fi.fcode.sector.Sector;
import org.bukkit.Bukkit;

public class PlayerTransferHelper {


    public static void connect(final User user, final Sector sector) {
        user.saveData(Bukkit.getPlayer(user.getName()));
        SectorsPlugin.getInstance().getMessengerCache().publish(sector.getId(), new UserSynchronizeDataPacket(GsonHelper.toJson(user),sector.getId()));

    }


}


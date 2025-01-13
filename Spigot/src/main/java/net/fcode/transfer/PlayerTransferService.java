package net.fcode.transfer;

import net.fcode.MessengerService;
import net.fcode.SectorsPlugin;
import net.fcode.event.PlayerSectorChangeEvent;
import net.fcode.user.User;
import net.fcode.helper.GsonHelper;
import net.fcode.packet.impl.UserSynchronizeDataPacket;
import net.fcode.sector.Sector;
import org.bukkit.entity.Player;

public class PlayerTransferService {

    private final SectorsPlugin plugin;

    public PlayerTransferService(SectorsPlugin plugin) {
        this.plugin = plugin;
    }

    public void connect(final Player player, final User user, final Sector sector) {
        PlayerSectorChangeEvent sectorChangeEvent = new PlayerSectorChangeEvent(player,this.plugin.getSectorCache().getCurrentSector(),sector);
        this.plugin.getServer().getPluginManager().callEvent(sectorChangeEvent);

        if(sectorChangeEvent.isCancelled()) return;

        if(player.isInsideVehicle()) {
            player.leaveVehicle();
        }

        user.saveData(player,this.plugin);

        this.plugin.getMessengerService().publish(sector.getId(), new UserSynchronizeDataPacket(GsonHelper.toJson(user),sector.getId()));

    }

}


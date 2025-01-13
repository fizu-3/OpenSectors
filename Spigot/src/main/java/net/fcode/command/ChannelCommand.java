package net.fcode.command;

import net.fcode.configuration.impl.MessagesConfiguration;
import net.fcode.transfer.PlayerTransferService;
import net.fcode.user.cache.UserCache;
import net.fcode.inventory.ChannelInventory;
import net.fcode.sector.SectorCache;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChannelCommand implements CommandExecutor {

    private final UserCache userCache;

    private final SectorCache sectorCache;

    private final PlayerTransferService transferService;

    private final MessagesConfiguration messagesConfiguration;

    public ChannelCommand(UserCache userCache, SectorCache sectorCache, PlayerTransferService transferService, MessagesConfiguration messagesConfiguration) {
        this.userCache = userCache;
        this.sectorCache = sectorCache;
        this.transferService = transferService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;

        ChannelInventory.openInventory(player,this.sectorCache,this.userCache,this.transferService,this.messagesConfiguration);
        return false;
    }
}

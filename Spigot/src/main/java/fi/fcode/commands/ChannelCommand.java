package fi.fcode.commands;

import fi.fcode.gui.ChannelGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.nio.channels.Channel;

public class ChannelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        ChannelGui.openGui(player);
        return false;
    }
}

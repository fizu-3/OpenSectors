package net.fcode.event;

import net.fcode.sector.Sector;
import net.fcode.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSaveDataEvent extends Event {

    private boolean cancelled;

    private Player player;

    private User user;

    public PlayerSaveDataEvent(Player player,User user) {
        this.cancelled = false;
        this.player = player;
        this.user = user;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public User getUser() {
        return user;
    }
}

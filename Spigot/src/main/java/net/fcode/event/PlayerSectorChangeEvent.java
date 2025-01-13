package net.fcode.event;

import net.fcode.sector.Sector;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSectorChangeEvent extends Event {

    private boolean cancelled;

    private Sector currentSector,newSector;

    private Player player;

    public PlayerSectorChangeEvent(Player player, Sector currentSector, Sector newSector) {
        this.player = player;
        this.currentSector = currentSector;
        this.newSector = newSector;
        this.cancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Sector getCurrentSector() {
        return currentSector;
    }

    public Sector getNewSector() {
        return newSector;
    }

    public Player getPlayer() {
        return player;
    }
}

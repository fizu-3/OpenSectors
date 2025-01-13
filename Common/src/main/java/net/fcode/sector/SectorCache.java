package net.fcode.sector;

import org.bukkit.Location;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SectorCache {

    private Map<String,Sector> sectorMap = new ConcurrentHashMap<>();
    private String currentSector;

    public void createSector(String id,Sector sector) {
        this.sectorMap.put(id,sector);
    }

    public void loadSectors(Sector[] sectors) {
        for(Sector sector : sectors) {
            this.sectorMap.put(sector.getId(),sector);
        }
    }

    public Optional<Sector> getSector(String id) {
        return Optional.ofNullable(this.sectorMap.get(id));
    }

    public Optional<Sector> getSectorByLocation(Location location) {
        return this.sectorMap.values().stream().filter(sector -> isIn(location,sector)).filter(
                sector -> !sector.equals(getCurrentSector()) && !sector.getSectorType().equals(SectorType.SPAWN) || !getCurrentSector().getSectorType().equals(SectorType.SPAWN)).findFirst();
    }

    public Optional<Sector> getSpawnSector() {
        return this.sectorMap.values().stream().filter(sector -> sector.getSectorType().equals(SectorType.SPAWN)).filter(Sector::isOnline).findFirst();
    }

    public static boolean isIn(final Location location, final Sector sector) {
        return location.getBlockX() > sector.getMinX() && location.getBlockX() < sector.getMaxX() && location.getBlockZ() > sector.getMinZ() && location.getBlockZ() < sector.getMaxZ();
    }

    public double distance(Location location) {
        Sector sector = getCurrentSector();
        return Math.min(
                Math.min(Math.abs(sector.getMinX() - location.getX()),
                        Math.abs(sector.getMaxX() - location.getX())),
                Math.min(Math.abs(sector.getMinZ() - location.getZ()),
                        Math.abs(sector.getMaxZ() - location.getZ()))) + 1.0;
    }

    public void setCurrentSector(String currentSector) {
        this.currentSector = currentSector;
    }

    public String getCurrentSectorID() {
        return currentSector;
    }

    public Sector getCurrentSector() {
        return this.sectorMap.get(currentSector);
    }

    public Map<String, Sector> getSectorMap() {
        return sectorMap;
    }
}

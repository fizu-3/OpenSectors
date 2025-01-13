package net.fcode.sector;

import java.io.Serializable;

public class Sector implements Serializable {
    private final String id;

    private final SectorType sectorType;

    private final int minX,maxX,minZ,maxZ,centerX,centerZ;

    private int players;

    private long lastUpdate;

    private double tps;

    public Sector(String id,SectorType sectorType,int minX,int maxX,int minZ,int maxZ) {
        this.id = id;
        this.sectorType = sectorType;
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.centerX = (this.minX + this.maxX) / 2;
        this.centerZ = (this.minZ + this.maxZ) / 2;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterZ() {
        return centerZ;
    }

    public String getId() {
        return id;
    }

    public SectorType getSectorType() {
        return sectorType;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public double getTps() {
        return tps;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public boolean isOnline() {
        return this.lastUpdate + 2500L > System.currentTimeMillis();
    }

    public void setTps(double tps) {
        this.tps = tps;
    }
}

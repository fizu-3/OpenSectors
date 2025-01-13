package net.fcode.configuration.wrapper;

import net.fcode.sector.SectorType;

public class SectorWrapper {

    private String name;

    private SectorType sectorType;

    private int minX,maxX,minZ,maxZ;

    public SectorWrapper(String name, SectorType sectorType, int minX, int maxX, int minZ, int maxZ) {
        this.name = name;
        this.sectorType = sectorType;
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public String getName() {
        return name;
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
}

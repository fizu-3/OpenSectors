package net.fcode.configuration.impl;

import net.fcode.configuration.Configuration;

public class SectorConfiguration implements Configuration {

    private final String currentSector;

    public SectorConfiguration() {
        this.currentSector = "s1";
    }

    public String getCurrentSector() {
        return currentSector;
    }

    @Override
    public String getFileName() {
        return "config.json";
    }
}

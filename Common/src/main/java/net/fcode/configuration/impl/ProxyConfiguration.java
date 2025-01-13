package net.fcode.configuration.impl;

import net.fcode.configuration.Configuration;
import net.fcode.configuration.wrapper.SectorWrapper;
import net.fcode.sector.Sector;
import net.fcode.sector.SectorType;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProxyConfiguration implements Configuration {

    private final Map<String, SectorWrapper> sectorMap;

    public ProxyConfiguration() {
        this.sectorMap = new LinkedHashMap<>() {{
            this.put("s1",new SectorWrapper("s1", SectorType.NORMAL,-100,1000,100,1000));
            this.put("spawn_1",new SectorWrapper("spawn_1", SectorType.SPAWN,-100,100,-100,100));
        }};

    }

    public Map<String, SectorWrapper> getSectorMap() {
        return sectorMap;
    }

    public String getFileName() {
        return "sectors.json";
    }
}

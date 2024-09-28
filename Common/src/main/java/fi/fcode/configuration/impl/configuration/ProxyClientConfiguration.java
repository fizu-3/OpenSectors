package fi.fcode.configuration.impl.configuration;

import fi.fcode.configuration.ConfigurationData;
import fi.fcode.sector.SectorType;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProxyClientConfiguration extends ConfigurationData {
    public RedisWrapper redisWrapper = new RedisWrapper();
    public Map<String, SectorWrapper> sectorMap = new LinkedHashMap<String, SectorWrapper>() {{
        for (int index = 0; index < 3; index++) {
            this.put("spawn_" + (index + 1), createSectorWrapper(SectorType.SPAWN, -100, 100, -100, 100));
        }

        this.put("s1", createSectorWrapper(SectorType.NORMAL, -100, 1000, 100, 1000));
        this.put("w1", createSectorWrapper(SectorType.NORMAL, -1000, -100, -100, 1000));
        this.put("e1", createSectorWrapper(SectorType.NORMAL, 100, 1000, -1000, 100));
        this.put("n1", createSectorWrapper(SectorType.NORMAL, -1000, 100, -1000, -100));
    }};

    private SectorWrapper createSectorWrapper(SectorType type, int minX, int maxX, int minZ,
                                                                   int maxZ) {
        SectorWrapper wrapper = new ProxyClientConfiguration.SectorWrapper();
        wrapper.type = type;
        wrapper.minX = minX;
        wrapper.maxX = maxX;
        wrapper.minZ = minZ;
        wrapper.maxZ = maxZ;
        return wrapper;
    }

    public class RedisWrapper {
        public String redisHost = "localhost";
        public int redisPort = 1337;
        public String redisPassword = "root";
    }
    public final class SectorWrapper {

        public SectorType type;
        public int minX, maxX;
        public int minZ, maxZ;
    }

}

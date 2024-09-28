package fi.fcode.configuration.impl.configuration;

import fi.fcode.configuration.ConfigurationData;
import fi.fcode.sector.SectorType;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientConfiguration extends ConfigurationData {

    public String currentSector = "s1";
    public RedisDatabaseWrapper redis = new RedisDatabaseWrapper();


    public class RedisDatabaseWrapper {
        public String redisHost = "localhost";
        public int redisPort = 1337;
        public String redisPassword = "root";
    }

}

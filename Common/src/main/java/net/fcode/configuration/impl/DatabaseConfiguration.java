package net.fcode.configuration.impl;

import net.fcode.configuration.Configuration;

public class DatabaseConfiguration implements Configuration {

    private final String redisHost,redisPassword;

    private final int redisPort;

    public DatabaseConfiguration() {
        this.redisHost = "localhost";
        this.redisPort = 6379;
        this.redisPassword = "root";

    }

    public String getRedisHost() {
        return redisHost;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public int getRedisPort() {
        return redisPort;
    }

    @Override
    public String getFileName() {
        return "database.json";
    }
}

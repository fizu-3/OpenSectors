package net.fcode.configuration.service;

import net.fcode.configuration.Configuration;
import net.fcode.helper.GsonHelper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ConfigurationService {

    private String directory;

    public ConfigurationService(String directory) {
        this.directory = directory;
    }

    public <T> T loadConfiguration(Configuration configuration) {
        try {
            String fileName = configuration.getFileName();

            File file = new File(this.directory, fileName);

            if(file.exists())
                return (T) GsonHelper.fromJson(Files.readString(file.toPath()), configuration.getClass());

            return saveConfiguration(configuration);
        } catch (IOException e) {
            throw new RuntimeException("Loading configuration failed",e);
        }
    }

    public <T> T saveConfiguration(Configuration configuration) {
        try {

            String fileName = configuration.getFileName();

            File fileDirectory = new File(this.directory);

            if (!fileDirectory.exists())
                fileDirectory.mkdirs();

            File file = new File(fileDirectory,fileName);
            String json = GsonHelper.toJson(configuration);

            try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
                writer.println(json);
            }

            return (T) configuration;
        } catch (IOException e) {
            throw new RuntimeException("Saving configuration failed",e);
        }
    }
}

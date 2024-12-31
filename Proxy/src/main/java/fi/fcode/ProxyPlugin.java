package fi.fcode;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import fi.fcode.configuration.ConfigurationHelper;
import fi.fcode.configuration.impl.configuration.ProxyClientConfiguration;
import fi.fcode.listener.PacketPlayerConnectSectorListener;
import fi.fcode.listener.PacketSectorConfigurationRequestListener;
import fi.fcode.sector.SectorCache;
import org.slf4j.Logger;

import java.io.File;

@Plugin(
        id = "proxy",
        name = "Proxy",
        version = "1.0-SNAPSHOT"
)
public class ProxyPlugin {
    private final ProxyServer server;
    private final Logger logger;
    private MessengerService messengerService;
    private SectorCache sectorCache;
    private static ProxyPlugin instance;
    @Inject
    public ProxyPlugin(ProxyServer server, Logger logger) {
        instance = this;
        this.server = server;
        this.logger = logger;

        logger.info("SectorsProxy maded on Velocity");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        ProxyClientConfiguration configuration = ConfigurationHelper.load(new File("config.json"),ProxyClientConfiguration.class);
        this.messengerService = new MessengerService(configuration.redisWrapper.redisHost,configuration.redisWrapper.redisPort,configuration.redisWrapper.redisPassword);
        this.messengerService.setPacketSender("proxy");
        this.messengerService.subscribe("proxy",new PacketSectorConfigurationRequestListener());
        this.messengerService.subscribe("proxy",new PacketPlayerConnectSectorListener());

        this.sectorCache = new SectorCache();
        this.sectorCache.setCurrentSector("proxy");

        configuration.sectorMap.forEach((s, sectorWrapper) -> {
            this.sectorCache.createSector(s,sectorWrapper.type,sectorWrapper.minX,sectorWrapper.maxX,sectorWrapper.minZ,sectorWrapper.maxZ);
        });

    }

    public MessengerService getMessengerCache() {
        return messengerService;
    }

    public SectorCache getSectorCache() {
        return sectorCache;
    }

    public static ProxyPlugin getInstance() {
        return instance;
    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }
}

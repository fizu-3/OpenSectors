package net.fcode;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.fcode.configuration.impl.DatabaseConfiguration;
import net.fcode.configuration.impl.ProxyConfiguration;
import net.fcode.configuration.service.ConfigurationService;
import net.fcode.listener.PacketPlayerConnectSectorListener;
import net.fcode.listener.PacketSectorConfigurationRequestListener;
import net.fcode.sector.Sector;
import net.fcode.sector.SectorCache;
import org.slf4j.Logger;

import java.io.File;


@Plugin(
        id = "sectors-proxy",
        name = "sectors-proxy",
        version = "2.0",
        authors = "fajzu"
)
public class ProxyPlugin {

    private final ProxyServer server;

    private MessengerService messengerService;
    private SectorCache sectorCache;
    private ConfigurationService configurationService;

    @Inject
    public ProxyPlugin(ProxyServer server, Logger logger) {
        this.server = server;

        logger.info("SectorsProxy maded on Velocity");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

        this.configurationService = new ConfigurationService("plugins/sectors-proxy");

        ProxyConfiguration proxyConfiguration = this.configurationService.loadConfiguration(new ProxyConfiguration());
        DatabaseConfiguration databaseConfiguration = this.configurationService.loadConfiguration(new DatabaseConfiguration());

        this.messengerService = new MessengerService(databaseConfiguration.getRedisHost(),databaseConfiguration.getRedisPort(),databaseConfiguration.getRedisPassword());
        this.messengerService.setPacketSender("proxy");

        this.messengerService.subscribe("proxy",new PacketSectorConfigurationRequestListener(this.sectorCache,this.messengerService));
        this.messengerService.subscribe("proxy",new PacketPlayerConnectSectorListener(this));

        this.sectorCache = new SectorCache();
        this.sectorCache.setCurrentSector("proxy");

        proxyConfiguration.getSectorMap().forEach((string, sectorWrapper) -> {
            this.sectorCache.createSector(string,new Sector(sectorWrapper.getName(),sectorWrapper.getSectorType(),sectorWrapper.getMinX(),sectorWrapper.getMaxX(),sectorWrapper.getMinZ(),sectorWrapper.getMaxZ()));
        });


    }

    public ProxyServer getServer() {
        return server;
    }

}

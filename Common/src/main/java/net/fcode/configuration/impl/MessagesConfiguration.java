package net.fcode.configuration.impl;

import net.fcode.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MessagesConfiguration implements Configuration {

    private final String cannotPlaceBlockNearSectorMessage, cannotBreakBlockNearSectorMessage;

    private final String actionbarBorderMessage;

    private final String sectorIsOfflineMessage,noSectorsAvailableMessage;

    private final String playerDataNotFoundMessage,playerDataLoadedMessage,playerAlreadyConnectedMessage;

    private final String spawnSectorNotFoundMessage;

    private final String scoreboardTitle;

    private final List<String> scoreboardLines;

    private final String connectedInfoTitle;
    private final String connectedInfoSubTitle;

    public MessagesConfiguration() {
        this.cannotBreakBlockNearSectorMessage = "&cNie możesz niszczyć bloków przy granicy sektora!";
        this.cannotPlaceBlockNearSectorMessage = "&CNie możesz stawiać bloków przy granicy sektora!";

        this.actionbarBorderMessage = "&7Jesteś blisko sektora &2{DISTANCE}&7m";

        this.sectorIsOfflineMessage = "&cSektor z którym się chcesz aktualnie połączyć jest aktualnie wyłączony!";
        this.noSectorsAvailableMessage = "&cBrak dostepnych sektorów";

        this.playerDataNotFoundMessage = "&cWystąpił problem podczas ładowania danych";
        this.playerDataLoadedMessage = "&aPomyslnie zaladowano twoje dane";
        this.playerAlreadyConnectedMessage = "&cJesteś aktualnie połączony z tym kanałem";

        this.spawnSectorNotFoundMessage = "&cNie odnaleziono dostepnego sektora spawna";

        this.connectedInfoTitle = "&a&lOpenSectors 2.0";
        this.connectedInfoSubTitle = "&7Pomyslnie &a&npolaczono &7z sektorem &a&n{SECTOR}";

        this.scoreboardTitle = "&a&lOpenSectors 2.0";
        this.scoreboardLines = new ArrayList<>();

        this.scoreboardLines.add("");
        this.scoreboardLines.add("&7Polaczono z &a{SECTOR}");
        this.scoreboardLines.add("&7Uzyj: &a/ch &7aby ");
        this.scoreboardLines.add("&7zmienic kanal");
        this.scoreboardLines.add("");
        this.scoreboardLines.add("&7Online: &a{ONLINE}");
        this.scoreboardLines.add("&7TPS: &a{TPS}");
        this.scoreboardLines.add("");

    }

    public String getConnectedInfoSubTitle() {
        return connectedInfoSubTitle;
    }

    public String getConnectedInfoTitle() {
        return connectedInfoTitle;
    }

    public List<String> getScoreboardLines() {
        return scoreboardLines;
    }

    public String getScoreboardTitle() {
        return scoreboardTitle;
    }

    public String getPlayerAlreadyConnectedMessage() {
        return playerAlreadyConnectedMessage;
    }

    public String getPlayerDataLoadedMessage() {
        return playerDataLoadedMessage;
    }

    public String getNoSectorsAvailableMessage() {
        return noSectorsAvailableMessage;
    }

    public String getSpawnSectorNotFoundMessage() {
        return spawnSectorNotFoundMessage;
    }

    public String getCannotPlaceBlockNearSectorMessage() {
        return cannotPlaceBlockNearSectorMessage;
    }

    public String getCannotBreakBlockNearSectorMessage() {
        return cannotBreakBlockNearSectorMessage;
    }

    public String getActionbarBorderMessage() {
        return actionbarBorderMessage;
    }

    public String getSectorIsOfflineMessage() {
        return sectorIsOfflineMessage;
    }

    public String getPlayerDataNotFoundMessage() {
        return playerDataNotFoundMessage;
    }

    @Override
    public String getFileName() {
        return "messages.json";
    }
}

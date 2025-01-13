package net.fcode.scoreboard;

import net.fcode.configuration.impl.MessagesConfiguration;
import net.fcode.helper.ChatHelper;
import net.fcode.scoreboard.api.AssembleAdapter;
import net.fcode.sector.SectorCache;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnScoreboard implements AssembleAdapter {

    private final SectorCache sectorCache;

    private final MessagesConfiguration messagesConfiguration;

    public SpawnScoreboard(SectorCache sectorCache, MessagesConfiguration messagesConfiguration) {
        this.sectorCache = sectorCache;
        this.messagesConfiguration = messagesConfiguration;
    }

    @Override
    public String getTitle(Player player) {
        return ChatHelper.colored(this.messagesConfiguration.getScoreboardTitle());
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        for(String scoreboardLines : messagesConfiguration.getScoreboardLines()) {
            lines.add(ChatHelper.colored(scoreboardLines
                    .replace("{SECTOR}",this.sectorCache.getCurrentSectorID())
                    .replace("{ONLINE}",String.valueOf(this.sectorCache.getCurrentSector().getTps())
                    .replace("{TPS}",String.valueOf(this.sectorCache.getCurrentSector().getTps())))));
        }

        return lines;
    }
}

package fi.fcode.scoreboard;

import fi.fcode.SectorsPlugin;
import fi.fcode.helper.ChatHelper;
import fi.fcode.scoreboard.api.AssembleAdapter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnScoreboard implements AssembleAdapter {
    @Override
    public String getTitle(Player player) {
        return ChatHelper.colored("&a&lSectors");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();
        lines.add("");
        lines.add("&7Polaczono z &a" + SectorsPlugin.getInstance().getSectorCache().getCurrentSectorID());
        lines.add("&7Uzyj: &a/ch &7aby ");
        lines.add("&7zmienic kanal");
        lines.add("");
        lines.add("&7Online: &a" + SectorsPlugin.getInstance().getSectorCache().getCurrentSector().getPlayers());
        lines.add("&7TPS: &a" + Math.round(SectorsPlugin.getInstance().getSectorCache().getCurrentSector().getTps()));
        lines.add("");
        return ChatHelper.colored(lines);
    }
}

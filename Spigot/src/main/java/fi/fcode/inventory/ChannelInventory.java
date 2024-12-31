package fi.fcode.inventory;

import fi.fcode.SectorsPlugin;
import fi.fcode.inventory.api.GuiWindow;
import fi.fcode.inventory.api.builder.ItemBuilder;
import fi.fcode.helper.ChatHelper;
import fi.fcode.helper.CustomHeadHelper;
import fi.fcode.helper.PlayerTransferHelper;
import fi.fcode.sector.Sector;
import fi.fcode.sector.SectorType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ChannelInventory {

    public static void openInventory(Player player) {
        GuiWindow guiWindow = new GuiWindow("&7Kanaly...", 1);

        int i = 0;
        for (Sector sector : SectorsPlugin.getInstance().getSectorCache().getSectorMap().values()) {
            if (sector.getSectorType() == SectorType.SPAWN) continue;

            ItemStack itemStack = (sector.isOnline() ? CustomHeadHelper.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGEyZjljNzYxZmMxMzFkYmViZDA3M2IwYjFkZDdkMWJhZWExOTFjZTlkMzNjNDljM2FjYTk0NDhiMWI2YjY4NCJ9fX0=") : CustomHeadHelper.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWIwZTA3NjMyMmZjOWFmNzk1OTJlYjg1MmNhOGM3YzQ1YmIyYzNjZWFiYzNjMGU4YTZhMWUwNGI0Y2UzZDM0YiJ9fX0="));
            ItemBuilder item = new ItemBuilder(itemStack).setTitle("&7Sektor &a" + sector.getId());

            if (sector.isOnline()) {
                item.addLores(Arrays.asList("", ChatHelper.colored("&7Online: &a" + sector.getPlayers()), ChatHelper.colored("&7TPS: &a" + Math.round(sector.getTps())), ""));
            } else {
                item.addLores(Arrays.asList("", ChatHelper.colored("&cSektor jest offline")));
            }

            if (sector.getId().equals(SectorsPlugin.getInstance().getSectorCache().getCurrentSectorID())) {
                item.setGlow();
                item.addLore("&eZnajdujesz sie na tym kanale");
            } else if (sector.isOnline()) {
                item.addLore("&eKliknij aby polaczyc sie z kanalem");
            }

            guiWindow.setItem(i, item.buildFromItemStack(), (event -> {
                if (SectorsPlugin.getInstance().getSectorCache().getCurrentSector().getId().equals(sector.getId())) {
                    player.sendMessage(ChatHelper.colored("&cJestes polaczony z sektorem " + sector.getId()));
                    return;
                }
                if (!sector.isOnline()) {
                    player.sendMessage(ChatHelper.colored("&cSektor " + sector.getId() + " jest wylaczony"));
                    return;
                }
                SectorsPlugin.getInstance().getUserCache().findUserByUUID(player.getUniqueId()).ifPresent(user -> {
                    PlayerTransferHelper.connect(user, sector);
                });
            }));
            i++;

        }
        guiWindow.open(player);
    }

}
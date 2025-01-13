package net.fcode.inventory;

import net.fcode.configuration.impl.MessagesConfiguration;
import net.fcode.user.cache.UserCache;
import net.fcode.inventory.api.GuiWindow;
import net.fcode.inventory.api.builder.ItemBuilder;
import net.fcode.helper.ChatHelper;
import net.fcode.helper.CustomHeadHelper;
import net.fcode.transfer.PlayerTransferService;
import net.fcode.sector.Sector;
import net.fcode.sector.SectorCache;
import net.fcode.sector.SectorType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ChannelInventory {

    public static void openInventory(Player player, SectorCache sectorCache, UserCache userCache, PlayerTransferService transferService, MessagesConfiguration messagesConfiguration) {
        GuiWindow guiWindow = new GuiWindow("&7Kanaly...", 1);

        int i = 0;

        for (Sector sector : sectorCache.getSectorMap().values()) {
            if (!sector.getSectorType().equals(SectorType.SPAWN)) continue;

            ItemStack itemStack = (sector.isOnline() ? CustomHeadHelper.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGEyZjljNzYxZmMxMzFkYmViZDA3M2IwYjFkZDdkMWJhZWExOTFjZTlkMzNjNDljM2FjYTk0NDhiMWI2YjY4NCJ9fX0=") : CustomHeadHelper.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWIwZTA3NjMyMmZjOWFmNzk1OTJlYjg1MmNhOGM3YzQ1YmIyYzNjZWFiYzNjMGU4YTZhMWUwNGI0Y2UzZDM0YiJ9fX0="));
            ItemBuilder sectorItem = new ItemBuilder(itemStack).setTitle("&7Sektor &a" + sector.getId());

            sectorItem.addLores(sector.isOnline() ? Arrays.asList("", ChatHelper.colored("&7Online: &a" + sector.getPlayers()), ChatHelper.colored("&7TPS: &a" + Math.round(sector.getTps())), "") : Arrays.asList("", ChatHelper.colored("&cSektor jest offline")));

            sectorItem.addLore(sector.getId().equals(sectorCache.getCurrentSectorID()) ? "&eZnajdujesz sie na tym kanale" : "&eKliknij aby polaczyc sie z kanalem");

            guiWindow.setItem(i, sectorItem.buildToItemStack(), (event -> {
                if (sectorCache.getCurrentSector().getId().equals(sector.getId())) {
                    player.sendMessage(ChatHelper.colored(messagesConfiguration.getPlayerAlreadyConnectedMessage()));
                    return;
                }

                if (!sector.isOnline()) {
                    player.sendMessage(ChatHelper.colored(messagesConfiguration.getSectorIsOfflineMessage()));
                    return;
                }

                userCache.findUserByUUID(player.getUniqueId()).ifPresent(user -> {
                    transferService.connect(player,user,sector);
                });

            }));
            i++;
        }

        guiWindow.open(player);
    }

}
package fi.fcode.gui.api.builder;

import fi.fcode.helpers.ChatHelper;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

import org.bukkit.potion.Potion;

public class ItemBuilder {
    private Material mat;

    private int amount;

    private short data;

    private String title;

    private List<String> lore;

    private HashMap<Enchantment, Integer> enchants;

    private Color color;

    private Potion potion;

    private final List<ItemFlag> itemFlags;
    private ItemStack itemStack;
    public ItemBuilder(ItemStack mat) {
        this.title = null;
        this.amount = 1;
        this.enchants = new HashMap<>();
        this.lore = new ArrayList<>();
        this.itemFlags = new ArrayList<>();
        this.itemStack = mat;
    }

    public ItemBuilder(Material mat) {
        this(mat, 1);
    }

    public ItemBuilder(Material mat, int amount) {
        this(mat, amount, (short)0);
    }

    public ItemBuilder(Material mat, short data) {
        this(mat, 1, data);
    }

    public ItemBuilder(Material mat, int amount, short data) {
        this.title = null;
        this.lore = new ArrayList<>();
        this.enchants = new HashMap<>();
        this.itemFlags = new ArrayList<>();
        this.mat = mat;
        this.amount = amount;
        this.data = data;
    }

    public ItemBuilder setType(Material mat) {
        this.mat = mat;
        return this;
    }

    public ItemBuilder setTitle(String title) {
        this.title = ChatHelper.colored(title);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        this.itemFlags.add(flag);
        return this;
    }

    public ItemBuilder addFlags(List<ItemFlag> flags) {
        this.itemFlags.addAll(flags);
        return this;
    }

    public ItemBuilder addLores(List<String> lores) {
        this.lore.addAll(ChatHelper.fixColors(lores));
        return this;
    }
    public ItemBuilder setGlow() {
        this.enchants.remove(Enchantment.DURABILITY);
        this.enchants.put(Enchantment.DURABILITY, Integer.valueOf(10));
        return this;
    }
    public ItemBuilder addLore(String lore) {
        this.lore.add(ChatHelper.colored(lore));
        return this;
    }

    public ItemBuilder setData(short data) {
        this.data = data;
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchant, int level) {
        this.enchants.remove(enchant);
        this.enchants.put(enchant, Integer.valueOf(level));
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchant) {
        this.enchants.remove(enchant);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        if (!this.mat.name().contains("LEATHER_"))
            throw new IllegalArgumentException("Can only dye leather armor!");
        this.color = color;
        return this;
    }
    public ItemBuilder addLores(String... lores) {
        this.lore = ChatHelper.fixColors(Arrays.asList(lores));
        return this;
    }
    public ItemBuilder setPotion(Potion potion) {
        if (this.mat != Material.POTION)
            this.mat = Material.POTION;
        this.potion = potion;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }
    public ItemStack buildFromItemStack() {
        Material mat = this.mat;
        if (mat == null) {
            mat = Material.AIR;
        }
        ItemStack item = this.itemStack;
        ItemMeta meta = item.getItemMeta();
        if (this.title != null)
            meta.setDisplayName(this.title);
        if (!this.lore.isEmpty())
            meta.setLore(this.lore);
        if (meta instanceof LeatherArmorMeta)
            ((LeatherArmorMeta)meta).setColor(this.color);
        if (this.itemFlags.size() >= 1)
            this.itemFlags.forEach(xva$0 -> meta.addItemFlags(new ItemFlag[] { xva$0 }));
        item.setItemMeta(meta);
        item.addUnsafeEnchantments(this.enchants);
        return item;
    }


    public ItemStack build() {
        Material mat = this.mat;
        if (mat == null) {
            mat = Material.AIR;
        }
        ItemStack item = new ItemStack(this.mat, this.amount, this.data);
        ItemMeta meta = item.getItemMeta();
        if (this.title != null)
            meta.setDisplayName(this.title);
        if (!this.lore.isEmpty())
            meta.setLore(this.lore);
        if (meta instanceof LeatherArmorMeta)
            ((LeatherArmorMeta)meta).setColor(this.color);
        if (this.itemFlags.size() >= 1)
            this.itemFlags.forEach(xva$0 -> meta.addItemFlags(new ItemFlag[] { xva$0 }));
        item.setItemMeta(meta);
        item.addUnsafeEnchantments(this.enchants);
        return item;
    }

    public Material getType() {
        return this.mat;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean hasEnchantment(Enchantment enchant) {
        return this.enchants.containsKey(enchant);
    }

    public int getEnchantmentLevel(Enchantment enchant) {
        return ((Integer)this.enchants.get(enchant)).intValue();
    }

    public HashMap<Enchantment, Integer> getAllEnchantments() {
        return this.enchants;
    }

    public boolean isItem(ItemStack item) {
        return isItem(item, false);

    }

    public boolean isItem(ItemStack item, boolean strictDataMatch) {
        ItemMeta meta = item.getItemMeta();
        if (item.getType() != getType())
            return false;
        if (!meta.hasDisplayName() && getTitle() != null)
            return false;
        if (!meta.getDisplayName().equals(getTitle()))
            return false;
        if (!meta.hasLore() && !getLore().isEmpty())
            return false;
        if (meta.hasLore()) {
            Iterator<String> iterator = meta.getLore().iterator();
            while (iterator.hasNext()) {
                String lore = iterator.next();
                if (!getLore().contains(lore))
                    return false;
            }
        }
        Iterator<Enchantment> var5 = item.getEnchantments().keySet().iterator();
        while (var5.hasNext()) {
            Enchantment enchant = var5.next();
            if (!hasEnchantment(enchant))
                return false;
        }
        return true;
    }

}
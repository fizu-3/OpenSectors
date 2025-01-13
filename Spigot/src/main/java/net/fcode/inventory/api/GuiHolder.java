package net.fcode.inventory.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiHolder implements InventoryHolder {
  private final Map<Integer, Consumer<InventoryClickEvent>> actions;
  
  private Inventory inventory;
  
  public GuiHolder() {
    this.actions = new HashMap<>();
  }
  
  public void handleClick(InventoryClickEvent event) {
    (this.actions.getOrDefault(event.getRawSlot(), e -> e.setCancelled(true))).accept(event);
  }
  
  public Inventory getInventory() {
    return this.inventory;
  }
  
  public void setActionOnSlot(Integer slot, Consumer<InventoryClickEvent> consumer) {
    this.actions.put(slot, (consumer != null) ? consumer : (event -> {
        
    }));
  }
  
  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }
}

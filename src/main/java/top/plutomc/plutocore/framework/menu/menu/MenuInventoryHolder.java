package top.plutomc.plutocore.framework.menu.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class MenuInventoryHolder implements InventoryHolder {
    private final UUID MENU_UUID = UUID.randomUUID();

    @Override
    public Inventory getInventory() {
        return null;
    }
}

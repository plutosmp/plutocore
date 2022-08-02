package top.plutomc.plutocore.framework.menu.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import top.plutomc.plutocore.framework.menu.MenuFramework;
import top.plutomc.plutocore.framework.menu.action.Action;
import top.plutomc.plutocore.framework.menu.button.Button;

import java.util.HashSet;

public abstract class Menu {
    private final Inventory bukkitInventory;
    private final InventoryType bukkitInventoryType;
    private final String title;
    private final HashSet<Action> ACTIONS = new HashSet<>();
    private int size;
    private String[] pattern;

    public Menu(Inventory bukkitInventory, InventoryType bukkitInventoryType, String title) {
        this.bukkitInventory = bukkitInventory;
        this.bukkitInventoryType = bukkitInventoryType;
        this.title = title;
        size = bukkitInventory.getSize();
    }

    public String[] getPattern() {
        return pattern;
    }

    public Menu setPattern(String... pattern) {
        this.pattern = pattern;
        return this;
    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public InventoryType getBukkitInventoryType() {
        return bukkitInventoryType;
    }

    public Menu addButton(char pattern, Button button) {
        for (int i : processPattern(pattern)) {
            bukkitInventory.setItem(i, button.getItemStack());
        }
        return this;
    }

    public Menu addButton(int location, Button button) {
        bukkitInventory.setItem(location, button.getItemStack());
        return this;
    }

    public void openForPlayer(Player player) {
        player.openInventory(bukkitInventory);
    }

    public abstract int[] processPattern(char patternSymbol);

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public Menu setSize(int size) {
        this.size = size;
        return this;
    }

    public Menu addAction(Action menuAction) {
        this.ACTIONS.add(menuAction);
        MenuFramework.getMenuActionMap().put(bukkitInventory.getHolder(), ACTIONS);
        return this;
    }
}

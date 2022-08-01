package top.plutomc.plutocore.framework.menu.button;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import top.plutomc.plutocore.framework.menu.action.Action;
import top.plutomc.plutocore.framework.menu.MenuFramework;
import top.plutomc.plutocore.framework.menu.utils.ButtonUtils;

import java.util.HashSet;
import java.util.UUID;

public class Button {
    private final UUID uuid;
    private final HashSet<Action> ACTIONS = new HashSet<>();
    private ItemStack itemStack;

    public Button() {
        uuid = UUID.randomUUID();
        itemStack = new ItemStack(Material.STONE, 1);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setUUID("buttonUUID", uuid);
    }

    public Button addAction(Action action) {
        ACTIONS.add(action);
        MenuFramework.getButtonActionMap().put(itemStack, ACTIONS);
        return this;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Button setItemStack(ItemStack itemStack) {
        this.itemStack = ButtonUtils.addUuid(ButtonUtils.convertToButtonItem(itemStack), uuid);
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }
}
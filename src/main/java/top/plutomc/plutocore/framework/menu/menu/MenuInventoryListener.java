package top.plutomc.plutocore.framework.menu.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import top.plutomc.plutocore.framework.menu.action.Action;
import top.plutomc.plutocore.framework.menu.action.ButtonAction;
import top.plutomc.plutocore.framework.menu.action.MenuAction;
import top.plutomc.plutocore.framework.menu.action.MenuActionType;
import top.plutomc.plutocore.framework.menu.MenuFramework;
import top.plutomc.plutocore.framework.menu.utils.ButtonUtils;

public final class MenuInventoryListener implements Listener {
    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof Player) {
            if (event.getView().getTopInventory().getHolder() instanceof MenuInventoryHolder) {
                event.setCancelled(true);
            }
        }

        if (!(event.getClickedInventory().getHolder() instanceof MenuInventoryHolder)) return;
        if (event.getCurrentItem() == null) return;
        if (!ButtonUtils.isMenuButtonItem(event.getCurrentItem())) return;

        event.setCancelled(true);

        ItemStack itemStack = event.getCurrentItem();

        try {
            for (Action action : MenuFramework.getButtonActionMap().get(itemStack)) {
                if (action != null) {
                    if (action instanceof ButtonAction) {
                        ButtonAction buttonAction = (ButtonAction) action;
                        if (buttonAction.getClickTypes().contains(event.getClick())) {
                            buttonAction.on(player);
                        }
                    }
                }
            }
        }catch (Exception ignored) {

        }

    }

    @EventHandler
    public void inventoryOpenEvent(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player player = (Player) event.getPlayer();

        if (event.getInventory().getHolder() == null) return;

        InventoryHolder inventoryHolder = event.getInventory().getHolder();

        if (!MenuFramework.getMenuActionMap().containsKey(inventoryHolder)) return;

        for (Action action : MenuFramework.getMenuActionMap().get(inventoryHolder)) {
            if (action instanceof MenuAction) {
                MenuAction menuAction = (MenuAction) action;
                if (menuAction.getMenuActionTypes().contains(MenuActionType.OPEN)) {
                    action.on(player);
                }
            }
        }
    }

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player player = (Player) event.getPlayer();

        if (event.getInventory().getHolder() == null) return;

        InventoryHolder inventoryHolder = event.getInventory().getHolder();

        if (!MenuFramework.getMenuActionMap().containsKey(inventoryHolder)) return;

        for (Action action : MenuFramework.getMenuActionMap().get(inventoryHolder)) {
            if (action instanceof MenuAction) {
                MenuAction menuAction = (MenuAction) action;
                if (menuAction.getMenuActionTypes().contains(MenuActionType.CLOSE)) {
                    action.on(player);
                }
            }
        }
    }
}
package top.plutomc.plutocore.framework.menu.menu.types;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import top.plutomc.plutocore.framework.menu.menu.Menu;
import top.plutomc.plutocore.framework.menu.menu.MenuInventoryHolder;
import top.plutomc.plutocore.framework.menu.utils.PatternUtils;

public class ChestMenu extends Menu {
    public ChestMenu(String title) {
        super(Bukkit.createInventory(new MenuInventoryHolder(), InventoryType.CHEST, title), InventoryType.CHEST, title);
    }

    @Override
    public int[] processPattern(char patternSymbol) {
        return PatternUtils.process(patternSymbol, getPattern(),
                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8},
                new int[]{9, 10, 11, 12, 13, 14, 15, 16, 17},
                new int[]{18, 19, 20, 21, 22, 23, 24, 25, 26});
    }
}

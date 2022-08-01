package top.plutomc.plutocore.framework.menu.menu.types;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import top.plutomc.plutocore.framework.menu.menu.Menu;
import top.plutomc.plutocore.framework.menu.menu.MenuInventoryHolder;
import top.plutomc.plutocore.framework.menu.utils.PatternUtils;

public class LargeChestMenu extends Menu {
    public LargeChestMenu(String title) {
        super(Bukkit.createInventory(new MenuInventoryHolder(), 54, title), InventoryType.CHEST, title);
    }

    @Override
    public int[] processPattern(char patternSymbol) {
        return PatternUtils.process(patternSymbol, getPattern(),
                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8},
                new int[]{9, 10, 11, 12, 13, 14, 15, 16, 17},
                new int[]{18, 19, 20, 21, 22, 23, 24, 25, 26},
                new int[]{27, 28, 29, 30, 31, 32, 33, 34, 35},
                new int[]{36, 37, 38, 39, 40, 41, 42, 43, 44},
                new int[]{45, 46, 47, 48, 49, 50, 51, 52, 53});
    }
}

package top.plutomc.plutocore.menus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import top.plutomc.plutocore.CorePlugin;
import top.plutomc.plutocore.framework.menu.action.ButtonAction;
import top.plutomc.plutocore.framework.menu.action.MenuAction;
import top.plutomc.plutocore.framework.menu.action.MenuActionType;
import top.plutomc.plutocore.framework.menu.button.Button;
import top.plutomc.plutocore.framework.menu.button.ItemStackBuilder;
import top.plutomc.plutocore.framework.menu.menu.types.LargeChestMenu;
import top.plutomc.plutocore.utils.MessageUtil;

public final class ArmorMenu extends LargeChestMenu {
    public ArmorMenu(Player reciver, Player target) {
        super(MessageUtil.INSTANCE.parseLegacyColor(CorePlugin.Companion.getInstance().getConfig().getString("armorMenu.title").replace("<player>", target.getName())));
        setPattern("########X", "####0####", "####1####", "####2####", "####3####", "#########");
        addButton('#', new Button().setItemStack(
                new ItemStackBuilder()
                        .setMaterial(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(MessageUtil.INSTANCE.parseLegacyColor("&7占位符"))
                        .build()
        ));
        ItemStack[] itemStacks = target.getInventory().getArmorContents();
        for (int i = 0; i < 3; i++) {
            if (itemStacks[i] != null) {
                addButton(i, new Button().setItemStack(itemStacks[0]));
            } else {
                addButton(i, new Button().setItemStack(new ItemStack(Material.AIR, 1)));
            }
        }
        addButton('X', new Button().setItemStack(
                        new ItemStackBuilder()
                                .setMaterial(Material.RED_STAINED_GLASS_PANE)
                                .setName(MessageUtil.INSTANCE.parseLegacyColor("&c关闭"))
                                .build()
                ).addAction(new ButtonAction() {
                    @Override
                    public void on(Player player) {
                        player.playSound(player, Sound.BLOCK_IRON_DOOR_CLOSE, 2, 1);
                        player.closeInventory();
                    }
                }.addClickType(ClickType.LEFT))
        );
        addAction(new MenuAction() {
            @Override
            public void on(Player player) {
                player.playSound(player, Sound.BLOCK_IRON_DOOR_OPEN, 2, 1);
                player.closeInventory();
            }
        }.addMenuActionType(MenuActionType.OPEN));
        openForPlayer(reciver);
    }
}

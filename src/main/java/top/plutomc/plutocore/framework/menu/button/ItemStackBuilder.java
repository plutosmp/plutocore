package top.plutomc.plutocore.framework.menu.button;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.plutomc.plutocore.framework.menu.utils.SkullUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder {
    private Material material;
    private String name;

    private int amount;

    private List<String> lore;

    private String skullValue;

    public ItemStackBuilder() {
        lore = new ArrayList<>();
        amount = 1;
    }

    public ItemStackBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemStackBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStackBuilder addLore(String s) {
        lore.add(s);
        return this;
    }

    public ItemStackBuilder setSkullValue(String s) {
        skullValue = s;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(Material.STONE, amount);
        if (material != null) itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (name != null) itemMeta.setDisplayName(name);
        itemStack.setAmount(amount);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        if (skullValue != null) {
            return SkullUtils.processItem(skullValue, itemStack);
        }
        return itemStack;
    }
}

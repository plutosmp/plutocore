package top.plutomc.plutocore.framework.menu.utils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SkullUtils {
    private SkullUtils() {
    }

    public static ItemStack processItem(String value, ItemStack itemStack) {
        UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound skull = nbtItem.addCompound("SkullOwner");
        skull.setString("Id", hashAsId.toString());
        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        texture.setString("Value", value);
        return nbtItem.getItem();
    }
}

package top.plutomc.plutocore.framework.menu.action;

import org.bukkit.event.inventory.ClickType;

import java.util.HashSet;

public abstract class ButtonAction implements Action {
    private HashSet<ClickType> clickTypes = new HashSet<>();

    public HashSet<ClickType> getClickTypes() {
        return clickTypes;
    }

    public ButtonAction addClickType(ClickType clickType) {
        clickTypes.add(clickType);
        return this;
    }
}

package top.plutomc.plutocore.framework.menu.action;

import java.util.HashSet;

public abstract class MenuAction implements Action {
    private HashSet<MenuActionType> menuActionTypes = new HashSet<>();

    public HashSet<MenuActionType> getMenuActionTypes() {
        return menuActionTypes;
    }

    public MenuAction addMenuActionType(MenuActionType menuActionType) {
        menuActionTypes.add(menuActionType);
        return this;
    }
}

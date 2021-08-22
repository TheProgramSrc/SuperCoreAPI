package xyz.theprogramsrc.supercoreapi.spigot.guis.action;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Representation of the ClickType
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}.
 */
@Deprecated public enum ClickType {

    LEFT_CLICK,
    SHIFT_LEFT,
    MIDDLE_CLICK,
    RIGHT_CLICK,
    SHIFT_RIGHT,
    Q,
    CTRL_Q,
    DOUBLE

    ;


    public static ClickType fromEvent(InventoryClickEvent event){
        switch (event.getClick()){
            default:
                return LEFT_CLICK;
            case RIGHT:
                return RIGHT_CLICK;
            case DROP:
                return Q;
            case SHIFT_LEFT:
                return SHIFT_LEFT;
            case MIDDLE:
                return MIDDLE_CLICK;
            case SHIFT_RIGHT:
                return SHIFT_RIGHT;
            case CONTROL_DROP:
                return CTRL_Q;
            case DOUBLE_CLICK:
                return DOUBLE;
        }
    }
}

package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import xyz.theprogramsrc.supercoreapi.spigot.gui.Gui;

/**
 * Representation of a GUI action.
 * @since 5.2.0
 */
public class GuiAction {

    /**
     * The player executing the action
     */
    public final Player player;
    /**
     * The type of click
     */
    public final ClickType clickType;
    /**
     * The Gui that the action is executed on
     */
    public final Gui gui;

    /**
     * The InventoryClickEvent that triggered the action
     */
    public final InventoryClickEvent inventoryClickEvent;


    public GuiAction(Gui gui, Player player, ClickType clickType, InventoryClickEvent inventoryClickEvent){
        this.gui = gui;
        this.player = player;
        this.clickType = clickType;
        this.inventoryClickEvent = inventoryClickEvent;
    }

    /**
     * Representation of a click type.
     */
    public enum ClickType {

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
    
}

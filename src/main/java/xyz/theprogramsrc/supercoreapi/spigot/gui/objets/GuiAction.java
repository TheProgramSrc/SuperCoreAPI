package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import xyz.theprogramsrc.supercoreapi.spigot.gui.Gui;

public class GuiAction {

    public final Player player;
    public final ClickType clickType;
    public final Gui gui;

    public GuiAction(Gui gui, Player player, ClickType clickType) {
        this.gui = gui;
        this.player = player;
        this.clickType = clickType;
    }

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

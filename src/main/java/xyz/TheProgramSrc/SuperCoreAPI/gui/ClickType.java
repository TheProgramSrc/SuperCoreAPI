/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

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

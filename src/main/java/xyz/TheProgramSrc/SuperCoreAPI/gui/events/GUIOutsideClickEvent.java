/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.events;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.gui.GUI;

public class GUIOutsideClickEvent extends GUIEvent{
    private boolean canDrop;

    public GUIOutsideClickEvent(GUI gui, Player player) {
        super(gui, player);
    }

    public void setCanDrop(boolean canDrop){
        this.canDrop = canDrop;
    }

    public boolean canDrop() {
        return canDrop;
    }
}

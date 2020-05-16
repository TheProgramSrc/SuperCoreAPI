/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

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

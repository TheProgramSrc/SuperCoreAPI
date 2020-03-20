/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.events;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.gui.GUI;

public class GUIEmptyClickEvent extends GUIEvent{

    private int slot;

    public GUIEmptyClickEvent(GUI gui, int slot, Player player) {
        super(gui, player);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}

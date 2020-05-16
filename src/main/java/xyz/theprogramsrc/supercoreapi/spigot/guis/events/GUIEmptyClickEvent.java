/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.guis.events;


import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

public class GUIEmptyClickEvent extends GUIEvent{

    private final int slot;

    public GUIEmptyClickEvent(GUI gui, int slot, Player player) {
        super(gui, player);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}

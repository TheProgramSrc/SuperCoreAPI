/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;

public class GUIClickEvent extends GUIEvent{

    private final GUIButton button;
    private final int slot;

    public GUIClickEvent(GUI gui, Player player, GUIButton button, int slot) {
        super(gui, player);
        this.button = button;
        this.slot = slot;
    }

    public GUIButton getButton() {
        return button;
    }

    public int getSlot() {
        return slot;
    }
}

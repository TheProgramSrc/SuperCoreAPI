/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.events;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.gui.GUI;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;

public class GUIClickEvent extends GUIEvent{

    private GUIButton button;
    private int slot;

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

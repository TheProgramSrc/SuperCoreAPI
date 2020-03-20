/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.actions;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.gui.ClickType;
import xyz.TheProgramSrc.SuperCoreAPI.gui.GUI;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;

public class ClickAction{

    private GUI gui;
    private Player player;
    private GUIButton guiButton;
    private ClickType clickType;
    private int slot;

    public ClickAction(GUI gui, Player player, GUIButton button, ClickType clickType, int slot){
        this.gui = gui;
        this.player = player;
        this.guiButton = button;
        this.clickType = clickType;
        this.slot = slot;
    }

    public GUI getGUI() {
        return gui;
    }

    public GUIButton getGUIButton() {
        return guiButton;
    }

    public Player getPlayer() {
        return player;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public int getSlot() {
        return slot;
    }

    public SuperCore getCore() {
        return this.gui.getCore();
    }
}

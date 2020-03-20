/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.events;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.gui.GUI;

public class GUIEvent {

    private GUI gui;
    private Player player;

    public GUIEvent(GUI gui, Player player) {
        this.gui = gui;
        this.player = player;
    }

    public GUI getGUI() {
        return this.gui;
    }

    public Player getPlayer(){
        return this.player;
    }
}

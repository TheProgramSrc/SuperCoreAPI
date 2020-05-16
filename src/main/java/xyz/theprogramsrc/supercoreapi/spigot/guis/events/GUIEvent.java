/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

public class GUIEvent {

    private final GUI gui;
    private final Player player;

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

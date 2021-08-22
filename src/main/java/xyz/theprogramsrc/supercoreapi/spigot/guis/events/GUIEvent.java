package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import org.bukkit.entity.Player;

import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

/**
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}.
 */
@Deprecated public class GUIEvent {

    private final GUI gui;

    public GUIEvent(GUI gui){
        this.gui = gui;
    }

    public GUI getGUI() {
        return gui;
    }

    public Player getPlayer(){
        return this.getGUI().getPlayer();
    }
}

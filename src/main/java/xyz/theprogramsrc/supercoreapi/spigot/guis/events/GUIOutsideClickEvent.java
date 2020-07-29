package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

/**
 * Executed when a player clicks outside the GUI
 */
public class GUIOutsideClickEvent extends GUIEvent {
    private boolean canDrop;

    public GUIOutsideClickEvent(GUI gui) {
        super(gui);
    }

    public void setCanDrop(boolean canDrop){
        this.canDrop = canDrop;
    }

    public boolean canDrop() {
        return canDrop;
    }
}

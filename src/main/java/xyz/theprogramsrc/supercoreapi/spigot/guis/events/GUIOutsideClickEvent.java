package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

/**
 * Executed when a player clicks outside the GUI
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}.
 */
@Deprecated public class GUIOutsideClickEvent extends GUIEvent {
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

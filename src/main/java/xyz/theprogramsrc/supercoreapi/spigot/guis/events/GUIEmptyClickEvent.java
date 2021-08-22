package xyz.theprogramsrc.supercoreapi.spigot.guis.events;


import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

/**
 * Executed when a player clicks on a empty slot
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}.
 */
@Deprecated public class GUIEmptyClickEvent extends GUIEvent {

    private final int slot;

    public GUIEmptyClickEvent(GUI gui, int slot) {
        super(gui);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}

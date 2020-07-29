package xyz.theprogramsrc.supercoreapi.spigot.guis.events;


import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

/**
 * Executed when a player clicks on a empty slot
 */
public class GUIEmptyClickEvent extends GUIEvent {

    private final int slot;

    public GUIEmptyClickEvent(GUI gui, int slot) {
        super(gui);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}

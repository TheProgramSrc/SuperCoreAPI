package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;

/**
 * Executed when a player click a slot with item
 */
public class GUIClickEvent extends GUIEvent {

    private final GUIButton button;
    private final int slot;

    public GUIClickEvent(GUI gui, GUIButton button, int slot) {
        super(gui);
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

package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

public class GUICloseEvent extends GUIEvent{

    private boolean cancelled;

    public GUICloseEvent(GUI gui) {
        super(gui);
        this.cancelled = false;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

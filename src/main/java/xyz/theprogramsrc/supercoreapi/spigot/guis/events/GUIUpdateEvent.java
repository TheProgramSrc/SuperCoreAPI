package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

/**
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}.
 */
@Deprecated public class GUIUpdateEvent extends GUIEvent{

    public GUIUpdateEvent(GUI gui) {
        super(gui);
    }

}

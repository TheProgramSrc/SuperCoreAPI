package xyz.theprogramsrc.supercoreapi.spigot.guis.action;

/**
 * Representation of an Action executed while a GUI Button is clicked
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}. 
 */
@Deprecated public interface Action {

    void onClick(ClickAction clickAction);
}

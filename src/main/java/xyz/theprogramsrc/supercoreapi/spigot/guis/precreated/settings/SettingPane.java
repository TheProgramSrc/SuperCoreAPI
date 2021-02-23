package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

public abstract class SettingPane extends SpigotModule {

    /**
     * Gets the name to display in the setting title
     * @return the name to show in the title
     */
    public abstract String getDisplayName();

    /**
     * Gets the item to display in the setting selector gui
     * @return the item to display
     */
    public abstract SimpleItem getDisplayItem();

    /**
     * Gets the available buttons to show in the gui
     * @return the available buttons to show
     */
    public abstract GUIButton[] getButtons();

    /**
     * If this is true when a container slot is empty this will
     * show a white stained glass pane, otherwise the slot will
     * be empty.
     * @return true to show item in an empty slot container, false to leave empty
     */
    public boolean showItemsForEmpty(){
        return false;
    }

    public GUIRows getRows(){
        return GUIRows.SIX;
    }

    /**
     * Available slots to use in the Setting pane
     * @return the available slots to use in the setting pane
     */
    public int[] getContainerSlots(){
        return new int[]{
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34,
                37, 38, 39, 40, 41, 42, 43,
        };
    }

}

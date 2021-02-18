package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

public abstract class SettingPane extends SpigotModule {

    public abstract String getDisplayName();

    public abstract SimpleItem getDisplayItem();

    public abstract GUIButton[] getButtons();

    public GUIRows getRows(){
        return GUIRows.SIX;
    }

    public int[] getContainerSlots(){
        return new int[]{
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34,
                37, 38, 39, 40, 41, 42, 43,
        };
    }

}

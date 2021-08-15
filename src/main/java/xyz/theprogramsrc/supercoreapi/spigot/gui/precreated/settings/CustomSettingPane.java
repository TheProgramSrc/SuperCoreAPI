package xyz.theprogramsrc.supercoreapi.spigot.gui.precreated.settings;

import java.util.LinkedList;

import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiEntry;

public abstract class CustomSettingPane<OBJ> extends SettingPane {

    public abstract GuiEntry getEntry(OBJ obj);

    public abstract OBJ[] getObjects();

    @Override
    public GuiEntry[] getButtons() {
        LinkedList<GuiEntry> list = new LinkedList<>();
        for(OBJ obj : getObjects()) {
            list.add(getEntry(obj));
        }
        return list.toArray(new GuiEntry[list.size()]);
    }
    
}

package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings;

import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;

import java.util.LinkedList;

public abstract class CustomSettingPane<OBJ> extends SettingPane {

    public abstract GUIButton getButton(OBJ obj);

    public abstract OBJ[] getObjects();

    @Override
    public GUIButton[] getButtons() {
        LinkedList<GUIButton> buttons = new LinkedList<>();
        for(OBJ obj : this.getObjects()){
            buttons.add(this.getButton(obj));
        }
        return buttons.toArray(new GUIButton[0]);
    }
}

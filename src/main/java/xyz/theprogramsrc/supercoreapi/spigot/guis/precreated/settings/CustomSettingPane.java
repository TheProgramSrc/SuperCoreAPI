package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings;

import java.util.LinkedList;

import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;

/**
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}.
 */
@Deprecated public abstract class CustomSettingPane<OBJ> extends SettingPane {

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

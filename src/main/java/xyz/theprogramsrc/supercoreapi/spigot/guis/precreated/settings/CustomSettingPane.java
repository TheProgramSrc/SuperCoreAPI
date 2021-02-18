package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

import java.util.LinkedList;

public abstract class CustomSettingPane<OBJ> extends SettingPane {

    public abstract GUIButton getButton(OBJ obj);

    public abstract OBJ[] getObjects();

    @Override
    public GUIButton[] getButtons() {
        LinkedList<GUIButton> buttons = new LinkedList<>();
        int[] available = this.getContainerSlots();
        OBJ[] objs = this.getObjects();
        for(int i = 0; i < available.length; ++i){
            int slot = available[i];
            if(i < objs.length){
                buttons.add(this.getButton(objs[i]).setSlot(slot));
            }else{
                buttons.add(new GUIButton(slot, this.getPreloadedItems().emptyItem()));
            }
        }
        return buttons.toArray(new GUIButton[0]);
    }
}

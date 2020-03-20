/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.categories;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.gui.GUI;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.actions.ClickAction;

import java.util.ArrayList;
import java.util.List;

public abstract class SettingCategoryGUI extends GUI {

    private int[] availableSlots;

    public SettingCategoryGUI(SuperCore core, Player player) {
        super(core, player);
        this.availableSlots = new int[]{10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34};
    }

    @Override
    public GUIButton[] getButtons() {
        List<GUIButton> list = new ArrayList<>();
        list.add(new GUIButton(45, this.getPreloadedItems().getBackItem()).setAction(this::onBack));
        GUIButton[] objs = this.getObjects();

        for(int i = 0; i < objs.length; ++i){
            GUIButton b = objs[i];
            int slot = this.availableSlots[i];
            if(b.getSlot() == -1) b.setSlot(slot);
            list.add(b);
        }

        GUIButton[] buttons = new GUIButton[list.size()];
        buttons = list.toArray(buttons);
        return buttons;
    }

    public abstract GUIButton[] getObjects();

    public abstract void onBack(ClickAction action);
}

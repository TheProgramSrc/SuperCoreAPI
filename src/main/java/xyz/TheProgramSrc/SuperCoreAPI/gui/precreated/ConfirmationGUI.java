/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.gui.GUI;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.actions.ClickAction;
import xyz.TheProgramSrc.SuperCoreAPI.items.SimpleItem;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;
import xyz.TheProgramSrc.SuperCoreAPI.utils.XMaterial;

public abstract class ConfirmationGUI extends GUI {

    public ConfirmationGUI(SuperCore core, Player player) {
        super(core, player);
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public GUIButton[] getButtons() {
        return new GUIButton[]{
                new GUIButton(26, this.getPreloadedItems().getBackItem()).setAction(this::onBack),
                new GUIButton(11, new SimpleItem(XMaterial.GREEN_WOOL).setDisplayName(Base.CONFIRM).setLore("&7", "&b",Base.LEFT_CLICK + "&a " + Base.CONFIRM)).setAction(this::onConfirm),
                new GUIButton(15, new SimpleItem(XMaterial.RED_WOOL).setDisplayName(Base.DECLINE).setLore("&7", "&b"+Base.LEFT_CLICK + "&c " + Base.DECLINE)).setAction(this::onDecline)
        };
    }

    public abstract void onBack(ClickAction action);

    public abstract void onConfirm(ClickAction action);
    public abstract void onDecline(ClickAction action);
}

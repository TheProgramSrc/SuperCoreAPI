/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.precreatedSettings.settingsEditor;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;
import xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.categories.SettingCategoryGUI;
import xyz.TheProgramSrc.SuperCoreAPI.items.SimpleItem;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;
import xyz.TheProgramSrc.SuperCoreAPI.utils.XMaterial;

public abstract class SettingsEditor extends SettingCategoryGUI {

    public SettingsEditor(SuperCore core, Player player) {
        super(core, player);
    }

    @Override
    public GUIButton[] getObjects() {
        return new GUIButton[]{
                this.getUpdater(),
                this.getCloseWord()
        };
    }

    @Override
    public String getTitle() {
        return Base.SETTINGS.toString();
    }

    private GUIButton getUpdater(){
        SimpleItem item = new SimpleItem(XMaterial.REDSTONE_TORCH)
                .setDisplayName(Base.SETTINGS_TOGGLE_UPDATER_NAME)
                .setLore(
                        "&7",
                        Base.SETTINGS_TOGGLE_UPDATER_DESCRIPTION.options()
                                .vars(this.getSystemSettings().isUpdaterEnabled() ? Base.ENABLED.toString() : Base.DISABLED.toString())
                                .get()
                );
        return new GUIButton(20,item);
    }

    private GUIButton getCloseWord(){
        SimpleItem item = new SimpleItem(XMaterial.PAPER)
                .setDisplayName(Base.SET_CLOSE_WORD_NAME)
                .setLore("&7",Base.SET_CLOSE_WORD_DESCRIPTION.options().vars(this.getSystemSettings().getCloseWord()).get());
        return new GUIButton(24,item);
    }
}

/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.gui.GUI;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.actions.ClickAction;
import xyz.TheProgramSrc.SuperCoreAPI.gui.events.GUIEvent;
import xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.categories.SettingCategory;
import xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.precreatedSettings.languageSelector.LanguageSelector;
import xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.precreatedSettings.settingsEditor.SettingsEditor;
import xyz.TheProgramSrc.SuperCoreAPI.items.SimpleItem;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;
import xyz.TheProgramSrc.SuperCoreAPI.utils.XMaterial;

import java.util.ArrayList;
import java.util.List;

public abstract class SettingsGUI extends GUI {

    private int[] availableSlots;
    private SettingCategory[] categories;

    public SettingsGUI(SuperCore core, Player player) {
        super(core, player);
        this.availableSlots = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34};
        this.categories = this.getCategories();
    }

    @Override
    public String getTitle() {
        return Base.SETTINGS.toString();
    }

    @Override
    public GUIButton[] getButtons() {
        List<GUIButton> list = new ArrayList<>();
        list.add(new GUIButton(45, this.getPreloadedItems().getBackItem()).setAction(this::onBack));

        int slot;
        for(int i = 0; i < this.categories.length; ++i){
            slot = this.availableSlots[i];
            SettingCategory category = this.categories[i];
            list.add(new GUIButton(slot, new SimpleItem(category.getMaterial()).setDisplayName(category.getName()).setLore("&7").addLoreLines(Utils.split(category.getDescription(),36))).setAction(category::onClick));
        }

        if(this.categories.length < this.availableSlots.length){
            for(int i = this.categories.length; i < this.availableSlots.length; ++i){
                list.add(new GUIButton(this.availableSlots[i], this.getPreloadedItems().emptyItem()));
            }
        }


        GUIButton[] buttons = new GUIButton[list.size()];
        buttons = list.toArray(buttons);
        return buttons;
    }

    public abstract void onBack(ClickAction action);

    public abstract SettingCategory[] getCategories();

    public SettingCategory getLanguageSelectorCategory(){
        return new SettingCategory(XMaterial.BOOKSHELF, "&a"+Base.LANGUAGES, "&7"+Base.LANGUAGE_SELECTOR_DESCRIPTION) {
            @Override
            public void onClick(ClickAction action) {
                new LanguageSelector(action.getCore(), action.getPlayer()) {
                    @Override
                    public void onEvent(GUIEvent event) {

                    }

                    @Override
                    public void onBack(ClickAction action) {
                        SettingsGUI.this.open();
                    }
                }.open();
            }
        };
    }

    public SettingCategory getChangeSettingsCategory(){
        return new SettingCategory(XMaterial.COMMAND_BLOCK, "&a"+Base.SETTINGS_EDITOR_NAME, "&7"+Base.SETTINGS_EDITOR_DESCRIPTION) {
            @Override
            public void onClick(ClickAction action) {
                new SettingsEditor(action.getCore(), action.getPlayer()){

                    @Override
                    public void onEvent(GUIEvent event) {

                    }

                    @Override
                    public void onBack(ClickAction action) {
                        SettingsGUI.this.open();
                    }
                }.open();
            }
        };
    }
}

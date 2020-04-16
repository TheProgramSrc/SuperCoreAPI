/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.precreatedSettings.settingsEditor;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.dialogs.Dialog;
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
                this.getToggleUpdater(),
                this.getChangePrefix(),
                this.getChangeCloseWord(),
                this.getToggleDownloadTranslations(),
        };
    }

    @Override
    public String getTitle() {
        return Base.SETTINGS.toString();
    }

    private GUIButton getToggleUpdater(){
        SimpleItem item = new SimpleItem(XMaterial.ANVIL)
                .setDisplayName(Base.SETTINGS_TOGGLE_UPDATER_NAME)
                .setLore(
                        "&7",
                        Base.SETTINGS_TOGGLE_UPDATER_DESCRIPTION.options()
                                .vars(this.getSystemSettings().isUpdaterEnabled() ? Base.ENABLED.toString() : Base.DISABLED.toString())
                                .get()
                );
        return new GUIButton(10,item).setAction(a->{
            this.getSystemSettings().setUpdaterEnabled(!this.getSystemSettings().isUpdaterEnabled());
            this.open();
        });
    }

    private GUIButton getChangePrefix(){
        SimpleItem item = new SimpleItem(XMaterial.BOOK)
                .setDisplayName(Base.SET_PREFIX_NAME)
                .setLore("&7",Base.SET_PREFIX_DESCRIPTION.options().vars(this.getSystemSettings().getPrefix()).get());
        return new GUIButton(16,item).setAction(a->{
            new Dialog(getCore(), a.getPlayer()){
                @Override
                public String getTitle() {
                    return Base.DIALOG_CHANGE_VALUE_TITLE.toString();
                }

                @Override
                public String getSubtitle() {
                    return Base.DIALOG_CHANGE_VALUE_SUBTITLE.toString();
                }

                @Override
                public String getActionbar() {
                    return Base.DIALOG_CHANGE_VALUE_ACTIONBAR.toString();
                }

                @Override
                public boolean onResult(String playerInput) {
                    this.getSystemSettings().setPrefix(playerInput);
                    return true;
                }
            }.setRecall(p-> SettingsEditor.this.open()).addPlaceholder("{CurrentValue}", this.getSystemSettings().getPrefix());
        });
    }

    private GUIButton getChangeCloseWord(){
        SimpleItem item = new SimpleItem(XMaterial.PAPER)
                .setDisplayName(Base.SET_CLOSE_WORD_NAME)
                .setLore("&7",Base.SET_CLOSE_WORD_DESCRIPTION.options().vars(this.getSystemSettings().getCloseWord()).get());
        return new GUIButton(30,item).setAction(a->{
            new Dialog(getCore(), a.getPlayer()){
                @Override
                public String getTitle() {
                    return Base.DIALOG_CHANGE_VALUE_TITLE.toString();
                }

                @Override
                public String getSubtitle() {
                    return Base.DIALOG_CHANGE_VALUE_SUBTITLE.toString();
                }

                @Override
                public String getActionbar() {
                    return Base.DIALOG_CHANGE_VALUE_ACTIONBAR.toString();
                }

                @Override
                public boolean onResult(String playerInput) {
                    this.getSystemSettings().setCloseWord(playerInput);
                    return true;
                }
            }.setRecall(p-> SettingsEditor.this.open()).addPlaceholder("{CurrentValue}", this.getSystemSettings().getCloseWord());
        });
    }

    private GUIButton getToggleDownloadTranslations(){
        SimpleItem item = new SimpleItem(XMaterial.CRAFTING_TABLE)
                .setDisplayName(Base.SETTINGS_TOGGLE_TRANSLATION_DOWNLOADER_NAME)
                .setLore(
                        "&7",
                        Base.SETTINGS_TOGGLE_TRANSLATION_DOWNLOADER_DESCRIPTION.options()
                                .vars(this.getSystemSettings().isTranslationDownloaderEnabled() ? Base.ENABLED.toString() : Base.DISABLED.toString())
                                .get()
                );
        return new GUIButton(32,item).setAction(a->{
            this.getSystemSettings().setTranslationDownloaderEnabled(!this.getSystemSettings().isTranslationDownloaderEnabled());
            this.open();
        });
    }
}

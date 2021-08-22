package xyz.theprogramsrc.supercoreapi.spigot.gui.precreated.settings.precreated;

import com.cryptomorin.xseries.XMaterial;

import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.dialog.Dialog;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiEntry;
import xyz.theprogramsrc.supercoreapi.spigot.gui.precreated.settings.SettingPane;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

public class GeneralConfigurationSettingPane extends SettingPane {

    private final boolean hasDownloader = this.getPlugin().getPluginDataStorage().contains("TranslationDownloader");

    @Override
    public String getDisplayName() {
        return Base.GENERAL.toString();
    }

    @Override
    public SimpleItem getDisplayItem() {
        return new SimpleItem(XMaterial.COMMAND_BLOCK_MINECART)
                .setDisplayName("&a" + Base.SETTINGS_GENERAL_NAME)
                .setLore(
                        "&7",
                        "&7" + Base.SETTINGS_GENERAL_DESCRIPTION
                );
    }

    @Override
    public GuiEntry[] getButtons() {
        return new GuiEntry[]{
            this.getToggleTranslationButton(),
            this.getChangePrefixButton()
        };
    }

    @Override
    public int[] getContainerSlots() {
        return this.hasDownloader ? new int[]{12,14} : new int[]{13};
    }

    private GuiEntry getToggleTranslationButton(){
        SimpleItem item = new SimpleItem(XMaterial.ANVIL)
                .setDisplayName("&a" + Base.GENERAL_TOGGLE_TRANSLATION_DOWNLOADER_NAME)
                .setLore(
                        "&7",
                        "&7" + Base.GENERAL_TOGGLE_TRANSLATION_DOWNLOADER_DESCRIPTION
                ).addPlaceholder("{Status}", Utils.parseEnabledBoolean(this.getPlugin().getPluginDataStorage().getBoolean("TranslationDownloader")));
        return new GuiEntry(item, a-> {
            this.getPlugin().getPluginDataStorage().set("TranslationDownloader", !this.hasDownloader);
            a.gui.open();
        });
    }

    private GuiEntry getChangePrefixButton(){
        SimpleItem item = new SimpleItem(XMaterial.NAME_TAG)
                .setDisplayName("&a" + Base.GENERAL_SET_PREFIX_NAME)
                .setLore(
                        "&7",
                        "&7" + Base.GENERAL_SET_PREFIX_DESCRIPTION
                ).addPlaceholder("{Prefix}", "&r" + this.getSettings().getPrefix() + "&r");
        return new GuiEntry(item, a-> new Dialog(a.player){
            @Override
            public String getTitle() {
                return "&9" + Base.DIALOG_CHANGE_PREFIX_TITLE;
            }

            @Override
            public String getSubtitle() {
                return "&7" + Base.DIALOG_CHANGE_PREFIX_SUBTITLE;
            }

            @Override
            public String getActionbar() {
                return "&a" + Base.DIALOG_CHANGE_PREFIX_ACTIONBAR;
            }

            @Override
            public boolean onResult(String playerInput) {
                this.getSettings().setPrefix(playerInput);
                a.gui.open();
                return true;
            }
        }.addPlaceholder("{Prefix}", "&r"+this.getSettings().getPrefix()+"&r"));
    }
}
package xyz.theprogramsrc.supercoreapi.spigot.gui.precreated.settings.precreated;

import com.cryptomorin.xseries.XMaterial;

import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.dialog.Dialog;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiEntry;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiRows;
import xyz.theprogramsrc.supercoreapi.spigot.gui.precreated.settings.SettingPane;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

public class GeneralConfigurationSettingPane extends SettingPane {

    @Override
    public String getDisplayName() {
        return Base.GENERAL.toString();
    }

    @Override
    public GuiRows getRows(){
        return GuiRows.THREE;
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
            this.getChangePrefixButton()
        };
    }

    @Override
    public int[] getContainerSlots() {
        return new int[]{13};
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
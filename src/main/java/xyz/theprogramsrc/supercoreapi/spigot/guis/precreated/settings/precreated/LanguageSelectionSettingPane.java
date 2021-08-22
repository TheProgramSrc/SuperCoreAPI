package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings.precreated;

import com.cryptomorin.xseries.XMaterial;

import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;
import xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings.CustomSettingPane;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

/**
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}.
 */
@Deprecated public class LanguageSelectionSettingPane extends CustomSettingPane<String> {

    @Override
    public String getDisplayName() {
        return Base.LANGUAGES.toString();
    }

    @Override
    public SimpleItem getDisplayItem() {
        return new SimpleItem(XMaterial.BOOKSHELF)
                .setDisplayName("&a" + Base.LANGUAGE_SELECTOR_NAME)
                .setLore(
                        "&7",
                        "&7" + Base.LANGUAGE_SELECTOR_DESCRIPTION
                );
    }

    @Override
    public GUIButton getButton(String language) {
        boolean selected = this.getSettings().getLanguage().equals(language);
        SimpleItem item = new SimpleItem(XMaterial.BOOK)
                .setDisplayName("&a" + this.plugin.getTranslationManager().translate("Display", language))
                .setLore(
                        "&7",
                        "&7" + (selected ? Base.LANGUAGE_SELECTED_DESCRIPTION : Base.LANGUAGE_SELECT_DESCRIPTION)
                ).addPlaceholder("{Language}", this.plugin.getTranslationManager().translate("Display", language));
        return new GUIButton(item, a->{
            this.getSettings().setLanguage(language);
            this.getSettings().getConfig().load();
            a.openGUI();
        });
    }

    @Override
    public String[] getObjects() {
        return this.spigotPlugin.getTranslationManager().getTranslations();
    }

    @Override
    public boolean showItemsForEmpty() {
        return true;
    }
}

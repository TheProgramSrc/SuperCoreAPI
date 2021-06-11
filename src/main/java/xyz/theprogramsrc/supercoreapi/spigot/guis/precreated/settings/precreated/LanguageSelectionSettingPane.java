package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings.precreated;

import com.cryptomorin.xseries.XMaterial;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;
import xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings.CustomSettingPane;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

import java.util.Locale;

public class LanguageSelectionSettingPane extends CustomSettingPane<Locale> {

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
    public GUIButton getButton(Locale locale) {
        boolean selected = this.getSettings().getLanguage().equals(locale.toString());
        SimpleItem item = new SimpleItem(XMaterial.BOOK)
                .setDisplayName("&a" + locale.getDisplayLanguage())
                .setLore(
                        "&7",
                        "&7" + (selected ? Base.LANGUAGE_SELECTED_DESCRIPTION : Base.LANGUAGE_SELECT_DESCRIPTION)
                ).addPlaceholder("{Language}", locale.getDisplayLanguage(locale));
        return new GUIButton(item, a->{
            this.getSettings().setLanguage(locale.toString());
            this.getSettings().getConfig().reload();
            a.openGUI();
        });
    }

    @Override
    public Locale[] getObjects() {
        return this.spigotPlugin.getTranslationManager().getTranslations();
    }

    @Override
    public boolean showItemsForEmpty() {
        return true;
    }
}

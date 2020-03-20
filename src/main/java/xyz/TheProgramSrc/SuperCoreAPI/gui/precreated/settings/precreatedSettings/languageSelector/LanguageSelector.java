/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.precreatedSettings.languageSelector;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;
import xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.categories.SettingCategoryGUI;
import xyz.TheProgramSrc.SuperCoreAPI.items.SimpleItem;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;
import xyz.TheProgramSrc.SuperCoreAPI.utils.XMaterial;

import java.util.ArrayList;
import java.util.List;

public abstract class LanguageSelector extends SettingCategoryGUI {

    public LanguageSelector(SuperCore core, Player player) {
        super(core, player);
    }

    @Override
    public GUIButton[] getObjects() {
        List<GUIButton> list = new ArrayList<>();
        this.getTranslationManager().getLanguages().forEach(lang->{
            SimpleItem item = new SimpleItem(XMaterial.BOOK).setDisplayName("&a" + lang);
            if(this.getLanguage().equals(lang)){
                item.setLore(Base.LANGUAGE_SELECTED_DESCRIPTION).addEnchantment(Enchantment.DURABILITY).setShowEnchantments(false);
            }else{
                item.setLore(Base.LANGUAGE_SELECT_DESCRIPTION);
            }
            list.add(new GUIButton(item).setAction(a->{
                this.getSystemSettings().setLanguage(lang);
                this.open();
            }));
        });

        GUIButton[] buttons = new GUIButton[list.size()];
        buttons = list.toArray(buttons);
        return buttons;
    }

    @Override
    public String getTitle() {
        return Base.LANGUAGES.toString();
    }
}

/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.faq;

import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.gui.BrowserGUI;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;
import xyz.TheProgramSrc.SuperCoreAPI.items.SimpleItem;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;
import xyz.TheProgramSrc.SuperCoreAPI.utils.XMaterial;

public abstract class FAQBrowser extends BrowserGUI<FAQ> {

    public FAQBrowser(SuperCore core, Player player) {
        super(core, player);
    }

    @Override
    public GUIButton getButton(FAQ faq) {
        return new GUIButton(new SimpleItem(XMaterial.PAPER).setDisplayName(faq.getQuestion()).setLore("&7").addLoreLines(Utils.breakText(faq.getAnswer(), 36, "&7")));
    }

    @Override
    public String getTitle() {
        return "F.A.Q";
    }
}

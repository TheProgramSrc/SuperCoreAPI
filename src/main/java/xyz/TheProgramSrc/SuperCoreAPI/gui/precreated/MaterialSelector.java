/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.gui.BrowserGUI;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.actions.ClickAction;
import xyz.TheProgramSrc.SuperCoreAPI.items.SimpleItem;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;
import xyz.TheProgramSrc.SuperCoreAPI.utils.XMaterial;

import java.util.ArrayList;
import java.util.List;

public abstract class MaterialSelector extends BrowserGUI<XMaterial> {

    public MaterialSelector(SuperCore core, Player player) {
        super(core, player);
    }

    @Override
    public XMaterial[] getObjects() {
        Inventory inv = Bukkit.createInventory(null, 9);
        XMaterial[] materials = XMaterial.values();
        List<XMaterial> list = new ArrayList<>();
        for(XMaterial m : materials){
            if(m != null){
                if(m != XMaterial.AIR){
                    inv.setItem(0, new ItemStack(m.parseMaterial()));
                    ItemStack item = inv.getItem(0);
                    if(item != null){
                        list.add(m);
                        inv.clear();
                    }
                }
            }
        }
        return list.stream().filter(XMaterial::isSupported).toArray(XMaterial[]::new);
    }

    @Override
    public GUIButton getButton(final XMaterial xMaterial) {
        return new GUIButton(new SimpleItem(xMaterial).setDisplayName(Base.MATERIAL_SELECTOR_ITEM_NAME).setLore(Base.MATERIAL_SELECTOR_ITEM_DESCRIPTION).addPlaceholder("{Material}", xMaterial.getHumanName())).setAction(a-> this.onSelect(a, xMaterial));
    }

    public abstract void onSelect(ClickAction action, XMaterial selected);
}

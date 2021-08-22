package xyz.theprogramsrc.supercoreapi.spigot.gui.precreated;

import java.util.Arrays;

import com.cryptomorin.xseries.XMaterial;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.gui.BrowserGui;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiAction;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiEntry;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

public abstract class MaterialBrowser extends BrowserGui<XMaterial>{

    public MaterialBrowser(Player player, boolean automaticallyOpen) {
        super(player, automaticallyOpen);
    }

    public MaterialBrowser(Player player) {
        super(player);
    }

    @Override
    public GuiEntry getEntry(XMaterial obj) {
        SimpleItem item = new SimpleItem(obj)
            .setDisplayName("&a" + Base.MATERIAL_SELECTOR_ITEM_NAME)
            .setLore(
                    "&7",
                    "&7" + Base.MATERIAL_SELECTOR_ITEM_DESCRIPTION
            ).addPlaceholder("{Material}", Utils.getEnumName(obj));
        return new GuiEntry(item, a-> this.onSelect(a, obj));
    }

    @Override
    public String[] getSearchTags(XMaterial obj) {
        return Arrays.stream(obj.name().split("_")).map(s-> s.toLowerCase()).toArray(String[]::new);
    }

    @Override
    public XMaterial[] getObjects() {
        Inventory inventory = Bukkit.createInventory(null, 9);
        return Arrays.stream(XMaterial.values()).filter(XMaterial::isSupported).filter(m-> m.parseMaterial() != null).filter(m->{
            inventory.setItem(4, m.parseItem());
            if(inventory.getItem(4) != null){
                inventory.clear();
                return true;
            }
            return false;
        }).toArray(XMaterial[]::new);
    }

    public abstract void onSelect(GuiAction action, XMaterial material);
}

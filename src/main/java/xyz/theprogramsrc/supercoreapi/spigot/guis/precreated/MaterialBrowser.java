package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.guis.BrowserGUI;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

import java.util.Arrays;

public abstract class MaterialBrowser extends BrowserGUI<XMaterial> {

    public MaterialBrowser(Player player) {
        super(player);
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

    @Override
    public GUIButton getButton(XMaterial xMaterial) {
        SimpleItem item = new SimpleItem(xMaterial)
                .setDisplayName("&a" + Base.MATERIAL_SELECTOR_ITEM_NAME)
                .setLore(
                        "&7",
                        "&7" + Base.MATERIAL_SELECTOR_ITEM_DESCRIPTION
                ).addPlaceholder("{Material}", Utils.getEnumName(xMaterial));
        return new GUIButton(item).setAction(a-> this.onSelect(a, xMaterial));
    }

    @Override
    public void onBack(ClickAction clickAction) {
        this.close();
    }

    @Override
    protected String getTitle() {
        return Base.MATERIAL_SELECTOR_TITLE.toString();
    }

    public abstract void onSelect(ClickAction clickAction, XMaterial xMaterial);
}

/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.items;

import org.bukkit.enchantments.Enchantment;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.utils.xseries.XMaterial;

public class PreloadedItems extends SpigotModule {
    public PreloadedItems(SpigotPlugin plugin) {
        super(plugin);
    }

    public SimpleItem getBackItem(){
        if(Utils.isConnected()){
            return new SimpleItem(Skulls.BACK.asSkinTexture()).setDisplayName(Base.ITEM_BACK_NAME.toString()).setLore(Base.ITEM_BACK_DESCRIPTION.toString());
        }else{
            return new SimpleItem(XMaterial.ARROW).setDisplayName(Base.ITEM_BACK_NAME.toString()).setLore(Base.ITEM_BACK_DESCRIPTION.toString());
        }
    }

    public SimpleItem getNextItem(){
        if(Utils.isConnected()){
            return new SimpleItem(Skulls.ARROW_RIGHT.asSkinTexture()).setDisplayName(Base.ITEM_NEXT_NAME.toString()).setLore(Base.ITEM_NEXT_DESCRIPTION.toString());
        }else{
            return new SimpleItem(XMaterial.PAPER).setDisplayName(Base.ITEM_NEXT_NAME.toString()).setLore(Base.ITEM_NEXT_DESCRIPTION.toString());
        }
    }

    public SimpleItem getPreviousItem(){
        if(Utils.isConnected()){
            return new SimpleItem(Skulls.ARROW_LEFT.asSkinTexture()).setDisplayName(Base.ITEM_PREVIOUS_NAME.toString()).setLore(Base.ITEM_PREVIOUS_DESCRIPTION.toString());
        }else{
            return new SimpleItem(XMaterial.PAPER).setDisplayName(Base.ITEM_PREVIOUS_NAME.toString()).setLore(Base.ITEM_PREVIOUS_DESCRIPTION.toString());
        }
    }

    public SimpleItem getSearchItem(){
        return new SimpleItem(XMaterial.BOOKSHELF).setDisplayName(Base.ITEM_SEARCH_NAME.toString()).setLore(Base.ITEM_SEARCH_DESCRIPTION.toString());
    }

    public SimpleItem getEndSearchItem(){
        return new SimpleItem(XMaterial.BOOKSHELF).addEnchantment(Enchantment.DURABILITY).setShowEnchantments(false).setDisplayName(Base.ITEM_END_SEARCH_NAME.toString()).setLore(Base.ITEM_END_SEARCH_DESCRIPTION.toString());
    }

    public SimpleItem emptyItem(){
        return new SimpleItem(XMaterial.WHITE_STAINED_GLASS).setDisplayName("&7").addEnchantment(Enchantment.DURABILITY).setShowEnchantments(false);
    }


}

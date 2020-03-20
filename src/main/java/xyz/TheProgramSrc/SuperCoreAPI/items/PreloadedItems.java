/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.items;

import org.bukkit.enchantments.Enchantment;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;
import xyz.TheProgramSrc.SuperCoreAPI.utils.XMaterial;

public class PreloadedItems extends SuperModule {

    public PreloadedItems(SuperCore core) {
        super(core);
    }

    public SimpleItem getBackItem(){
        if(Utils.isConnected()){
            return new SimpleItem(Skulls.BACK.asSkinTexture()).setDisplayName(Base.ITEM_BACK_NAME).setLore(Base.ITEM_BACK_DESCRIPTION);
        }else{
            return new SimpleItem(XMaterial.ARROW).setDisplayName(Base.ITEM_BACK_NAME).setLore(Base.ITEM_BACK_DESCRIPTION);
        }
    }

    public SimpleItem getNextItem(){
        if(Utils.isConnected()){
            return new SimpleItem(Skulls.ARROW_RIGHT.asSkinTexture()).setDisplayName(Base.ITEM_NEXT_NAME).setLore(Base.ITEM_NEXT_DESCRIPTION);
        }else{
            return new SimpleItem(XMaterial.PAPER).setDisplayName(Base.ITEM_NEXT_NAME).setLore(Base.ITEM_NEXT_DESCRIPTION);
        }
    }

    public SimpleItem getPreviousItem(){
        if(Utils.isConnected()){
            return new SimpleItem(Skulls.ARROW_LEFT.asSkinTexture()).setDisplayName(Base.ITEM_PREVIOUS_NAME).setLore(Base.ITEM_PREVIOUS_DESCRIPTION);
        }else{
            return new SimpleItem(XMaterial.PAPER).setDisplayName(Base.ITEM_PREVIOUS_NAME).setLore(Base.ITEM_PREVIOUS_DESCRIPTION);
        }
    }

    public SimpleItem getSearchItem(){
        return new SimpleItem(XMaterial.BOOKSHELF).setDisplayName(Base.ITEM_SEARCH_NAME).setLore(Base.ITEM_SEARCH_DESCRIPTION);
    }

    public SimpleItem getEndSearchItem(){
        return new SimpleItem(XMaterial.BOOKSHELF).addEnchantment(Enchantment.DURABILITY).setShowEnchantments(false).setDisplayName(Base.ITEM_END_SEARCH_NAME).setLore(Base.ITEM_END_SEARCH_DESCRIPTION);
    }

    public SimpleItem emptyItem(){
        return new SimpleItem(XMaterial.WHITE_STAINED_GLASS).setDisplayName("&7").addEnchantment(Enchantment.DURABILITY).setShowEnchantments(false);
    }
}

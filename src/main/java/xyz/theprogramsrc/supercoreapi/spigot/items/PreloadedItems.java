package xyz.theprogramsrc.supercoreapi.spigot.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.enchantments.Enchantment;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;

/**
 * Representation of the Pre-Created Items
 */
public class PreloadedItems extends SpigotModule {

    /**
     * Common item that represent a back button
     * @return the item
     */
    public SimpleItem getBackItem(){
        if(Utils.isConnected()){
            return new SimpleItem(Skulls.BACK.asSkinTexture()).setDisplayName(Base.ITEM_BACK_NAME.toString()).setLore(Base.ITEM_BACK_DESCRIPTION.toString());
        }else{
            return new SimpleItem(XMaterial.ARROW).setDisplayName(Base.ITEM_BACK_NAME.toString()).setLore(Base.ITEM_BACK_DESCRIPTION.toString());
        }
    }

    /**
     * Common item that represent a next page button
     * @return the item
     */
    public SimpleItem getNextItem(){
        if(Utils.isConnected()){
            return new SimpleItem(Skulls.ARROW_RIGHT.asSkinTexture()).setDisplayName(Base.ITEM_NEXT_NAME.toString()).setLore(Base.ITEM_NEXT_DESCRIPTION.toString());
        }else{
            return new SimpleItem(XMaterial.PAPER).setDisplayName(Base.ITEM_NEXT_NAME.toString()).setLore(Base.ITEM_NEXT_DESCRIPTION.toString());
        }
    }

    /**
     * Common item that represent a previous page button
     * @return the item
     */
    public SimpleItem getPreviousItem(){
        if(Utils.isConnected()){
            return new SimpleItem(Skulls.ARROW_LEFT.asSkinTexture()).setDisplayName(Base.ITEM_PREVIOUS_NAME.toString()).setLore(Base.ITEM_PREVIOUS_DESCRIPTION.toString());
        }else{
            return new SimpleItem(XMaterial.PAPER).setDisplayName(Base.ITEM_PREVIOUS_NAME.toString()).setLore(Base.ITEM_PREVIOUS_DESCRIPTION.toString());
        }
    }

    /**
     * Common item that represent a Search button
     * @return the item
     */
    public SimpleItem getSearchItem(){
        return new SimpleItem(XMaterial.BOOKSHELF).setDisplayName(Base.ITEM_SEARCH_NAME.toString()).setLore(Base.ITEM_SEARCH_DESCRIPTION.toString());
    }

    /**
     * Common item that represent a End Search button
     * @return the item
     */
    public SimpleItem getEndSearchItem(){
        return new SimpleItem(XMaterial.BOOKSHELF).addEnchantment(Enchantment.DURABILITY).setShowEnchantments(false).setDisplayName(Base.ITEM_END_SEARCH_NAME.toString()).setLore(Base.ITEM_END_SEARCH_DESCRIPTION.toString());
    }

    /**
     * Common item that represent an empty button
     * @return the item
     */
    public SimpleItem emptyItem(){
        return new SimpleItem(XMaterial.WHITE_STAINED_GLASS_PANE).setDisplayName("&7").addEnchantment(Enchantment.DURABILITY).setShowEnchantments(false);
    }


}

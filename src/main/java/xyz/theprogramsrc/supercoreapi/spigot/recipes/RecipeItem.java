package xyz.theprogramsrc.supercoreapi.spigot.recipes;

import org.bukkit.inventory.ItemStack;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

/**
 * {@link RecipeItem Recipe Item} is a representation of an item
 * inside the crafting table.
 */
public class RecipeItem {

    private final int slot;
    private final ItemStack item;

    /**
     * Create an ordered-item.
     * An ordered-item represents an item inside the
     * crafting table that must be placed on a specific
     * slot.
     * @param slot where should be placed the item
     * @param item the item to check
     */
    public RecipeItem(int slot, ItemStack item) {
        Utils.notNull(item, "Item for RecipeItem cannot be null!");
        if(slot < 1 || slot > 9){
            throw new IllegalArgumentException("The slot '" + slot + "' for the item with material '" + Utils.getEnumName(item.getType()) + "' must be between 1 and 9. Please see: https://github.com/TheProgramSrc/PluginsResources/blob/master/SuperCoreAPI/README.md#custom-crafting-api");
        }
        this.slot = slot;
        this.item = item;
    }

    /**
     * Create an unordered-item.
     * An unordered-item represents an item inside the
     * crafting table that don't have a specific place where should be.
     * @param item the item to check
     */
    public RecipeItem(ItemStack item){
        this.slot = -1;
        this.item = item;
    }

    /**
     * Gets the slot where should be placed the item
     * <i>NOTE: If this is an unordered-item will return -1</i>
     * @return the slot where should be the item. If this is an unordered-item will return -1
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Gets the item to check
     * @return the item to check
     */
    public ItemStack getItem() {
        return item;
    }
}

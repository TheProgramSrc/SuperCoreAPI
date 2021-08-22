package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

import java.util.function.Consumer;

import org.bukkit.inventory.ItemStack;

import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

/**
 * Representation of a GuiEntry
 * @since 5.2.0
 */
public class GuiEntry {

    public final ItemStack item;
    public final Consumer<GuiAction> action;

    /**
     * Creates a new GuiEntry from an ItemStack and a Consumer
     * @param item The ItemStack
     * @param action The Consumer
     */
    public GuiEntry(ItemStack item, Consumer<GuiAction> action) {
        this.item = item;
        this.action = action;
    }

    /**
     * Creates a new GuiEntry from a SimpleItem and a Consumer
     * @param item The SimpleItem
     * @param action The Consumer
     */
    public GuiEntry(SimpleItem item, Consumer<GuiAction> action) {
        this(item.build(), action);
    }

    /**
     * Creates a new GuiEntry from an ItemStack but without an action.
     * @param item The ItemStack
     */
    public GuiEntry(ItemStack item) {
        this(item, null);
    }

    /**
     * Creates a new GuiEntry from a SimpleItem but without an action.
     * @param item The SimpleItem
     */
    public GuiEntry(SimpleItem item) {
        this(item.build(), null);
    }
    
}

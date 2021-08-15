package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

import java.util.function.Consumer;

import org.bukkit.inventory.ItemStack;

import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

public class GuiEntry {

    public final ItemStack item;
    public final Consumer<GuiAction> action;

    public GuiEntry(ItemStack item, Consumer<GuiAction> action) {
        this.item = item;
        this.action = action;
    }

    public GuiEntry(SimpleItem item, Consumer<GuiAction> action) {
        this(item.build(), action);
    }

    public GuiEntry(ItemStack item) {
        this(item, null);
    }

    public GuiEntry(SimpleItem item) {
        this(item.build(), null);
    }
    
}

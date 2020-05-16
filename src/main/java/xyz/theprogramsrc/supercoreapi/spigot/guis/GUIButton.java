/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.guis;

import org.bukkit.inventory.ItemStack;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.Action;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

public class GUIButton {

    private int slot;
    private final ItemStack itemStack;
    private Action action;

    public GUIButton(int slot, ItemStack itemStack, Action action){
        this.slot = slot;
        this.itemStack = itemStack;
        this.action = action;
    }

    public GUIButton(int slot, SimpleItem simpleItem, Action action){
        this(slot, simpleItem.build(), action);
    }

    public GUIButton(int slot, ItemStack itemStack) {
        this(slot, itemStack, null);
    }

    public GUIButton(int slot, SimpleItem simpleItem){
        this(slot, simpleItem.build());
    }

    public GUIButton(ItemStack itemStack){
        this(-1, itemStack);
    }

    public GUIButton(SimpleItem simpleItem){
        this(simpleItem.build());
    }

    public GUIButton setAction(Action action) {
        this.action = action;
        return this;
    }

    public GUIButton setSlot(int slot){
        this.slot = slot;
        return this;
    }

    public Action getAction() {
        return action;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }
}

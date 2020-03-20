/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.buttons;


import org.bukkit.inventory.ItemStack;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.actions.Action;
import xyz.TheProgramSrc.SuperCoreAPI.items.SimpleItem;

public class GUIButton {

    private ItemStack itemStack;
    private int slot;
    private Action action;

    public GUIButton(int slot, ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public GUIButton(int slot, SimpleItem simpleItem){
        this.slot = slot;
        this.itemStack = simpleItem.build();
    }

    public GUIButton(ItemStack itemStack){
        this.itemStack = itemStack;
        this.slot = -1;
    }

    public GUIButton(SimpleItem simpleItem){
        this.itemStack = simpleItem.build();
        this.slot = -1;
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

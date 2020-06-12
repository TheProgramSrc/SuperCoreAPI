package xyz.theprogramsrc.supercoreapi.spigot.guis;

import org.bukkit.inventory.ItemStack;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.Action;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

/**
 * Representation of a GUI Button
 */
public class GUIButton {

    private int slot;
    private final ItemStack itemStack;
    private Action action;

    /**
     * Constructor of a GUI Button
     * @param slot The slot where should be placed (See https://i.theprogramsrc.xyz/files/DoubleChest-slots.png)
     * @param itemStack The item to place {@link SimpleItem}
     * @param action The action to execute when is clicked
     */
    public GUIButton(int slot, ItemStack itemStack, Action action){
        this.slot = slot;
        this.itemStack = itemStack;
        this.action = action;
    }

    /**
     * Constructor of a GUI Button
     * @param slot The slot where should be placed (See https://i.theprogramsrc.xyz/files/DoubleChest-slots.png)
     * @param simpleItem The item to place
     * @param action The action to execute when is clicked
     */
    public GUIButton(int slot, SimpleItem simpleItem, Action action){
        this(slot, simpleItem.build(), action);
    }

    /**
     * Constructor of a GUI Button
     * @param slot The slot where should be placed (See https://i.theprogramsrc.xyz/files/DoubleChest-slots.png)
     * @param itemStack The item to place {@link SimpleItem}
     */
    public GUIButton(int slot, ItemStack itemStack) {
        this(slot, itemStack, null);
    }

    /**
     * Constructor of a GUI Button
     * @param slot The slot where should be placed (See https://i.theprogramsrc.xyz/files/DoubleChest-slots.png)
     * @param simpleItem The item to place
     */
    public GUIButton(int slot, SimpleItem simpleItem){
        this(slot, simpleItem.build());
    }

    /**
     * Constructor of a GUI Button
     * @param itemStack The item to place {@link SimpleItem}
     */
    public GUIButton(ItemStack itemStack){
        this(-1, itemStack);
    }

    /**
     * Constructor of a GUI Button
     * @param simpleItem The item to place
     */
    public GUIButton(SimpleItem simpleItem){
        this(simpleItem.build());
    }

    /**
     * Sets the action to trigger when the button is clicked
     * @param action the action
     * @return this GUIButton
     */
    public GUIButton setAction(Action action) {
        this.action = action;
        return this;
    }

    /**
     * Sets the slot of this button
     * @param slot the slot
     * @return this GUIButton
     */
    public GUIButton setSlot(int slot){
        this.slot = slot;
        return this;
    }

    /**
     * Gets the action to trigger when the button is clicked
     * @return the action
     */
    public Action getAction() {
        return action;
    }

    /**
     * Gets the item to place
     * @return the item
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Gets the slot where should be placed the button
     * @return the slot
     */
    public int getSlot() {
        return slot;
    }
}

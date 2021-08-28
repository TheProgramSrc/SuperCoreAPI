package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

import java.util.LinkedHashMap;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;

/**
 * Representation of the Gui Model
 * @since 5.2.0
 */
public class GuiModel extends SpigotModule{

    private GuiTitle title;
    private GuiRows rows;
    private final LinkedHashMap<Integer, GuiEntry> buttons;

    public GuiModel(GuiTitle title, GuiRows rows) {
        this.title = title;
        this.rows = rows;
        this.buttons = new LinkedHashMap<>();
    }

    public GuiTitle getTitle() {
        return title;
    }

    /**
     * Sets the title of the Gui
     * @param title The title of the Gui
     */
    public void setTitle(GuiTitle title) {
        this.title = title;
    }

    public GuiRows getRows() {
        return rows;
    }

    /**
     * Sets the rows of the Gui
     * @param rows The rows of the Gui
     */
    public void setRows(GuiRows rows) {
        this.rows = rows;
    }

    public LinkedHashMap<Integer, GuiEntry> getButtons() {
        return buttons;
    }

    /**
     * Clear the buttons from the Gui
     */
    public void clearButtons(){
        this.buttons.clear();
    }

    /**
     * Sets a new button in the Gui overriding it if it already exists
     * @param slot The slot of the button to add
     * @param entry The entry of the button to add
     */
    public void setButton(int slot, GuiEntry entry) {
        buttons.put(slot, entry);
    }

    /**
     * Adds the button to the first empty slot
     * @param entry The entry of the button to add
     */
    public void addButton(GuiEntry entry){
        for(int i = 0; i < this.rows.size; i++){
            if(!this.buttons.containsKey(i)){
                this.buttons.put(i, entry);
                break;
            }
        }
    }

    /**
     * Fills all the empty slots with the given entry
     * @param entry The entry of the button to add to all the empty slots
     * @since 5.2.1
     */
    public void fillEmptySlotsWithEntry(GuiEntry entry){
        for(int i = 0; i < this.rows.size; i++){
            if(!this.buttons.containsKey(i)){
                this.setButton(i, entry);
            }
        }
    }

    /**
     * Fills the empty slots with the empty item. 
     * See {@link xyz.theprogramsrc.supercoreapi.spigot.items.PreloadedItems#emptyItem}
     * @since 5.2.1
     */
    public void fillEmptySlots(){
        this.fillEmptySlotsWithEntry(new GuiEntry(this.getPreloadedItems().emptyItem()));
    }
    
    /**
     * Fills the specified slots with the specified entry.
     * @param entry The entry of the button to add to the given slots
     * @param slots The slots to fill
     * @since 5.2.1
     */

    public void fillWithEntry(GuiEntry entry, int[] slots){
        for(int slot : slots){
            this.setButton(slot, entry);
        }
    }

    /**
     * Fills the specified slots with an empty item.
     * @param slots The slots to fill with the empty item
     * @since 5.2.1
     */
    public void fillWithEmptyItem(int[] slots){
        this.fillWithEntry(new GuiEntry(this.getPreloadedItems().emptyItem()), slots);
    }
}

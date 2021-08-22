package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

import java.util.LinkedHashMap;

/**
 * Representation of the Gui Model
 * @since 5.2.0
 */
public class GuiModel {

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

    
    
}

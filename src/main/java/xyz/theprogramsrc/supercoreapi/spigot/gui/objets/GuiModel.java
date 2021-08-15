package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

import java.util.LinkedHashMap;

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

    public void setTitle(GuiTitle title) {
        this.title = title;
    }

    public GuiRows getRows() {
        return rows;
    }

    public void setRows(GuiRows rows) {
        this.rows = rows;
    }

    public LinkedHashMap<Integer, GuiEntry> getButtons() {
        return buttons;
    }

    public void clearButtons(){
        this.buttons.clear();
    }

    public void setButton(int slot, GuiEntry entry) {
        buttons.put(slot, entry);
    }

    public void addButton(GuiEntry e){
        for(int i = 0; i < this.rows.size; i++){
            if(!this.buttons.containsKey(i)){
                this.buttons.put(i, e);
                break;
            }
        }
    }

    
    
}

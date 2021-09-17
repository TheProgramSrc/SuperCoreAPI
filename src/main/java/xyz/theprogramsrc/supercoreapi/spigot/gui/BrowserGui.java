package xyz.theprogramsrc.supercoreapi.spigot.gui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.dialog.Dialog;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiAction;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiEntry;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiModel;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiRows;

/**
 * Representation of a Browser GUI
 * @since 5.2.0
 */
public abstract class BrowserGui<OBJ> extends Gui {

    public boolean backEnabled = false;
    public String searchTerm = null;
    public int maxItemsPerPage = 36;
    public int page = 0;

    /**
     * Creates a new Browser GUI
     * @param player The player
     * @param automaticallyOpen If the GUI should be opened automatically
     */
    public BrowserGui(Player player, boolean automaticallyOpen) {
        super(player, automaticallyOpen);
    }


    /**
     * Creates a new Browser GUI but automatically being opened. See {@link BrowserGui#BrowserGui(Player, boolean)}
     * @param player The player
     */
    public BrowserGui(Player player){
        this(player, true);
    }

    @Override
    public GuiRows getRows() {
        return GuiRows.SIX;
    }

    @Override
    public void onBuild(GuiModel model) {
        LinkedList<OBJ> objects = new LinkedList<>(Arrays.stream(this.getObjects()).filter(obj -> {
            String[] tags = this.getSearchTags(obj);
            if(tags == null || this.searchTerm == null) return true;
            if(tags.length == 0) return true;
            return Arrays.stream(tags).anyMatch(tag -> this.getSuperUtils().removeColor(tag).contains(this.getSuperUtils().removeColor(this.searchTerm)));
        }).collect(Collectors.toList()));
        int offset = this.page * this.maxItemsPerPage;
        int itemsToTake = Math.min(offset + this.maxItemsPerPage, objects.size());
        int maxPages = (int)Math.round(Math.ceil((double)objects.size() / (double)maxItemsPerPage));
        LinkedList<GuiEntry> entries = objects.subList(offset, itemsToTake).stream().map(this::getEntry).collect(Collectors.toCollection(LinkedList::new));
        int slot = 0;
        for(int o = 0; o < entries.size(); o++) {
            model.setButton(slot, entries.get(o));
            slot++;
        }

        GuiEntry searchEntry;
        if(this.searchTerm == null){
            searchEntry = new GuiEntry(this.getPreloadedItems().getSearchItem().build(), a-> new Dialog(this.player){
                @Override
                public String getTitle() {
                    return Base.DIALOG_SEARCH_TITLE.toString();
                }

                @Override
                public String getSubtitle() {
                    return Base.DIALOG_SEARCH_SUBTITLE.toString();
                }

                @Override
                public String getActionbar() {
                    return Base.DIALOG_SEARCH_ACTIONBAR.toString();
                }

                @Override
                public boolean onResult(String input){
                    BrowserGui.this.searchTerm = input;
                    return true;
                }
            }.setRecall(player -> {
                this.page = 0;
                this.getSpigotTasks().runTask(this::open);
            }));
        }else{
            searchEntry = new GuiEntry(this.getPreloadedItems().getEndSearchItem().build(), a -> {
                this.searchTerm = null;
                this.page = 0;
                this.open();
            });
        }
        model.setButton(49, searchEntry);

        if(this.page != 0){
            model.setButton(52, new GuiEntry(this.getPreloadedItems().getPreviousItem().build(), a -> {
                this.page -= 1;
                this.open();
            }));
        }

        if(this.page+1 < maxPages){
            model.setButton(53, new GuiEntry(this.getPreloadedItems().getNextItem().build(), a -> {
                this.page += 1;
                this.open();
            }));
        }

        if(this.backEnabled){
            model.setButton(45, new GuiEntry(this.getPreloadedItems().getBackItem().build(), this::onBack));
        }
    }

    /**
     * Gets a button from an object
     * @param obj The Object
     * @return The button for the Object
     */
    public abstract GuiEntry getEntry(OBJ obj);

    /**
     * Gets the search tags from an object
     * @param obj The Object
     * @return The search tags for the Object
     */
    public abstract String[] getSearchTags(OBJ obj);

    /**
     * Gets all the objects to show
     * @return The objects
     */
    public abstract OBJ[] getObjects();
    
    /**
     * Executed when the back button is clicked
     * @param action The GuiAction
     */
    public void onBack(GuiAction action){}
}

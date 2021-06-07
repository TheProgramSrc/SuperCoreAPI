package xyz.theprogramsrc.supercoreapi.spigot.guis;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.dialog.Dialog;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIOpenEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BrowserGUI<OBJ> extends GUI {

    private String searchTerm = null;
    public int maxItemsPerPage = 36;
    public boolean backEnabled = false;
    private int page = 0;

    /**
     * Creates a BrowserGUI
     * @param player the player who will see the BrowserGUI
     */
    public BrowserGUI(Player player){
        super(player);
    }

    @Override
    protected GUIRows getRows() {
        return GUIRows.SIX;
    }

    @Override
    public void onEvent(GUIEvent event) {
        if(event instanceof GUIOpenEvent){
            this.clearButtons();
            List<OBJ> objectsFound = Arrays.stream(this.getObjects()).filter(obj->{
                if(this.searchTerm != null){
                    String itemName = this.getSuperUtils().removeColor(new SimpleItem(this.getButton(obj).getItemStack()).getDisplayName()).toLowerCase();
                    String search = this.getSuperUtils().removeColor(this.searchTerm).toLowerCase();
                    return itemName.contains(search);
                }

                return true;
            }).collect(Collectors.toList());
            int index0 = this.page * this.maxItemsPerPage;
            int index1 = Math.min(index0 + this.maxItemsPerPage, objectsFound.size());
            int maxPages = (int)Math.round(Math.ceil((double)objectsFound.size() / (double)maxItemsPerPage));
            List<GUIButton> buttons =  objectsFound.subList(index0, index1).stream().map(this::getButton).collect(Collectors.toList());
            int i = 0;
            for(int o = 0; o < buttons.size(); ++o){
                buttons.set(o, buttons.get(o).setSlot(i));
                i++;
            }

            this.addButtons(buttons);
            this.removeButtons(43, 44,45,46,47,48,49,50,51,52,53);
            if(this.searchTerm == null){
                this.addButton(new GUIButton(49, this.getPreloadedItems().getSearchItem(), a-> new Dialog(this.getPlayer()){

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
                    public boolean onResult(String playerInput) {
                        BrowserGUI.this.searchTerm = playerInput;
                        return true;
                    }
                }.setRecall(player ->{
                    this.page = 0;
                    this.getSpigotTasks().runTask(this::open);
                })));
            }else{
                this.addButton(new GUIButton(49, this.getPreloadedItems().getEndSearchItem(), a->{
                    this.searchTerm = null;
                    this.page = 0;
                    this.open();
                }));
            }

            if(this.page != 0){
                this.addButton(new GUIButton(52, this.getPreloadedItems().getPreviousItem()).setAction(a -> {
                    this.page -= 1;
                    this.open();
                }));
            }
            if(this.page+1 < maxPages){
                this.addButton(new GUIButton(53, this.getPreloadedItems().getNextItem()).setAction(a -> {
                    this.page += 1;
                    this.open();
                }));
            }

            if(this.backEnabled){
                this.addButton(new GUIButton(45, this.getPreloadedItems().getBackItem()).setAction(this::onBack));
            }
        }
    }

    /**
     * Gets all the objects to show
     * @return The objects
     */
    public abstract OBJ[] getObjects();

    /**
     * Gets a button from an object
     * @param obj The Object
     * @return The button for the Object
     */
    public abstract GUIButton getButton(OBJ obj);

    /**
     * Executed when the back button is clicked
     * @param clickAction The ClickAction
     */
    public void onBack(ClickAction clickAction){

    }


}

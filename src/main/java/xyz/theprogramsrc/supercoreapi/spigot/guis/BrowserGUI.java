package xyz.theprogramsrc.supercoreapi.spigot.guis;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.dialog.Dialog;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BrowserGUI<OBJ> extends GUI {

    private String searchTerm = null;
    public int maxItemsPerPage = 36;
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
    public GUIButton[] getButtons() {
        OBJ[] objs = this.getObjects();
        List<OBJ> objectsFound = new ArrayList<>();
        for (OBJ obj : objs) {
            if(obj != null){
                if(this.searchTerm == null){
                    objectsFound.add(obj);
                }else{
                    GUIButton button = this.getButton(obj);
                    String name = ChatColor.stripColor(new SimpleItem(button.getItemStack()).getDisplayName()).toLowerCase();
                    if(name.contains(ChatColor.stripColor(this.searchTerm.toLowerCase()))){
                        objectsFound.add(obj);
                    }
                }
            }
        }
        int index0 = this.page * this.maxItemsPerPage;
        int index1 = Math.min(index0 + this.maxItemsPerPage, objectsFound.size());
        int maxPages = (int)Math.round(Math.ceil((double)objectsFound.size() / (double)maxItemsPerPage));
        List<GUIButton> buttons1 = new ArrayList<>();
        int slot = 0;
        for (GUIButton b : objectsFound.subList(index0, index1).stream().map(this::getButton).collect(Collectors.toList())) {
            b.setSlot(slot);
            buttons1.add(b);
            slot++;
        }
        List<GUIButton> buttons = new ArrayList<>(buttons1);
        buttons1.clear(); // Save memory
        objectsFound.clear(); // Save memory

        buttons.add(new GUIButton(49, this.searchTerm == null ? this.getPreloadedItems().getSearchItem() : this.getPreloadedItems().getEndSearchItem()).setAction(a -> {
            if(this.searchTerm == null){
                new Dialog(this.getPlayer()){

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
                    this.open();
                });
            }else{
                this.searchTerm = null;
                this.page = 0;
                this.open();
            }
        }));
        buttons.add(new GUIButton(45, this.getPreloadedItems().getBackItem()).setAction(this::onBack));

        if(this.page != 0){
            buttons.add(new GUIButton(52, this.getPreloadedItems().getPreviousItem()).setAction(a -> {
                this.page--;
                this.open();
            }));
        }
        if(this.page+1 < maxPages){
            buttons.add(new GUIButton(53, this.getPreloadedItems().getNextItem()).setAction(a -> {
                this.page++;
                this.open();
            }));
        }

        GUIButton[] guiButtons = new GUIButton[buttons.size()];
        guiButtons = buttons.toArray(guiButtons);
        return guiButtons;
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
    public abstract void onBack(ClickAction clickAction);
}

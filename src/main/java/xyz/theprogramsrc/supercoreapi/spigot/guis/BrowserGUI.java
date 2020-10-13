package xyz.theprogramsrc.supercoreapi.spigot.guis;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.dialog.Dialog;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

import java.util.Arrays;
import java.util.Iterator;
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
        List<OBJ> objectsFound = Arrays.stream(objs).filter(o->{
            if(this.searchTerm == null){
                return true;
            }else{
                String itemDisplayName = this.getSuperUtils().removeColor(new SimpleItem(this.getButton(o).getItemStack()).getDisplayName());
                String searchTerm = this.getSuperUtils().removeColor(this.searchTerm);
                return itemDisplayName.toLowerCase().contains(searchTerm.toLowerCase());
            }
        }).collect(Collectors.toList());
        int index0 = this.page * maxItemsPerPage;
        int index1 = Math.min(index0 + maxItemsPerPage, objectsFound.size());
        List<GUIButton> buttons = objectsFound.subList(index0, index1).stream().map(this::getButton).collect(Collectors.toList());
        int slot = 0;
        for(Iterator<GUIButton> it = buttons.iterator(); it.hasNext(); ++slot){
            it.next().setSlot(slot);
        }

        int x = (int)Math.round(Math.ceil((double)objectsFound.size() / (double)maxItemsPerPage));
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
                        this.getSpigotTasks().runTask(()-> {
                            BrowserGUI.this.searchTerm = playerInput;
                            BrowserGUI.this.page = 0;
                            BrowserGUI.this.open();
                        });
                        return true;
                    }
                };
            }else{
                this.page = 0;
                this.searchTerm = null;
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
        if(this.page+1 < x){
            buttons.add(new GUIButton(53, this.getPreloadedItems().getNextItem()).setAction(a -> {
                this.page++;
                this.open();
            }));
        }

        return buttons.stream().filter(Utils::nonNull).toArray(GUIButton[]::new);
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

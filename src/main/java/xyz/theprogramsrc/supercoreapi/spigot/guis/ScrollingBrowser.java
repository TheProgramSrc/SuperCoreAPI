package xyz.theprogramsrc.supercoreapi.spigot.guis;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.dialog.Dialog;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ScrollingBrowser<OBJ> extends GUI{

    private String search;
    private final int[] slots = new int[]{0,1,2,3,4,5, 9,10,11,12,13,14, 18,19,20,21,22,23, 27,28,29,30,31,32, 36,37,38,39,40,41, 45,46,47,48,49,50};
    private int page = 1;
    private boolean backEnabled = true;

    public ScrollingBrowser(Player player) {
        super(player);
    }

    @Override
    protected GUIRows getRows() {
        return GUIRows.SIX;
    }

    @Override
    protected GUIButton[] getButtons() {
        this.clearButtons();
        List<OBJ> filteredObjs = Arrays.stream(this.getObjects()).filter(obj->{
            if(this.search == null){
                return true;
            }else{
                String itemName = this.getSuperUtils().removeColor(new SimpleItem(this.getButton(obj).getItemStack()).getDisplayName()).toLowerCase();
                String search = this.getSuperUtils().removeColor(this.search).toLowerCase();
                return itemName.contains(search);
            }
        }).collect(Collectors.toList());
        int from = this.page * 36 - 36;
        int to = Math.min(filteredObjs.size(), from + 36);
        int max = (int)Math.round(Math.ceil((double)filteredObjs.size() / 36.0D));
        List<GUIButton> buttons = filteredObjs.subList(from, to).stream().map(this::getButton).collect(Collectors.toList());
        for(int i = 0, o = 0; i < Math.min(buttons.size(), this.slots.length); ++i, ++o){
            int slot = this.slots[i];
            GUIButton button = buttons.get(o);
            button.setSlot(slot);
            buttons.set(o, button);
        }

        if(this.backEnabled){
            buttons.add(new GUIButton(8, this.getPreloadedItems().getBackItem(), this::onBack));
        }

        if(this.page != 1){
            buttons.add(new GUIButton(26, this.getPreloadedItems().getPreviousItem(), a->{
                this.page -= 1;
                this.open();
            }));
        }

        if(this.page != max){
            buttons.add(new GUIButton(35, this.getPreloadedItems().getNextItem(), a->{
                this.page += 1;
                this.open();
            }));
        }

        if(this.search == null){
            buttons.add(new GUIButton(53, this.getPreloadedItems().getSearchItem(), a-> new Dialog(this.getPlayer()){

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
                    ScrollingBrowser.this.search = playerInput;
                    return true;
                }
            }.setRecall(player ->{
                this.page = 1;
                this.getSpigotTasks().runTask(this::open);
            })));
        }else{
            buttons.add(new GUIButton(53, this.getPreloadedItems().getEndSearchItem(), a->{
                this.search = null;
                this.page = 1;
                this.open();
            }));
        }

        GUIButton[] guiButtons = new GUIButton[buttons.size()];
        guiButtons = buttons.toArray(guiButtons);
        return guiButtons;
    }

    public abstract OBJ[] getObjects();

    public abstract GUIButton getButton(OBJ obj);

    public void setBackEnabled(boolean backEnabled) {
        this.backEnabled = backEnabled;
        this.open();
    }

    public void onBack(ClickAction clickAction){

    }
}

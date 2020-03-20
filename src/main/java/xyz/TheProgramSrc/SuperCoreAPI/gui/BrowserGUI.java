/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.dialogs.Dialog;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.actions.ClickAction;
import xyz.TheProgramSrc.SuperCoreAPI.items.SimpleItem;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BrowserGUI<OBJ> extends GUI{

    private String searchTerm = null;
    public int maxItemsPerPage = 36;
    private int page = 0;

    public BrowserGUI(SuperCore core, Player player){
        super(core, player);
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public GUIButton[] getButtons() {
        OBJ[] objs = this.getObjects();
        List<OBJ> objectsFound = Arrays.stream(objs).filter(o->{
            if(this.searchTerm == null){
                return true;
            }else{
                return ChatColor.stripColor(new SimpleItem(this.getButton(o).getItemStack()).getDisplayName().toLowerCase()).contains(ChatColor.stripColor(this.searchTerm.toLowerCase()));
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
                new Dialog(this.getCore(), this.getPlayer()){

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
                }.setRecall(p-> {
                    this.page = 0;
                    this.open();
                });
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

    public abstract OBJ[] getObjects();

    public abstract GUIButton getButton(OBJ obj);

    public abstract void onBack(ClickAction clickAction);
}

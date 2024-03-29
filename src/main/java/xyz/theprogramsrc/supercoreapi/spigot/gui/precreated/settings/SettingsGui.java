package xyz.theprogramsrc.supercoreapi.spigot.gui.precreated.settings;

import org.bukkit.entity.Player;

import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.gui.Gui;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiAction;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiEntry;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiModel;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiRows;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiTitle;

public abstract class SettingsGui extends Gui{

    private final int[] containerSlots = {
        19, 20, 21, 22, 23, 24, 25,
        28, 29, 30, 31, 32, 33, 34,
        37, 38, 39, 40, 41, 42, 43,
    };
    private int current;
    private SettingPane[] settingPanes;

    public SettingsGui(Player player){
        super(player, false);
        this.current = -1;
        this.settingPanes = this.getSettingPanes();
        this.open();
    }

    @Override
    public void open() {
        this.settingPanes = this.getSettingPanes();
        if(this.settingPanes == null)
            this.settingPanes = new SettingPane[0];
        super.open();
    }

    @Override
    public GuiRows getRows() {
        if(this.current == -1){
            return GuiRows.SIX;
        }

        SettingPane settingPane = this.settingPanes[this.current];
        return settingPane.getRows() != null ? settingPane.getRows() : GuiRows.SIX;
    }
    
    @Override
    public GuiTitle getTitle() {
        String result;
        if(this.current != -1){
            SettingPane settingPane = this.settingPanes[this.current];
            result = "&c" + Base.SETTING_PANE_GUI_TITLE.options().placeholder("{Setting}", settingPane.getDisplayName()).get();
        }else{
            result = "&c" + Base.SETTINGS_GUI_TITLE;
        }

        return GuiTitle.of(result);
    }
    
    @Override
    public void onBuild(GuiModel model) {
        model.clearButtons();
        if(this.current == -1){
            model.setButton(0, new GuiEntry(this.getPreloadedItems().getBackItem(), this::onBack));
            for(int i = 0; i < this.containerSlots.length; i++){
                int slot = this.containerSlots[i];
                if(i < this.settingPanes.length){
                    int newCurrent = i;
                    model.setButton(slot, new GuiEntry(this.settingPanes[i].getDisplayItem(), a -> {
                        this.current = newCurrent;
                        this.open();
                    }));
                }else{
                    model.setButton(slot, new GuiEntry(this.getPreloadedItems().emptyItem()));
                }
            }
        }else{
            SettingPane pane = this.settingPanes[current];
            int[] paneContainerSlots = pane.getContainerSlots();
            GuiEntry[] paneEntries = pane.getButtons();
            for(int i = 0; i < paneContainerSlots.length; i++){
                int slot = paneContainerSlots[i];
                if(i < paneEntries.length){
                    model.setButton(slot, paneEntries[i]);
                }else{
                    if(pane.showItemsForEmpty()){
                        model.setButton(slot, new GuiEntry(this.getPreloadedItems().emptyItem()));
                    }
                }
            }

            model.setButton(0, new GuiEntry(this.getPreloadedItems().getBackItem(), a -> {
                this.current = -1;
                this.open();
            }));
        }
    }

    public abstract SettingPane[] getSettingPanes();

    public abstract void onBack(GuiAction action);

}

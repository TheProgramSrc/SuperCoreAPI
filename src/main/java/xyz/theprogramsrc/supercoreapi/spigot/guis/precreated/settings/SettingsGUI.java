package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.settings;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;

import java.util.LinkedList;

public abstract class SettingsGUI extends GUI {

    private final int[] containerSlots = {
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43,
    };
    private int current;
    private SettingPane[] settingPanes;

    public SettingsGUI(Player player) {
        super(player);
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
    protected GUIRows getRows() {
        if(this.current == -1){
            return GUIRows.SIX;
        }

        SettingPane settingPane = this.settingPanes[this.current];
        return settingPane.getRows() != null ? settingPane.getRows() : GUIRows.SIX;
    }

    @Override
    protected String getTitle() {
        if(this.current != -1){
            SettingPane settingPane = this.settingPanes[this.current];
            return "&c" + Base.SETTING_PANE_GUI_TITLE.options().placeholder("{Setting}", settingPane.getDisplayName()).get();
        }

        return "&c" + Base.SETTINGS_GUI_TITLE.toString();
    }

    public abstract SettingPane[] getSettingPanes();

    @Override
    protected GUIButton[] getButtons() {
        LinkedList<GUIButton> buttons = new LinkedList<>();
        if(this.current == -1){
            buttons.add(new GUIButton(0, this.getPreloadedItems().getBackItem(), this::onBack));

            for(int i = 0; i < this.containerSlots.length; ++i){
                int slot = this.containerSlots[i];
                if(i < this.settingPanes.length){
                    int finalI = i;
                    buttons.add(new GUIButton(slot, this.settingPanes[i].getDisplayItem(), a-> {
                        this.current = finalI;
                        this.open();
                    }));
                }else{
                    buttons.add(new GUIButton(slot, this.getPreloadedItems().emptyItem()));
                }
            }
        }else{
            buttons.add(new GUIButton(0, this.getPreloadedItems().getBackItem(), a-> {
                this.current = -1;
                this.open();
            }));
            buttons.addAll(Utils.toList(this.settingPanes[current].getButtons()));
        }

        return buttons.toArray(new GUIButton[0]);
    }

    public abstract void onBack(ClickAction clickAction);
}

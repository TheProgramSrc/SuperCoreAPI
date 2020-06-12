package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

/**
 * Executed when a player clicks outside the GUI
 */
public class GUIOutsideClickEvent extends GUIEvent{
    private boolean canDrop;

    public GUIOutsideClickEvent(GUI gui, Player player) {
        super(gui, player);
    }

    public void setCanDrop(boolean canDrop){
        this.canDrop = canDrop;
    }

    public boolean canDrop() {
        return canDrop;
    }
}

package xyz.theprogramsrc.supercoreapi.spigot.guis.events;


import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

/**
 * Executed when a player clicks on a empty slot
 */
public class GUIEmptyClickEvent extends GUIEvent{

    private final int slot;

    public GUIEmptyClickEvent(GUI gui, int slot, Player player) {
        super(gui, player);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}

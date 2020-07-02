package xyz.theprogramsrc.supercoreapi.spigot.guis.events;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;

public class GUIOpenEvent extends GUIEvent{

    public GUIOpenEvent(GUI gui, Player player) {
        super(gui, player);
    }

}

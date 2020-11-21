package xyz.theprogramsrc.supercoreapi.global.placeholders;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

import java.util.LinkedList;

public class SpigotPlaceholderManager extends PlaceholderManager{

    private final boolean placeholderAPI;

    public SpigotPlaceholderManager(SpigotPlugin plugin){
        this.placeholderAPI = plugin.getSuperUtils().isPlugin("PlaceholderAPI");
    }

    public String applyPlaceholders(String string, Player player) {
        LinkedList<Placeholder> placeholders = new LinkedList<>(this.placeholders);
        placeholders.add(new Placeholder("{Player}", player.getName()));
        placeholders.add(new Placeholder("{DisplayName}", player.getDisplayName()));
        placeholders.add(new Placeholder("{World}", player.getWorld().getName()));
        placeholders.add(new Placeholder("{UUID}", player.getUniqueId().toString()));
        String result = PlaceholderManager.applyPlaceholders(placeholders, string);
        if(this.placeholderAPI){
            result = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, result);
        }

        return result;
    }
}

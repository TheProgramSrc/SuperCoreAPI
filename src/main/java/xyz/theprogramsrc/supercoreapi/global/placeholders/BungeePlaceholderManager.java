package xyz.theprogramsrc.supercoreapi.global.placeholders;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.LinkedList;

public class BungeePlaceholderManager extends PlaceholderManager{

    public String applyPlaceholders(String string, ProxiedPlayer player) {
        LinkedList<Placeholder> placeholders = new LinkedList<>(this.placeholders);
        placeholders.add(new Placeholder("{Player}", player.getName()));
        placeholders.add(new Placeholder("{DisplayName}", player.getDisplayName()));
        placeholders.add(new Placeholder("{UUID}", player.getUniqueId().toString()));
        placeholders.add(new Placeholder("{Server}", player.getServer().getInfo().getName()));
        placeholders.add(new Placeholder("{IP}", player.getPendingConnection().getVirtualHost().getAddress().getHostAddress()));
        return PlaceholderManager.applyPlaceholders(placeholders, string);
    }
}

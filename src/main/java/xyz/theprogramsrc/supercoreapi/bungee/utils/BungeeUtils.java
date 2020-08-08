package xyz.theprogramsrc.supercoreapi.bungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.theprogramsrc.supercoreapi.SuperUtils;

/**
 * For documentation see {@link SuperUtils}
 */
public class BungeeUtils implements SuperUtils {

    @Override
    public ChatColor parseHex(String hex) {
        return ChatColor.of(hex);
    }

    @Override
    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public String removeColor(String message) {
        return ChatColor.stripColor(message);
    }

    @Override
    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(new TextComponent(color(message)));
    }
}

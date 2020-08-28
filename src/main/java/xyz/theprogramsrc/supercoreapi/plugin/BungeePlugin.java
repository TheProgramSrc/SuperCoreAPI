package xyz.theprogramsrc.supercoreapi.plugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlugin extends Plugin {

    @Override
    public void onEnable() {
        this.log("&3SuperCoreAPI &awas loaded as .jar file");
    }

    @Override
    public void onDisable() {
        this.log("&3SuperCoreAPI &awas disabled");
    }

    private void log(String s){
        this.getProxy().getConsole().sendMessage(new TextComponent(c(s)));
    }

    private String c(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}

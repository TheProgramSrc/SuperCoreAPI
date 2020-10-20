package xyz.theprogramsrc.supercoreapi.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.log("&3SuperCoreAPI &awas loaded as .jar file");
    }

    @Override
    public void onDisable() {
        this.log("&3SuperCoreAPI &awas disabled");
    }

    private void log(String s){
        Bukkit.getConsoleSender().sendMessage(c(s));
    }

    private String c(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}

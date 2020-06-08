package xyz.theprogramsrc.supercoreapi.bungee.utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Listener;
import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;
import xyz.theprogramsrc.supercoreapi.bungee.BungeePlugin;

public class BungeeConsole extends BungeeModule {

    public BungeeConsole(BungeePlugin plugin) {
        super(plugin);
    }

    public void listeners(Listener... listeners){
        ((BungeePlugin)this.plugin).listener(listeners);
    }

    public void execute(String command){
        this.getProxy().getPluginManager().dispatchCommand(this.getProxy().getConsole(), command);
    }

    public CommandSender parseCommandSender(){
        return this.getProxy().getConsole();
    }

}

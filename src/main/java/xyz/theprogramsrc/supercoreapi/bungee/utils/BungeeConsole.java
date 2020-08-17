package xyz.theprogramsrc.supercoreapi.bungee.utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Listener;
import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;

/**
 * Representation of a BungeeCord Console
 */
public class BungeeConsole extends BungeeModule {

    /**
     * Registers the specified listeners
     * @param listeners The listeners to register
     */
    public void listeners(Listener... listeners){
        this.bungeePlugin.listener(listeners);
    }

    /**
     * Executes a command as Console
     * @param command The command to execute
     */
    public void execute(String command){
        this.getProxy().getPluginManager().dispatchCommand(this.getProxy().getConsole(), command);
    }

    /**
     * Gets the {@link CommandSender}
     * @return The CommandSender
     */
    public CommandSender parseCommandSender(){
        return this.getProxy().getConsole();
    }

}

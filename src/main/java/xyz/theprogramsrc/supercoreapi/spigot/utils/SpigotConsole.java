package xyz.theprogramsrc.supercoreapi.spigot.utils;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

/**
 * Representation of a SpigotConsole
 */
public class SpigotConsole extends SpigotModule {

    /**
     * Registers listeners
     * @param listeners the listeners
     */
    public void listeners(Listener... listeners){
        ((SpigotPlugin)this.plugin).listener(listeners);
    }

    /**
     * Executes a command in the console
     * @param command the command
     */
    public void execute(final String command){
        ((SpigotPlugin)this.plugin).getServer().getScheduler().runTask(((SpigotPlugin)this.plugin), ()->((SpigotPlugin)this.plugin).getServer().dispatchCommand(((SpigotPlugin)this.plugin).getServer().getConsoleSender(), command));
    }

    /**
     * Log a message in the console
     * @param msg the message
     */
    public void logMessage(String msg){
        this.log(msg);
    }

    /**
     * Gets the command sender
     * @return the command sender
     */
    public ConsoleCommandSender parseConsoleCommandSender(){
        return ((SpigotPlugin)this.plugin).getServer().getConsoleSender();
    }
}

/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.utils;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

public class SpigotConsole extends SpigotModule {

    public SpigotConsole(SpigotPlugin plugin){
        super(plugin);
    }

    public void listeners(Listener... listeners){
        ((SpigotPlugin)this.plugin).listener(listeners);
    }

    public void execute(final String command){
        ((SpigotPlugin)this.plugin).getServer().getScheduler().runTask(((SpigotPlugin)this.plugin), ()->((SpigotPlugin)this.plugin).getServer().dispatchCommand(((SpigotPlugin)this.plugin).getServer().getConsoleSender(), command));
    }

    public void logMessage(String msg){
        this.log(msg);
    }

    public ConsoleCommandSender parseConsoleCommandSender(){
        return ((SpigotPlugin)this.plugin).getServer().getConsoleSender();
    }
}

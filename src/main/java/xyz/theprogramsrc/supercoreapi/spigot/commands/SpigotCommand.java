/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotConsole;

public abstract class SpigotCommand extends SpigotModule {

    public SpigotCommand(final SpigotPlugin plugin) {
        super(plugin);
        BukkitCommand command = new BukkitCommand(this.getCommand(), "", "/", Utils.toList(this.getAliases())) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                CommandResult result;
                if (sender instanceof Player) {
                    result = SpigotCommand.this.onPlayerExecute(((Player) sender), args);
                } else {
                    result = SpigotCommand.this.onConsoleExecute(new SpigotConsole(plugin), args);
                }
                SpigotCommand.this.onResult(sender, result);
                return true;
            }
        };
        command.setPermission(this.getPermission());
        plugin.getSuperUtils().registerBukkitCommand(command);
        plugin.addDisableHook(()-> plugin.getSuperUtils().unregisterCommand(this.getCommand()));
    }

    private void onResult(CommandSender sender, CommandResult result){
        if(result == CommandResult.NO_PERMISSION){
            sender.sendMessage(this.plugin.getSuperUtils().color(Base.NO_PERMISSION.toString()));
        }else if(result == CommandResult.NO_ACCESS){
            sender.sendMessage(this.plugin.getSuperUtils().color(Base.NO_ACCESS.toString()));
        }else if(result == CommandResult.NOT_SUPPORTED){
            if(sender instanceof Player){
                sender.sendMessage(this.plugin.getSuperUtils().color(Base.NOT_SUPPORTED.options().vars(Base.CONSOLE.toString()).toString()));
            }else{
                sender.sendMessage(this.plugin.getSuperUtils().color(Base.NOT_SUPPORTED.options().vars(Base.PLAYERS.toString()).toString()));
            }
        }else if(result == CommandResult.INVALID_ARGS){
            sender.sendMessage(this.plugin.getSuperUtils().color(Base.INVALID_ARGUMENTS.toString()));
        }
    }

    public abstract String getCommand();

    public String getPermission(){
        return null;
    }

    public String[] getAliases(){
        return new String[0];
    }

    public abstract CommandResult onPlayerExecute(Player player, String[] args);

    public abstract CommandResult onConsoleExecute(SpigotConsole console, String[] args);

}

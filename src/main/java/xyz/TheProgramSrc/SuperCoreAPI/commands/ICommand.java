/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;


@SuppressWarnings("ALL")
public abstract class ICommand extends Command{

    private SuperCore core;
    private String command;
    private ArrayList<String> aliases;

    public ICommand(SuperCore core, String command) {
        this(core, command, new String[0]);
    }

    public ICommand(SuperCore core, String command, String... aliases){
        this(core, command, "None", aliases);
    }

    public ICommand(SuperCore core, String command, String permission, String... aliases){
        super(command);
        this.core = core;
        this.command = command;
        this.aliases = new ArrayList<>(Arrays.asList(aliases));
        this.setPermission(permission);
        this.setAliases(this.getAliases());
        this.setUsage(this.getUsage());
        this.setDescription(this.getDescription());

        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            ((CommandMap)bukkitCommandMap.get(Bukkit.getServer())).register(command,this);
        } catch (Exception ex) {
            this.core.debug(ex);
        }
    }

    public boolean execute(CommandSender commandSender, String s, String[] args) {
        Result result;
        if (commandSender instanceof Player) {
            if (!this.getPermission().equalsIgnoreCase("None") && !commandSender.hasPermission(this.getPermission())) {
                result = Result.NO_PERMISSION;
            } else {
                result = this.onPlayerExecute((Player)commandSender, args);
            }
        } else {
            result = this.onConsoleExecute(commandSender, args);
        }

        this.sendResult(result, commandSender);
        return true;
    }

    private void sendResult(Result result, CommandSender sender) {
        String msg;
        if(sender instanceof Player){
            if(result == Result.NOT_SUPPORTED){
                msg = this.getCore().getPrefix() + Base.NOT_SUPPORTED.toString();
                Utils.sendMessage(sender, msg.replace("{Supported}", sender instanceof Player ? Base.CONSOLE.toString() : Base.PLAYERS.toString()));
            }else if(result == Result.NO_PERMISSION){
                msg = this.getCore().getPrefix() + Base.NO_PERMISSION.toString();
                Utils.sendMessage(sender, msg);
            }else if(result == Result.INVALID_ARGS){
                msg = this.getCore().getPrefix() + Base.INVALID_ARGUMENTS.toString();
                Utils.sendMessage(sender, msg);
            }else if(result == Result.NO_ACCESS){
                msg = this.getCore().getPrefix() + Base.NO_ACCESS.toString();
                Utils.sendMessage(sender, msg);
            }
        }else{if(result == Result.NOT_SUPPORTED){
            msg = Base.NOT_SUPPORTED.toString();
            this.getCore().log(msg.replace("{Supported}", sender instanceof Player ? Base.CONSOLE.toString() : Base.PLAYERS.toString()));
        }else if(result == Result.NO_PERMISSION){
            this.getCore().log(Base.NO_PERMISSION.toString());
        }else if(result == Result.INVALID_ARGS){
            this.getCore().log(Base.INVALID_ARGUMENTS.toString());
        }else if(result == Result.NO_ACCESS){
            this.getCore().log(Base.NO_ACCESS.toString());
        }
        }
    }

    public SuperCore getCore() {
        return this.core;
    }

    public abstract Result onPlayerExecute(Player player, String[] args);

    public abstract Result onConsoleExecute(CommandSender commandSender, String[] args);
}

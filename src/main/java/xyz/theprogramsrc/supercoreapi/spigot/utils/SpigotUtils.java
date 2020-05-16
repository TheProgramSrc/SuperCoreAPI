/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.utils;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import xyz.theprogramsrc.supercoreapi.SuperUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * For documentation see {@link SuperUtils}
 */
public class SpigotUtils implements SuperUtils {

    public String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public String removeColor(String message) {
        return ChatColor.stripColor(message);
    }

    @Override
    public void unregisterCommand(String command) {
        try{
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap)commandMapField.get(Bukkit.getServer());
            Field availableCommandsField = Arrays.stream(((Field[])ArrayUtils.addAll(commandMap.getClass().getDeclaredFields(), commandMap.getClass().getSuperclass().getDeclaredFields())))
                    .filter(f-> f.getName().equals("knownCommands"))
                    .findAny().orElse(null);
            if(availableCommandsField != null){
                Map<?, ?> commands = (Map<?, ?>)availableCommandsField.get(commandMap);
                commands.remove(command);
                availableCommandsField.set(commandMap, commands);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void registerBukkitCommand(BukkitCommand command) {
        try{
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap)commandMapField.get(Bukkit.getServer());
            commandMap.register("command", command);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void sendMessage(CommandSender sender, String message){
        sender.sendMessage(color(message));
    }
}

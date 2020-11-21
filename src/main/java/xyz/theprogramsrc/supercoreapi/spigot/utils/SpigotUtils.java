package xyz.theprogramsrc.supercoreapi.spigot.utils;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;
import xyz.theprogramsrc.supercoreapi.SuperUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * For documentation see {@link SuperUtils}
 */
public class SpigotUtils implements SuperUtils {

    /**
     * Translate the given Hex into ChatColor
     * @param hex The color in hex
     * @return the hex color as ChatColor
     */
    @Override
    public net.md_5.bungee.api.ChatColor parseHex(String hex) {
        return net.md_5.bungee.api.ChatColor.of(hex);
    }

    /**
     * Translates the message into minecraft coloured message
     * @param message Message to translate
     * @return Translated message
     */
    @Override
    public String color(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Removes the color from the specified message
     * @param message Message to translate
     * @return Message without colors
     */
    @Override
    public String removeColor(String message) {
        return ChatColor.stripColor(message);
    }

    /**
     * Unregister a Bukkit Command
     * @param command the command to unregister
     */
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
        }catch (IllegalAccessException | NoSuchFieldException e){
            e.printStackTrace();
        }
    }

    /**
     * Register a new Bukkit Command
     * @param command the {@link BukkitCommand command} to register
     */
    public void registerBukkitCommand(BukkitCommand command) {
        try{
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap)commandMapField.get(Bukkit.getServer());
            commandMap.register("command", command);
        }catch (IllegalAccessException | NoSuchFieldException e){
            e.printStackTrace();
        }
    }

    /**
     * Send a message with color support
     * @param sender the sender
     * @param message the message
     */
    public void sendMessage(CommandSender sender, String message){
        sender.sendMessage(color(message));
    }

    /**
     * Used to check if a plugin is installed
     * @param name Name of the plugin
     * @return true if is installed, otherwise false
     */
    public boolean isPlugin(String name){
        return Bukkit.getPluginManager().getPlugin(name) != null;
    }

    /**
     * Used to get the plugin instance of a plugin
     * @param name Name of the plugin
     * @return Plugin instance of the requested plugin
     */
    public Plugin getPlugin(String name){
        return Bukkit.getPluginManager().getPlugin(name);
    }
}

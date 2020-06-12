package xyz.theprogramsrc.supercoreapi;

import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Representation of a set of utils for
 * a plugin
 */
public interface SuperUtils {

    /**
     * Translates the message into minecraft coloured message
     * @param message Message to translate
     * @return Translated message
     */
    String color(String message);

    /**
     * Translates the array into minecraft coloured message
     * @param strings Array to translate
     * @return Translated array
     */
    default String[] color(String... strings){
        return Arrays.stream(strings).map(this::color).toArray(String[]::new);
    }

    /**
     * Translates the collection into minecraft coloured message
     * @param strings Collection to translate
     * @return Translated collection
     */
    default Collection<String> color(Collection<String> strings){
        return strings.stream().map(this::color).collect(Collectors.toList());
    }

    /**
     * Removes the color from the specified message
     * @param message Message to translate
     * @return Message without colors
     */
    String removeColor(String message);

    /**
     * Removes the color from the specified array
     * @param strings Array to translate
     * @return Array without colors
     */
    default String[] removeColor(String... strings){
        return Arrays.stream(strings).map(this::removeColor).toArray(String[]::new);
    }

    /**
     * Removes the color from the specified collection
     * @param strings Collection to translate
     * @return Collection without colors
     */
    default Collection<String> removeColor(Collection<String> strings){
        return strings.stream().map(this::removeColor).collect(Collectors.toList());
    }

    /**
     * Unregisters a command using Reflection
     * @param command Command to unregister
     */
    default void unregisterCommand(String command){
        throw new RuntimeException("The operation unregisterCommand(String) is not supported yet");
    }

    /**
     * Register a command using Reflection
     * @param command Command to register
     */
    default void registerBukkitCommand(BukkitCommand command){
        throw new RuntimeException("The operation registerBukkitCommand(BukkitCommand) is not supported yet");
    }

    /**
     * Send a message
     * @param sender Sender to send the message
     * @param message Message to send
     */
    default void sendMessage(org.bukkit.command.CommandSender sender, String message){
        throw new RuntimeException("The operation sendMessage(CommandSender, String) is not supported yet");
    }

    /**
     * Send a message
     * @param sender Sender to send the message
     * @param message Message to send
     */
    default void sendMessage(net.md_5.bungee.api.CommandSender sender, String message){
        throw new RuntimeException("The operation sendMessage(CommandSender, String) is not supported yet");
    }
}

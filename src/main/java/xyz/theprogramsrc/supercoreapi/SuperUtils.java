package xyz.theprogramsrc.supercoreapi;

import net.md_5.bungee.api.ChatColor;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Representation of a set of utils for
 * a plugin
 */
public interface SuperUtils {

    /**
     * Translate the given Hex into ChatColor
     * @param hex The color in hex
     * @return the hex color as ChatColor
     */
    ChatColor parseHex(String hex);

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
        return this.removeColor(Utils.toList(strings)).toArray(String[]::new);
    }

    /**
     * Removes the color from the specified collection
     * @param strings Collection to translate
     * @return Collection without colors
     */
    default Collection<String> removeColor(Collection<String> strings){
        return strings.stream().map(this::removeColor).collect(Collectors.toList());
    }
}

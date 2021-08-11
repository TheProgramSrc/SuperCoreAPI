package xyz.theprogramsrc.supercoreapi.spigot.packets;


import java.lang.reflect.Constructor;
import java.util.Objects;

import org.bukkit.entity.Player;

import xyz.theprogramsrc.supercoreapi.spigot.utils.ReflectionUtils;

public class DemoMessage {

    /**
     * Sends the demo screen as packet
     * @param player the player to send the demo screen
     */
    public static void send(Player player) {
        try {
            Class<?> gameStateChange = ReflectionUtils.getNMSClass("PacketPlayOutGameStateChange");
            Constructor<?> playOutConstructor = ReflectionUtils.getConstructor(gameStateChange, Integer.TYPE, Float.TYPE);
            Object packet = Objects.requireNonNull(playOutConstructor).newInstance(5, 0);
            ReflectionUtils.sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


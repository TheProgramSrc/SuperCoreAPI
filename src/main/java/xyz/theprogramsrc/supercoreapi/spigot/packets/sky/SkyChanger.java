package xyz.theprogramsrc.supercoreapi.spigot.packets.sky;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.utils.ReflectionUtils;

import java.lang.reflect.Constructor;

public class SkyChanger{

    /**
     * Changes the sky of a player with packets
     * @param player the player
     * @param color the sky color
     */
    public static void changeSky(Player player, SkyColor color) {
        change(player, color.getValue());
    }

    private static void change(Player player, int number) {
        try {
            Class<?> packetClass = ReflectionUtils.getNMSClass("PacketPlayOutGameStateChange");
            Constructor<?> packetConstructor = packetClass.getConstructor(Integer.TYPE, Float.TYPE);
            Object packet = packetConstructor.newInstance(7, number);
            ReflectionUtils.sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

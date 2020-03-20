package xyz.TheProgramSrc.SuperCoreAPI.packets;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.Objects;

@SuppressWarnings("unused")
public class DemoMessage{

    public static void send(Player player){
        try {
            Class<?> gameStateChange = Reflection.getNMSClass("PacketPlayOutGameStateChange");
            Constructor<?> playOutConstructor = Reflection.getConstructor(gameStateChange, Integer.TYPE, Float.TYPE);
            Object packet = Objects.requireNonNull(playOutConstructor).newInstance(5, 0);
            Reflection.sendPacket(player, packet);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }
}

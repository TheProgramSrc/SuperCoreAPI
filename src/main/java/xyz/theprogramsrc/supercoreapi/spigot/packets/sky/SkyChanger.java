package xyz.theprogramsrc.supercoreapi.spigot.packets.sky;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.utils.ReflectionUtils;

import java.lang.reflect.Constructor;

@SuppressWarnings("all")
public class SkyChanger{

    /**
     * Changes the sky of a player with packets
     * @param player the player
     * @param color the sky color
     */
    public static void changeSky(Player player, SkyColor color) {
        /*
        if (color == SkyColor.FREEZE) {
            freeze(player);
        } else if (color == SkyColor.UNFREEZE) {
            unfreeze(player);
        } else {
            change(player, color.getValue());
        }
*/

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

    /*
    private static void freeze(Player player) {
        try {
            World w = player.getWorld();
            Class<?> packetClass = Reflection.getNMSClass("PacketPlayOutRespawn");
            Class<?> diffClass = Reflection.getNMSClass("EnumDifficulty");
            Class<?> wtClass = Reflection.getNMSClass("WorldType");
            Class<?> gameModeClass = Reflection.getNMSClass("EnumGamemode");
            Method diffGetById = diffClass.getMethod("getById", Integer.TYPE);
            Method gmGetById = gameModeClass.getMethod("getById", Integer.TYPE);
            Constructor packetConstructor;
            Object packet;
            try {
                packetConstructor = packetClass.getConstructor(Integer.TYPE, diffClass, wtClass, gameModeClass);
                packet = packetConstructor.newInstance(EnviromentUtils.getId(w.getEnvironment()), Utils.notNull(diffGetById).invoke(null, DifficultyUtils.getValue(w.getDifficulty())), wtClass.getField("NORMAL").get(null), gmGetById.invoke(null, GamemodeUtil.getValue(player.getGameMode())));
            } catch (Exception var20) {
                Class<?> worldSettings = Reflection.getNMSClass("WorldSettings");
                Class<?>[] innerClasses = worldSettings.getDeclaredClasses();
                Class<?> wsGameMode = null;
                for(Class<?> enumGamemode : innerClasses){
                    if(enumGamemode.getSimpleName().equals("EnumGamemode")){
                        wsGameMode = enumGamemode;
                    }
                }
                Method a = worldSettings.getMethod("a", Integer.TYPE);
                packetConstructor = packetClass.getConstructor(Integer.TYPE, diffClass, wtClass, wsGameMode);
                packet = packetConstructor.newInstance(EnviromentUtils.getId(w.getEnvironment()), Utils.notNull(diffGetById).invoke(null, DifficultyUtils.getValue(w.getDifficulty())), wtClass.getField("NORMAL").get(null), a.invoke(null, GamemodeUtil.getValue(player.getGameMode())));
            }
            Reflection.sendPacket(player, packet);
            player.updateInventory();
        } catch (Exception var21) {
            var21.printStackTrace();
        }
    }
    private static void unfreeze(Player player) {
        player.teleport(player.getLocation());
    }
     */
}

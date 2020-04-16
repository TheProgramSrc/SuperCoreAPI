/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.packets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

@SuppressWarnings("unused")
public class Title{

    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static Class<?> getNMSClass(String packageName) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        try {
            return Class.forName("net.minecraft.server." + version + "." + packageName);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title) {
        sendTitle(player, fadeIn, stay, fadeOut, title, null);
    }

    public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String subtitle) {
        sendTitle(player, fadeIn, stay, fadeOut, null, subtitle);
    }

    public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle){
        sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        if(title != null){
            title = ChatColor.translateAlternateColorCodes('&',title);
            title = title.replaceAll("%player%", player.getDisplayName());
        }

        if(subtitle != null){
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
        }

        try {
            Method sendTitle = player.getClass().getDeclaredMethod("sendTitle", String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            if (sendTitle != null) {
                sendTitle.invoke(player, title, subtitle, fadeIn, stay, fadeOut);
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            Object iChatBaseComponent;
            Constructor<?> packetPlayOutTitleConstructor;
            Object packetPlayOutTitleInstance;
            Object titleSubtitleField;
            if (title != null) {
                titleSubtitleField = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("TIMES").get(null);
                iChatBaseComponent = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                packetPlayOutTitleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                packetPlayOutTitleInstance = packetPlayOutTitleConstructor.newInstance(titleSubtitleField, iChatBaseComponent, fadeIn, stay, fadeOut);
                sendPacket(player, packetPlayOutTitleInstance);
                titleSubtitleField = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("TITLE").get(null);
                iChatBaseComponent = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                packetPlayOutTitleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
                packetPlayOutTitleInstance = packetPlayOutTitleConstructor.newInstance(titleSubtitleField, iChatBaseComponent);
                sendPacket(player, packetPlayOutTitleInstance);
            }

            if (subtitle != null) {
                titleSubtitleField = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("TIMES").get(null);
                iChatBaseComponent = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                packetPlayOutTitleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                packetPlayOutTitleInstance = packetPlayOutTitleConstructor.newInstance(titleSubtitleField, iChatBaseComponent, fadeIn, stay, fadeOut);
                sendPacket(player, packetPlayOutTitleInstance);
                titleSubtitleField = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                iChatBaseComponent = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
                packetPlayOutTitleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                packetPlayOutTitleInstance = packetPlayOutTitleConstructor.newInstance(titleSubtitleField, iChatBaseComponent, fadeIn, stay, fadeOut);
                sendPacket(player, packetPlayOutTitleInstance);
            }
        } catch (Exception fadeIn0) {
            fadeIn0.printStackTrace();
        }

    }

    public static void clearTitle(Player player) {
        sendTitle(player, 0, 2, 0, "&7", "&7");
    }
}

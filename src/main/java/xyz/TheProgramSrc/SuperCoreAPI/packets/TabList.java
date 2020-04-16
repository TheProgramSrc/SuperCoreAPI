/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.packets;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

@SuppressWarnings("all")
public class TabList{

    public static void sendFullTablist(Player player, String header, String footer){
        if (header == null) {
            header = "";
        }
        header = ChatColor.translateAlternateColorCodes('&',header);
        header = header.replace("{Player}", player.getDisplayName());
        if (footer == null){
            footer = "";
        }
        footer = ChatColor.translateAlternateColorCodes('&',footer);
        footer = footer.replace("{Player}", player.getDisplayName());
        try {
            Constructor<?> tablistConstructor = Reflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor();
            Object packetInstance = tablistConstructor.newInstance();
            Object top = Reflection.getIChatBaseComponent(header);
            Object bottom = Reflection.getIChatBaseComponent(footer);
            if(MinecraftVersion.Version.isCurrentEqualOrHigher(MinecraftVersion.Version.v1_13_R1)){
                Reflection.setField(packetInstance, "header", top);
                Reflection.setField(packetInstance, "footer", bottom);
            }else{
                Reflection.setField(packetInstance, "a", top);
                Reflection.setField(packetInstance, "b", bottom);
            }

            Reflection.sendPacket(player, packetInstance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    public static void sendTablistHeader(Player player, String header){
        sendFullTablist(player, header, "&7");
    }

    public static void sendTablistFooter(Player player, String footer){
        sendFullTablist(player, "&7", footer);
    }

    public static void clearTabList(Player player){
        sendFullTablist(player, "", "");
    }

	private static String toJSON(String string){
        return "{" + "\"" + "text" + "\"" + ":" + "\"" + string + "\"" + "}";
    }
}

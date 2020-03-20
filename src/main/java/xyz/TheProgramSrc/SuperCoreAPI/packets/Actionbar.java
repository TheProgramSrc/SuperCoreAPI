package xyz.TheProgramSrc.SuperCoreAPI.packets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("all")
public class Actionbar{
    private static String nmserverVersion;
    private static boolean useOldMethods;

    
    public static void sendActionbar(Player toSend, String content) {
        if (nmserverVersion == null) {
            nmserverVersion = Bukkit.getServer().getClass().getPackage().getName();
            nmserverVersion = nmserverVersion.substring(nmserverVersion.lastIndexOf(".") + 1);
            if (nmserverVersion.equalsIgnoreCase("v1_8_R1") || nmserverVersion.startsWith("v1_7_")) {
                useOldMethods = true;
            }
        }
        
        content = content.replace("{Player}", toSend.getDisplayName());
        content = ChatColor.translateAlternateColorCodes('&',content);
        if(toSend != null && toSend.isOnline()){
            try {
                Class.forName("net.minecraft.server." + nmserverVersion + ".IChatBaseComponent");
            } catch (Exception ex) {
                return;
            }

            try {
                Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmserverVersion + ".entity.CraftPlayer");
                Object castedCraftPlayer = craftPlayerClass.cast(toSend);
                Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmserverVersion + ".PacketPlayOutChat");
                Class<?> nmsPacket = Class.forName("net.minecraft.server." + nmserverVersion + ".Packet");
                Object packetPlayOutChatInstance;
                Class<?> chatSerializerClass;
                Class<?> iChatBaseComponentClass;
                Object player;
                if (useOldMethods) {
                    chatSerializerClass = Class.forName("net.minecraft.server." + nmserverVersion + ".ChatSerializer");
                    iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmserverVersion + ".IChatBaseComponent");
                    Method var22 = chatSerializerClass.getDeclaredMethod("a", String.class);
                    player = iChatBaseComponentClass.cast(var22.invoke(chatSerializerClass, "{\"text\": \"" + content + "\"}"));
                    packetPlayOutChatInstance = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, Byte.TYPE).newInstance(player, (byte)2);
                } else {
                    chatSerializerClass = Class.forName("net.minecraft.server." + nmserverVersion + ".ChatComponentText");
                    iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmserverVersion + ".IChatBaseComponent");

                    try {
                        Class<?> chatMessageType = Class.forName("net.minecraft.server." + nmserverVersion + ".ChatMessageType");
                        Object[] chatMessageTypeEnumConstants = chatMessageType.getEnumConstants();
                        Object chatMessageTypeEnumConstant = null;

                        for(int i = 0; i < chatMessageTypeEnumConstants.length; ++i) {
                            Object enumConstant = chatMessageTypeEnumConstants[i];
                            if (enumConstant.toString().equals("GAME_INFO")) {
                                chatMessageTypeEnumConstant = enumConstant;
                            }
                        }

                        Object chatSerializerConstructor = chatSerializerClass.getConstructor(String.class).newInstance(content);
                        packetPlayOutChatInstance = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, chatMessageType).newInstance(chatSerializerConstructor, chatMessageTypeEnumConstant);
                    } catch (ClassNotFoundException player8) {
                        player = chatSerializerClass.getConstructor(String.class).newInstance(content);
                        packetPlayOutChatInstance = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, Byte.TYPE).newInstance(player, (byte)2);
                    }
                }

                Method getHandle = craftPlayerClass.getDeclaredMethod("getHandle");
                Object invoke = getHandle.invoke(castedCraftPlayer);
                Field playerConnection = invoke.getClass().getDeclaredField("playerConnection");
                player = playerConnection.get(invoke);
                Method sendPacket = player.getClass().getDeclaredMethod("sendPacket", nmsPacket);
                sendPacket.invoke(player, packetPlayOutChatInstance);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    
    public static void sendTimedActionbar(final Player player, String actionbar, int seconds){
        sendActionbar(player,actionbar);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Actionbar.clearActionbar(player);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, seconds * 1000);
    }

    
    public static void clearActionbar(Player player){ sendActionbar(player, "&7"); }

    public static void sendToAll(String text){
        for(Player player : Bukkit.getOnlinePlayers()){
            sendActionbar(player, text);
        }
    }

    public static void sendToAllTimed(String text, int seconds){
        for(Player player : Bukkit.getOnlinePlayers()){
            sendTimedActionbar(player, text, seconds);
        }
    }
}
package xyz.TheProgramSrc.SuperCoreAPI.packets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("all")
public class Reflection{

    public static Class<?> getNMSClass(String className){
        return getClass("net.minecraft.server." + getVersion() + "." + className);
    }

    public static Class<?> getOCBClass(String className){
        return getClass("org.bukkit.craftbukkit." + getVersion() + "." + className);
    }

    public static void sendPacket(Player player, Object packet){
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getVersion(){
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... params) {
        try {
            return clazz.getConstructor(params);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... arguments) {
        try {
            return clazz.getMethod(methodName);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getField(fieldName);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Field getDeclaredField(Class<?> clazz, String fieldName){
        try{
            return clazz.getDeclaredField(fieldName);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Class<?> getClass(String className){
        try{
            return Class.forName(className);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Object invoke(Method method, Object obj, Object... arguments){
        try{
            return method.invoke(obj, arguments);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... initargs){
        try{
            return constructor.newInstance(initargs);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Object getIChatBaseComponent(String text) {
        try{
            Class<?> iChatBaseComponent = getNMSClass("IChatBaseComponent");
            Method m = iChatBaseComponent.getDeclaredClasses()[0].getMethod("a", String.class);
            return m.invoke(iChatBaseComponent, "{\"text\":\"" + text + "\"}");
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static void setField(Object object, String fieldName, Object fieldValue){
        setField(object, fieldName, fieldValue, true);
    }

    public static void setField(Object object, String fieldName, Object fieldValue, boolean declared){
        try{
            Field field;
            if (declared) {
                field = object.getClass().getDeclaredField(fieldName);
            } else {
                field = object.getClass().getField(fieldName);
            }

            field.setAccessible(true);
            field.set(object, fieldValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

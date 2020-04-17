/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import sun.misc.Unsafe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static boolean nonNull(Object object) {
        return object != null;
    }

    public static <T> T requireNonNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        } else {
            return obj;
        }
    }

    public static boolean isEmpty(String string) {
        return string.length() == 0;
    }

    public static boolean isEmpty(ArrayList<?> arrayList) {
        return arrayList.size() == 0;
    }

    public static boolean isEmpty(List<?> list) {
        return list.size() == 0;
    }

    public static boolean isEmpty(Object... objects) {
        return objects.length == 0;
    }

    public static boolean isEmpty(String... strings) {
        return strings.length == 0;
    }

    public static void Log(Level level, String message) {
        Logger.getLogger("SuperCoreAPI").log(level, message);
    }

    public static String[] setFirstForAll(String firstForAll, String[] strings) {
        ArrayList<String> result = new ArrayList<>();

        for(String s : strings){
            String r = firstForAll + s;
            result.add(r);
        }

        String[] r = new String[result.size()];
        r = result.toArray(r);
        return r;
    }

    public static String[] setFirstFor(Integer index, String firstFor, String[] strings) {
        ArrayList<String> result = new ArrayList<>();

        for(Integer i = 0; i < strings.length; i = i + 1) {
            String s = strings[i];
            if (i.equals(index)) {
                String r = firstFor + s;
                result.add(r);
            } else {
                result.add(s);
            }
        }

        String[] r = new String[result.size()];
        r = result.toArray(r);

        return r;
    }

    public static String setFirstFor(String string0, String string1) {
        return string0 + string1;
    }

    public static String getEnumName(Enum<?> enumToGetName) {
        ArrayList<String> list = new ArrayList<>();
        String[] splittedEnum = enumToGetName.name().split("_");

        for(String enumName : splittedEnum){
            list.add(enumName.toUpperCase().charAt(0) + enumName.toLowerCase().substring(1));
        }

        return String.join(" ", list);
    }

    public static String[] fromList(List<String> list) {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < list.size(); ++i) {
            String ltt = list.get(i);
            if (i == 0) {
                result = new StringBuilder(ct(ltt) + ":split:");
            } else {
                result.append(ct(ltt)).append(":split:");
            }
        }

        String[] r = result.toString().split(":split:");
        return ct(r);
    }

    public static <T> T notNull(T object) {
        return notNull(object, "The object '" + object.getClass().getSimpleName() + "' cannot be null!");
    }

    public static <T> T notNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        } else {
            return object;
        }
    }

    public static <T> T[] notEmpty(T[] array, String message){
        if (array.length == 0) {
            throw new RuntimeException(message);
        } else {
            return array;
        }
    }

    public static <T> T[] notEmpty(T[] array) {
        return notEmpty(array, "The array " + array.getClass().getName() + " is Empty!");
    }

    public static String notEmpty(String string, String message) {
        if (string.length() == 0) {
            throw new RuntimeException(message);
        } else {
            return string;
        }
    }

    public static String notEmpty(String string) {
        return notEmpty(string, "The string cannot be null!");
    }

    public static String firstUpperCase(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /* Messages & Placeholders */

    public static String ct(String text) {
        return text == null ? "" : ChatColor.translateAlternateColorCodes('&', text);
    }

    public static ArrayList<String> ct(ArrayList<String> list) {
        ArrayList<String> result = new ArrayList<>();
        list.forEach((s) -> result.add(s != null ? (!s.equals(" ") ? ct(s) : " ") : " "));
        return result;
    }

    public static List<String> ct(List<String> list) {
        return ct(new ArrayList<>(list));
    }

    public static String[] ct(String[] array) {
        ArrayList<String> arrayList = new ArrayList<>();

        for(String s : array){
            arrayList.add(s != null ? (!s.equals(" ") ? ct(s) : " ") : " ");
        }

        String[] r = new String[arrayList.size()];
        r = arrayList.toArray(r);

        return r;
    }

    public static String PAPI(Player player, String text){
        return !isPAPI() ? text : PlaceholderAPI.setPlaceholders(player, ct(text));
    }

    public static ArrayList<String> PAPI(Player player, ArrayList<String> list){
        ArrayList<String> result = new ArrayList<>();
        list.forEach(s-> result.add(PAPI(player,s)));
        return result;
    }

    public static List<String> PAPI(Player player, List<String> list) {
        return PAPI(player, new ArrayList<>(list));
    }

    public static String[] PAPI(Player player, String[] array){
        ArrayList<String> arrayList = new ArrayList<>();

        for(String s : array){
            arrayList.add(s != null ? (!s.equals(" ") ? PAPI(player, s) : " ") : " ");
        }

        String[] r = new String[arrayList.size()];
        r = arrayList.toArray(r);

        return r;
    }

    public static void sendMessage(CommandSender sender, String message) {
        if(sender instanceof Player){
            if(isPAPI()){
                sender.sendMessage(PAPI(((Player)sender),message));
            }else{
                sender.sendMessage(ct(message));
            }
        }else{
            sender.sendMessage(ct(message));
        }
    }

    /* String */

    public static String randomString(int length, String available){
        StringBuilder builder = new StringBuilder();

        while(length-- != 0) {
            int character = (int)(Math.random() * (double)available.length());
            builder.append(available.charAt(character));
        }

        return builder.toString();
    }

    public static String randomAlphaNumeric(int length) {
        return randomString(length, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
    }

    public static String randomPassword(int length){
        return randomString(length, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#*()[]{},.:;_-+$%/=");
    }

    public static String[] toStringArray(List<String> list) {
        String[] s = new String[list.size()];
        s = list.toArray(s);
        return s;
    }

    public static String removeCharAt(int index, String s) {
        StringBuilder builder = new StringBuilder(s);
        builder.deleteCharAt(index);
        return builder.toString();
    }

    public static String capitalize(String string) {
        return capitalize(string, null);
    }

    public static String capitalize(String string, char[] chars) {
        int x = chars == null ? -1 : chars.length;
        if (string != null && !isEmpty(string) && x != 0) {
            int size = string.length();
            StringBuilder builder = new StringBuilder(size);
            boolean y = true;

            for(int i = 0; i < size; ++i) {
                char charAt = string.charAt(i);
                if (isDelimiter(charAt, chars)) {
                    builder.append(charAt);
                    y = true;
                } else if (y) {
                    builder.append(Character.toTitleCase(charAt));
                    y = false;
                } else {
                    builder.append(charAt);
                }
            }

            return builder.toString();
        } else {
            return string;
        }
    }

    private static boolean isDelimiter(char character, char[] chars) {
        if (chars == null) {
            return Character.isWhitespace(character);
        } else {
            int x = 0;

            for(int i = chars.length; x < i; ++x) {
                if (character == chars[x]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String uuidToFullUUID(String uuid){
        return (new StringBuffer(uuid)).insert(8, "-").insert(13, "-").insert(18, "-").insert(23, "-").toString();
    }

    /* Time */

    public static String getActualTime(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime time = LocalDateTime.now();
        return dtf.format(time);
    }

    public static boolean isToday(String date){
        String today = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now());
        return date.equals(today);
    }

    public static boolean isNow(String time){
        String now = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
        return now.equals(time);
    }

    /* Internet */

    public static boolean isConnected() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static String readWithInputStream(String url) {
        try{
            URL javaURL = new URL(url);
            URLConnection connection = javaURL.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            return new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static String readWithIO(String url){
        try {
            return IOUtils.toString(new URI(url), Charset.defaultCharset());
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /* Maths */
    
    public static long toTicks(int seconds){
        return (seconds * 20);
    }

    public static long toMillis(int seconds) {
        return (seconds * 1000);
    }

    public static double random(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public static int fairRoundedRandom(int min, int max) {
        int num;
        do {
            num = (int)Math.floor(Math.random() * (double)(max - min) + (double)min);
        } while(num > max - 1);

        return num;
    }

    /* Encode & Decode */

    public static String encodeBase64(String data){
        return new String(Base64.getEncoder().encode(data.getBytes()));
    }

    public static String decodeBase64(String data){
        return new String(Base64.getDecoder().decode(data.getBytes()));
    }

    public static String[] breakText(String string, int partitionSize) {
        List<String> list = new ArrayList<>();
        int len = string.length();
        for (int i = 0; i < len; i += partitionSize) {
            list.add(string.substring(i, Math.min(len, i + partitionSize)));
        }
        return toStringArray(list);
    }

    public static String[] breakText(String string, int partitionSize, String color){
        List<String> list = new ArrayList<>();
        int len = string.length();
        for (int i = 0; i < len; i += partitionSize) {
            list.add(color + string.substring(i, Math.min(len, i + partitionSize)));
        }
        return toStringArray(list);
    }

    public static String vars(String text, String format, String... vars){
        String r = text;
        for(int i = 0; i < vars.length; ++i){
            String var = vars[i];
            String replace = format.replace("INDEX", (i+1)+"");
            r = r.replace(replace, var);
        }
        return r;
    }

    public static Locale fromDisplayLanguage(String displayLanguage){
        return Arrays.stream(Locale.getAvailableLocales()).filter(l-> l.getDisplayLanguage(Locale.getDefault()).equals(displayLanguage)).findFirst().orElse(new Locale("en","US"));
    }

    public static Locale toLocale(String language) {
        return new Locale(language.split("_")[0], language.split("_")[1]);
    }

    public static Object createInstance(Class<?> clazz) {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            Unsafe unsafe = ((Unsafe)field.get(null));
            return unsafe.allocateInstance(clazz);
        } catch (Exception ex) {
            return null;
        }
    }

    /* Other */

    public static void unloadPlugin(Plugin plugin) {
        String pluginName = plugin.getName();
        PluginManager pluginManager = Bukkit.getPluginManager();
        SimpleCommandMap simpleCommandMap = null;
        List<?> pluginList = null;
        Map<?,?> pluginNames = null;
        Map<?,?> commands = null;
        Map<?,?> listeners = null;
        boolean haveListeners = true;
        pluginManager.disablePlugin(plugin);

        Field field1;
        Field field2;
        try {
            Field plugins = Bukkit.getPluginManager().getClass().getDeclaredField("plugins");
            plugins.setAccessible(true);
            pluginList = (List<?>)plugins.get(pluginManager);
            field1 = Bukkit.getPluginManager().getClass().getDeclaredField("lookupNames");
            field1.setAccessible(true);
            pluginNames = (Map<?,?>)field1.get(pluginManager);

            try {
                field2 = Bukkit.getPluginManager().getClass().getDeclaredField("listeners");
                field2.setAccessible(true);
                listeners = (Map<?,?>)field2.get(pluginManager);
            } catch (Exception ex) {
                haveListeners = false;
            }

            field2 = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field2.setAccessible(true);
            simpleCommandMap = (SimpleCommandMap)field2.get(pluginManager);
            Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);
            commands = (Map<?,?>)knownCommands.get(simpleCommandMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        pluginManager.disablePlugin(plugin);
        if (pluginList != null) {
            pluginList.remove(plugin);
        }

        if (pluginNames != null) {
            pluginNames.remove(pluginName);
        }

        Iterator<?> iterator;
        if (listeners != null && haveListeners) {
            iterator = listeners.values().iterator();

            while(iterator.hasNext()) {
                Iterator<?> it = ((SortedSet<?>)iterator.next()).iterator();

                while(it.hasNext()) {
                    RegisteredListener registeredListener = (RegisteredListener)it.next();
                    if (registeredListener.getPlugin() == plugin) {
                        it.remove();
                    }
                }
            }
        }

        if (simpleCommandMap != null) {
            iterator = Utils.requireNonNull(commands).entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry<?,?> entry = (Map.Entry<?,?>)iterator.next();
                if (entry.getValue() instanceof PluginCommand) {
                    PluginCommand pluginCommand = (PluginCommand)entry.getValue();
                    if (pluginCommand.getPlugin() == plugin) {
                        pluginCommand.unregister(simpleCommandMap);
                        iterator.remove();
                    }
                }
            }
        }

        ClassLoader classLoader = plugin.getClass().getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            try {
                field1 = classLoader.getClass().getDeclaredField("plugin");
                field1.setAccessible(true);
                field1.set(classLoader, null);
                field2 = classLoader.getClass().getDeclaredField("pluginInit");
                field2.setAccessible(true);
                field2.set(classLoader, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                ((URLClassLoader)classLoader).close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.gc();
    }

    public static boolean isPAPI(){
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public static boolean isPlugin(String name){
        return Bukkit.getPluginManager().getPlugin(name) != null;
    }

    public static Plugin getPlugin(String name){
        return Bukkit.getPluginManager().getPlugin(name);
    }

    public static <T> List<T> toList(T... array) {
        List<T> list = new ArrayList<>();
        int var3 = array.length;

        Collections.addAll(list, array);

        return list;
    }

    public static void runDelayedTask(final Runnable runnable, long milliseconds) {
        try {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runnable.run();
                    this.cancel();
                }
            }, milliseconds);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static String toString(boolean bool){
        return bool ? "true" : "false";
    }

    public static String toString(int i) {
        return Integer.toString(i);
    }

    public static boolean isInteger(String integer) {
        try {
            Integer.parseInt(integer);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static Integer Int(String string) {
        return Integer.parseInt(string);
    }

    public static String toString(long l) {
        return Long.toString(l);
    }

    public static long longValueOf(String s) {
        return Long.parseLong(s);
    }

    public static String toString(double d) {
        return Double.toString(d);
    }

    public static double doubleValueOf(String s) {
        return Double.parseDouble(s);
    }

    public static String toString(float f) {
        return Float.toString(f);
    }

    public static float floatValueOf(String s) {
        return Float.parseFloat(s);
    }

    public static <T> T[] addToArray(T[] originalArray, T objectToAdd) {
        T[] r = Arrays.copyOf(originalArray, originalArray.length + 1);
        r[originalArray.length] = objectToAdd;
        return r;
    }
}


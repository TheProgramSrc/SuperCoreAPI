package xyz.theprogramsrc.supercoreapi.global.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;

import xyz.theprogramsrc.supercoreapi.global.translations.Base;

public class Utils {

    private static long lastInternetCheck = 0L;
    private static boolean internetCheck = false;
    private static Random random = new Random();

    private Utils(){}

    /**
     * Used to check if a object is not null
     * @param object Object to check
     * @return true if is not null, otherwise false
     */
    public static boolean nonNull(Object object) {
        return object != null;
    }

    /**
     * Used to require non null objects
     * @param obj Object to check
     * @param <T> Type of the object to check
     * @return The required object if is not null
     * @throws NullPointerException if the object is null
     */
    public static <T> T requireNonNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        } else {
            return obj;
        }
    }

    /**
     * Used to check if a object is not null
     * @param object Object to check
     * @param <T> type of the object to check
     * @return The object if is not null
     * @throws NullPointerException if the object is null
     *
     * See {@link #notNull(Object, String)}
     */
    public static <T> T notNull(T object) {
        return notNull(object, "The object '" + object.getClass().getSimpleName() + "' cannot be null!");
    }

    /**
     * Used to check if a object is not null
     * @param object Object to check
     * @param <T> type of the object to check
     * @param message Message to send if the object is null
     * @return The object if is not null
     * @throws NullPointerException if the object is null
     */
    public static <T> T notNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        } else {
            return object;
        }
    }

    /**
     * Used to check if an array is not empty
     * @param array Array to check
     * @param message message to send if is empty
     * @param <T> Type of array
     * @return The requested array if is not null
     * @throws RuntimeException if the array is empty
     */
    public static <T> T[] notEmpty(T[] array, String message){
        if (array.length == 0) {
            throw new RuntimeException(message);
        } else {
            return array;
        }
    }
    /**
     * Used to check if an array is not empty
     * @param array Array to check
     * @param <T> Type of array
     * @return The requested array if is not null
     * @throws RuntimeException if the array is empty
     * 
     * See {@link #notEmpty(Object[], String)}
     */
    public static <T> T[] notEmpty(T[] array) {
        return notEmpty(array, "The array " + array.getClass().getName() + " is Empty!");
    }

    private static final LinkedHashMap<Enum<?>, String> enumNameCache = new LinkedHashMap<>();

    /**
     * Used to get a human readable string from an enum
     * @param enumToGetName Enum to translate
     * @return Human readable string
     */
    public static String getEnumName(Enum<?> enumToGetName) {
        if(!enumNameCache.containsKey(enumToGetName)){
            ArrayList<String> list = new ArrayList<>();
            String[] splittedEnum = enumToGetName.name().split("_");

            for(String enumName : splittedEnum){
                list.add(enumName.toUpperCase().charAt(0) + enumName.toLowerCase().substring(1));
            }

            enumNameCache.put(enumToGetName, String.join(" ", list));
        }

        return enumNameCache.get(enumToGetName);
    }

    /**
     * Used to generate a random string (Useful for passwords)
     * @param length Size of the string
     * @param available Available characters to randomize
     * @return Randomized string
     */
    public static String randomString(int length, String available){
        StringBuilder builder = new StringBuilder();

        while(length-- != 0) {
            int character = random.nextInt() * available.length();
            builder.append(available.charAt(character));
        }

        return builder.toString();
    }

    /**
     * Used to generate a random Alpha Numeric String
     * @param length Size of the string
     * @return Random alpha numeric string
     */
    public static String randomAlphaNumeric(int length) {
        return randomString(length, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
    }

    /**
     * Used to generate a random password
     * @param length Size of the password
     * @return random password
     */
    public static String randomPassword(int length){
        return randomString(length, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#*()[]{},.:;_-+$%/=");
    }

    /**
     * Use to remove a character from a string 
     * @param index Index to remove the character
     * @param string String to remove the character
     * @return New string without the character
     */
    public static String removeCharAt(int index, String string) {
        StringBuilder builder = new StringBuilder(string);
        builder.deleteCharAt(index);
        return builder.toString();
    }

    /**
     * Used to capitalize strings
     * @param string String to capitalize
     * @return Capitalized string
     */
    public static String capitalize(String string) {
        return capitalize(string, null);
    }

    /**
     * Used to capitalize strings
     * @param string String to capitalize
     * @param chars Delimiter chars
     * @return Capitalized String
     * 
     * See {@link #capitalize(String)}
     */
    public static String capitalize(String string, char[] chars) {
        int x = chars == null ? -1 : chars.length;
        if (string != null && string.length() != 0 && x != 0) {
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

    private static final LinkedHashMap<String, String> uuidToFullUUID = new LinkedHashMap<>();

    /**
     * Used to translate a full uuid into a normal uuid
     * @param uuid Full UUID
     * @return Normal UUID
     */
    public static String uuidToFullUUID(String uuid){
        return uuidToFullUUID.computeIfAbsent(uuid, k -> (new StringBuffer(uuid)).insert(8, "-").insert(13, "-").insert(18, "-").insert(23, "-").toString());
    }

    /* Time */

    /**
     * Used to get the actual time
     * @param format Format of the time (Example: MMM/dd/yyyy HH:mm:ss)
     * @return ActualTime formatted
     */
    public static String getActualTime(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime time = LocalDateTime.now();
        return dtf.format(time);
    }

    /**
     * Used to check if the inserted date is today
     * @param date Date to check
     * @param format Format of the date to check
     * @return true if the date is today, otherwise false
     */
    public static boolean isToday(String date, String format){
        String today = DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
        return date.equals(today);
    }

    /**
     * Same as {@link #isToday(String, String)} but with Hours, minutes and seconds
     * @param time Time to check
     * @param format Format of the time to check
     * @return true if the time is now, otherwise false
     */
    public static boolean isNow(String time, String format){
        String now = DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
        return now.equals(time);
    }

    /**
     * Gets the specified time string in seconds
     * Format:
     * - 1h 30s
     * - 1d 4h 30s
     * - 5m 37s
     * 
     * Example: getTimeSecondsFromString("1h 30s") will return 3630 seconds
     * @param timeString Time string to convert
     * @return Time in seconds
     * @since 5.2.0
     */
    public static long getTimeSecondsFromString(String timeString) {
        return Arrays.stream(timeString.split(" ")).mapToLong(Utils::getTimeSecondsFromWord).sum();
    }

    /**
     * Gets the specified time word in seconds.
     * Format:
     * - 1h
     * - 30m
     * - 1d
     * - 37s
     * 
     * Example: getTimeSecondsFromWord("1h") will return 3600 seconds
     * @param word Word to get the seconds from
     * @return The amount in seconds
     * @since 5.2.0
     */
    public static long getTimeSecondsFromWord(String word) {
        if (word.length() < 2) return 0L;
        String timeUnitString = word.toCharArray()[word.length() - 1] + "";
        TimeUnit timeUnit = Arrays.stream(new TimeUnit[] { TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MINUTES, TimeUnit.SECONDS }).filter(t -> t.toString().toLowerCase().startsWith(timeUnitString)).findFirst().orElse(null);
        if(timeUnit == null) return 0L;
        try{
            return timeUnit.toSeconds(Integer.parseInt(word.substring(0, word.length() - 1)));
        }catch(NumberFormatException e){
            return 0L;
        }
    }

    /* Internet */

    /**
     * Used to check if the application have internet connection
     * @return if is connected true, otherwise false
     */
    public static boolean isConnected() {
        long now = System.currentTimeMillis();
        if(lastInternetCheck == 0L || (now - lastInternetCheck >= (30 * 1000))){
            lastInternetCheck = now;
            try {
                URL url = new URL("https://raw.githubusercontent.com/TheProgramSrc/PluginsResources/master/SuperCoreAPI/connection-test");
                URLConnection conn = url.openConnection();
                conn.connect();
                conn.getInputStream().close();
                internetCheck = true;
            } catch (IOException var2) {
                internetCheck = false;
            }
        }
        return internetCheck;
    }


    public static final Map<String, String> readWithInputStreamCache = new LinkedHashMap<>();

    /**
     * Used to read a whole website
     * @param url URL of the website
     * @return Content of the website
     */
    public static String readWithInputStream(String url) {
        if(!readWithInputStreamCache.containsKey(url)){
            try{
                URL javaURL = new URL(url);
                URLConnection connection = javaURL.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
                readWithInputStreamCache.put(url, new BufferedReader(new InputStreamReader(connection.getInputStream())).lines().collect(Collectors.joining()));
            }catch (IOException ex){
                ex.printStackTrace();
                return null;
            }
        }

        return readWithInputStreamCache.get(url);
    }

    public static final Map<String, List<String>> readLinesWithInputStreamCache = new LinkedHashMap<>();

    /**
     * Used to read a whole website returning a list of every line
     *
     * @param url URL of the website
     * @return Content of the website
     */
    public static List<String> readLinesWithInputStream(String url){
        if(!readLinesWithInputStreamCache.containsKey(url)){
            try{
                URL webURL = new URL(url);
                URLConnection connection = webURL.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                readLinesWithInputStreamCache.put(url, reader.lines().collect(Collectors.toList()));
            }catch (IOException e){
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        return readLinesWithInputStreamCache.get(url);
    }

    /* Maths */

    /**
     * Transform seconds into Minecraft ticks (1 second = 20 ticks)
     * @param seconds Seconds to transform
     * @return Seconds in ticks
     */
    public static long toTicks(int seconds){
        return (seconds * 20L);
    }

    /**
     * Used to transform seconds into milliseconds (1 second = 1000 milliseconds)
     * @param seconds Seconds to transform
     * @return Seconds in milliseconds
     */
    public static long toMillis(int seconds) {
        return (seconds * 1000L);
    }

    /**
     * Used to generate a random double
     * @param min Minimum of the random number
     * @param max Maximum of the random number
     * @return Random double number
     */
    public static double random(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    /**
     * Used to generate a random integer
     * @param min Minimum of the random number
     * @param max Maximum of the random number
     * @return Random integer number
     */
    public static int random(int min, int max) {
        int num;
        do {
            num = (random.nextInt() * (max - min) + min);
        } while(num > max - 1);

        return num;
    }

    /**
     * Get the hypotenuse of multiple values.
     * @param values array of values to parse the hypotenuse
     * @return The hypotenuse length.
     *
     * @since 4.12.1
     */
    public static double parseHypotenuse(double... values) {
        double result = 0.0;
        for (double d : values) {
            result = result + d * d;
        }

        return result;
    }

    /**
     * Calculate the square root of the specified value
     * @param a value to calculate the square root.
     * @return the positive square root of a. If the argument is NaN or less than zero, the result is NaN.
     *
     * @since 4.12.1
     */
    public static double squareRoot(double a){
        return Math.sqrt(a);
    }

    /* Others */

    /**
     * Used to transform a String collection into an String array
     * @param collection Collection to transform
     * @return String array from the collection
     */
    public static String[] toStringArray(Collection<String> collection){
        String[] array = new String[collection.size()];
        array = collection.toArray(array);
        return array;
    }

    /**
     * Used to encode into base64
     * @param data Data to encode
     * @return Encoded data
     */
    public static String encodeBase64(String data){
        return new String(Base64.getEncoder().encode(data.getBytes()));
    }

    /**
     * Used to decode from base64
     * @param data Data to decode
     * @return Decoded data
     */
    public static String decodeBase64(String data){
        return new String(Base64.getDecoder().decode(data.getBytes()));
    }

    private static final LinkedHashMap<String, String[]> breakTextA = new LinkedHashMap<>();

    /**
     * Used to create a text-break at some point
     * @param string String to break
     * @param partitionSize Max length of a string
     * @return String array of the broken text
     */
    public static String[] breakText(String string, int partitionSize) {
        String id = string+";"+partitionSize;
        if(!breakTextA.containsKey(id)){
            List<String> list = new ArrayList<>();
            int len = string.length();
            for (int i = 0; i < len; i += partitionSize) {
                list.add(string.substring(i, Math.min(len, i + partitionSize)));
            }
            breakTextA.put(id, toStringArray(list));
        }

        return breakTextA.get(id);
    }

    private static final LinkedHashMap<String, String[]> breakTextB = new LinkedHashMap<>();

    /**
     * Used to create a text-break at some point and add a text before the broken text
     * @param string String to break
     * @param partitionSize Max length of a string
     * @param textToAdd Text to add before the broken text
     * @return String array of the broken text
     */
    public static String[] breakText(String string, int partitionSize, String textToAdd){
        String id = string+";"+partitionSize+";"+textToAdd;
        if(!breakTextB.containsKey(id)){
            List<String> list = new ArrayList<>();
            int len = string.length();
            for (int i = 0; i < len; i += partitionSize) {
                list.add(textToAdd + string.substring(i, Math.min(len, i + partitionSize)));
            }
            breakTextB.put(id, toStringArray(list));
        }

        return breakTextB.get(id);
    }

    /**
     * Used to transform an Array into list
     * @param array Array to transform
     * @param <T> Type of the array
     * @return List of the array
     */
    @SafeVarargs
    public static <T> List<T> toList(T... array) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    /**
     * Used to check if a string is integer
     * @param string String to check
     * @return true if the string is integer, otherwise false
     */
    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Used to add at the end of an array an object
     * @param originalArray Original array
     * @param objectToAdd Object to add into the array
     * @param <T> Type of the array and object
     * @return The new array
     */
    public static <T> T[] addToArray(T[] originalArray, T objectToAdd) {
        T[] r = Arrays.copyOf(originalArray, originalArray.length + 1);
        r[originalArray.length] = objectToAdd;
        return r;
    }

    /**
     * Used to create a folder if not exists
     * @param folder Folder to check
     * @return Folder checked
     */
    public static File folder(File folder) {
        if(!folder.exists()) folder.mkdirs();
        return folder;
    }

    /**
     * Used to read a file into string
     * @param file File to read
     * @return Content of the file
     * @throws IOException If occurs any exception on the file reading
     */
    public static String readFile(File file) throws IOException{
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }

    /**
     * Reads all the lines of a file
     * @param file File to read lines
     * @return Lines of the file
     * @throws IOException If occurs any exception on the file reading
     */
    public static List<String> readLines(File file)throws IOException{
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    /**
     * Writes a set of lines into a file
     * @param file File to write
     * @param data Data to write into the file
     * @throws IOException If occurs any exception on the file writing
     */
    public static void writeFile(File file, Collection<String> data) throws IOException{
        FileUtils.writeLines(file, data);
    }

    /**
     * Used to write a string into a file
     * @param file File to write the data
     * @param data Data to write into the file
     * @throws IOException If occurs any exception during the file writing
     */
    public static void writeFile(File file, String data) throws IOException{
        FileUtils.write(file, data, Charset.defaultCharset());
    }

    /**
     * Used to destroy files
     * @param file File to destroy
     * @throws NullPointerException  if the directory is null
     * @throws FileNotFoundException if the file was not found
     * @throws IOException in case deletion is unsuccessful
     */
    public static void destroyFile(File file) throws IOException{
        FileUtils.forceDelete(file);
    }

    /**
     * Download a file from internet
     * @param url Direct location to download the file
     * @param destination File to save the downloaded file
     * @throws UnknownHostException if is not connected to internet
     * @throws IOException if the URL cannot be opened
     * @throws IOException if the destination is a directory
     * @throws IOException if the destination file cannot be written
     * @throws IOException if the destination file needs creating but can't be
     * @throws IOException if an IO error occurs during copying
     */
    public static void downloadFile(String url, File destination) throws IOException {
        if(!isConnected()) throw new RuntimeException("You're not connected to internet or connections are not allowed!");
        HttpURLConnection connection = ((HttpURLConnection) new URL(url).openConnection());
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
        connection.connect();
        FileUtils.copyInputStreamToFile(connection.getInputStream(), destination);
    }

    /**
     * See {@link #downloadFile(String, File)}
     * @param url Direct location to download the file
     * @param outputFolder Destination folder
     * @param fileName Name of the downloaded file
     * @throws UnknownHostException if is not connected to internet
     * @throws IOException if the URL cannot be opened
     * @throws IOException if the destination is a directory
     * @throws IOException if the destination file cannot be written
     * @throws IOException if the destination file needs creating but can't be
     * @throws IOException if an IO error occurs during copying
     */
    public static void downloadFile(String url, File outputFolder, String fileName) throws IOException{
        downloadFile(url, new File(folder(outputFolder), fileName));
    }

    /**
     * Transform a boolean into a Enabled or Disabled depending on the {@code boolean} value given.
     *
     * The text returned will be translated using the {@link xyz.theprogramsrc.supercoreapi.global.translations.TranslationManager translation system}
     *
     * @param check the boolean check
     * @return Enabled if the check it's true, Disabled otherwise.
     */
    public static String parseEnabledBoolean(boolean check){
        return check ? ("&a" + Base.ENABLED) : ("&c" + Base.DISABLED);
    }

    /**
     * Basic color text translation (Replace &amp; by §)
     * @param text Text to translate
     * @return Translated text
     */
    public static String ct(String text) {
        return text.replace("&","§");
    }

    /**
     * Basic color text translation (Replace &amp; by §)
     * @param array Array to translate
     * @return Translated array
     */
    public static String[] ct(String... array){
        return Arrays.stream(array).map(Utils::ct).toArray(String[]::new);
    }

    /**
     * Basic color text translation (Replace &amp; by §)
     * @param list List to translate
     * @return Translated list
     */
    public static List<String> ct(List<String> list){
        return list.stream().map(Utils::ct).collect(Collectors.toList());
    }

    /**
     * Returns {@code true} if the arguments are equal to each other
     * and {@code false} otherwise.
     *
     * @param a an object
     * @param b an object to be compared with {@code a} for equality
     * @return {@code true} if the arguments are equal to each other
     * and {@code false} otherwise
     */
    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    /**
     * Returns {@code true} if the input is a valid json
     * @return {@code true} if the input is a valid json, otherwise {@code false}
     */
    public static boolean isJSONEncoded(String input){
        try{
            new JsonParser().parse(input);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Create a new post request
     * @param url the url
     * @param contentData the data to send
     * @return the url response output
     */
    public static String postRequest(String url, String contentData) throws IOException{
        URL javaURL = new URL(url);
        HttpURLConnection connection = ((HttpURLConnection)javaURL.openConnection());
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
        connection.addRequestProperty("Content-Type", "text/plain; charset=UTF-8");
        connection.addRequestProperty("Content-Length", contentData.length()+"");
        OutputStream out = connection.getOutputStream();
        out.write(contentData.getBytes());
        connection.connect();
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String result = reader.lines().collect(Collectors.joining());
        connection.disconnect();
        return result;
    }

    private static final LinkedHashMap<String, String> pastes = new LinkedHashMap<>();

    /**
     * Upload a new paste to <a href="https://paste.theprogramsrc.xyz/">https://paste.theprogramsrc.xyz/</a>
     *
     * @param body the body of the paste
     * @return the paste key
     * @throws IOException in case of any problem
     */
    public static String uploadPaste(String body) throws IOException{
        if(!pastes.containsKey(body) || pastes.get(body) == null){
            String url = "https://paste.theprogramsrc.xyz/documents";
            String post = postRequest(url, body);
            if(post != null && isJSONEncoded(post)){
                JsonObject json = new JsonParser().parse(post).getAsJsonObject();
                pastes.put(body, json.get("key").getAsString());
            }
        }

        return pastes.getOrDefault(body, null);
    }

    private static final LinkedHashMap<String, String> exceptionsToString = new LinkedHashMap<>();

    /**
     * Build an {@link Exception exception} into a string
     * @param e the exception
     * @return the stack trace as string
     */
    public static String exceptionToString(Exception e){
        String id = e.toString();
        if(exceptionsToString.containsKey(id)){
            StringBuilder builder = new StringBuilder();
            builder.append(exceptionMessage(e)).append("\n");
            for (StackTraceElement ste : e.getStackTrace()) {
                builder.append("\tat ").append(ste.getClassName()).append(".").append(ste.getMethodName()).append("(").append(ste.getFileName()).append(":").append(ste.getLineNumber()).append(")").append("\n");
            }
            exceptionsToString.put(id, builder.toString());
        }

        return exceptionsToString.get(id);
    }

    /**
     * Gets the message title from an exception
     * @param e the exception
     * @return the message of the exception (Usually it's foo.bar.Class: Message)
     */
    public static String exceptionMessage(Exception e){
        return e.getClass().getName() + ": " + (e.getMessage() != null ? e.getMessage() : "null");
    }

    /**
     * Checks if the object is in the given array.
     *
     * The method returns <code>false</code> if a <code>null</code> array is passed in.
     *
     * @param array  the array to search through
     * @param objectToFind  the object to find
     * @return <code>true</code> if the array contains the object, <code>false</code> otherwise
     */
    public static boolean inArray(Object[] array, Object objectToFind){
        return indexOf(array, objectToFind) != -1;
    }

    /**
     * Finds the index of the given object in the array starting at <code>0</code>.
     * @param array the array to search through for the object, maybe be <code>null</code>
     * @param objectToFind the object to find, may be <code>null</code>
     * @return the index of the object within the array starting at the index, <code>-1</code> if not found or <code>null</code> array input
     */
    public static int indexOf(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * Finds the index of the given object in the array starting at the given index.
     *
     * A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return <code>-1</code>.
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param objectToFind  the object to find, may be <code>null</code>
     * @param startIndex  the index to start searching at
     * @return the index of the object within the array starting at the index, <code>-1</code> if not found or <code>null</code> array input
     */
    public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }


    private static double javaVersion = -1;

    /**
     * Gets the java version as double
     * @return the java version as double
     */
    public static double getJavaVersion(){
        if(javaVersion == -1){
            String version = System.getProperty("java.version");
            int pos = version.indexOf('.');
            pos = version.indexOf('.', pos+1);
            javaVersion = Double.parseDouble (version.substring (0, pos));
        }

        return javaVersion;
    }

    private static final LinkedHashMap<String, MessageDigest> digestsCache = new LinkedHashMap<>();

    /**
     * Gets the {@link MessageDigest} for the given algorithm
     * @param algorithm the algorithm to use
     * @return the {@link MessageDigest} for the given {@code algorithm}
     * @throws NoSuchAlgorithmException if no Provider supports a MessageDigestSpi implementation for the specified algorithm
     */
    public static MessageDigest getDigest(String algorithm) throws NoSuchAlgorithmException {
        Preconditions.checkNotNull(algorithm, "Algorithm cannot not be null!");
        if(!digestsCache.containsKey(algorithm)){
            digestsCache.put(algorithm, MessageDigest.getInstance(algorithm));
        }

        return digestsCache.get(algorithm);
    }

    /**
     * Validates the File Checksum using the given checksum algorithm
     * @param digest the {@link MessageDigest} to use in order to generate the checksum
     * @param file the file to use and generate the checksum
     * @return the generated checksum
     * @throws IOException if an I/O error occurs
     */
    public static String generateFileChecksum(MessageDigest digest, File file) throws IOException {
        try(FileInputStream inputStream = new FileInputStream(file)){
            byte[] bytes = new byte[1024];
            int bytesCount;
            while((bytesCount = inputStream.read(bytes)) != -1){
                digest.update(bytes, 0, bytesCount);
            }

            bytes = digest.digest();

            StringBuilder builder = new StringBuilder();
            for(byte _byte : bytes){
                builder.append(Integer.toString((_byte & 0xff) + 0x100, 16).substring(1));
            }

            return builder.toString();
        }
    }

    /**
     * Checks if the given class exists
     * @param className the name of the class
     * @return true if exists, false otherwise.
     */
    public static boolean hasClass(String className){
        try{
            Class.forName(className);
            return true;
        }catch (ClassNotFoundException e){
            return false;
        }
    }
}


package xyz.theprogramsrc.supercoreapi.global.utils.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.UUID;

public class JSONUtil {

    protected JsonObject json;
    protected UUID jsonUtilUuid;

    /**
     * @param json JsonObject to use in the util
     */
    public JSONUtil(JsonObject json){
        this.json = json;
        this.jsonUtilUuid = UUID.randomUUID();
    }

    public JSONUtil(){
        this(new JsonObject());
    }

    /**
     * Used to check if the file contains a key
     * @param key Key to check
     * @return true if the file contains the key, otherwise false
     */
    public boolean contains(String key){
        return this.json.has(key);
    }

    /**
     * Used to insert data into the file
     * @param key Key of the value to store
     * @param value {@link JsonElement} to save
     */
    public void set(String key, JsonElement value){
        this.json.add(key,value);
    }

    /**
     * Used to add data if not exists into the file
     * If you want to replace data use {@link #set(String, JsonElement)}
     * @param key Key of the value to store
     * @param value Object to save
     */
    public void add(String key, JsonElement value){
        if(!this.contains(key)) this.set(key, value);
    }

    /**
     * Used to insert data into the file
     * @param key Key of the value to store
     * @param value String to save
     */
    public void set(String key, String value){
        this.json.addProperty(key, value);
    }

    /**
     * Used to insert data into the file
     * @param key Key of the value to store
     * @param value Boolean to save
     */
    public void set(String key, Boolean value){
        this.json.addProperty(key, value);
    }

    /**
     * Used to insert data into the file
     * @param key Key of the value to store
     * @param value Number to save
     */
    public void set(String key, Number value){
        this.json.addProperty(key, value);
    }

    /**
     * Used to add data if not exists into the file
     * If you want to replace data use {@link #set(String, String)}
     * @param key Key of the value to store
     * @param value Object to save
     */
    public void add(String key, String value){
        if(!this.contains(key)) this.set(key, value);
    }


    /**
     * Used to add data if not exists into the file
     * If you want to replace data use {@link #set(String, Boolean)}
     * @param key Key of the value to store
     * @param value Object to save
     */
    public void add(String key, Boolean value){
        if(!this.contains(key)) this.set(key, value);
    }


    /**
     * Used to add data if not exists into the file
     * If you want to replace data use {@link #set(String, Number)}
     * @param key Key of the value to store
     * @param value Object to save
     */
    public void add(String key, Number value){
        if(!this.contains(key)) this.set(key, value);
    }

    /**
     * Used to get a json element from the file
     * @param key Key of the element to request
     * @return {@link JsonElement} stored in the key inside the file
     * @throws NullPointerException if the key does not exists
     */
    public JsonElement get(String key){
        if(!this.contains(key)){
            throw new NullPointerException("Cannot find JsonElement with key '" + key + "' in the JSON Util with id '" + this.jsonUtilUuid + "'!");
        }
        return this.json.get(key);
    }

    /**
     * Use it to get a {@link JsonObject}
     * If it does not exists it will be created
     * @param key Key of the requested {@link JsonObject}
     * @return {@link JsonObject} stored in the key inside the file
     */
    public JsonObject getOrCreateObject(String key){
        this.add(key, new JsonObject());
        return this.get(key).getAsJsonObject();
    }


    /**
     * Use it to get a {@link JsonObject}
     * If it does not exists it will be created
     * @param key Key of the requested {@link JsonArray}
     * @return {@link JsonArray} stored in the key inside the file
     */
    public JsonArray getOrCreateArray(String key){
        this.add(key, new JsonArray());
        return this.get(key).getAsJsonArray();
    }

    /**
     * Used to request a {@link String} from the json file
     * @param key Key of the requested {@link String}
     * @return {@link String} stored in the key inside the file
     */
    public String getString(String key){
        return this.get(key).getAsString();
    }

    /**
     * Used to request a {@link Integer} from the json file
     * @param key Key of the requested {@link Integer}
     * @return {@link Integer} stored in the key inside the file
     */
    public int getInt(String key){
        return this.get(key).getAsInt();
    }

    /**
     * Used to request a {@link Boolean} from the json file
     * @param key Key of the requested {@link Boolean}
     * @return {@link Boolean} stored in the key inside the file
     */
    public boolean getBoolean(String key){
        return this.get(key).getAsBoolean();
    }

    /**
     * Used to request a {@link Number} from the json file
     * @param key Key of the requested {@link Number}
     * @return {@link Number} stored in the key inside the file
     */
    public Number getNumber(String key){
        return this.get(key).getAsNumber();
    }

    /**
     * Toggle a boolean value using the specified {@code key}
     * @param key the key of the path to toggle the boolean
     * @return the new value
     */
    public boolean toggle(String key){
        boolean b = !this.getBoolean(key);
        this.set(key, b);
        return this.getBoolean(key);
    }

    /**
     * Increases the value of an Integer object by 1 in the file
     * @param key the key of the Integer object
     * @return the new value
     */
    public int incr(String key){
        int i = this.getInt(key)+1;
        this.set(key, i);
        return this.getInt(key);
    }

    /**
     * Decreases the value of an Integer object by 1 in the file
     * @param key the key of the Integer object
     * @return the new value
     */
    public int decr(String key){
        int i = this.getInt(key)-1;
        this.set(key, i);
        return this.getInt(key);
    }

    public JsonObject getJsonObject() {
        return json;
    }
}

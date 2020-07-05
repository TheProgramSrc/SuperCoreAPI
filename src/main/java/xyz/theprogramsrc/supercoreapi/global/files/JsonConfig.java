package xyz.theprogramsrc.supercoreapi.global.files;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;

/**
 * This can be used to create files with JSON Format
 */
public class JsonConfig {

    protected final File file;
    protected JsonObject json;

    /**
     * A JsonConfig is a representation of a file written in Json format
     * @param file File to save and write all the data
     */
    public JsonConfig(File file){
        JsonObject json;
        if(file.exists()){
            try{
                String content = Utils.readFile(file);
                if(content == null){
                    json = new JsonObject();
                }else{
                    if(content.isEmpty() || content.equals(" ")){
                        json = new JsonObject();
                    }else{
                        json = new JsonParser().parse(Utils.readFile(file)).getAsJsonObject();
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
                json = new JsonObject();
            }
        }else{
            try{
                file.createNewFile();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            json = new JsonObject();
        }
        this.json = json;
        this.file = file;
    }

    /**
     * A JsonConfig is a representation of a file written in Json format
     * @param folder Directory where the file will be located
     * @param name Name of the file to save and write all the data
     */
    public JsonConfig(File folder, String name){
        this(new File(folder, name));
    }

    /**
     * Use it to save all the data you modified
     */
    public void save(){
        try{
            Utils.writeFile(this.file, this.json.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * This will refresh the information
     * (Is recommended to save before do this)
     */
    public void reload() {
        try{
            if(!file.exists()){
                file.createNewFile();
                this.json = new JsonObject();
            }else{
                json = new JsonParser().parse(Utils.readFile(file)).getAsJsonObject();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Use it to delete the file
     * (Is not recommended to use, YOU WILL LOOSE ALL THE DATA!)
     */
    public void destroy(){
        try{
            Utils.destroyFile(this.file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
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
        this.save();
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
        this.save();
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
     * Used to get a json element from the file
     * @param key Key of the element to request
     * @return {@link JsonElement} stored in the key inside the file
     * @throws NullPointerException if the key does not exists
     */
    public JsonElement get(String key){
        if(!this.contains(key)){
            throw new NullPointerException("Cannot find JsonElement with key '" + key + "' in file '" + this.file.getName() + "'!");
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




}

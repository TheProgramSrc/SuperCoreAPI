/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JSONConfig {
    private File file;
    private JsonObject json;

    public JSONConfig(File file){
        this.file = file;
        JsonParser parser = new JsonParser();

        try{
            if(this.file.exists()){
                String content =  FileUtils.readFileToString(file, Charset.defaultCharset());
                JsonElement element = parser.parse(content);
                this.json = element.isJsonNull() ? new JsonObject() : element.getAsJsonObject();
            }else{
                this.file.createNewFile();
                this.json = new JsonObject();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public JSONConfig(File folder, String name){
        this(new File(folder, name));
    }

    public JsonObject getOrCreateObject(String key) {
        return this.contains(key) ? this.json.get(key).getAsJsonObject() : new JsonObject();
    }

    public JsonArray getOrCreateArray(String key) {
        return this.contains(key) ? this.json.get(key).getAsJsonArray() : new JsonArray();
    }

    public void add(String key, JsonElement value){
        this.json.add(key, value);
        this.save();
    }

    public void addProperty(String key, Number value){
        this.json.addProperty(key, value);
        this.save();
    }

    public void addProperty(String key, String value){
        this.json.addProperty(key, value);
        this.save();
    }

    public void addProperty(String key, Boolean value){
        this.json.addProperty(key, value);
        this.save();
    }

    public void addProperty(String key, Character value){
        this.json.addProperty(key, value);
        this.save();
    }

    public JsonObject getJSON() {
        return this.json;
    }

    public boolean contains(String key) {
        return this.json.has(key);
    }

    public boolean getAsBoolean(String key) {
        return this.contains(key) && this.json.get(key).getAsBoolean();
    }

    public String getAsString(String key) {
        return this.contains(key) ? this.json.get(key).getAsString() : null;
    }

    public Set<String> keySet(){
        return this.getJSON().keySet();
    }

    public JsonElement get(String key){
        return this.json.get(key);
    }

    public void save() {
        try {
            FileUtils.writeByteArrayToFile(this.file, this.json.toString().getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

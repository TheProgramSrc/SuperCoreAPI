/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.config.storage;

import com.google.gson.JsonObject;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.config.JSONConfig;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class SuperStorage<OBJ> extends SuperModule {

    private final HashMap<String, JsonObject> memory;
    private final File local;
    private JSONConfig cfg;

    public SuperStorage(SuperCore core, File local) {
        super(core);
        this.memory = new HashMap<>();
        this.local = local;
        this.cfg = new JSONConfig(local);
    }

    public JSONConfig getConfig() {
        return cfg;
    }

    public abstract JsonObject serialize(OBJ obj);

    public abstract OBJ deserialize(JsonObject json);

    public void set(String key, OBJ obj, StorageMode mode){
        if(mode == StorageMode.MEMORY){
            this.memory.put(key, this.serialize(obj));
        }else{
            this.cfg.addProperty(key, this.serialize(obj).toString());
        }
    }

    public OBJ get(String key, StorageMode mode){
        if(mode == StorageMode.MEMORY){
            if(!this.memory.containsKey(key)){
                return null;
            }else{
                return this.deserialize(this.memory.get(key));
            }
        }else{
            if(!this.cfg.contains(key)){
                return null;
            }else{
                return this.deserialize(this.cfg.get(key).getAsJsonObject());
            }
        }
    }

    public Set<String> getKeys(){
        return this.memory.keySet();
    }

    public Set<JsonObject> getValues(){
        return new HashSet<>(this.memory.values());
    }

    public void save(){
        this.getKeys().forEach(s-> this.set(s, this.get(s, StorageMode.MEMORY), StorageMode.LOCAL));
    }

    public void reload(){
        this.cfg = new JSONConfig(this.local);
        this.memory.clear();
    }
}

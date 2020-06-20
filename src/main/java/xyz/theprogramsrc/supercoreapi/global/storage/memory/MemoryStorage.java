package xyz.theprogramsrc.supercoreapi.global.storage.memory;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;

public class MemoryStorage<OBJ> {

    protected SuperPlugin<?> plugin;
    protected LinkedHashMap<String, OBJ> memory;

    public MemoryStorage(SuperPlugin<?> plugin){
        this.plugin = plugin;
        this.memory = new LinkedHashMap<>();
    }

    public void add(String key, OBJ value){
        if(!this.memory.containsKey(key)){
            this.memory.put(key, value);
        }
    }

    public void set(String key, OBJ value){
        this.memory.put(key, value);
    }

    public void remove(String key){
        this.memory.remove(key);
    }

    public boolean has(String key){
        return this.memory.containsKey(key);
    }

    public boolean hasValue(OBJ value){
        return this.memory.containsValue(value);
    }

    public void forEach(BiConsumer<String, OBJ> consumer){
        this.memory.forEach(consumer);
    }

    public LinkedHashSet<Map.Entry<String,OBJ>> entrySet(){
        return new LinkedHashSet<>(this.memory.entrySet());
    }

    public OBJ get(String key){
        return this.memory.get(key);
    }

    public String fromValue(OBJ obj){
        return this.keys().stream().filter(k-> this.memory.get(k).equals(obj)).findFirst().orElse(null);
    }

    public LinkedList<String> keys(){
        final LinkedList<String> keys = new LinkedList<>();
        this.forEach((k,v)-> keys.add(k));
        return keys;
    }

    public LinkedList<OBJ> values(){
        final LinkedList<OBJ> values = new LinkedList<>();
        this.forEach((k,v)-> values.add(v));
        return values;
    }
}

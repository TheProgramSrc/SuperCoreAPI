/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Set;

public class YAMLConfig {

    private FileConfiguration config;
    private File file;

    public YAMLConfig(File file){
        this.file = file;
        this.load();
    }

    private void load(){
        try {
            if(!this.file.exists()) this.file.createNewFile();
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void reload(){
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save(){
        try{
            this.config.save(this.file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void set(String path, Object value){
        if(value instanceof Float){
            float val = ((float) value);
            value = Float.toString(val);
        }
        this.config.set(path, value);
        this.save();
    }

    public void add(String path, Object value){
        if(!this.contains(path)) this.set(path, value);
    }

    public boolean contains(String path){
        return this.config.contains(path);
    }

    public Object get(String path){
        return this.config.get(path);
    }

    public Object get(String path, Object def){
        this.add(path, def);
        return this.get(path);
    }

    public String getString(String path){
        return this.config.getString(path);
    }

    public String getString(String path, String def){
        this.add(path, def);
        return this.getString(path);
    }

    public boolean getBoolean(String path){
        return this.config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def){
        this.add(path, def);
        return this.getBoolean(path);
    }

    public int getInt(String path){
        return this.config.getInt(path);
    }

    public int getInt(String path, int def){
        this.add(path, def);
        return this.getInt(path);
    }

    public double getDouble(String path){
        return this.config.getDouble(path);
    }

    public double getDouble(String path, double def){
        this.add(path,def);
        return this.getDouble(path);
    }

    public long getLong(String path){
        return this.config.getLong(path);
    }

    public long getLong(String path, long def){
        this.add(path, def);
        return this.getLong(path);
    }

    public float getFloat(String path){
        return Long.parseLong(this.getString(path));
    }

    public float getFloat(String path, float def){
        this.add(path, def);
        return this.getFloat(path);
    }

    public List<?> getList(String path){
        return this.config.getList(path);
    }

    public List<?> getList(String path, List<?> def){
        this.add(path, def);
        return this.getList(path);
    }

    public List<String> getStringList(String path){
        return this.config.getStringList(path);
    }

    public List<String> getStringList(String path, List<String> def){
        this.add(path, def);
        return this.getStringList(path);
    }

    public List<Boolean> getBooleanList(String path){
        return this.config.getBooleanList(path);
    }

    public List<Boolean> getBooleanList(String path, List<Boolean> def){
        this.add(path, def);
        return this.getBooleanList(path);
    }

    public List<Integer> getIntList(String path){
        return this.config.getIntegerList(path);
    }

    public List<Integer> getIntList(String path, List<Integer> def){
        this.add(path, def);
        return this.getIntList(path);
    }

    public List<Double> getDoubleList(String path){
        return this.config.getDoubleList(path);
    }

    public List<Double> getDoubleList(String path, List<Double> def){
        this.add(path, def);
        return this.getDoubleList(path);
    }

    public List<Long> getLongList(String path){
        return this.config.getLongList(path);
    }

    public List<Long> getLongList(String path, List<Long> def){
        this.add(path, def);
        return this.getLongList(path);
    }

    public List<Float> getFloatList(String path){
        return this.config.getFloatList(path);
    }

    public List<Float> getFloatList(String path, List<Float> def){
        this.add(path, def);
        return this.getFloatList(path);
    }

    public ConfigurationSection getSection(String path){
        return this.config.getConfigurationSection(path);
    }

    public Set<String> getKeys(boolean deep){
        return this.config.getKeys(deep);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }
}

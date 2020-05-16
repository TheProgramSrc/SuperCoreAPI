/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.bungee.utils.storage;


import net.md_5.bungee.config.Configuration;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigurationField {

    private final Configuration config;
    private final String path;
    private Object def;

    public ConfigurationField(Configuration configuration, String path, Object defaultValue){
        this.config = configuration;
        this.path = path;
        this.def = defaultValue;
        this.add();
    }

    public ConfigurationField(Configuration configuration, String path){
        this.config = configuration;
        this.path = path;
    }

    private void add() {
        if(!this.config.contains(this.path)) this.config.set(this.path, this.def);
    }

    public String toString(){
        return this.config.getString(this.path);
    }

    public Object asObject(){
        return this.config.get(this.path);
    }

    public String asString(){
        return this.toString();
    }

    public boolean asBoolean(){
        return this.config.getBoolean(this.path);
    }

    public int asInt(){
        return this.config.getInt(this.path);
    }

    public double asDouble(){
        return this.config.getDouble(this.path);
    }

    public List<?> asList(){
        return this.config.getList(this.path);
    }

    public List<String> asStringList(){
        return this.config.getStringList(this.path);
    }

    public Configuration asSection(){
        return this.config.getSection(this.path);
    }

    public Collection<String> asKeys(){
        return this.config.getKeys();
    }

    public Configuration getConfig() {
        return config;
    }

    public String getPath() {
        return path;
    }

    public Object getDef() {
        return def;
    }
}


/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class KeyedConfig {

    private Properties props;
    private File file;

    public KeyedConfig(File file){
        this.file = file;
        this.props = new Properties();
        this.load();
    }

    private void load() {
        try{
            if(!this.file.exists()){
                this.file.createNewFile();
            }else{
                FileInputStream inputStream = new FileInputStream(this.file);
                this.props.load(inputStream);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void save(){
        try{
            FileOutputStream out = new FileOutputStream(this.file);
            this.props.store(out, null);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void reload(){
        try {
            FileInputStream inputStream = new FileInputStream(this.file);
            this.props.load(inputStream);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void set(String key, String value){
        this.props.setProperty(key, value);
        this.save();
    }

    public void add(String key, String value){
        if(!this.contains(key)) this.set(key, value);
    }

    public boolean contains(String key){
        return this.props.containsKey(key);
    }

    public String getString(String key){
        return this.props.getProperty(key);
    }

    public String getString(String key, String def){
        this.add(key, def);
        return this.getString(key);
    }

    public File getFile() {
        return file;
    }

    public Properties getProps() {
        return props;
    }
}

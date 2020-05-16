/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.storage;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.utils.storage.SpigotYMLConfig;

public class SettingsStorage extends SpigotModule {

    private SpigotYMLConfig cfg;
    private String defaultPrefix;

    public SettingsStorage(SpigotPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onLoad() {
        this.cfg = new SpigotYMLConfig(this.getPluginFolder(), "Settings.yml");
        this.defaultPrefix = "&9" + this.getPluginName() + "»&r";
        if(this.plugin.isFirstStart()){
            this.loadDefaults();
        }
    }

    public void setPrefix(String prefix){
        this.cfg.set("Prefix", prefix);
    }

    public String getPrefix(){
        return this.cfg.getString("Prefix");
    }

    public String getLanguage() {
        return this.cfg.getString("Language");
    }

    public String getCloseWord() {
        return this.cfg.getString("DialogCloseWord");
    }

    public void loadDefaults(){
        this.cfg.add("Prefix", this.defaultPrefix);
        this.cfg.add("Language", "en_US");
        this.cfg.add("DialogCloseWord", "close");
    }

    public SpigotYMLConfig getConfig() {
        return cfg;
    }
}

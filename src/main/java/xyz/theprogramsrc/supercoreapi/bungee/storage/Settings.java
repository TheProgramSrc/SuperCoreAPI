package xyz.theprogramsrc.supercoreapi.bungee.storage;

import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;
import xyz.theprogramsrc.supercoreapi.bungee.utils.storage.BungeeYMLConfig;

/**
 * Basic BungeeCord Settings used by the Plugin
 */
public class Settings extends BungeeModule {

    private BungeeYMLConfig config;

    private String defaultPrefix;

    @Override
    public void onLoad() {
        this.config = new BungeeYMLConfig(this.getPluginFolder(), "Settings.yml");
        this.defaultPrefix = "&9" + this.getPluginName() + "»&r";
        if(this.plugin.isFirstStart()){
            this.loadDefaults();
        }
    }

    public String getLanguage(){
        return this.config.getString("Language");
    }

    public BungeeYMLConfig getConfig() {
        return config;
    }

    public void loadDefaults(){
        this.config.add("Prefix", this.defaultPrefix);
        this.config.add("Language","en_US");
    }
}

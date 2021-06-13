package xyz.theprogramsrc.supercoreapi.spigot.storage;

import xyz.theprogramsrc.supercoreapi.global.files.yml.YMLConfig;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;

/**
 * Representation of the Plugin Settings File
 */
public class SettingsStorage extends SpigotModule {

    private YMLConfig cfg;
    private String defaultPrefix;

    @Override
    public void onLoad() {
        this.cfg = new YMLConfig(this.getPluginFolder(), "Settings.yml");
        this.defaultPrefix = "&9" + this.getPluginName() + "»&r";
        if(this.plugin.isFirstStart()){
            this.loadDefaults();
        }
    }

    /**
     * Sets the prefix of the Plugin
     * @param prefix the prefix
     */
    public void setPrefix(String prefix){
        this.cfg.set("Prefix", prefix);
    }

    /**
     * Sets the language of the Plugin
     * @param language the language
     */
    public void setLanguage(String language) {
        this.cfg.set("Language", language);
    }

    /**
     * Gets the prefix of the plugin
     * @return the prefix
     */
    public String getPrefix(){
        return this.cfg.getString("Prefix", this.defaultPrefix);
    }

    /**
     * Gets the language of the plugin
     * @return the language
     */
    public String getLanguage() {
        return this.cfg.getString("Language", "en_US");
    }

    private void loadDefaults(){
        this.cfg.add("Prefix", this.defaultPrefix);
        this.cfg.add("Language", "en");
    }

    /**
     * Gets the Config
     * @return the config
     */
    public YMLConfig getConfig() {
        return cfg;
    }
}

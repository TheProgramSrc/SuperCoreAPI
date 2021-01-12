package xyz.theprogramsrc.supercoreapi.spigot.storage;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.utils.storage.SpigotYMLConfig;

/**
 * Representation of the Plugin Settings File
 */
public class SettingsStorage extends SpigotModule {

    private SpigotYMLConfig cfg;
    private String defaultPrefix;

    @Override
    public void onLoad() {
        this.cfg = new SpigotYMLConfig(this.getPluginFolder(), "Settings.yml");
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
        this.cfg.add("Language", "en_US");
    }

    /**
     * Gets the Config
     * @return the config
     */
    public SpigotYMLConfig getConfig() {
        return cfg;
    }
}

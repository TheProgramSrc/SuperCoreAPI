/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.storage;

import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.config.YAMLConfig;

import java.io.File;

public class SystemSettings extends YAMLConfig{

    private final SuperCore core;
    private String defaultPrefix, defaultCloseWord;

    public SystemSettings(SuperCore core) {
        super(new File(core.getDataFolder(), "System.cfg"));
        this.core = core;
        this.load();
    }

    public void load() {
        this.defaultPrefix = "&9" + this.core.getName() + "»&r ";
        this.defaultCloseWord = "close";
        this.getPrefix();
        this.getLanguage();
        this.getCloseWord();
        this.isTranslationDownloaderEnabled();
        this.isUpdaterEnabled();
    }

    public String getCloseWord(){
        return this.getString("DialogCloseWord", this.defaultCloseWord);
    }

    public String getPrefix(){
        return this.getString("Prefix", this.defaultPrefix);
    }

    public String getLanguage(){
        return this.getString("Language", "en_US");
    }

    public void setLanguage(String lang) {
        this.set("Language", lang);
    }

    public void setPrefix(String prefix){
        this.set("Prefix", prefix);
    }

    public void setCloseWord(String closeWord){
        this.set("DialogCloseWord", closeWord.toLowerCase());
    }

    public boolean isUpdaterEnabled() {
        return this.getBoolean("Updater.Enabled", true);
    }

    public void setUpdaterEnabled(boolean enabled){
        this.set("Updater.Enabled", enabled);
    }

    public SuperCore getCore() {
        return core;
    }

    public boolean isTranslationDownloaderEnabled() {
        return this.getBoolean("TranslationDownloader", true);
    }

    public void setTranslationDownloaderEnabled(boolean b) {
        this.set("TranslationDownloader", b);
    }
}

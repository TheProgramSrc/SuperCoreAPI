/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.storage;

import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.config.YAMLConfig;
import xyz.TheProgramSrc.SuperCoreAPI.database.SQLSettings;

import java.io.File;

public class SystemSettings {

    private SuperCore core;
    private YAMLConfig config;
    private String defaultPrefix, defaultCloseWord;

    public SystemSettings(SuperCore core) {
        this.core = core;
        this.config = new YAMLConfig(new File(core.getDataFolder(), "System.cfg"));
        this.load();
    }

    public void load() {
        this.defaultPrefix = "&9" + this.core.getName() + "»&r ";
        this.defaultCloseWord = "close";
        this.getPrefix();
        this.getLanguage();
        this.getCloseWord();
        this.isUpdaterEnabled();
        this.isSQLEnabled();
    }

    public String getCloseWord(){
        return this.config.getString("DialogCloseWord", this.defaultCloseWord);
    }

    public String getPrefix(){
        return this.config.getString("Prefix", this.defaultPrefix);
    }

    public String getLanguage(){
        return this.config.getString("Language", "English");
    }

    public void setLanguage(String lang) {
        this.config.set("Language", lang);
    }

    public void setPrefix(String prefix){
        this.config.set("Prefix", prefix);
    }

    public void setCloseWord(String closeWord){
        this.config.set("DialogCloseWord", closeWord.toLowerCase());
    }

    public boolean isUpdaterEnabled() {
        return this.config.getBoolean("Updater.Enabled", true);
    }

    public boolean isSQLEnabled(){
        return this.config.getBoolean("SQL.Enabled", false);
    }

    public void saveSQLSettings(SQLSettings settings){
        String path = "SQL";
        this.config.set(path + ".UseSSL", settings.isUseSSL());
        this.config.set(path + ".Host", settings.getHost());
        this.config.set(path + ".Port", settings.getPort());
        this.config.set(path + ".DataBase", settings.getDatabase());
        this.config.set(path + ".Username", settings.getUsername());
        this.config.set(path + ".Password", settings.getPassword());
    }

    public SQLSettings getSQLSettings(){
        String path = "SQL";
        boolean useSSL = this.config.getBoolean(path + ".UseSSL");
        String host = this.config.getString(path + ".Host"),
                port = this.config.getString(path + ".Port"),
                database = this.config.getString(path + ".DataBase"),
                username = this.config.getString(path + ".Username"),
                password = this.config.getString(path + ".Password");
        return new SQLSettings(host,port,database,username,password,useSSL);
    }

    public YAMLConfig getConfig() {
        return config;
    }

    public SuperCore getCore() {
        return core;
    }
}

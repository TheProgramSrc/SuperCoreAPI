/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.storage;

import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.config.YAMLConfig;
import xyz.TheProgramSrc.SuperCoreAPI.database.SQLSettings;

import java.io.File;

public class SystemSettings extends YAMLConfig{

    private SuperCore core;
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
        this.isUpdaterEnabled();
        this.isSQLEnabled();
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

    public boolean isSQLEnabled(){
        return this.getBoolean("SQL.Enabled", false);
    }

    public void saveSQLSettings(SQLSettings settings){
        String path = "SQL";
        this.set(path + ".UseSSL", settings.isUseSSL());
        this.set(path + ".Host", settings.getHost());
        this.set(path + ".Port", settings.getPort());
        this.set(path + ".DataBase", settings.getDatabase());
        this.set(path + ".Username", settings.getUsername());
        this.set(path + ".Password", settings.getPassword());
    }

    public SQLSettings getSQLSettings(){
        String path = "SQL";
        boolean useSSL = this.getBoolean(path + ".UseSSL");
        String host = this.getString(path + ".Host"),
                port = this.getString(path + ".Port"),
                database = this.getString(path + ".DataBase"),
                username = this.getString(path + ".Username"),
                password = this.getString(path + ".Password");
        return new SQLSettings(host,port,database,username,password,useSSL);
    }

    public SuperCore getCore() {
        return core;
    }
}

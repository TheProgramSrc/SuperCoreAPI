/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import xyz.theprogramsrc.supercoreapi.bungee.storage.Settings;

import java.io.File;

public class BungeeModule implements Listener {

    protected final BungeePlugin plugin;

    public BungeeModule(BungeePlugin plugin, boolean registerListener){
        this.plugin = plugin;
        if(registerListener) this.plugin.getProxy().getPluginManager().registerListener(this.plugin, this);
        this.onLoad();
    }

    public BungeeModule(BungeePlugin plugin){
        this(plugin, true);
    }

    public void onLoad(){

    }

    protected File getPluginFolder() {
        return this.plugin.getPluginFolder();
    }

    protected File getServerFolder() {
        return this.plugin.getServerFolder();
    }

    protected String getPluginName() {
        return this.plugin.getPluginName();
    }

    protected String getPluginVersion(){
        return this.plugin.getPluginVersion();
    }

    protected void log(String message){
        this.plugin.log(message);
    }

    protected void listener(Listener... listeners){
        this.plugin.listener(listeners);
    }

    protected Settings getSettings(){
        return this.plugin.getSettings();
    }

    protected ProxyServer getProxy(){
        return this.plugin.getProxy();
    }

    public BungeePlugin getPlugin() {
        return plugin;
    }
}

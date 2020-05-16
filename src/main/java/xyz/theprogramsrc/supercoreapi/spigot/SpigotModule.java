/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot;

import org.bukkit.event.Listener;
import xyz.theprogramsrc.supercoreapi.SuperUtils;
import xyz.theprogramsrc.supercoreapi.spigot.items.PreloadedItems;
import xyz.theprogramsrc.supercoreapi.spigot.storage.SettingsStorage;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotTasks;

import java.io.File;

public class SpigotModule implements Listener {

    protected SpigotPlugin plugin;

    public SpigotModule(SpigotPlugin plugin, boolean registerListener){
        this.plugin = plugin;
        if(registerListener) this.plugin.listener(this);
        this.onLoad();
    }

    public SpigotModule(SpigotPlugin plugin){
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

    protected SpigotTasks getSpigotTasks() {
        return this.plugin.getSpigotTasks();
    }

    protected PreloadedItems getPreloadedItems(){
        return this.plugin.getPreloadedItems();
    }

    public SpigotPlugin getPlugin() {
        return plugin;
    }

    protected void listener(Listener... listeners){
        this.plugin.listener(listeners);
    }

    protected SettingsStorage getSettings() {
        return this.plugin.getSettingsStorage();
    }

    protected SuperUtils getSuperUtils() {
        return this.plugin.getSuperUtils();
    }
}

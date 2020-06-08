/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot;

import org.bukkit.event.Listener;
import xyz.theprogramsrc.supercoreapi.SuperModule;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.SuperUtils;
import xyz.theprogramsrc.supercoreapi.spigot.items.PreloadedItems;
import xyz.theprogramsrc.supercoreapi.spigot.storage.SettingsStorage;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotTasks;

import java.io.File;

public class SpigotModule extends SuperModule<Listener> implements Listener {

    public SpigotModule(SpigotPlugin plugin, boolean registerListener){
        super(plugin);
        if(registerListener) this.listener(this);
        this.onLoad();
    }

    public SpigotModule(SpigotPlugin plugin){
        this(plugin, true);
    }

    protected SpigotTasks getSpigotTasks() {
        return ((SpigotPlugin)this.plugin).getSpigotTasks();
    }

    protected PreloadedItems getPreloadedItems(){
        return ((SpigotPlugin)this.plugin).getPreloadedItems();
    }

    protected void listener(Listener... listeners){
        ((SpigotPlugin)this.plugin).listener(listeners);
    }

    protected SettingsStorage getSettings() {
        return ((SpigotPlugin)this.plugin).getSettingsStorage();
    }
}

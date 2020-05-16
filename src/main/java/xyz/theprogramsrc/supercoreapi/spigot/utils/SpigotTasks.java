/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.utils;

import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

public class SpigotTasks extends SpigotModule {

    private BukkitScheduler scheduler;

    public SpigotTasks(SpigotPlugin plugin){
        super(plugin);
    }

    @Override
    public void onLoad() {
        this.scheduler = this.plugin.getServer().getScheduler();
    }

    public BukkitTask runTask(Runnable runnable){
        return this.scheduler.runTask(this.plugin, runnable);
    }

    public BukkitTask runAsyncTask(Runnable runnable){
        return this.scheduler.runTask(this.plugin, runnable);
    }

    public BukkitTask runTaskLater(long ticks, Runnable runnable){
        return this.scheduler.runTaskLater(this.plugin, runnable, ticks);
    }

    public void cancelTask(int id){
        this.scheduler.cancelTask(id);
    }

    public int runRepeatingTask(long ticksDelay, long ticksPeriod, Runnable runnable){
        return this.scheduler.scheduleSyncRepeatingTask(this.plugin, runnable, ticksDelay, ticksPeriod);
    }

    public BukkitScheduler getScheduler() {
        return this.scheduler;
    }
}

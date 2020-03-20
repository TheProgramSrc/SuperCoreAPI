/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils.tasks;

import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;

public class TaskUtil extends SuperModule {

    private BukkitScheduler scheduler;

    public TaskUtil(SuperCore core) {
        super(core);
    }

    @Override
    public void onModuleLoad() {
        this.scheduler = this.getCore().getServer().getScheduler();
    }

    public BukkitTask runTask(Runnable runnable){
        return this.scheduler.runTask(this.getCore(), runnable);
    }

    public BukkitTask runAsyncTask(Runnable runnable){
        return this.scheduler.runTask(this.getCore(), runnable);
    }

    public BukkitTask runTaskLater(long ticks, Runnable runnable){
        return this.scheduler.runTaskLater(this.getCore(), runnable, ticks);
    }

    public void cancelTask(int id){
        this.scheduler.cancelTask(id);
    }

    public int runRepeatingTask(long ticksDelay, long ticksPeriod, Runnable runnable){
        return this.scheduler.scheduleSyncRepeatingTask(this.getCore(), runnable, ticksDelay, ticksPeriod);
    }

    public BukkitScheduler getScheduler() {
        return this.scheduler;
    }
}

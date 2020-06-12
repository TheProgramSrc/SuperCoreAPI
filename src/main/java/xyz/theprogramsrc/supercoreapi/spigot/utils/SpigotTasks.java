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
        this.scheduler = ((SpigotPlugin)this.plugin).getServer().getScheduler();
    }

    /**
     * Runs a task after 1 Tick (0.05 Seconds)
     * @param runnable the runnable
     * @return the bukkit task
     */
    public BukkitTask runTask(Runnable runnable){
        return this.scheduler.runTask(((SpigotPlugin)this.plugin), runnable);
    }

    /**
     * Runs an async task after 1 Tick (0.05 Seconds)
     * @param runnable the runnable
     * @return the bukkit task
     */
    public BukkitTask runAsyncTask(Runnable runnable){
        return this.scheduler.runTaskAsynchronously(((SpigotPlugin)this.plugin), runnable);
    }

    /**
     * Runs a task after the specified ticks
     * @param runnable the runnable
     * @param ticks the ticks to wait before starting the task
     * @return the bukkit task
     */
    public BukkitTask runTaskLater(long ticks, Runnable runnable){
        return this.scheduler.runTaskLater(((SpigotPlugin)this.plugin), runnable, ticks);
    }

    /**
     * Cancels a task
     * @param id the identifier of a task
     */
    public void cancelTask(int id){
        this.scheduler.cancelTask(id);
    }

    /**
     * Run a repeating task
     * @param ticksDelay ticks to delay before starting the task
     * @param ticksPeriod ticks to wait between every execution
     * @param runnable the runnable
     * @return the bukkit task
     */
    public int runRepeatingTask(long ticksDelay, long ticksPeriod, Runnable runnable){
        return this.scheduler.scheduleSyncRepeatingTask(((SpigotPlugin)this.plugin), runnable, ticksDelay, ticksPeriod);
    }

    /**
     * Gets the bukkit scheduler
     * @return
     */
    public BukkitScheduler getScheduler() {
        return this.scheduler;
    }
}

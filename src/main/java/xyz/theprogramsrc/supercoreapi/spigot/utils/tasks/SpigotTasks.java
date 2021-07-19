package xyz.theprogramsrc.supercoreapi.spigot.utils.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import xyz.theprogramsrc.supercoreapi.global.objects.RecurringTask;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

public class SpigotTasks extends SpigotModule {

    private BukkitScheduler scheduler;

    @Override
    public void onLoad() {
        this.scheduler = this.spigotPlugin.getServer().getScheduler();
    }

    /**
     * Runs a task after 1 Tick (0.05 Seconds)
     * @param runnable the {@link Runnable} to be executed
     * @return the {@link BukkitTask Bukkit Task}
     */
    public BukkitTask runTask(Runnable runnable){
        return this.scheduler.runTask(((SpigotPlugin)this.plugin), runnable);
    }

    /**
     * Runs an async task after 1 Tick (0.05 Seconds)
     * @param runnable the {@link Runnable} to be executed
     * @return the {@link BukkitTask Bukkit Task}
     */
    public BukkitTask runAsyncTask(Runnable runnable){
        return this.scheduler.runTaskAsynchronously(((SpigotPlugin)this.plugin), runnable);
    }

    /**
     * Runs a task after the specified ticks
     * @param runnable the runnable
     * @param ticks the ticks to wait before starting the task
     * @return the {@link BukkitTask Bukkit Task}
     */
    public BukkitTask runTaskLater(long ticks, Runnable runnable){
        return this.scheduler.runTaskLater(((SpigotPlugin)this.plugin), runnable, ticks);
    }

    /**
     * Cancels a task
     * @param task the {@link RecurringTask task} to cancel
     */
    public void cancelTask(RecurringTask task){
        task.stop();
    }

    /**
     * Runs a repeating task
     *
     * <i>NOTE: 20 ticks = 1 Second. 1 tick = 0.05 Seconds</i>
     *
     * @param ticksDelay ticks to delay before starting the task
     * @param ticksPeriod ticks to wait between every execution
     * @param runnable the {@link Runnable} to be executed
     * @return the {@link RecurringTask Recurring Task}
     */
    public RecurringTask runRepeatingTask(long ticksDelay, long ticksPeriod, Runnable runnable){
        return this.parseRecurringTask(()-> this.scheduler.runTaskTimer(((SpigotPlugin)this.plugin), runnable, ticksDelay, ticksPeriod));
    }

    /**
     * Runs a repeating task asynchronously
     *
     * <i>NOTE: 20 ticks = 1 Second. 1 tick = 0.05 Seconds</i>
     *
     * @param ticksDelay ticks to delay before starting the task
     * @param ticksPeriod ticks to wait between every execution
     * @param runnable the {@link Runnable} to be executed
     * @return the {@link RecurringTask Recurring Task}
     */
    public RecurringTask runAsyncRepeatingTask(long ticksDelay, long ticksPeriod, Runnable runnable){
        return this.parseRecurringTask(()-> this.scheduler.runTaskTimerAsynchronously(((SpigotPlugin)this.plugin), runnable, ticksDelay, ticksPeriod));
    }

    /**
     * Gets the bukkit scheduler
     * @return the bukkit scheduler
     */
    public BukkitScheduler getScheduler() {
        return this.scheduler;
    }

    private RecurringTask parseRecurringTask(final RecurringTaskBuilder builder){
        return new RecurringTask() {

            private BukkitTask task = builder.get();

            @Override
            public void start() {
                if(this.task == null){
                    this.task = builder.get();
                }

                
                if(!Bukkit.getScheduler().isCurrentlyRunning(this.task.getTaskId())){
                    this.task = builder.get();
                }
            }

            @Override
            public void stop() {
                if(this.task != null){
                    this.task.cancel();
                    this.task = null;
                }
            }
        };
    }

    private interface RecurringTaskBuilder{
        BukkitTask get();
    }

}

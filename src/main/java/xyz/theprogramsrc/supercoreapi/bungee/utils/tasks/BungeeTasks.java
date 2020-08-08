package xyz.theprogramsrc.supercoreapi.bungee.utils.tasks;

import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;
import xyz.theprogramsrc.supercoreapi.bungee.BungeePlugin;
import xyz.theprogramsrc.supercoreapi.global.objects.RecurringTask;

import java.util.concurrent.TimeUnit;

public class BungeeTasks extends BungeeModule {

    private final TaskScheduler scheduler;

    public BungeeTasks(BungeePlugin plugin) {
        super(plugin);
        this.scheduler = plugin.getProxy().getScheduler();
    }

    /**
     * Schedule a task to be executed asynchronously. The task will commence
     * running as soon as this method returns.
     *
     * @param task the task to run
     * @return the scheduled task
     */
    public ScheduledTask runAsync(Runnable task){
        return this.scheduler.runAsync(((BungeePlugin)this.plugin), task);
    }

    /**
     * Schedules a task to be executed asynchronously after the specified delay
     * is up.
     *
     * @param task the task to run
     * @param delay the delay before this task will be executed (in ticks)
     * @return the scheduled task
     */
    public ScheduledTask runAsyncLater(Runnable task, long delay){
        return this.scheduler.schedule(((BungeePlugin)this.plugin), task, delay*50L, TimeUnit.MILLISECONDS);
    }

    /**
     * Schedules a task to be executed asynchronously after the specified delay
     * is up. The scheduled task will continue running at the specified
     * interval. The interval will not begin to count down until the last task
     * invocation is complete.
     *
     * @param task the task to run
     * @param delay the delay before this task will be executed (in ticks)
     * @param period the interval before subsequent executions of this task (in ticks)
     * @return the scheduled task
     */
    public RecurringTask runAsyncTimer(Runnable task, long delay, long period){
        ScheduledTask scheduledTask = this.scheduler.schedule(((BungeePlugin)this.plugin), task, delay*50L, period*50L, TimeUnit.MILLISECONDS);
        return new RecurringTask() {
            private boolean cancelled;
            @Override
            public void start() {
                if(this.cancelled){
                    BungeeTasks.this.runAsyncTimer(task, delay, period);
                }
            }

            @Override
            public void stop() {
                scheduledTask.cancel();
                this.cancelled = true;
            }
        };
    }
}

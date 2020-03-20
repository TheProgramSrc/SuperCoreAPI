/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class CountdownTimer implements Runnable {
    private JavaPlugin plugin;
    private Integer assignedTaskId;
    private int seconds;
    private int secondsLeft;
    private Consumer<CountdownTimer> everySecond;
    private Runnable beforeTimer;
    private Runnable afterTimer;

    public CountdownTimer(JavaPlugin plugin, int seconds, Runnable beforeTimer, Runnable afterTimer, Consumer<CountdownTimer> everySecond) {
        this.plugin = plugin;
        this.seconds = seconds;
        this.secondsLeft = seconds;
        this.beforeTimer = beforeTimer;
        this.afterTimer = afterTimer;
        this.everySecond = everySecond;
    }

    public void run() {
        if (this.secondsLeft < 1) {
            this.afterTimer.run();
            if (this.assignedTaskId != null) {
                Bukkit.getScheduler().cancelTask(this.assignedTaskId);
            }

        } else {
            if (this.secondsLeft == this.seconds) {
                this.beforeTimer.run();
            }

            this.everySecond.accept(this);
            --this.secondsLeft;
        }
    }
    public int getTotalSeconds() {
        return this.seconds;
    }

    public int getSecondsLeft() {
        return this.secondsLeft;
    }

    public void scheduleTimer() {
        this.assignedTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, this, 0L, 20L);
    }
}


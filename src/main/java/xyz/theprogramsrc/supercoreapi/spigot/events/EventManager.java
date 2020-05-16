/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.events;

import org.bukkit.Bukkit;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.Time;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.TimerEvent;

public class EventManager {

    private static boolean timerEnabled = false;
    private final SpigotPlugin plugin;

    public EventManager(SpigotPlugin plugin){
        this.plugin = plugin;
    }

    private void loadTimerTask(){
        if (!timerEnabled) {
            timerEnabled = true;
            for(Time t : Time.values()) Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> Bukkit.getPluginManager().callEvent(new TimerEvent(t)), 0L, t.getTime());
        }
    }
}

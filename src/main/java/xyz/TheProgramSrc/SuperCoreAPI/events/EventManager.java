/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.events;

import org.bukkit.Bukkit;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.events.timer.Time;
import xyz.TheProgramSrc.SuperCoreAPI.events.timer.TimerEvent;

public class EventManager extends SuperModule {

    private static boolean timerEnabled = false;

    public EventManager(SuperCore core) {
        super(core);
        this.loadTimerTask();
    }

    private void loadTimerTask(){
        if (!timerEnabled) {
            timerEnabled = true;
            for(Time t : Time.values()) Bukkit.getScheduler().scheduleSyncRepeatingTask(this.getCore(), () -> Bukkit.getPluginManager().callEvent(new TimerEvent(t)), 0L, t.getTime());
        }
    }
}

/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.events.timer;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("unused")
public class TimerEvent extends Event{
    private static final HandlerList handlerList = new HandlerList();
    private final Time time;

    public TimerEvent(Time time) {
        this.time = time;
    }

    public Time getTime() {
        return this.time;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}

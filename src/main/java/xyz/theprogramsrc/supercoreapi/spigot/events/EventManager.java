/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.events.messagingService.SpigotMessagingReceiver;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.Time;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.TimerEvent;

public class EventManager extends SpigotModule {

    private static boolean timerEnabled = false;

    public EventManager(SpigotPlugin plugin){
        super(plugin);
        this.plugin = plugin;
        this.loadTimerTask();
        this.getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(this.plugin, this.plugin.getPluginMessagingChannelName());
        this.getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(this.plugin, "BungeeCord");
    }

    public void registerMessagingService(SpigotMessagingReceiver receiver){
        this.getPlugin().getServer().getMessenger().registerIncomingPluginChannel(this.plugin, this.plugin.getPluginMessagingChannelName(), receiver);
    }

    private void loadTimerTask(){
        if (!timerEnabled) {
            timerEnabled = true;
            for(Time t : Time.values()) Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> Bukkit.getPluginManager().callEvent(new TimerEvent(t)), 0L, t.getTime());
        }
    }
}

package xyz.theprogramsrc.supercoreapi.spigot.events;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.Time;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.TimerEvent;

public class EventManager extends SpigotModule {

    private static boolean timerEnabled = false;

    public EventManager(SpigotPlugin plugin){
        super(plugin);
    }

    @Override
    public void onLoad() {
        this.spigotPlugin.getServer().getMessenger().registerOutgoingPluginChannel(this.spigotPlugin, "BungeeCord");
        this.loadTimerTask();
    }

    private void loadTimerTask(){
        if (!timerEnabled) {
            timerEnabled = true;
            for(Time t : Time.values()) {
                this.getSpigotTasks().getScheduler().runTaskTimer(this.spigotPlugin, ()-> this.spigotPlugin.getServer().getPluginManager().callEvent(new TimerEvent(t)), 0L, t.getTime());
            }
        }
    }

}

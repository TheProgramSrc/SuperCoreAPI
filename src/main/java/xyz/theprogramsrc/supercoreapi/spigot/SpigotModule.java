package xyz.theprogramsrc.supercoreapi.spigot;

import org.bukkit.event.Listener;
import xyz.theprogramsrc.supercoreapi.SuperModule;
import xyz.theprogramsrc.supercoreapi.spigot.items.PreloadedItems;
import xyz.theprogramsrc.supercoreapi.spigot.storage.SettingsStorage;
import xyz.theprogramsrc.supercoreapi.spigot.utils.tasks.SpigotTasks;

/**
 * For more docs see {@link SuperModule}
 */
public class SpigotModule extends SuperModule<Listener> implements Listener {

    protected SpigotPlugin spigotPlugin;

    /**
     * Creates a new Spigot Module
     * @param registerListener if the listener should be registered
     */
    public SpigotModule(boolean registerListener){
        super(SpigotPlugin.i);
        this.spigotPlugin = SpigotPlugin.i;
        if(registerListener) this.listener(this);
        this.onLoad();
    }

    /**
     * Creates a new Spigot Module (this will register automatically the listener)
     */
    public SpigotModule(){
        this(true);
    }

    /**
     * Gets the spigot tasks
     * @return the spigot tasks
     */
    protected SpigotTasks getSpigotTasks() {
        return this.spigotPlugin.getSpigotTasks();
    }

    /**
     * Gets the preloaded items
     * @return the preloaded items
     */
    protected PreloadedItems getPreloadedItems(){
        return this.spigotPlugin.getPreloadedItems();
    }

    /**
     * Registers listeners
     * @param listeners Listeners to register
     */
    protected void listener(Listener... listeners){
        this.spigotPlugin.listener(listeners);
    }

    /**
     * Gets the plugin settings storage
     * @return the settings storage
     */
    protected SettingsStorage getSettings() {
        return this.spigotPlugin.getSettingsStorage();
    }
}

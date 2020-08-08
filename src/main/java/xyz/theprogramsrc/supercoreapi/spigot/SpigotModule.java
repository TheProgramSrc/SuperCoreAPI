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
     * @param plugin the plugin
     * @param registerListener if the listener should be registered
     */
    public SpigotModule(SpigotPlugin plugin, boolean registerListener){
        super(plugin);
        if(registerListener) this.listener(this);
        this.spigotPlugin = plugin;
        this.onLoad();
    }

    /**
     * Creates a new Spigot Module (this will register automatically the listener)
     * @param plugin the plugin
     */
    public SpigotModule(SpigotPlugin plugin){
        this(plugin, true);
    }

    /**
     * Gets the spigot tasks
     * @return the spigot tasks
     */
    protected SpigotTasks getSpigotTasks() {
        return ((SpigotPlugin)this.plugin).getSpigotTasks();
    }

    /**
     * Gets the preloaded items
     * @return the preloaded items
     */
    protected PreloadedItems getPreloadedItems(){
        return ((SpigotPlugin)this.plugin).getPreloadedItems();
    }

    /**
     * Registers listeners
     * @param listeners Listeners to register
     */
    protected void listener(Listener... listeners){
        ((SpigotPlugin)this.plugin).listener(listeners);
    }

    /**
     * Gets the plugin settings storage
     * @return the settings storage
     */
    protected SettingsStorage getSettings() {
        return ((SpigotPlugin)this.plugin).getSettingsStorage();
    }
}

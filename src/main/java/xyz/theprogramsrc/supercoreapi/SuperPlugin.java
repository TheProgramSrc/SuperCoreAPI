package xyz.theprogramsrc.supercoreapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import xyz.theprogramsrc.supercoreapi.global.LogsFilter;
import xyz.theprogramsrc.supercoreapi.global.data.PluginDataStorage;
import xyz.theprogramsrc.supercoreapi.global.dependencies.DependencyManager;
import xyz.theprogramsrc.supercoreapi.global.translations.TranslationManager;
import xyz.theprogramsrc.supercoreapi.global.translations.TranslationPack;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Representation of a Plugin
 * @param <PLUGIN> the plugin bootstrap ({@link net.md_5.bungee.api.plugin.Plugin} or {@link org.bukkit.plugin.java.JavaPlugin})
 */
public interface SuperPlugin<PLUGIN> {

    /**
     * Gets if this plugin is paid, By default is set to true, but is recommended to change it if your plugin is free.
     * @return Whether the plugin is paid or not
     */
    default boolean isPaid(){
        return true;
    }

    /**
     * Send a message through the Console using the Plugin Name ({@link #getPluginName()}) as prefix
     * @param message Message to log
     */
    default void log(String message){
        this.sendConsoleMessage("&b&l[" + this.getPluginName() + "]&r: " + message);
    }

    /**
     * Send a message through the console
     * @param message Message to send
     */
    void sendConsoleMessage(String message);

    /**
     * Gets a SuperUtils that only works on a specific server software
     * @return Super Utils
     */
    SuperUtils getSuperUtils();

    /**
     * Gets the plugin name from the Plugin.yml in Bukkit/Spigot/Paper Servers and Bungee.yml in Proxies
     * @return The name of the plugin
     */
    String getPluginName();

    /**
     * Gets the plugin version from the Plugin.yml in Bukkit/Spigot/Paper Servers and Bungee.yml in Proxies
     * @return The current version of the plugin
     */
    String getPluginVersion();

    /**
     * Gets the plugin author from the plugin.yml
     * You can also replace it manually overriding the method
     * @return The plugin author
     */
    String getPluginAuthor();

    /**
     * Check if this is the first start
     * @return If this is the first start
     */
    boolean isFirstStart();

    /**
     * Gets the Server folder (Usually where is located all the worlds and startup jar)
     * @return Server folder
     */
    File getServerFolder();

    /**
     * Gets the plugin folder where is stored all the information of the plugin
     * @return Plugin folder
     */
    File getPluginFolder();

    /**
     * Gets the Translations folder where is stored the translations of the plugin
     * @return Translations Folder
     */
    File getTranslationsFolder();

    /**
     * Gets the file which contains this plugin
     * @return The file containing this plugin
     */
    File getPluginFile();

    /**
     * Gets the translation manager
     * @return The translation manager
     */
    TranslationManager getTranslationManager();

    default void registerTranslation(Class<? extends TranslationPack> pack){
        this.getTranslationManager().registerTranslation(pack);
    }

    /**
     * Those runnables are executed while the plugin is disabling
     * @return Disable hooks
     */
    List<Runnable> getDisableHooks();

    /**
     * Add a disable hook to be executed while the plugin is being disabled
     */
    default void addDisableHook(Runnable runnable){
        this.getDisableHooks().add(runnable);
    }

    /**
     * Gets the current language
     * @return The current language
     */
    String getLanguage();

    /**
     * Executed while the plugin is being loaded
     */
    void onPluginLoad();

    /**
     * Executed while the plugin is being enabled
     */
    void onPluginEnable();

    /**
     * Executed while the plugin is being disabled
     */
    void onPluginDisable();

    /**
     * Return the Plugin OS (BungeeCord or Spigot)
     * @return Plugin OS
     */
    PLUGIN getPlugin();

    /**
     * Gets the dependency manager
     * @return Dependency Manager
     */
    DependencyManager getDependencyManager();

    /**
     * Stops the plugin
     */
    void emergencyStop();

    /**
     * Registers a log filter
     * @param logsFilter the filter
     */
    default void registerLogFilter(LogsFilter logsFilter){
        Logger consoleLogger = (Logger) LogManager.getRootLogger();
        consoleLogger.addFilter(logsFilter);
    }

    /**
     * Gets the plugin data storage
     * @return the plugin data storage
     */
    PluginDataStorage getPluginDataStorage();

    /**
     * Gets the last errors
     */
    LinkedList<Exception> getLastErrors();

    /**
     * Add an error to the last errors list
     * @param e the error to add
     */
    void addError(Exception e);

    default Exception getLastError(){
        if(getLastErrors().isEmpty()){
            return null;
        }else{
            return getLastErrors().getLast();
        }
    }
}

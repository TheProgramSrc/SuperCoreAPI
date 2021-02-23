package xyz.theprogramsrc.supercoreapi;

import xyz.theprogramsrc.supercoreapi.global.data.PluginDataStorage;
import xyz.theprogramsrc.supercoreapi.global.dependencies.DependencyManager;
import xyz.theprogramsrc.supercoreapi.global.translations.TranslationDownloader;
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

    /*
     * This need to be updated on every new release
     */
    String SUPER_CORE_API_VERSION = "4.10.2";

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

    /**
     * Removes an error from the errors list
     * @param i the index to delete
     */
    void removeError(int i);

    default Exception getLastError(){
        if(getLastErrors().isEmpty()){
            return null;
        }else{
            return getLastErrors().getLast();
        }
    }

    /**
     * Gets if the current plugin is running on a bungeecord instance
     * @return true if it's running in bungeecord, false otherwise
     */
    boolean isBungeeInstance();

    /**
     * Checks if the debug mode is enabled
     * @return if the debug mode is enabled
     */
    default boolean isDebugEnabled(){
        return this.getPluginDataStorage().isDebugEnabled();
    }

    /**
     * Show a debug message if the debug mode is enabled
     * @param message if the debug mode is enabled shows the specified debug message
     */
    default void debug(String message){
        if(this.isDebugEnabled()) {
            this.sendConsoleMessage("&b&l[" + this.getPluginName() + " DEBUG]&r: " + message);
        }
    }

    /**
     * Configures the plugin to automatically
     * download the plugin translations from GitHub
     */
    default void setupGithubTranslationDownloader(String githubUser, String githubRepo, String folder){
        this.getPluginDataStorage().add("TranslationDownloader", true);
        if(this.getPluginDataStorage().getBoolean("TranslationDownloader")){
            TranslationDownloader.downloadFromGitHub(this, githubUser, githubRepo, folder);
        }
    }

    /**
     * Configures the plugin to automatically
     * download the plugin translations from GitHub
     */
    default void setupRawTranslationDownloader(String url, String filename){
        this.getPluginDataStorage().add("TranslationDownloader", true);
        if(this.getPluginDataStorage().getBoolean("TranslationDownloader")){
            TranslationDownloader.downloadTranslation(this, url, filename);
        }
    }
}

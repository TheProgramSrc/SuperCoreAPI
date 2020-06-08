package xyz.theprogramsrc.supercoreapi;

import java.io.File;

public abstract class SuperModule<LISTENER> {

    protected SuperPlugin<?> plugin;

    public SuperModule(SuperPlugin<?> plugin){
        this.plugin = plugin;
    }

    public void onLoad(){

    }

    /**
     * Sends a message with prefix through console
     * @param message Message to send
     */
    protected void log(String message){
        this.plugin.log(message);
    }

    /**
     * Registers new listeners
     * @param listeners Listeners to register
     */
    protected abstract void listener(LISTENER... listeners);

    /**
     * Gets the server folder
     * @return server folder
     */
    protected File getServerFolder(){
        return this.plugin.getServerFolder();
    }

    /**
     * Gets the plugin file
     * @return plugin file
     */
    protected File getPluginFile(){
        return this.plugin.getPluginFile();
    }

    /**
     * Gets the plugin folder
     * @return plugin folder
     */
    protected File getPluginFolder(){
        return this.plugin.getPluginFolder();
    }

    /**
     * Gets the translation folder
     * @return translation folder
     */
    protected File getTranslationsFolder(){
        return this.plugin.getTranslationsFolder();
    }

    /**
     * Gets the plugin utils
     * @return plugin utils
     */
    protected SuperUtils getSuperUtils(){
        return this.plugin.getSuperUtils();
    }

    /**
     * Gets the plugin version
     * @return plugin version
     */
    protected String getPluginVersion(){
        return this.plugin.getPluginVersion();
    }

    /**
     * Gets the plugin name
     * @return plugin name
     */
    protected String getPluginName(){
        return this.plugin.getPluginName();
    }

    /**
     * Gets the plugin
     * @return plugin
     */
    public SuperPlugin<?> getPlugin() {
        return plugin;
    }
}

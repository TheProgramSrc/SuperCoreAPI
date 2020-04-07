/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI;
/*
    Project: PrivateDevelopment
    Package: xyz.TheProgramSrc.SuperCoreAPI
    Created by TheProgramSrc at 17-03-20
*/

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.TheProgramSrc.SuperCoreAPI.events.EventManager;
import xyz.TheProgramSrc.SuperCoreAPI.items.PreloadedItems;
import xyz.TheProgramSrc.SuperCoreAPI.skintexture.SkinTextureManager;
import xyz.TheProgramSrc.SuperCoreAPI.storage.DebugFile;
import xyz.TheProgramSrc.SuperCoreAPI.storage.SystemSettings;
import xyz.TheProgramSrc.SuperCoreAPI.translation.TranslationManager;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;
import xyz.TheProgramSrc.SuperCoreAPI.utils.tasks.TaskUtil;

import java.io.File;

public abstract class SuperCore extends JavaPlugin implements PluginProvider, iCore{

    private File serverFolder, translationsFolder, logsFolder;
    private SystemSettings systemSettings;
    private TranslationManager translationManager;
    private DebugFile debugFile;
    private TaskUtil taskUtil;
    private PreloadedItems preloadedItems;
    private SkinTextureManager skinManager;
    private boolean firstStart, emergencyStop;
//  private DependencyManager dependencyManager;
    
    @Override
    public void onLoad() {
        this.serverFolder = new File(".");
        this.firstStart = !this.getDataFolder().exists();
        if(this.isFirstStart()) this.getDataFolder().mkdir();
        this.logsFolder = new File(this.getDataFolder(), "logs/");
        if(!this.logsFolder.exists()) this.logsFolder.mkdir();
        this.translationsFolder = new File(this.getDataFolder(), "translations/");
        if(!this.translationsFolder.exists()) this.translationsFolder.mkdir();
        this.debugFile = new DebugFile(this);
        this.onPluginLoad();
        if(this.emergencyStop) return;
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        this.log("Loading Plugin...");
        /* Dependencies Load 
        this.dependencyManager = new DependencyManager();
        this.getDependencies().forEach(depend->{
            //TODO: Check if the file exists, if not download, if yes load
            //maybe something like this:
            this.dependencyManager.loadDepend(depend);
        });
        
        */
        
        this.systemSettings = new SystemSettings(this);

        /* Managers */
        this.translationManager = new TranslationManager(this);
        new EventManager(this);
        this.skinManager = new SkinTextureManager(this);

        /* Other */
        this.taskUtil = new TaskUtil(this);
        this.preloadedItems = new PreloadedItems(this);

        /* Enable */
        this.onPluginEnable();
        if(this.emergencyStop) return;
        this.log("Loaded Plugin in &c" + (System.currentTimeMillis() - start) + "ms");
        if(this.isPaid()){
            this.log("&3Thanks for buying this plugin from &aTheProgramSrc");
        }else{
            this.log("&3Thanks for downloading this plugin from &aTheProgramSrc");
        }
    }

    @Override
    public void onDisable() {
        if(this.emergencyStop) return;
        this.onPluginDisable();
    }


    @Override
    public void log(String message) {
        Utils.sendMessage(this.getServer().getConsoleSender(), "&9[&e" + this.getName() + "&9]&r: " + message);
        if(this.debugFile != null){
            this.debug(message);
        }
    }

    @Override
    public boolean isFirstStart() {
        return firstStart;
    }

    @Override
    public File getServerFolder(){
        return this.serverFolder;
    }

    @Override
    public File getTranslationsFolder() {
        return translationsFolder;
    }

    @Override
    public File getLogsFolder() {
        return logsFolder;
    }

    @Override
    public SystemSettings getSystemSettings() {
        return systemSettings;
    }

    @Override
    public TranslationManager getTranslationManager() {
        return this.translationManager;
    }

    @Override
    public String getPrefix() {
        return this.getSystemSettings().getPrefix();
    }

    @Override
    public String getLanguage() {
        return this.getSystemSettings().getLanguage();
    }

    @Override
    public void debug(String text) {
        if(this.debugFile != null) this.debugFile.debug(text);
    }

    @Override
    public void debug(Exception ex) {
        if(this.debugFile != null) this.debugFile.debug(ex);
    }

    @Override
    public void listener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public TaskUtil getTaskUtil() {
        return this.taskUtil;
    }

    @Override
    public PreloadedItems getPreloadedItems() {
        return this.preloadedItems;
    }

    @Override
    public SkinTextureManager getSkinManager() {
        return this.skinManager;
    }

    public void emergencyStop(){
        this.emergencyStop = true;
        HandlerList.unregisterAll(this);
        Bukkit.getPluginManager().disablePlugin(this);
    }
    
    /*
    public abstract List<Dependency> getDependencies();
    */
}

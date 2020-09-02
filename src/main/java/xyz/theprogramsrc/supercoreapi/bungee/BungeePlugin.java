package xyz.theprogramsrc.supercoreapi.bungee;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.SuperUtils;
import xyz.theprogramsrc.supercoreapi.bungee.events.BungeeEventManager;
import xyz.theprogramsrc.supercoreapi.bungee.storage.Settings;
import xyz.theprogramsrc.supercoreapi.bungee.utils.BungeeUtils;
import xyz.theprogramsrc.supercoreapi.bungee.utils.tasks.BungeeTasks;
import xyz.theprogramsrc.supercoreapi.global.data.PluginDataStorage;
import xyz.theprogramsrc.supercoreapi.global.dependencies.Dependencies;
import xyz.theprogramsrc.supercoreapi.global.dependencies.DependencyManager;
import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.PluginClassLoader;
import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.ReflectionClassLoader;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.translations.TranslationManager;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class BungeePlugin extends Plugin implements SuperPlugin<Plugin> {

    private boolean firstStart, emergencyStop;
    private List<Runnable> disableHooks;
    private LinkedList<Exception> errors;
    private File translationsFolder;

    private Settings settings;
    private TranslationManager translationManager;
    private DependencyManager dependencyManager;
    private PluginDataStorage pluginDataStorage;
    private BungeeTasks bungeeTasks;

    public static BungeePlugin i;

    @Override
    public void onLoad() {
        long start = System.currentTimeMillis();
        i = this;
        this.errors = new LinkedList<>();
        this.emergencyStop = false;
        new xyz.theprogramsrc.supercoreapi.Base(this);
        this.log("Loading plugin &3v"+this.getPluginVersion());
        this.disableHooks = new ArrayList<>();
        this.firstStart = !this.getDataFolder().exists();
        Utils.folder(this.getDataFolder());
        this.pluginDataStorage = new PluginDataStorage(this);
        this.onPluginLoad();
        if(this.emergencyStop) return;
        this.log("Loaded plugin in " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onEnable() {
        if(this.emergencyStop) return;
        this.log("Enabling plugin &3v" + this.getPluginVersion());
        this.bungeeTasks = new BungeeTasks();
        this.settings = new Settings();
        this.translationsFolder = Utils.folder(new File(this.getDataFolder(), "translations/"));
        this.translationManager = new TranslationManager(this);
        this.getTranslationManager().registerTranslation(Base.class);
        new BungeeEventManager();
        PluginClassLoader classLoader = new ReflectionClassLoader(this);
        this.dependencyManager = new DependencyManager(this, classLoader);
        this.dependencyManager.loadDependencies(Dependencies.get());
        this.onPluginEnable();
        this.log("Enabled plugin");
        if(this.isFirstStart()){
            if(this.isPaid()){
                this.log("Thanks for buying this plugin from &3" + this.getPluginAuthor());
            }else{
                this.log("Thanks for downloading this plugin from &3" + this.getPluginAuthor());
            }
            this.log("If you need help you can contact the developer");
            this.log("Consider leaving a positive rating");
        }
    }

    @Override
    public void onDisable() {
        if(this.emergencyStop) return;
        this.log("Disabling plugin &3v" + this.getPluginVersion());
        this.getDisableHooks().forEach(Runnable::run);
        this.onPluginDisable();
        this.log("Disabled plugin");
    }

    @Override
    public void sendConsoleMessage(String message) {
        this.getProxy().getConsole().sendMessage(new TextComponent(this.getSuperUtils().color(message)));
    }

    @Override
    public SuperUtils getSuperUtils() {
        return new BungeeUtils();
    }

    @Override
    public String getPluginName() {
        return this.getDescription().getName();
    }

    @Override
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public String getPluginAuthor() {
        return this.getDescription().getAuthor() != null ? this.getDescription().getAuthor() : "TheProgramSrc";
    }

    @Override
    public boolean isFirstStart() {
        return this.firstStart;
    }

    @Override
    public File getServerFolder() {
        return Utils.folder(new File("."));
    }

    @Override
    public File getPluginFolder() {
        return Utils.folder(this.getDataFolder());
    }

    @Override
    public File getTranslationsFolder() {
        return this.translationsFolder;
    }

    @Override
    public File getPluginFile() {
        return this.getFile();
    }

    @Override
    public TranslationManager getTranslationManager() {
        return this.translationManager;
    }

    @Override
    public String getLanguage() {
        return this.settings.getLanguage();
    }

    @Override
    public List<Runnable> getDisableHooks() {
        return disableHooks;
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    public void listener(Listener... listeners) {
        Arrays.stream(listeners).forEach(l->this.getProxy().getPluginManager().registerListener(this, l));
    }

    public Settings getSettings() {
        return this.settings;
    }

    @Override
    public DependencyManager getDependencyManager() {
        return this.dependencyManager;
    }

    /**
     * Not recommended in bungeecord because this is not an official way to disable a plugin
     */
    @Override
    public void emergencyStop() {
        this.emergencyStop = true;
        this.getProxy().getPluginManager().unregisterCommands(this);
        this.getProxy().getPluginManager().unregisterListeners(this);
        this.onDisable();
    }

    /**
     * Checks if this is an emergency stop
     * @return true if is an emergency stop, otherwise false
     */
    public boolean isEmergencyStop() {
        return this.emergencyStop;
    }

    @Override
    public PluginDataStorage getPluginDataStorage() {
        return this.pluginDataStorage;
    }

    /**
     * Gets the BungeeTasks util
     * @return the bungeetasks util
     */
    public BungeeTasks getBungeeTasks() {
        return bungeeTasks;
    }

    @Override
    public LinkedList<Exception> getLastErrors() {
        return new LinkedList<>(this.errors);
    }

    /**
     * Add an error to the Error List
     */
    public void addError(Exception e){
        this.errors.add(e);
    }
}

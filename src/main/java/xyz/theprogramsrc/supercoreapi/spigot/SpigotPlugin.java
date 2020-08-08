package xyz.theprogramsrc.supercoreapi.spigot;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.SuperUtils;
import xyz.theprogramsrc.supercoreapi.global.data.PluginDataStorage;
import xyz.theprogramsrc.supercoreapi.global.dependencies.Dependencies;
import xyz.theprogramsrc.supercoreapi.global.dependencies.DependencyManager;
import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.PluginClassLoader;
import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.ReflectionClassLoader;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.translations.TranslationManager;
import xyz.theprogramsrc.supercoreapi.global.updater.SpigotUpdateChecker;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.events.EventManager;
import xyz.theprogramsrc.supercoreapi.spigot.items.PreloadedItems;
import xyz.theprogramsrc.supercoreapi.spigot.items.Skulls;
import xyz.theprogramsrc.supercoreapi.spigot.recipes.CustomRecipe;
import xyz.theprogramsrc.supercoreapi.spigot.recipes.RecipeCreator;
import xyz.theprogramsrc.supercoreapi.spigot.storage.SettingsStorage;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotUtils;
import xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture.SkinTextureManager;
import xyz.theprogramsrc.supercoreapi.spigot.utils.tasks.SpigotTasks;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class SpigotPlugin extends JavaPlugin implements SuperPlugin<JavaPlugin> {

    public static SpigotPlugin i;

    private boolean firstStart, emergencyStop;
    private List<Runnable> disableHooks;
    private File serverFolder, translationsFolder;

    private SpigotTasks spigotTasks;
    private SettingsStorage settingsStorage;
    private TranslationManager translationManager;
    private PreloadedItems preloadedItems;
    private DependencyManager dependencyManager;
    private SkinTextureManager skinManager;
    private EventManager eventManager;
    private RecipeCreator recipeCreator;

    private PluginDataStorage pluginDataStorage;

    @Override
    public void onLoad() {
        long current = System.currentTimeMillis();
        i = this;
        this.emergencyStop = false;
        new xyz.theprogramsrc.Base();
        this.disableHooks = new ArrayList<>();
        this.serverFolder = Utils.folder(new File("."));
        this.firstStart = !this.getDataFolder().exists();
        Utils.folder(this.getDataFolder());
        this.pluginDataStorage = new PluginDataStorage(this);
        this.onPluginLoad();
        if(this.emergencyStop) {
            setEnabled(false);
            return;
        }
        this.log("Loaded plugin in " + (System.currentTimeMillis() - current) + "ms");
    }

    @Override
    public void onEnable() {
        if(this.emergencyStop) {
            setEnabled(false);
            return;
        }
        this.settingsStorage = new SettingsStorage(this);
        this.translationsFolder = Utils.folder(new File(this.getDataFolder(), "translations/"));
        this.translationManager = new TranslationManager(this);
        this.getTranslationManager().registerTranslation(Base.class);
        this.skinManager = new SkinTextureManager();
        this.spigotTasks = new SpigotTasks(this);
        this.preloadedItems = new PreloadedItems(this);
        this.eventManager = new EventManager(this);
        PluginClassLoader pluginClassLoader = new ReflectionClassLoader(this);
        this.dependencyManager = new DependencyManager(this, pluginClassLoader);
        this.dependencyManager.loadDependencies(Dependencies.get());
        this.recipeCreator = new RecipeCreator(this);
        Skulls.loadFromGitHub();
        this.onPluginEnable();
        if(this.emergencyStop) {
            setEnabled(false);
            return;
        }
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
        if(this.emergencyStop) {
            return;
        }
        this.getDisableHooks().forEach(Runnable::run);
        this.onPluginDisable();
        this.log("Disabled plugin");
    }

    @Override
    public SuperUtils getSuperUtils() {
        return new SpigotUtils();
    }

    @Override
    public boolean isFirstStart() {
        return firstStart;
    }

    @Override
    public String getPluginName() {
        return this.getName();
    }

    @Override
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public String getPluginAuthor() {
        return this.getDescription().getAuthors().size() != 0 ? (this.getDescription().getAuthors().get(0) != null ? this.getDescription().getAuthors().get(0) : "TheProgramSrc") : "TheProgramSrc";
    }

    @Override
    public void sendConsoleMessage(String message) {
        this.getServer().getConsoleSender().sendMessage(this.getSuperUtils().color(message));
    }

    /* Folders */
    @Override
    public File getServerFolder() {
        return this.serverFolder;
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
    public List<Runnable> getDisableHooks() {
        return disableHooks;
    }

    @Override
    public String getLanguage() {
        return this.settingsStorage.getLanguage();
    }

    @Override
    public TranslationManager getTranslationManager() {
        return this.translationManager;
    }

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }

    /**
     * Register the Spigot listeners
     * @param listeners Listeners to register
     */
    public void listener(Listener... listeners){
        Arrays.stream(listeners).forEach(l->this.getServer().getPluginManager().registerEvents(l, this));
    }

    /**
     * Gets the settings storage
     * @return Settings Storage
     */
    public SettingsStorage getSettingsStorage() {
        return settingsStorage;
    }

    /**
     * Gets a task util
     * @return Spigot Tasks util
     */
    public SpigotTasks getSpigotTasks() {
        return spigotTasks;
    }

    /**
     * Gets the preloaded items
     * @return Preloaded items
     */
    public PreloadedItems getPreloadedItems() {
        return preloadedItems;
    }

    /**
     * Checks if the server has bungeecord enabled
     * @return True if BungeeCord is enabled, otherwise false
     */
    public boolean isBungeeEnabled(){
        if(!getServer().getVersion().toLowerCase().contains("spigot") && !getServer().getVersion().toLowerCase().contains("paper")){
            return false;
        }
        ConfigurationSection section = getServer().spigot().getConfig().getConfigurationSection("settings");

        if(section == null){
            return false;
        }

        return section.getBoolean("settings.bungeecord");
    }

    @Override
    public DependencyManager getDependencyManager() {
        return this.dependencyManager;
    }

    /**
     * Gets the SkinTextureManager
     * @return the SkinTextureManager
     */
    public SkinTextureManager getSkinManager() {
        return this.skinManager;
    }

    @Override
    public void emergencyStop() {
        this.emergencyStop = true;
        HandlerList.unregisterAll(this);
        this.getServer().getPluginManager().disablePlugin(this);
    }

    public boolean isEmergencyStop() {
        return this.emergencyStop;
    }

    /**
     * Gets the event manager
     * @return the event manager
     */
    public EventManager getEventManager() {
        return this.eventManager;
    }

    @Override
    public PluginDataStorage getPluginDataStorage() {
        return this.pluginDataStorage;
    }

    public void checkCoreUpdates(){
        new SpigotUpdateChecker("77096"){
            @Override
            public void onCheckFail() { }

            @Override
            public void onCheckSuccess(String lastVersion) {
                SpigotPlugin.this.log("&c&lThis is not an error, this is a warning for the plugin developer that the SuperCoreAPI is outdated");
                SpigotPlugin.this.log("Please update SuperCoreAPI to v" + lastVersion);
            }
        }.check();
    }

    /**
     * Registers a new custom recipe
     *
     * @param id the identifier (must be unique)
     * @param recipe the recipe
     */
    public void registerRecipe(String id, CustomRecipe recipe){
        this.recipeCreator.addRecipe(id, recipe);
    }

    /**
     * Unregister a recipe
     * @param id the identifier
     */
    public void unregisterRecipe(String id){
        this.recipeCreator.removeRecipe(id);
    }

    /**
     * Gets all the registered recipes
     * @return the recipes
     */
    public HashMap<String, CustomRecipe> getRecipes(){
        return this.recipeCreator.getRecipes();
    }

}

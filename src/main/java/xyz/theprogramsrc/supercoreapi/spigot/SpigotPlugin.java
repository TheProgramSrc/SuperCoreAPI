package xyz.theprogramsrc.supercoreapi.spigot;

import dev.jorel.commandapi.CommandAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.data.PluginDataStorage;
import xyz.theprogramsrc.supercoreapi.global.dependencies.Dependencies;
import xyz.theprogramsrc.supercoreapi.global.dependencies.DependencyManager;
import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.PluginClassLoader;
import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.ReflectionClassLoader;
import xyz.theprogramsrc.supercoreapi.global.placeholders.SpigotPlaceholderManager;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.translations.TranslationManager;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.events.EventManager;
import xyz.theprogramsrc.supercoreapi.spigot.items.PreloadedItems;
import xyz.theprogramsrc.supercoreapi.spigot.items.Skulls;
import xyz.theprogramsrc.supercoreapi.spigot.recipes.CustomRecipe;
import xyz.theprogramsrc.supercoreapi.spigot.recipes.RecipeCreator;
import xyz.theprogramsrc.supercoreapi.spigot.storage.SettingsStorage;
import xyz.theprogramsrc.supercoreapi.spigot.utils.ServerVersion;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotUtils;
import xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture.SkinTextureManager;
import xyz.theprogramsrc.supercoreapi.spigot.utils.tasks.SpigotTasks;

import java.io.File;
import java.util.*;

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
    private SpigotPlaceholderManager placeholderManager;

    private PluginDataStorage pluginDataStorage;

    private LinkedList<Exception> errors;

    @Override
    public void onLoad() {
        long current = System.currentTimeMillis();
        i = this;
        this.errors = new LinkedList<>();
        this.emergencyStop = false;
        this.disableHooks = new ArrayList<>();
        this.serverFolder = Utils.folder(new File("."));
        this.firstStart = !this.getDataFolder().exists();
        Utils.folder(this.getDataFolder());
        this.pluginDataStorage = new PluginDataStorage(this);
        this.debug("Checking for updates");
        new xyz.theprogramsrc.supercoreapi.Base(this);
        if(ServerVersion.isServerVersionAtLeast(ServerVersion.V1_13)) {
            this.debug("Enabling CommandAPI by Jorel (https://commandapi.jorel.dev)");
            CommandAPI.onLoad(false);
        }else{
            this.debug("CommandAPI by Jorel (https://commandapi.jorel.dev) could not be enabled because of the version incompatibility. This might bring issues. Please consider updating your server to 1.13+");
        }
        this.debug("Loading plugin");
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
        this.debug("Enabling SuperCoreAPI");
        this.debug("Loading SettingsStorage");
        this.settingsStorage = new SettingsStorage();
        this.debug("Loading Translations");
        this.translationsFolder = Utils.folder(new File(this.getDataFolder(), "translations/"));
        this.translationManager = new TranslationManager(this);
        this.getTranslationManager().registerTranslation(Base.class);
        this.debug("Loading SkinManager");
        this.skinManager = new SkinTextureManager();
        this.debug("Loading SpigotTasks");
        this.spigotTasks = new SpigotTasks();
        this.debug("Loading PreloadedItems");
        this.preloadedItems = new PreloadedItems();
        this.debug("Loading EventManager");
        this.eventManager = new EventManager();
        this.debug("Loading Dependency Manager");
        PluginClassLoader pluginClassLoader = new ReflectionClassLoader(this);
        this.dependencyManager = new DependencyManager(this, pluginClassLoader);
        this.dependencyManager.loadDependencies(Dependencies.get());
        this.debug("Loading RecipeCreator [BETA]");
        this.recipeCreator = new RecipeCreator();
        this.debug("Loading SkullsDatabase from GitHub");
        Skulls.loadFromGitHub();
        this.debug("Loading Placeholder Manager");
        this.placeholderManager = new SpigotPlaceholderManager(this);
        if(ServerVersion.isServerVersionAtLeast(ServerVersion.V1_13)){
            this.debug("Enabling CommandAPI by Jorel (https://commandapi.jorel.dev)");
            CommandAPI.onEnable(this);
        }else{
            this.debug("Cannot enable CommandAPI by Jorel (https://commandapi.jorel.dev). This might bring issues. Please consider updating your server software to 1.13+");
        }
        this.debug("Enabling plugin");
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
            this.log("If you need help you can contact the developer" + (this.getDescription().getAuthors().size() > 1 ? "s" : ""));
            this.log("Consider leaving a positive rating");
        }
    }

    @Override
    public void onDisable() {
        if(this.emergencyStop) {
            return;
        }
        this.debug("Disabling plugin...");
        this.debug("Running disable hooks");
        this.getDisableHooks().forEach(Runnable::run);
        this.onPluginDisable();
        this.log("Disabled plugin");
    }

    public SpigotUtils getSuperUtils() {
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
        List<String> authors = this.getDescription().getAuthors();
        return authors.isEmpty() ? "TheProgramSrc" : String.join(",", authors);
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
        this.debug("Registering " + listeners.length + " listeners.");
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
     * Checks if the server has BungeeCord enabled
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
        this.debug("Initializing emergency stop");
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

    /**
     * Registers a new custom recipe
     *
     * @param id the identifier (must be unique)
     * @param recipe the recipe
     */
    public void registerRecipe(String id, CustomRecipe recipe){
        this.debug("Registering the recipe with id '" + id + "'");
        this.recipeCreator.addRecipe(id, recipe);
    }

    /**
     * Unregister a recipe
     * @param id the identifier
     */
    public void unregisterRecipe(String id){
        this.debug("Unregistering the recipe with id '" + id + "'");
        this.recipeCreator.removeRecipe(id);
    }

    /**
     * Gets all the registered recipes
     * @return the recipes
     */
    public HashMap<String, CustomRecipe> getRecipes(){
        return this.recipeCreator.getRecipes();
    }

    @Override
    public LinkedList<Exception> getLastErrors() {
        return new LinkedList<>(this.errors);
    }

    @Override
    public void addError(Exception e){
        this.debug("Adding new exception with message '" + e.getMessage() + "'");
        this.errors.add(e);
    }

    @Override
    public void removeError(int i) {
        if(i <= this.errors.size()){
            Exception e = this.errors.get(i);
            this.debug("Removing the error with message '" + e.getMessage() + "'");
            this.errors.remove(i);
        }
    }

    /**
     * Gets the {@link SpigotPlaceholderManager} (Useful to handle placeholders and add PlaceholderAPI Support)
     * @return the {@link SpigotPlaceholderManager}
     */
    public SpigotPlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }

    @Override
    public boolean isBungeeInstance() {
        return this.isBungeeEnabled();
    }
}

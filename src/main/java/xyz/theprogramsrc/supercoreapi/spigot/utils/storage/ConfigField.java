package xyz.theprogramsrc.supercoreapi.spigot.utils.storage;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

/**
 * Representation of the Configuration Field
 */
public class ConfigField {

    private final FileConfiguration config;
    private final String path;
    private Object def;

    /**
     * Constructor of the ConfigField. If the specified path doesn't exists or is empty, it will be filled with the default value
     * @param configuration the configuration
     * @param path the path
     * @param defaultValue the default value
     */
    public ConfigField(FileConfiguration configuration, String path, Object defaultValue){
        this.config = configuration;
        this.path = path;
        this.def = defaultValue;
        this.add();
    }

    /**
     * Constructor of the ConfigField (Without default value)
     * @param configuration the configuration
     * @param path the path
     */
    public ConfigField(FileConfiguration configuration, String path){
        this.config = configuration;
        this.path = path;
    }

    private void add() {
        if(!this.config.contains(this.path)) this.config.set(this.path, this.def);
    }

    /**
     * Gets the value of the path as String
     * @return the value of the path as String
     */
    public String toString(){
        return this.config.getString(this.path);
    }

    /**
     * Gets the value of the path as Object
     * @return the value of the path as Object
     */
    public Object asObject(){
        return this.config.get(this.path);
    }

    /**
     * Gets the value of the path as String
     * @return the value of the path as String
     */
    public String asString(){
        return this.toString();
    }

    /**
     * Gets the value of the path as Boolean
     * @return the value of the path as Boolean
     */
    public boolean asBoolean(){
        return this.config.getBoolean(this.path);
    }

    /**
     * Gets the value of the path as Integer
     * @return the value of the path as Integer
     */
    public int asInt(){
        return this.config.getInt(this.path);
    }

    /**
     * Gets the value of the path as Double
     * @return the value of the path as Double
     */
    public double asDouble(){
        return this.config.getDouble(this.path);
    }

    /**
     * Gets the value of the path as a List of Unknown objects
     * @return the value of the path as List of Unknown objects
     */
    public List<?> asList(){
        return this.config.getList(this.path);
    }

    /**
     * Gets the value of the path as a List of Strings
     * @return the value of the path as a List of Strings
     */
    public List<String> asStringList(){
        return this.config.getStringList(this.path);
    }

    /**
     * Gets the value of the path as a Section
     * @return the value of the path as a Section
     */
    public ConfigurationSection asSection(){
        return this.config.getConfigurationSection(this.path);
    }

    /**
     * Gets the value of the path as keys
     *
     * If deep is set to true, then this will contain all the keys within any child ConfigurationSections (and their children, etc). These will be in a valid path notation for you to use.
     *
     * If deep is set to false, then this will contain only the keys of any direct children, and not their own children.
     * @param deep Whether or not to get a deep list, as opposed to a shallow list.
     * @return the value of the path as keys
     */
    public Set<String> asKeys(boolean deep){
        return this.config.getKeys(deep);
    }

    /**
     * Gets the File Configuration
     * @return the file configuration
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Gets the path
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the default value of the path
     * @return the default value of the path
     */
    public Object getDef() {
        return def;
    }
}

package xyz.theprogramsrc.supercoreapi.bungee.utils.storage;


import net.md_5.bungee.config.Configuration;

import java.util.Collection;
import java.util.List;

/**
 * Representation of a Configuration Field
 */
public class ConfigurationField {

    private final Configuration config;
    private final String path;
    private Object def;

    /**
     * Constructor of the ConfigurationField
     *
     * If the specified Configuration doesn't have the specified path
     * this util will set the specified default value.
     *
     * @param configuration The configuration
     * @param path The path of the field
     * @param defaultValue The default value if the field
     */
    public ConfigurationField(Configuration configuration, String path, Object defaultValue){
        this.config = configuration;
        this.path = path;
        this.def = defaultValue;
        this.add();
    }

    /**
     * Constructor of the ConfigurationField (without default)
     *
     * @param configuration The configuration
     * @param path The path of the field
     */
    public ConfigurationField(Configuration configuration, String path){
        this.config = configuration;
        this.path = path;
    }

    private void add() {
        if(!this.config.contains(this.path)) this.config.set(this.path, this.def);
    }

    /**
     * Gets the content of the path as String
     * @see #asString()
     *
     * @return The value of the path as String
     */
    public String toString(){
        return this.config.getString(this.path);
    }

    /**
     * Gets the content of the path as Object
     * @return The value of the path as Object
     */
    public Object asObject(){
        return this.config.get(this.path);
    }

    /**
     * Gets the content of the path as String
     * @return The value of the path as String
     */
    public String asString(){
        return this.toString();
    }

    /**
     * Gets the content of the path as Boolean
     * @return The value of the path as Boolean
     */
    public boolean asBoolean(){
        return this.config.getBoolean(this.path);
    }

    /**
     * Gets the content of the path as Integer
     * @return The value of the path as Integer
     */
    public int asInt(){
        return this.config.getInt(this.path);
    }

    /**
     * Gets the content of the path as Double
     * @return The value of the path as Double
     */
    public double asDouble(){
        return this.config.getDouble(this.path);
    }

    /**
     * Gets the content of the path as a List of unknown objects
     * @return The value of the path as a List of unknown objects
     */
    public List<?> asList(){
        return this.config.getList(this.path);
    }

    /**
     * Gets the content of the path as a List of Strings
     * @return The value of the path as a List of string
     */
    public List<String> asStringList(){
        return this.config.getStringList(this.path);
    }

    /**
     * Gets the content of the path as a Configuration Section
     * @return The value of the path as a Configuration Section
     */
    public Configuration asSection(){
        return this.config.getSection(this.path);
    }

    /**
     * Gets the content of the path as Keys
     * @return The value of the path as Keys
     */
    public Collection<String> asKeys(){
        return this.config.getKeys();
    }

    /**
     * Gets the Configuration
     * @return The configuration
     */
    public Configuration getConfig() {
        return config;
    }


    /**
     * Gets the path
     * @return The path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the default value of the field
     * @return The default value
     */
    public Object getDef() {
        return def;
    }
}


package xyz.theprogramsrc.supercoreapi.global.files.yml;

import org.simpleyaml.configuration.ConfigurationSection;
import org.simpleyaml.configuration.comments.CommentType;

import java.util.List;
import java.util.Set;

/**
 * Representation of the Configuration Field
 */
public class ConfigField {

    private final YMLConfig config;
    private final String path;
    private final Object def;

    /**
     * Constructor of the ConfigField. If the specified path doesn't exists or is empty, it will be filled with the default value
     * @param configuration the configuration
     * @param path the path
     * @param defaultValue the default value
     * @param comment the comment to add to the field
     * @param commentType the type of comment to add
     */
    public ConfigField(YMLConfig configuration, String path, Object defaultValue, String comment, CommentType commentType){
        this.config = configuration;
        this.path = path;
        this.def = defaultValue;
        if(this.def != null){
            this.config.add(this.path, this.def);
        }

        if(comment != null){
            this.config.addComment(path, comment, commentType);
        }
    }

    /**
     * Constructor of the ConfigField. If the specified path doesn't exists or is empty, it will be filled with the default value
     * @param configuration the configuration
     * @param path the path
     * @param defaultValue the default value
     * @param comment the comment to add to the field
     */
    public ConfigField(YMLConfig configuration, String path, Object defaultValue, String comment){
        this(configuration, path, defaultValue, comment, CommentType.BLOCK);
    }

    /**
     * Constructor of the ConfigField. If the specified path doesn't exists or is empty, it will be filled with the default value
     * @param configuration the configuration
     * @param path the path
     * @param defaultValue the default value
     */
    public ConfigField(YMLConfig configuration, String path, Object defaultValue){
        this(configuration, path, defaultValue, null);
    }

    /**
     * Constructor of the ConfigField (Without default value)
     * @param configuration the configuration
     * @param path the path
     */
    public ConfigField(YMLConfig configuration, String path){
        this(configuration, path, null, null);
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
        return this.config.getSection(this.path);
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
     * Gets the YMLConfiguration
     * @return the YMLConfiguration
     */
    public YMLConfig getConfig() {
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

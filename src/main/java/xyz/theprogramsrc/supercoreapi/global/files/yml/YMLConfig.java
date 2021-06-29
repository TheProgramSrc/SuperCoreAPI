package xyz.theprogramsrc.supercoreapi.global.files.yml;

import org.simpleyaml.configuration.ConfigurationSection;
import org.simpleyaml.configuration.comments.CommentType;
import org.simpleyaml.configuration.file.FileConfiguration;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class YMLConfig {

    private final YamlFile config;
    private final File file;

    public YMLConfig(File file){
        this.file = file;
        this.config = new YamlFile(file);
        this.load();
    }

    public YMLConfig(File folder, String name){
        this(new File(Utils.folder(folder), name));
    }

    /**
     * Loads the configuration from the file into the memory overriding any existing changes.
     */
    public void load(){
        try{
            if(!this.file.exists()) this.file.createNewFile();
            this.config.load(this.file);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    /**
     * Saves the configuration to the file
     */
    public void save(){
        try{
            this.config.save(this.file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Clear the file and its content and then create a new file
     */
    public void clear(){
        try{
            Utils.destroyFile(this.file);
            if(!this.file.exists())this.file.createNewFile();
            this.load();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Sets a comment into the given path using the given content and type replacing the existing one
     * @param path the path where the comment
     * @param comment the comment to add
     * @param commentType the type of comment to add
     */
    public void setComment(String path, String comment, CommentType commentType){
        this.config.setComment(path, comment, commentType);
        this.save();
    }

    /**
     * Sets a comment into the given path using the given content replacing the existing one
     * @param path the path where the comment
     * @param comment the comment to add
     */
    public void setComment(String path, String comment){
        this.config.setComment(path, comment);
        this.save();
    }

    /**
     * Adds a comment into the given path using the given content and type
     * @param path the path where the comment
     * @param comment the comment to add
     * @param commentType the type of comment to add
     */
    public void addComment(String path, String comment, CommentType commentType){
        if(this.config.getComment(path, commentType) == null) this.config.setComment(path, comment, commentType);
    }

    /**
     * Adds a comment into the given path using the given content replacing the existing one
     * @param path the path where the comment
     * @param comment the comment to add
     */
    public void addComment(String path, String comment){
        if(this.config.getComment(path) == null) this.config.setComment(path, comment);
    }

    /**
     * Used to set information inside the file
     * @param path Location of the saved information
     * @param value Information to save
     */
    public void set(String path, Object value){
        if(value instanceof Float){
            float val = ((float) value);
            value = Float.toString(val);
        }
        this.config.set(path, value);
        this.save();
    }

    /**
     * Used to add information inside the file
     * ONLY IF DOES NOT EXISTS
     * @param path Location of the saved information
     * @param value Information to save
     */
    public void add(String path, Object value){
        if(!this.contains(path)) this.set(path, value);
    }

    /**
     * Used to check if the configuration contains a specific Path
     * @param path Path to check
     * @return true if the path exists, otherwise false
     */
    public boolean contains(String path){
        return this.config.contains(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public Object get(String path){
        return this.config.get(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public Object get(String path, Object def){
        this.add(path, def);
        return this.get(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public String getString(String path){
        return this.config.getString(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public String getString(String path, String def){
        this.add(path, def);
        return this.getString(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public boolean getBoolean(String path){
        return this.config.getBoolean(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public boolean getBoolean(String path, boolean def){
        this.add(path, def);
        return this.getBoolean(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public int getInt(String path){
        return this.config.getInt(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public int getInt(String path, int def){
        this.add(path, def);
        return this.getInt(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public double getDouble(String path){
        return this.config.getDouble(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public double getDouble(String path, double def){
        this.add(path,def);
        return this.getDouble(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public long getLong(String path){
        return this.config.getLong(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public long getLong(String path, long def){
        this.add(path, def);
        return this.getLong(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public float getFloat(String path){
        return Long.parseLong(this.getString(path));
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public float getFloat(String path, float def){
        this.add(path, def);
        return this.getFloat(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public List<?> getList(String path){
        return this.config.getList(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public List<?> getList(String path, List<?> def){
        this.add(path, def);
        return this.getList(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public List<String> getStringList(String path){
        return this.config.getStringList(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public List<String> getStringList(String path, List<String> def){
        this.add(path, def);
        return this.getStringList(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public List<Boolean> getBooleanList(String path){
        return this.config.getBooleanList(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public List<Boolean> getBooleanList(String path, List<Boolean> def){
        this.add(path, def);
        return this.getBooleanList(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public List<Integer> getIntList(String path){
        return this.config.getIntegerList(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public List<Integer> getIntList(String path, List<Integer> def){
        this.add(path, def);
        return this.getIntList(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public List<Double> getDoubleList(String path){
        return this.config.getDoubleList(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public List<Double> getDoubleList(String path, List<Double> def){
        this.add(path, def);
        return this.getDoubleList(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public List<Long> getLongList(String path){
        return this.config.getLongList(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public List<Long> getLongList(String path, List<Long> def){
        this.add(path, def);
        return this.getLongList(path);
    }

    /**
     * Used to request information from the file
     * @param path Path where is saved the information
     * @return The information saved in the specified path
     */
    public List<Float> getFloatList(String path){
        return this.config.getFloatList(path);
    }

    /**
     * Used to request information from the file, and if not exists set the default value
     * @param path Path where is saved the information
     * @param def Object to save if there is no existent information in the path
     * @return The information saved in the specified path
     */
    public List<Float> getFloatList(String path, List<Float> def){
        this.add(path, def);
        return this.getFloatList(path);
    }

    /**
     * Used to request {@link ConfigurationSection} from the file
     * @param path Path where is saved the {@link ConfigurationSection}
     * @return The {@link ConfigurationSection} saved in the specified path
     */
    public ConfigurationSection getSection(String path){
        return this.config.getConfigurationSection(path);
    }

    /**
     * Used to get a set containing all keys in this section.
     *
     * If deep is set to true, then this will contain all the keys within any child ConfigurationSections (and their children, etc). These will be in a valid path notation for you to use.
     *
     * If deep is set to false, then this will contain only the keys of any direct children, and not their own children.
     * @param deep Whether or not to get a deep list, as opposed to a shallow list.
     * @return Set of keys contained within this ConfigurationSection.
     */
    public Set<String> getKeys(boolean deep){
        return this.config.getKeys(deep);
    }

    /**
     * Used to get the {@link FileConfiguration}
     * @return the {@link FileConfiguration}
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Used to get the file containing all this information
     * @return File containing all this information
     */
    public File getFile() {
        return file;
    }
}

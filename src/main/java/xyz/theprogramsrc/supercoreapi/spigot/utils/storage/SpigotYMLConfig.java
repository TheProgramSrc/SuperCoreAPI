package xyz.theprogramsrc.supercoreapi.spigot.utils.storage;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Used to generate and work with YAML files
 */
public class SpigotYMLConfig {
    private FileConfiguration config;
    private final File file;

    public SpigotYMLConfig(File file){
        this.file = file;
        this.load();
    }

    public SpigotYMLConfig(File folder, String fileName){
        this(new File(Utils.folder(folder), fileName));
    }

    private void load(){
        try {
            if(!this.file.exists()) this.file.createNewFile();
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Clear the file and his contents
     * and then create a new file
     */
    public void clear(){
        try{
            Utils.destroyFile(this.file);
            if(!this.file.exists())this.file.createNewFile();
            this.reload();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Used to reload the cache
     * (Make sure to use {@link #save()} before executing this)
     */
    public void reload(){
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Used to save all the cached information
     */
    public void save(){
        try{
            this.config.save(this.file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
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
     * Used to get the main {@link FileConfiguration}
     * @return Main {@link FileConfiguration}
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

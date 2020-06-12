package xyz.theprogramsrc.supercoreapi.bungee.utils.storage;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Used to generate and work with YAML files
 */
public class BungeeYMLConfig {
    private Configuration config;
    private final File file;

    public BungeeYMLConfig(File file){
        this.file = file;
        this.load();
    }

    public BungeeYMLConfig(File folder, String fileName){
        this(new File(Utils.folder(folder), fileName));
    }

    private void load(){
        try {
            if(!this.file.exists()) this.file.createNewFile();
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
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
     *
     * @throws Exception if occurs any exception
     */
    public void reload(){
        try{
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Used to save all the cached information
     */
    public void save(){
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, this.file);
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
        return this.config.getIntList(path);
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
     * Used to request {@link Configuration} from the file
     * @param path Path where is saved the {@link Configuration}
     * @return The {@link Configuration} saved in the specified path
     */
    public Configuration getSection(String path){
        return this.config.getSection(path);
    }

    /**
     * Gets keys, not deep by default.
     *
     * @return top level keys for this section
     */
    public Set<String> getKeys(){
        return new HashSet<>(this.config.getKeys());
    }

    /**
     * Used to get the main {@link Configuration}
     * @return Main {@link Configuration}
     */
    public Configuration getConfig() {
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

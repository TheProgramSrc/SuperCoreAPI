package xyz.theprogramsrc.supercoreapi.global.storage.universal;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.files.JsonConfig;
import xyz.theprogramsrc.supercoreapi.global.json.JSONUtil;
import xyz.theprogramsrc.supercoreapi.global.storage.DataBase;
import xyz.theprogramsrc.supercoreapi.global.storage.DataBaseSettings;
import xyz.theprogramsrc.supercoreapi.global.storage.MySQLDataBase;
import xyz.theprogramsrc.supercoreapi.global.storage.SQLiteDataBase;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;

/**
 * This basically instead of having a lot of databases
 * you can use this universal storage which only uses 1
 * centralized database.
 */
public class UniversalStorage {

    private static File universalStorageFolder;
    private static JsonConfig config;
    private static DataBase dataBase;

    // Here we require the plugin to get the plugins folder
    private static void setup(SuperPlugin<?> plugin){
        if(universalStorageFolder == null){ // First we setup the directories
            File pluginsFolder = new File(plugin.getServerFolder(), "plugins/");
            universalStorageFolder = new File(pluginsFolder, "UniversalStorage/");
            if(!universalStorageFolder.exists()) universalStorageFolder.mkdirs();
        }

        if(config == null){
            config = new JsonConfig(universalStorageFolder, "Settings.json");
            JSONUtil json = new JSONUtil();
            json.add("enabled", false);
            json.add("host", "sql.example.com");
            json.add("port", "3306");
            json.add("database", "universal_storage");
            json.add("username", "universal_storage");
            json.add("password", Utils.randomPassword(16));
            json.add("ssl", false);
            config.add("sql_settings", json.getJsonObject());
        }

        if(dataBase == null){
            JSONUtil json = new JSONUtil(config.get("sql_settings").getAsJsonObject());
            if(json.getBoolean("enabled")){ // MySQL
                dataBase = new MySQLDataBase(null) {
                    @Override
                    public DataBaseSettings getDataBaseSettings() {
                        return new DataBaseSettings() {
                            @Override
                            public String host() {
                                return json.getString("host");
                            }

                            @Override
                            public String port() {
                                return json.getString("port");
                            }

                            @Override
                            public String database() {
                                return json.getString("database");
                            }

                            @Override
                            public String username() {
                                return json.getString("username");
                            }

                            @Override
                            public String password() {
                                return json.getString("password");
                            }

                            @Override
                            public boolean useSSL() {
                                return json.getBoolean("ssl");
                            }
                        };
                    }
                };
            }else{ // SQLite
                dataBase = new SQLiteDataBase(new File(universalStorageFolder, "UniversalStorage.db"));
            }
        }

        JsonArray plugins = config.getOrCreateArray("plugins");
        if(!plugins.contains(new JsonPrimitive(plugin.getPluginName()))){
            plugins.add(plugin.getPluginName());
        }
        config.set("plugins", plugins);
    }

    /**
     * Registers a plugin in the UniversalStorage
     * @param plugin the plugin to register
     */
    public static void register(SuperPlugin<?> plugin){
        setup(plugin); // With this method we ensure that the universal storage settings exists
    }

    /**
     * Gets the database
     * @return the database
     */
    public static DataBase database() {
        return dataBase;
    }
}

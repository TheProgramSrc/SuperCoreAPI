package xyz.theprogramsrc.supercoreapi.global.storage;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * An util to manage and use DataBase Storage
 */
public class DataBaseStorage {

    protected DataBase dataBase;
    protected SuperPlugin<?> plugin;

    public DataBaseStorage(SuperPlugin<?> plugin, DataBase dataBase){
        this.plugin = plugin;
        this.dataBase = dataBase;
    }

    /**
     * Gets the prefix of the tables (PluginName_)
     *
     * @return the prefix of the tables
     */
    public String getTablePrefix(){
        return this.plugin.getPluginName().toLowerCase() + "_";
    }

    /**
     * Used to get the last inserted id (using the column 'id')
     * @param connection The connection
     * @param table The table to check
     * @return the last inserted id
     */
    protected int requestLastInsertedID(Connection connection, String table) {
        String select = "SELECT * FROM " + this.getTablePrefix() + table + " ORDER BY id DESC LIMIT 1";
        String query;
        if (this.dataBase instanceof SQLiteDataBase) {
            query = table == null ? "SELECT last_insert_rowid()" : select;
        } else {
            query = table == null ? "SELECT LAST_INSERT_ID()" : select;
        }

        int id = -1;
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query);
            result.next();
            id = result.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }


    /**
     * Gets the plugin
     * @return the plugin
     */
    public SuperPlugin<?> getPlugin() {
        return plugin;
    }

    /**
     * Gets the DataBase that it's being used
     * @return the database
     */
    public DataBase getDataBase() {
        return dataBase;
    }
}

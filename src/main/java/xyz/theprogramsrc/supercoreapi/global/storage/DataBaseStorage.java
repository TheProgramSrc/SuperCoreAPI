package xyz.theprogramsrc.supercoreapi.global.storage;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseStorage {

    protected DataBase dataBase;
    protected SuperPlugin<?> plugin;

    public DataBaseStorage(SuperPlugin<?> plugin, DataBase dataBase){
        this.plugin = plugin;
        this.dataBase = dataBase;
    }

    public String getTablePrefix(){
        return this.plugin.getPluginName().toLowerCase() + "_";
    }

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


}

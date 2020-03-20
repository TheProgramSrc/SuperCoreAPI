/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.database;

import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteDataBase extends SuperModule implements DataBase{

    private Connection connection;

    public SQLiteDataBase(SuperCore core) {
        super(core);
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void disconnect() {
        try{
            if(this.connection != null){
                this.connection.close();
            }
        }catch (Exception ex){
            this.log("&cCannot disconnect from SQL");
            this.debug(ex);
        }
    }

    @Override
    public void connect(ConnectionCallback callback) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception ex) {
            this.log("&cCannot load SQL Class");
            this.debug(ex);
        }

        try{
            this.connection = DriverManager.getConnection(this.getType().formatSQLite(this.getCore().getDataFolder(), this.getCore().getName().toLowerCase()+".db"));
        }catch (Exception ex){
            this.log("&cCannot Fetch connection with SQLite");
            this.debug(ex);
        }

        try{
            callback.onAccept(this.getConnection());
        }catch (Exception ex){
            this.log("&cCannot execute query for SQLite");
            this.debug(ex);
        }
    }

    @Override
    public DataBaseType getType() {
        return DataBaseType.SQLite;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

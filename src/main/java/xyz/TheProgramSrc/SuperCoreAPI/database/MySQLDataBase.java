/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.database;

import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLDataBase extends SuperModule implements DataBase{

    private boolean connected;
    private Connection connection;
    private String host, port, database, username, password;
    private boolean useSSL;

    public MySQLDataBase(SuperCore core, String host, String port, String database, String username, String password, boolean useSSL) {
        super(core);
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.useSSL = useSSL;
    }

    @Override
    public Connection getConnection() {
        try{
            return this.connection;
        }catch (Exception ex){
            this.log("&cCannot fetch MySQL Connection");
            this.debug(ex);
            return null;
        }
    }

    @Override
    public void disconnect() {
        try{
            this.connection.close();
            this.connected = false;
        }catch (Exception ex){
            this.log("&cCannot disconnect from MySQL");
            this.debug(ex);
        }
    }

    @Override
    public void connect(ConnectionCallback callback) {
        try{
            String url = this.getType().formatMySQL(this.host, this.port, this.database, this.useSSL);
            Connection connection = DriverManager.getConnection(url, this.username, this.password);
            this.connected = false;
            callback.onAccept(connection);
        }catch (Exception ex){
            this.log("&cCannot connect to MySQL");
            this.debug(ex);
        }
    }

    @Override
    public DataBaseType getType() {
        return DataBaseType.MySQL;
    }

    @Override
    public boolean isEnabled() {
        return this.connected;
    }
}

/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.config;

import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SQLConfig {

    private final String host, port, database, username, password, tablePrefix;
    private final boolean useSSL;

    public SQLConfig(String host, String port, String database, String username, String password, String tablePrefix, boolean useSSL) {
        Utils.notNull(host, "MySQL Host cannot be null!");
        Utils.notNull(database, "MySQL DataBase cannot be null!");
        Utils.notNull(username,"MySQL Username cannot be null!");
        Utils.notNull(password, "MySQL Password cannot be null");
        if(port == null) port = "3306";
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.tablePrefix = tablePrefix;
        this.useSSL = useSSL;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public boolean useSSL() {
        return useSSL;
    }

    public String getConnectionURL(){
        String ssl = this.useSSL() ? "true" : "false";
        return "jdbc:mysql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDatabase() + "?useSSL="+ssl+"&characterEncoding=utf-8";
    }

    public void update(String sql) throws SQLException{
        Connection connection = this.connect();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
        connection.close();
        ps.close();
    }

    public Connection connect() throws SQLException {
        String url = this.getConnectionURL();
        return DriverManager.getConnection(url, this.getUsername(), this.getPassword());
    }

}

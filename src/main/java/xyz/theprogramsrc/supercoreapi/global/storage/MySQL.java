/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.global.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MySQL {
    
    private final String host, port, database, user, password;

    public MySQL(String host, String port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void update(String query) throws Exception{
        Connection connection = getConnection();
        PreparedStatement p = connection.prepareStatement(query);
        p.execute();
        connection.close();
        p.close();
    }

    public Connection getConnection() throws Exception {
        String connectString = "jdbc:mysql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDatabase() + "?useSSL=false&characterEncoding=utf-8&serverTimezone=UTC";
        return DriverManager.getConnection(connectString, this.getUser(), this.getPassword());
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

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}

/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.database;

import java.sql.Connection;

public interface DataBase {

    Connection getConnection();

    void disconnect();

    void connect(ConnectionCallback callback);

    DataBaseType getType();

    boolean isEnabled();

    interface ConnectionCallback{

        void onAccept(Connection connection) throws Exception;
    }
}

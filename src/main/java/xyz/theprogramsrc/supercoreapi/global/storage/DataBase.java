package xyz.theprogramsrc.supercoreapi.global.storage;

import java.sql.Connection;

public interface DataBase{

    /**
     * Gets the DataBase Settings
     * @return DataBase Settings
     */
    DataBaseSettings getDataBaseSettings();

    /**
     * Checks if the DataBase is loaded
     * (Recommended to use before executing anything)
     *
     * @return If the DataBase is loaded or not
     */
    boolean isLoaded();

    /**
     * Closes the current connection with the DataBase
     */
    void closeConnection();

    /**
     * Used to connect to the DataBase and execute a {@link ConnectionCall}
     * @param call ConnectionCall to execute
     */
    void connect(ConnectionCall call);

    /**
     * Interface executed when a Connection is called
     */
    interface ConnectionCall{
        void onConnect(Connection connection);
    }
}

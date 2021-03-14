package xyz.theprogramsrc.supercoreapi.global.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

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

    /**
     * Tests the current database connection by requesting the database name
     * @return true if the connection is successful, false otherwise
     */
    default boolean testConnection(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.connect(c-> {
            try{
                c.getMetaData().getDatabaseProductName();
                atomicBoolean.set(true);
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        });

        return atomicBoolean.get();
    }
}

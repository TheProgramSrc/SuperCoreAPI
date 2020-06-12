package xyz.theprogramsrc.supercoreapi.global.storage;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class SQLiteDataBase implements DataBase {

    protected Connection connection;
    protected SuperPlugin<?> plugin;

    public SQLiteDataBase(SuperPlugin<?> plugin){
        this.plugin = plugin;

        this.createConnection();
    }

    private void createConnection() {
        try{
            File file = new File(this.plugin.getPluginFolder(), this.plugin.getPluginName().toLowerCase()+".db");
            if(!file.exists()) file.createNewFile();
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        }catch (Exception ex){
            this.plugin.log("&cCannot create SQLite Connection:");
            ex.printStackTrace();
        }
    }

    /**
     * SQLite is always available
     * @return True because SQLite dont require internet
     */
    @Override
    public boolean isLoaded() {
        return true;
    }

    /**
     * Closes the current connection with DataBase
     */
    @Override
    public void closeConnection() {
        try{
            if(this.connection != null){
                this.connection.close();
            }
        }catch (Exception ex){
            this.plugin.log("&cCannot close SQLite Connection:");
            ex.printStackTrace();
        }
    }

    /**
     * Connects to the DataBase and execute the specified call
     * @param call ConnectionCall to execute
     */
    @Override
    public void connect(ConnectionCall call) {
        if(this.connection == null){
            try{
                this.createConnection();
            }catch (Exception ex){
                this.plugin.log("&cCannot connect with SQLite DataBase:");
                ex.printStackTrace();
            }
        }

        try{
            call.onConnect(this.connection);
        }catch (Exception ex){
            this.plugin.log("&cCannot execute ConnectionCall:");
            ex.printStackTrace();
        }
    }
}

package xyz.theprogramsrc.supercoreapi.global.storage;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataBase implements DataBase {

    protected Connection connection;
    protected SuperPlugin<?> plugin;
    private final File databaseFile;

    public SQLiteDataBase(SuperPlugin<?> plugin){
        this.plugin = plugin;
        this.databaseFile = new File(this.plugin.getPluginFolder(), this.plugin.getPluginName().toLowerCase()+".db");
        this.createConnection();
    }

    public SQLiteDataBase(File databaseFile){
        this.databaseFile = databaseFile;
        this.createConnection();
    }

    private void createConnection() {
        if(this.plugin != null) this.plugin.debug("Connecting to SQLite DataBase");
        try{
            if(!databaseFile.exists()) databaseFile.createNewFile();
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getPath());
        }catch (SQLException | ClassNotFoundException | IOException ex){
            if(this.plugin != null) this.plugin.addError(ex);
            this.log("&cCannot create SQLite Connection:");
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
        if(this.plugin != null) this.plugin.debug("Closing connection with SQLite DataBase");
        try{
            if(this.connection != null){
                this.connection.close();
            }
        }catch (SQLException ex){
            if(this.plugin != null) this.plugin.addError(ex);
            this.log("&cCannot close SQLite Connection:");
            ex.printStackTrace();
        }
    }

    /**
     * Connects to the DataBase and execute the specified call
     * @param call ConnectionCall to execute
     */
    @Override
    public void connect(ConnectionCall call) {
        try{
            if(this.connection == null){
                this.createConnection();
            }else if(this.connection.isClosed()){
                this.createConnection();
            }
        }catch (SQLException e){
            if(this.plugin != null) this.plugin.addError(e);
            this.log("&cError while connecting to SQLite:");
            e.printStackTrace();
        }

        call.onConnect(this.connection);
    }

    /**
     * This is not needed because SQLite doesn't use credentials
     * @return null
     */
    @Override
    public DataBaseSettings getDataBaseSettings() {
        return null;
    }

    private void log(String msg){
        if(this.plugin == null)
            System.out.println(msg);
        else
            this.plugin.log(msg);
    }
}

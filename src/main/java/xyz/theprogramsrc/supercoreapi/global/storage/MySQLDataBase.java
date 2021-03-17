package xyz.theprogramsrc.supercoreapi.global.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.LogsFilter;

import java.sql.Connection;

public abstract class MySQLDataBase implements DataBase {

    protected Connection connection;
    private HikariDataSource dataSource;
    protected SuperPlugin<?> plugin;
    private boolean loaded;

    public MySQLDataBase(SuperPlugin<?> plugin) {
        boolean debug = plugin != null && plugin.isDebugEnabled();
        this.plugin = plugin;
        this.log("Connecting to '" + getDataBaseSettings().host() + ":" + getDataBaseSettings().port() + "'...");
        if (this.hideHikariLogs() && !debug) {
            new LogsFilter(LogsFilter.FilterResult.DENY, "com.zaxxer.hikari").register();
        }
        if (plugin != null) this.plugin.debug("Loading HikariConfig");
        HikariConfig cfg = new HikariConfig();
        cfg.setDriverClassName("com.mysql.jdbc.Driver");
        cfg.setJdbcUrl("jdbc:mysql://" + getDataBaseSettings().host() + ":" + getDataBaseSettings().port() + "/" + getDataBaseSettings().database() + "?useSSL=" + getDataBaseSettings().useSSL());
        cfg.setUsername(getDataBaseSettings().username());
        cfg.setPassword(getDataBaseSettings().password());
        cfg.setMaximumPoolSize(3);

        if (plugin != null) this.plugin.debug("Loading HikariDataSource");
        try {
            this.dataSource = new HikariDataSource(cfg);
            this.loaded = true;
        } catch (Exception ex) {
            this.log("&cCannot connect to MySQL DataBase:");
            if (plugin != null) this.plugin.addError(ex);
            ex.printStackTrace();
        }
    }

    /**
     * Used to check if the Connection is loaded
     *
     * @return if the connection is loaded
     */
    @Override
    public boolean isLoaded() {
        return this.loaded;
    }

    /**
     * Closes the connection between the plugin and the DataBase
     */
    @Override
    public void closeConnection() {
        if (this.plugin != null) this.plugin.debug("Closing connection with DataBase");
        try {
            if (this.dataSource != null) {
                this.dataSource.close();
            }
        } catch (Exception ex) {
            this.log("&cCannot close MySQL Connection:");
            if (this.plugin != null) this.plugin.addError(ex);
            ex.printStackTrace();
        }
    }

    /**
     * Used to connect to the DataBase and execute a {@link xyz.theprogramsrc.supercoreapi.global.storage.DataBase.ConnectionCall ConnectionCall}
     *
     * @param call ConnectionCall to execute
     */
    @Override
    public void connect(ConnectionCall call) {
        try (Connection connection = this.dataSource.getConnection()) {
            call.onConnect(connection);
        } catch (Exception ex) {
            this.log("&cCannot execute MySQL Connection Call:");
            if (this.plugin != null) this.plugin.addError(ex);
            ex.printStackTrace();
        }
    }

    /**
     * @return true to hide the hikaricp logs, otherwise false
     */
    public boolean hideHikariLogs() {
        return true;
    }

    private void log(String msg){
        if(this.plugin == null)
            System.out.println(msg);
        else
            this.plugin.log(msg);
    }
}

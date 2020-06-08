package xyz.theprogramsrc.plugin;

import xyz.theprogramsrc.supercoreapi.bungee.BungeePlugin;
import xyz.theprogramsrc.supercoreapi.global.storage.*;

import java.sql.ResultSet;

public class MyPlugin extends BungeePlugin {
    @Override
    public void onPluginLoad() {

    }

    @Override
    public void onPluginEnable() {
        DataBase db = new SQLiteDataBase(this) {
            @Override
            public DataBaseSettings getDataBaseSettings() {
                return null;
            }
        };

        exe(db);
        db = new MySQLDataBase(this) {
            @Override
            public DataBaseSettings getDataBaseSettings() {
                return new DataBaseSettings() {
                    @Override
                    public String host() {
                        return "localhost";
                    }

                    @Override
                    public String database() {
                        return "test";
                    }

                    @Override
                    public String username() {
                        return "test";
                    }

                    @Override
                    public String password() {
                        return "test";
                    }
                };
            }
        };
        exe(db);
    }

    private void exe(DataBase db){
        db.connect(c->{
            try{
                c.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS users (id VARCHAR(50), username VARCHAR(100))");
                if(!c.createStatement().executeQuery("SELECT * FROM users;").next()){
                    c.createStatement().executeUpdate("INSERT INTO users (identifier, username) VALUES ('1', 'theprogramsrc')");
                }

                ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users");
                while(rs.next()){
                    log("ID=" + rs.getInt("id"));
                    log("NAME=" + rs.getString("username"));
                    log("========================");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void onPluginDisable() {

    }
}

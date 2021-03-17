package xyz.theprogramsrc.supercoreapi.global.storage.global;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.storage.DataBaseStorage;
import xyz.theprogramsrc.supercoreapi.global.storage.MySQLDataBase;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Representation of a Storage that can be used in Bungee and Spigot
 * @param <OBJ> the main object to store
 */
public abstract class GlobalStorage<OBJ> extends DataBaseStorage {

    private HashMap<String, OBJ> cache;

    public GlobalStorage(SuperPlugin<?> plugin, MySQLDataBase mySQLDataBase){
        super(plugin, mySQLDataBase);
        this.cache = new HashMap<>();
        this.preloadTable();
    }

    /**
     * Gets the table where the object should be saved
     * @return the name of the table
     */
    public abstract String getTableName();

    /**
     * Gets the name of the key column
     * @return the name of the key column
     */
    public abstract String getKeyColumnName();

    /**
     * Gets the name of the value column
     * @return the name of the value column
     */
    public abstract String getValueColumnName();

    /**
     * Used to serialize and store in the database
     * @param obj the object
     * @return the serialized object
     */
    public abstract String serialize(OBJ obj);

    /**
     * Used to deserialize the Stored Data
     * @param data the data
     * @return the object
     */
    public abstract OBJ deserialize(String data);

    /**
     * Store the object in the database
     * @param key the key
     * @param value the object
     */
    public void save(final String key, OBJ value){
        this.cache.remove(key);
        final String data = this.serialize(value);
        this.dataBase.connect(c->{
            try{
                Statement s = c.createStatement();
                if(this.exists(key)){
                    s.executeUpdate("UPDATE " + this.getTableName() + " SET `" + this.getValueColumnName() + "`='"+data+"' WHERE `" + this.getKeyColumnName() + "`='"+key+"';");
                }else{
                    s.executeUpdate("INSERT INTO " + this.getTableName() + " (`" + this.getKeyColumnName() + "`,`" + this.getValueColumnName() + "`) VALUES ('"+key+"','"+data+"')");
                }
            }catch (Exception ex){
                this.plugin.log("&cCannot save data with key: '" + key + "'");
                ex.printStackTrace();
            }
        });
    }

    /**
     * Gets the object from the database
     * @param key the key
     * @return the object
     */
    public OBJ get(final String key){
        if(this.cache.containsKey(key)) return this.cache.get(key);
        AtomicReference<OBJ> obj = new AtomicReference<>(null);
        this.dataBase.connect(c->{
            try{
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM " + this.getTableName() + " WHERE `" + this.getKeyColumnName() + "`='"+key+"'");
                if(rs.next()){
                    obj.set(this.deserialize(rs.getString(this.getValueColumnName())));
                }
            }catch (Exception ex){
                this.plugin.log("&cCannot fetch data with key: '" + key + "'");
                ex.printStackTrace();
            }
        });
        return obj.get();
    }

    /**
     * Used to get all the object stored in the database
     * @return the objects
     */
    public HashMap<String, OBJ> getAll(){
        HashMap<String, OBJ> objs = new HashMap<>();

        this.dataBase.connect(c->{
            try{
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM " + this.getTableName() + ";");
                while(rs.next()){
                    String key = rs.getString(this.getKeyColumnName());
                    String value = rs.getString(this.getValueColumnName());
                    OBJ obj = this.deserialize(value);
                    objs.put(key, obj);
                }
            }catch (Exception ex){
                this.plugin.log("&cCouldn't fetch data:");
                ex.printStackTrace();
            }
        });
        this.cache = new HashMap<>(objs);
        return objs;
    }

    /**
     * Checks if the object is stored in the database
     * @param key the key
     * @return true if is stored, otherwise false
     */
    public boolean exists(final String key){
        AtomicBoolean exist = new AtomicBoolean(false);
        this.dataBase.connect(c->{
            try{
                Statement s = c.createStatement();
                exist.set(s.executeQuery("SELECT * FROM " + this.getTableName() + " WHERE `" + this.getKeyColumnName() + "`='"+key+"';").next());
            }catch (Exception ex){
                this.plugin.log("&cCannot fetch data with key: '" + key + "'");
                ex.printStackTrace();
            }
        });

        return exist.get();
    }

    private void preloadTable(){
        this.dataBase.connect(c->{
            try{
                Statement s = c.createStatement();
                s.executeUpdate("CREATE TABLE IF NOT EXISTS " + this.getTableName() + " (`" + this.getKeyColumnName() + "` VARCHAR(1000), `" + this.getValueColumnName() + "` VARCHAR(150000));");
            }catch (Exception ex){
                this.plugin.log("&cCouldn't load Table:");
                ex.printStackTrace();
            }
        });
    }
}

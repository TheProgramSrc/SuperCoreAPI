package xyz.theprogramsrc.supercoreapi.global.storage;

/**
 * A representation of the DataBase Settings
 */
public interface DataBaseSettings {

    /**
     * Gets the host of the DataBase
     * @return the host
     */
    String host();

    /**
     * The port of the DataBase
     * @return the port
     */
    default String port(){
        return "3306";
    }

    /**
     * Gets the database to Connect
     * @return the database
     */
    String database();

    /**
     * Gets the username of the DataBase
     * @return the username
     */
    String username();

    /**
     * Gets the password of the DataBase
     * @return the password
     */
    String password();

    /**
     * Gets if the DataBase uses SSL
     * @return if the DataBase uses SSL
     */
    default boolean useSSL(){
        return false;
    }

    /**
     * Gets the URL connection of the DataBase
     * @return the URL connection
     */
    default String getURL(){
        return "jdbc:mysql://{Host}:{Port}/{Database}?useSSL={UseSSL}";
    }
}

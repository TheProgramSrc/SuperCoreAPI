package xyz.theprogramsrc.supercoreapi.global.storage;

public interface DataBaseSettings {

    String host();

    default String port(){
        return "3306";
    }

    String database();

    String username();

    String password();

    default boolean useSSL(){
        return false;
    }
}

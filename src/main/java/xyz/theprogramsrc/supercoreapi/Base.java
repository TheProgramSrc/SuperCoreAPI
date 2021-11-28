package xyz.theprogramsrc.supercoreapi;

/**
 * This is a set of utils that need to be loaded before everything
 */
public class Base {

    protected SuperPlugin<?> plugin;

    public Base(SuperPlugin<?> plugin){
        this.plugin = plugin;
    }

}

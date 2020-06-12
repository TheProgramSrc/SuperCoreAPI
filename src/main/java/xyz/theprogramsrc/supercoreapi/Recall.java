package xyz.theprogramsrc.supercoreapi;

/**
 * Runnable with a specific object
 * @param <OBJ> the object
 */
public interface Recall<OBJ> {

    void run(OBJ obj);
}

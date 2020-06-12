package xyz.theprogramsrc.supercoreapi.spigot.guis.objects;

/**
 * Representation of the rows of a GUI
 */
public enum GUIRows {
    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54);

    private final int size;

    GUIRows(int size){
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}

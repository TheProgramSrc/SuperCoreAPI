package xyz.theprogramsrc.supercoreapi.spigot.guis.objects;

/**
 * Representation of the rows of a GUI
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}.
 */
@Deprecated public enum GUIRows {
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

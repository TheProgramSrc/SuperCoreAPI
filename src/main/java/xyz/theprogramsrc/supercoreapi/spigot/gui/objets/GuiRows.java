package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

/**
 * Representation of the Gui Rows
 * @since 5.2.0
 */
public enum GuiRows {
    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54);

    public final int size;

    GuiRows(int size){
        this.size = size;
    }

}

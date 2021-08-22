package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

/**
 * Representation of a GuiTitle
 * @since 5.2.0
 */
public class GuiTitle {

    private final String title;
    private final boolean centered;

    private GuiTitle(String title, boolean centered) {
        this.title = title;
        this.centered = centered;
    }

    public String getTitle() {
        String title = this.title;
        if(this.centered){
            StringBuilder result = new StringBuilder();
            int spaces = (27 - SpigotPlugin.i.getSuperUtils().removeColor(title).length());

            for (int i = 0; i < spaces; i++) {
                result.append(" ");
            }

            title = result.append(title).toString();
        }

        return SpigotPlugin.i.getSuperUtils().color(title);
    }

    /**
     * Creates a new GuiTitle with the given title
     * @param title The title of the GuiTitle
     * @param centered If the title should be centered
     * @return A new GuiTitle
     */
    public static GuiTitle of(String title, boolean centered) {
        return new GuiTitle(title, centered);
    }

    /**
     * Creates a new GuiTitle with the given title without being centered. See {@link #of(String, boolean)}
     * @param title The title of the GuiTitle
     * @return A new GuiTitle
     */
    public static GuiTitle of(String title) {
        return new GuiTitle(title, false);
    }
    
}

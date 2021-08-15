package xyz.theprogramsrc.supercoreapi.spigot.gui.objets;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

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

    public static GuiTitle of(String title, boolean centered) {
        return new GuiTitle(title, centered);
    }

    public static GuiTitle of(String title) {
        return new GuiTitle(title, false);
    }
    
}

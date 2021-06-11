package xyz.theprogramsrc.supercoreapi.spigot.utils;

import org.bukkit.Bukkit;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

public enum MinecraftServer {

    UNKNOWN, CRAFTBUKKIT, SPIGOT, PAPER, TACO, GLOWSTONE;
    private final static MinecraftServer minecraftServer = checkProject();

    private static MinecraftServer checkProject() {
        String serverPath = Bukkit.getServer().getClass().getName();
        if (serverPath.contains("glowstone")) {
            return GLOWSTONE;
        }

        // Taco is pretty easy to check. it uses paper stuff, though, so should be checked first
        try {
            Class.forName("net.techcable.tacospigot.TacoSpigotConfig");
            return TACO;
        } catch (ClassNotFoundException ignored) { }

        // Paper used to be called "paperclip"
        try {
            Class.forName("com.destroystokyo.paperclip.Paperclip");
            return PAPER;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return PAPER;
        } catch (ClassNotFoundException ignored) { }

        // Spigot is the fork that pretty much all builds are based on anymore
        try {
            Class.forName("org.spigotmc.SpigotConfig");
            return SPIGOT;
        } catch (ClassNotFoundException ignored) { }

        return serverPath.contains("craftbukkit") ? CRAFTBUKKIT : UNKNOWN;
    }

    /**
     * Gets tge current server software name
     * @return the current server software name
     */
    public static MinecraftServer getServerVersion() {
        return minecraftServer;
    }

    /**
     * Checks if the current server software it's the same as the provided one
     * @param version the other version to check
     * @return true if booth are the same, false otherwise
     */
    public static boolean isServer(MinecraftServer version) {
        return minecraftServer == version;
    }

    /**
     * Checks if the current server software it's one of the provided ones
     * @param versions the versions to check
     * @return true if the current server software it's in the list, false otherwise
     */
    public static boolean isServer(MinecraftServer... versions) {
        return Utils.inArray(versions, minecraftServer);
    }

}

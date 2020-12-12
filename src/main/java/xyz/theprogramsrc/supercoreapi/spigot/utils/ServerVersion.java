package xyz.theprogramsrc.supercoreapi.spigot.utils;

import org.bukkit.Bukkit;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

public enum ServerVersion {
    UNKNOWN, V1_7, V1_8, V1_9, V1_10, V1_11, V1_12, V1_13, V1_14, V1_15, V1_16, V1_17, V1_18, V1_19, V1_20;

    private final static String serverPackagePath = Bukkit.getServer().getClass().getPackage().getName();
    private final static String serverPackageVersion = serverPackagePath.substring(serverPackagePath.lastIndexOf('.') + 1);
    private final static String serverReleaseVersion = serverPackageVersion.indexOf('R') != -1 ? serverPackageVersion.substring(serverPackageVersion.indexOf('R') + 1) : "";
    private final static ServerVersion serverVersion = getVersion();

    private static ServerVersion getVersion() {
        for (ServerVersion version : values()) {
            if (serverPackageVersion.toUpperCase().startsWith(version.name())) {
                return version;
            }
        }
        return UNKNOWN;
    }

    /**
     * Checks if the current version is older than the provided one
     * @param other the other version to check
     * @return true if the version is older, false otherwise.
     */
    public boolean isLessThan(ServerVersion other) {
        return this.ordinal() < other.ordinal();
    }

    /**
     * Checks if the current version it's the same or older than the provided one
     * @param other the other version to check
     * @return true if the current version is equals or older than the provided one, false otherwise
     */
    public boolean isAtOrBelow(ServerVersion other) {
        return this.ordinal() <= other.ordinal();
    }

    /**
     * Checks if the current version is newer than the provided one.
     * @param other the other version to check
     * @return true if the current version is newer than the provided one, false otherwise
     */
    public boolean isGreaterThan(ServerVersion other) {
        return this.ordinal() > other.ordinal();
    }

    /**
     * Checks if the current version it's at least the provided one or higher
     * @param other the other version to check
     * @return true if the current version it's at least the provided one or higher, false otherwise
     */
    public boolean isAtLeast(ServerVersion other) {
        return this.ordinal() >= other.ordinal();
    }

    /**
     * Gets the server package version
     * @return the server package version
     */
    public static String getServerVersionString() {
        return serverPackageVersion;
    }

    /**
     * Gets the release number
     * @return the release number
     */
    public static String getVersionReleaseNumber() {
        return serverReleaseVersion;
    }

    /**
     * Gets the server version
     * @return the server version
     */
    public static ServerVersion getServerVersion() {
        return serverVersion;
    }

    /**
     * Checks if the server version is equals to the provided one
     * @param version the other version to check
     * @return true if the server version is equals to the provided one, false otherwise
     */
    public static boolean isServerVersion(ServerVersion version) {
        return serverVersion == version;
    }

    /**
     * Checks if the server version is equals to one of the provided ones
     * @param versions the other versions to check
     * @return true if the server version is equals to one of the provided ones, false otherwise
     */
    public static boolean isServerVersion(ServerVersion... versions) {
        return Utils.inArray(versions, serverVersion);
    }

    /**
     * Checks if the current version it's above the provided one
     * @param version the other version to check
     * @return true if the current version it's above the provided one, false otherwise
     */
    public static boolean isServerVersionAbove(ServerVersion version) {
        return serverVersion.ordinal() > version.ordinal();
    }

    /**
     * Checks if the current version it's at least the provided one
     * @param version the other version to check
     * @return true if the current version it's at least the provided one, false otherwise
     */
    public static boolean isServerVersionAtLeast(ServerVersion version) {
        return serverVersion.ordinal() >= version.ordinal();
    }

    /**
     * Checks if the current version it's the same or below the provided one
     * @param version the other version to check
     * @return true if the current version it's the same or below the provided one, false otherwise
     */
    public static boolean isServerVersionAtOrBelow(ServerVersion version) {
        return serverVersion.ordinal() <= version.ordinal();
    }

    /**
     * Checks if the current version it's below the provided one
     * @param version the other version to check
     * @return true if the current version it's below the provided one, false otherwise
     */
    public static boolean isServerVersionBelow(ServerVersion version) {
        return serverVersion.ordinal() < version.ordinal();
    }
}

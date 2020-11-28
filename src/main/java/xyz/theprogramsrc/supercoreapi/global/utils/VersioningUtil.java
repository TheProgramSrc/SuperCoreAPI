package xyz.theprogramsrc.supercoreapi.global.utils;

public class VersioningUtil {

    /**
     * Check any type of version into numbers to check the version.
     *
     * @param currentVersion the current version (usually stored in the plugin.yml or bungee.yml)
     * @param latestVersion the latest version stored in the database, or the platform with the latest update
     * @return 1 if there latest version is higher than the current one; 2 if the current version is higher than the latest version; 0 if booth versions are the same
     */
    public static int checkVersions(String currentVersion, String latestVersion){
        StringBuilder latest = new StringBuilder(latestVersion.split(" ")[0].replaceAll("\\.", "").replaceAll("[a-zA-Z]", "")),
                current = new StringBuilder(currentVersion.split(" ")[0].replaceAll("[^\\d.]", "").replaceAll("\\.", "").replaceAll("[a-zA-Z]", ""));
        if(latest.length() > current.length()){
            int amount = latest.length() - current.length();
            for(int i = 0; i < amount; ++i){
                current.append("0");
            }
        }else if(current.length() > latest.length()){
            int amount = current.length() - latest.length();
            for(int i = 0; i < amount; ++i){
                latest.append("0");
            }
        }
        int l = Integer.parseInt(latest.toString()), c = Integer.parseInt(current.toString());
        if(l > c){
            return 1;
        }else if(l < c){
            return 2;
        }else {
            return 0;
        }
    }
}

package xyz.TheProgramSrc.SuperCoreAPI.packets;

import org.bukkit.Bukkit;

public class MinecraftVersion {

    public Version getVersion() {
        return Version.getCurrent();
    }

    public enum Version {
        v1_8_R1,
        v1_8_R2,
        v1_8_R3,
        v1_9_R1,
        v1_9_R2,
        v1_10_R1,
        v1_11_R1,
        v1_12_R1,
        v1_13_R1,
        v1_13_R2,
        v1_14_R1,
        v1_14_R2,
        v1_15_R1;

        private Integer value = Integer.valueOf(this.name().replaceAll("[^\\d.]", ""));
        private String shortVersion = this.name().substring(0, this.name().length() - 3);
        private static Version current = null;

        public Integer getValue() {
            return this.value;
        }

        public String getShortVersion() {
            return this.shortVersion;
        }

        public static Version getCurrent() {
            if (current != null) {
                return current;
            } else {
                String[] packageVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
                String returnedVersion = packageVersion[packageVersion.length - 1];
                Version[] versions;

                for(int i = 0; i < (versions = values()).length; ++i) {
                    Version one = versions[i];
                    if (one.name().equalsIgnoreCase(returnedVersion)) {
                        current = one;
                        break;
                    }
                }

                return current;
            }
        }

        public boolean isEqual(Version version) {
            return this.getValue().equals(version.getValue());
        }

        public static boolean isCurrentEqualOrHigher(Version v) {
            return getCurrent().getValue() >= v.getValue();
        }
    }
}

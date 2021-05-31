package xyz.theprogramsrc.supercoreapi.global.dependencies;

import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.LoaderType;

import java.util.Arrays;

public enum Dependencies {

    SQLITE_DRIVER(
            "org.xerial",
            "sqlite-jdbc",
            "3.25.2",
            "pF2mGr7WFWilM/3s4SUJMYCCjt6w1Lb21XLgz0V0ZfY=",
            LoaderType.ISOLATED
    ),
    HIKARI(
            "com.zaxxer",
            "HikariCP",
            "3.3.1",
            "SIaA1yzGHOZNpZNoIt903f5ScJrIB3u8CT2cNkaLcy0=",
            LoaderType.ISOLATED
    ),

    ;

    private final Dependency dependency;

    Dependencies(String groupId, String artifactId, String version, String checksum, LoaderType loaderType) {
        this.dependency = new Dependency(name(), groupId, artifactId, version, checksum, loaderType);
    }

    public Dependency getDependency() {
        return dependency;
    }

    public static Dependency[] get(){
        return Arrays.stream(values()).map(Dependencies::getDependency).toArray(Dependency[]::new);
    }
}

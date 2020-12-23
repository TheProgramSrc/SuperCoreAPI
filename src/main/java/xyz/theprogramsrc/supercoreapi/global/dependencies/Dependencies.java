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

    SLF4J_API(
            "org.slf4j",
            "slf4j-api",
            "1.7.25",
            "GMSgCV1cHaa4F1kudnuyPSndL1YK1033X/OWHb3iW3k=",
            LoaderType.ISOLATED
    ),

    SLF4J_NOP(
            "org.slf4j",
            "slf4j-nop",
            "1.7.25",
            "bLEnE49Btahp+ezdBhrRd5mg4/5yBGAHlxVOsEMu6xI=",
            LoaderType.ISOLATED
    )

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

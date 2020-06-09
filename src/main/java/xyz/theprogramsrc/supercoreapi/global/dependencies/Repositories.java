package xyz.theprogramsrc.supercoreapi.global.dependencies;

public enum Repositories {

    MAVEN_CENTRAL("https://repo1.maven.org/maven2/"),
    SONATYPE("https://oss.sonatype.org/content/groups/public/"),
    CODE_MC("https://repo.codemc.org/repository/maven-public/"),


    ;
    private final String url;

    Repositories(String url){
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}

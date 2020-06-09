/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package xyz.theprogramsrc.supercoreapi.global.dependencies;

import com.google.common.collect.ImmutableList;
import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.LoaderType;

import java.net.URL;
import java.util.Base64;
import java.util.List;

public class Dependency {

    private static final String MAVEN_CENTRAL_REPO = "https://repo1.maven.org/maven2/";
    private static final String LUCK_MIRROR_REPO = "https://nexus.lucko.me/repository/maven-central/";
    private static final String MAVEN_FORMAT = "%s/%s/%s/%s-%s.jar";

    private final String name;
    private final List<URL> urls;
    private final String version;
    private final byte[] checksum;
    private boolean autoLoad;
    private LoaderType loaderType;

    public Dependency(String name, String groupId, String artifactId, String version, String checksum, LoaderType loaderType) {
        this.name = name;
        this.autoLoad = true;
        this.loaderType = loaderType;

        String path = String.format(MAVEN_FORMAT,
                rewriteEscaping(groupId).replace(".", "/"),
                rewriteEscaping(artifactId),
                version,
                rewriteEscaping(artifactId),
                version
        );
        try {
            this.urls = ImmutableList.of(
                    new URL(LUCK_MIRROR_REPO + path),
                    new URL(MAVEN_CENTRAL_REPO + path)
            );
        } catch (Exception e) {
            throw new RuntimeException(e); // propagate
        }
        this.version = version;
        this.checksum = Base64.getDecoder().decode(checksum);
    }

    private static String rewriteEscaping(String s) {
        return s.replace("{}", ".");
    }

    public static String getMavenCentralRepo() {
        return MAVEN_CENTRAL_REPO;
    }

    public static String getLuckMirrorRepo() {
        return LUCK_MIRROR_REPO;
    }

    public static String getMavenFormat() {
        return MAVEN_FORMAT;
    }

    public String getName() {
        return name;
    }

    public List<URL> getUrls() {
        return urls;
    }

    public String getVersion() {
        return version;
    }

    public byte[] getChecksum() {
        return checksum;
    }

    public boolean isAutoLoad() {
        return autoLoad;
    }

    public LoaderType getLoaderType() {
        return loaderType;
    }

    public void setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }

    public void setLoaderType(LoaderType loaderType) {
        this.loaderType = loaderType;
    }
}

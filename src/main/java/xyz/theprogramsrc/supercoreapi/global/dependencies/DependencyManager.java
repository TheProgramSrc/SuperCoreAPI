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

import com.google.common.collect.ImmutableSet;
import com.google.common.io.ByteStreams;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.IsolatedClassLoader;
import xyz.theprogramsrc.supercoreapi.global.dependencies.classloader.PluginClassLoader;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Responsible for loading runtime dependencies.
 */
public class DependencyManager {

    private final SuperPlugin<?> plugin;
    private final PluginClassLoader pluginClassLoader;
    private final MessageDigest digest;
    private final Map<Dependency, Path> loaded = new HashMap<>();
    private final Map<ImmutableSet<Dependency>, IsolatedClassLoader> loaders = new HashMap<>();

    public DependencyManager(SuperPlugin<?> plugin, PluginClassLoader pluginClassLoader) {
        this.plugin = plugin;
        this.pluginClassLoader = pluginClassLoader;

        try {
            this.digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the library saving directory
     *
     * @return The directory
     */
    private File getSaveDirectory() {
        File file = new File(plugin.getPluginFolder(), "lib");
        if (!file.exists()) file.mkdir();
        return file;
    }

    public SuperPlugin<?> getPlugin() {
        return plugin;
    }

    public PluginClassLoader getPluginClassLoader() {
        return pluginClassLoader;
    }

    public MessageDigest getDigest() {
        return digest;
    }

    public Map<Dependency, Path> getLoaded() {
        return loaded;
    }

    public Map<ImmutableSet<Dependency>, IsolatedClassLoader> getLoaders() {
        return loaders;
    }

    /**
     * Load the dependencies
     *
     * @param dependencies The dependencies to load
     */
    public void loadDependencies(Dependency... dependencies) {
        if(!Utils.isConnected()){
            this.plugin.log("&cCannot download dependencies. Please connect to internet. Some features will be disabled");
        }else{
            File saveDirectory = getSaveDirectory();

            // create a list of file sources
            List<Source> sources = new ArrayList<>();

            // obtain a file for each of the dependencies
            for (Dependency dependency : dependencies) {
                if (this.loaded.containsKey(dependency)) {
                    continue;
                }

                try {
                    Path file = downloadDependency(saveDirectory.toPath(), dependency);
                    sources.add(new Source(dependency, file));
                } catch (Throwable e) {
                    this.plugin.log("Exception whilst downloading dependency " + dependency.getName());
                    e.printStackTrace();
                }
            }

            // load each of the jars
            for (Source source : sources) {
                if (!source.dependency.isAutoLoad()) {
                    this.loaded.put(source.dependency, source.file);
                    continue;
                }

                try {
                    this.pluginClassLoader.loadJar(source.file);
                    this.loaded.put(source.dependency, source.file);
                } catch (Throwable e) {
                    this.plugin.log("&cFailed to load dependency jar '" + source.file.getFileName().toString() + "'.");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Download the dependency to specified directory
     *
     * @param saveDirectory The save directory
     * @param dependency    The dependency to download
     * @return The downloaded dependency's path
     * @throws Exception If something goes wrong
     */
    private Path downloadDependency(Path saveDirectory, Dependency dependency) throws Exception {
        String fileName = dependency.getName().toLowerCase() + "-" + dependency.getVersion() + ".jar";
        Path file = saveDirectory.resolve(fileName);

        // if the file already exists, don't attempt to re-download it.
        if (Files.exists(file)) {
            return file;
        }

        boolean success = false;
        Exception lastError = null;

        // getUrls returns two possible sources of the dependency.
        // [0] is a mirror of Maven Central, used to reduce load on central. apparently they don't like being used as a CDN
        // [1] is Maven Central itself

        // side note: the relative "security" of the mirror is less than central, but it actually doesn't matter.
        // we compare the downloaded file against a checksum here, so even if the mirror became compromised, RCE wouldn't be possible.
        // if the mirror download doesn't match the checksum, we just try maven central instead.

        List<URL> urls = dependency.getUrls();
        for (int i = 0; i < urls.size() && !success; i++) {
            URL url = urls.get(i);

            try {
                URLConnection connection = url.openConnection();

                // i == 0 when we're trying to use the mirror repo.
                // set some timeout properties so when/if this repository goes offline, we quickly fallback to central.
                if (i == 0) {
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
                    connection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(5));
                    connection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));
                }

                try (InputStream in = connection.getInputStream()) {
                    // download the jar content
                    byte[] bytes = ByteStreams.toByteArray(in);
                    if (bytes.length == 0) {
                        throw new RuntimeException("Empty stream");
                    }


                    // compute a hash for the downloaded file
                    byte[] hash = this.digest.digest(bytes);

                    // ensure the hash matches the expected checksum
                    if (!Arrays.equals(hash, dependency.getChecksum())) {
                        throw new RuntimeException("Downloaded file had an invalid hash. " +
                                "Expected: " + Base64.getEncoder().encodeToString(dependency.getChecksum()) + " " +
                                "Actual: " + Base64.getEncoder().encodeToString(hash));
                    }

                    // if the checksum matches, save the content to disk
                    Files.write(file, bytes);
                    success = true;
                }
            } catch (Exception e) {
                lastError = e;
            }
        }

        if (!success) {
            throw new RuntimeException("Unable to download", lastError);
        }

        // ensure the file saved correctly
        if (!Files.exists(file)) {
            throw new IllegalStateException("File not present: " + file.toString());
        } else {
            return file;
        }
    }

    /**
     * Get the isolated class loader
     * from the dependencies specified
     *
     * @param dependencies The dependencies
     * @return The isolated class loader
     */
    public IsolatedClassLoader getIsolatedClassLoader(Dependency... dependencies) {
        ImmutableSet<Dependency> set = ImmutableSet.copyOf(dependencies);

        for (Dependency dependency : dependencies) {
            if (!this.loaded.containsKey(dependency)) {
                throw new IllegalStateException("Dependency " + dependency + " is not loaded.");
            }
        }

        synchronized (this.loaders) {
            IsolatedClassLoader classLoader = this.loaders.get(set);
            if (classLoader != null) {
                return classLoader;
            }

            URL[] urls = set.stream()
                    .map(this.loaded::get)
                    .map(file -> {
                        try {
                            return file.toUri().toURL();
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray(URL[]::new);

            classLoader = new IsolatedClassLoader(urls);
            this.loaders.put(set, classLoader);
            return classLoader;
        }
    }

    private static final class Source {
        private final Dependency dependency;
        private final Path file;

        private Source(Dependency dependency, Path file) {
            this.dependency = dependency;
            this.file = file;
        }
    }
}

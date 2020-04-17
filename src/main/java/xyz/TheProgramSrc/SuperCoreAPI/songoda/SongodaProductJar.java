/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.songoda;

import java.util.List;

public class SongodaProductJar {
    private final int id,downloads;
    private final String status,version,size,uploadedBy,url,download;
    private final long createdAt;
    private final List<String> versions;

    public SongodaProductJar(int id, int downloads, String status, String version, String size, String uploadedBy, String url, String download, long createdAt, List<String> versions) {
        this.id = id;
        this.downloads = downloads;
        this.status = status;
        this.version = version;
        this.size = size;
        this.uploadedBy = uploadedBy;
        this.url = url;
        this.download = download;
        this.createdAt = createdAt;
        this.versions = versions;
    }

    public int getId() {
        return id;
    }

    public int getDownloads() {
        return downloads;
    }

    public String getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }

    public String getSize() {
        return size;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public String getUrl() {
        return url;
    }

    public String getDownload() {
        return download;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public List<String> getVersions() {
        return versions;
    }
}

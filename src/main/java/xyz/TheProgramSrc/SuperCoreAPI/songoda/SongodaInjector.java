/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.songoda;

public class SongodaInjector {

    private final String user, username, productId, version, node, timestamp, nonce, hash, songoda;

    public SongodaInjector() {
        this.user = "%%__USER__%%";
        this.username = "%%__USERNAME__%%";
        this.productId = "%%__RESOURCE__%%";
        this.version = "%%__VERSION__%%";
        this.node = "%%__SONGODA_NODE__%%";
        this.timestamp = "%%__TIMESTAMP__%%";
        this.nonce = "%%__NONCE__%%";
        this.hash = "%%__FILEHASH__%%";
        this.songoda = "%%__SONGODA__%%";
    }

    public String getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getProductID() {
        return productId;
    }

    public String getVersion() {
        return version;
    }

    public String getNode() {
        return node;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public String getHash() {
        return hash;
    }

    public boolean isFromSongoda() {
        return songoda.equals("true");
    }
}

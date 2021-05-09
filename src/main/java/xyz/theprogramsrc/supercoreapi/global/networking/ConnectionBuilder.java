package xyz.theprogramsrc.supercoreapi.global.networking;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionBuilder {

    private final URL url;

    /**
     * Create a new connection builder
     * @param url the url
     * @throws MalformedURLException if any error occurs
     */
    public ConnectionBuilder(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    /**
     * Create the Custom Connection
     * @return the {@link CustomConnection}
     * @throws IOException if any error occurs
     */
    public CustomConnection connect() throws IOException {
        return new CustomConnection(url, ((HttpURLConnection) this.url.openConnection()));
    }

    /**
     * Create the Custom Connection
     * @param url the url
     * @return the {@link CustomConnection}
     * @throws IOException if any error occurs
     */
    public static CustomConnection connect(String url) throws IOException{
        return new ConnectionBuilder(url).connect();
    }
}

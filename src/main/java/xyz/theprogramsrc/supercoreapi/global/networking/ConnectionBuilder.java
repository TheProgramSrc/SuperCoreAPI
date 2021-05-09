package xyz.theprogramsrc.supercoreapi.global.networking;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;

public class ConnectionBuilder {

    private final URL url;
    private final LinkedHashMap<String, String> props;

    /**
     * Create a new connection builder
     * @param url the url
     * @throws MalformedURLException if any error occurs
     */
    public ConnectionBuilder(String url) throws MalformedURLException {
        this.url = new URL(url);
        this.props = new LinkedHashMap<>();
    }

    /**
     * Adds a new property to the connection
     * @param key the name of the property
     * @param value the value of the property
     * @return this class
     */
    public ConnectionBuilder addProperty(String key, String value){
        this.props.put(key, value);
        return this;
    }

    /**
     * Create the Custom Connection
     * @return the {@link CustomConnection}
     * @throws IOException if any error occurs
     */
    public CustomConnection connect() throws IOException {
        return new CustomConnection(url, this.url.openConnection(), this.props);
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

    /**
     * Create the Custom Connection
     * @param url the url
     * @param properties the connection properties
     * @return the {@link CustomConnection}
     * @throws IOException if any error occurs
     */
    public static CustomConnection connect(String url, LinkedHashMap<String, String> properties) throws IOException{
        ConnectionBuilder builder = new ConnectionBuilder(url);
        properties.forEach(builder::addProperty);
        return builder.connect();
    }
}

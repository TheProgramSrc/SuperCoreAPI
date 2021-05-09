package xyz.theprogramsrc.supercoreapi.global.networking;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class CustomConnection {

    private final URL url;
    private final HttpURLConnection connection;
    private final InputStream inputStream;
    private LinkedList<String> cachedResponse;

    /**
     * Init a new custom connection (Used for managing a connection)
     * @param url the url
     * @param connection the {@link URLConnection connection}
     * @throws IOException if any error occurs
     */
    public CustomConnection(URL url, URLConnection connection) throws IOException {
        this(url, connection, new LinkedHashMap<>());
    }

    /**
     * Init a new custom connection (Used for managing a connection)
     * @param url the url
     * @param connection the {@link URLConnection connection}
     * @throws IOException if any error occurs
     */
    public CustomConnection(URL url, URLConnection connection, LinkedHashMap<String, String> properties) throws IOException{
        this.cachedResponse = new LinkedList<>();
        this.url = url;
        this.connection = ((HttpURLConnection) connection);
        if(properties.containsKey("Method")){
            this.connection.setRequestMethod(properties.getOrDefault("Method", "GET"));
            properties.remove("Method");
        }

        if(!properties.containsKey("User-Agent")){
            this.connection.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
        }

        properties.forEach(this.connection::addRequestProperty);
        this.connection.connect();
        this.inputStream = this.connection.getInputStream();
    }

    /**
     * Gets the url
     * @return the url
     */
    public URL getURL() {
        return this.url;
    }

    /**
     * Gets the url as string
     * @return the url as string
     * @throws URISyntaxException if any error occurs
     */
    public String getURLString() throws URISyntaxException {
        return this.getURL().toURI().toString();
    }

    /**
     * Gets the {@link HttpURLConnection connection}
     * @return the connection
     */
    public HttpURLConnection getConnection() {
        return this.connection;
    }

    /**
     * Gets the {@link InputStream} of the connection
     * @return the {@link InputStream}
     */
    public InputStream getInputStream() {
        return this.inputStream;
    }

    /**
     * Gets the content of a header
     * @param name the name of the header
     * @return the value of the specified header
     */
    public String getHeader(String name){
        return this.getConnection().getHeaderField(name);
    }

    /**
     * Gets the response code
     * @return the response code
     * @throws IOException if any error occurs
     */
    public String getResponseCode() throws IOException {
        return this.getConnection().getResponseCode()+"";
    }

    /**
     * Gets the response message
     * @return the response message
     * @throws IOException if any error occurs
     *
     * @see HttpURLConnection#getResponseMessage()
     */
    public String getResponseMessage() throws IOException {
        return this.getConnection().getResponseMessage();
    }

    /**
     * Gets the response from the server
     * @return the response
     */
    public LinkedList<String> getResponse() {
        if(this.cachedResponse.isEmpty()){
            this.cachedResponse = new BufferedReader(new InputStreamReader(this.getInputStream())).lines().collect(Collectors.toCollection(LinkedList::new));
        }
        return this.cachedResponse;
    }

    /**
     * Gets the response from the server as a string
     * @return the response from the server as a string
     */
    public String getResponseString() {
        return String.join("", this.getResponse());
    }

    /**
     * Gets the response from the server as a json object
     * @return the response from the server as a json object
     * @throws IOException if any error occurs
     */
    public JsonObject getResponseJson() throws IOException {
        String data = this.getResponseString();
        if(data == null) return null;
        JsonElement element = new JsonParser().parse(data);
        if(element.isJsonNull()) return null;
        return element.getAsJsonObject();
    }

    /**
     * Checks if the connection is not a null response
     * @return true if is not null, false otherwise
     * @throws IOException if any error occurs
     */
    public boolean isResponseNotNull() throws IOException {
        return this.getResponseString() != null;
    }

    /**
     * Checks if the connection has a valid response
     * @return true if the response code is 2xx, false otherwise
     * @throws IOException if any error occurs
     */
    public boolean isValidResponse() throws IOException {
        return (this.getResponseCode()+"").startsWith("2");
    }
}

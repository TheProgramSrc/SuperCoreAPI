package xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.networking.ConnectionBuilder;
import xyz.theprogramsrc.supercoreapi.global.networking.CustomConnection;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

import java.io.IOException;
import java.util.UUID;

@SuppressWarnings("ALL")
public class SkinTexture {
    private String url;

    /**
     * Creates a new SkinTexture
     * @param url the url of the texture
     */
    public SkinTexture(String url) {
        this.url = url;
    }

    /**
     * Gets the url of the texture
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Serialize the SkinTexture
     * (URL:split:TIME_CREATED)
     * @return the serialized SkinTexture
     */
    public String toString() {
        return this.url;
    }

    /**
     * Gets a SkinTexture from a Player
     * @param player the player
     * @return the skin
     */
    public static SkinTexture fromPlayer(Player player) {
        GameProfile gameProfile = PlayerGameProfile.get(player);
        Property texturesProperty = (Property)gameProfile.getProperties().get("textures").iterator().next();
        String texturesPropertyValue = texturesProperty.getValue();
        String base64ToUrl = base64ToUrl(texturesPropertyValue);
        return base64ToUrl != null && !base64ToUrl.equals("null") ? new SkinTexture(base64ToUrl) : null;
    }

    /**
     * Gets a SkinTexture from a Player Name
     * @param playerName the name
     * @return the skin
     */
    public static SkinTexture fromMojang(String playerName) {
        if(isMojangDown()) return null;
        try {
            if(SpigotPlugin.i.getSuperUtils().isPlugin("Geyser-Spigot")){ // Remove the '*' to avoid errors
                if(playerName.startsWith("*")){
                    playerName = playerName.substring(1);
                }
            }
            CustomConnection connection = new ConnectionBuilder("https://api.mojang.com/users/profiles/minecraft/" + playerName).connect();
            if(connection.getResponseCode() == 429) return null;
            JsonObject response = connection.getResponseJson();
            if(response == null) return null;
            String uuid = response.get("id").getAsString();
            String fullUUID = Utils.uuidToFullUUID(uuid);
            return fromMojang(UUID.fromString(fullUUID));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a SkinTexture from a Player UUID
     * @param uuid the uuid
     * @return the skin
     */
    public static SkinTexture fromMojang(UUID uuid) {
        if(isMojangDown()) return null;
        try {
            CustomConnection connection = new ConnectionBuilder("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false").connect();
            if(connection.getResponseCode() == 429) return null;
            JsonObject response = connection.getResponseJson();
            if(response == null) return null;
            JsonObject properties = response.get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String value = properties.get("value").getAsString();
            return new SkinTexture(base64ToUrl(value));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a SkinTexture from URL
     * @param url the url
     * @return the skin
     */
    public static SkinTexture fromURL(String url){
        return new SkinTexture(url);
    }

    private static String base64ToUrl(String base64) {
        String content = new String(Base64.decodeBase64(base64.getBytes()));
        JsonObject json = new JsonParser().parse(content).getAsJsonObject().get("textures").getAsJsonObject().get("SKIN").getAsJsonObject();
        return json != null ? json.get("url").getAsString() : null;
    }

    private static long lastCheck;
    private static boolean working;

    private static boolean isMojangDown(){
        long current = System.currentTimeMillis();
        if(lastCheck == 0L){
            lastCheck = current;
        }else{
            if(current - lastCheck >= 25000L){
                lastCheck = current;
                try{
                    CustomConnection connection = new ConnectionBuilder("http://status.mojang.com/check").connect();
                    String content = connection.getResponseString();
                    if(content != null){
                        JsonArray array = new JsonParser().parse(content).getAsJsonArray();
                        for(JsonElement el : array){
                            JsonObject json = el.getAsJsonObject();
                            if(json.has("sessionserver.mojang.com")){
                                working = json.get("sessionserver.mojang.com").getAsString().equals("green");
                            }
                        }
                    }
                }catch (Exception e){
                    working = false;
                }
            }
        }

        return working;
    }
}

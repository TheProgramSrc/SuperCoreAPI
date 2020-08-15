package xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
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
        String response = getResponse("https://api.mojang.com/users/profiles/minecraft/" + playerName);
        if(response == null)
            return null;
        String uuid = (new JsonParser()).parse(getResponse(response)).getAsJsonObject().get("id").getAsString();
        String fullUUID = Utils.uuidToFullUUID(uuid);
        return fromMojang(UUID.fromString(fullUUID));
    }

    /**
     * Gets a SkinTexture from a Player UUID
     * @param uuid the uuid
     * @return the skin
     */
    public static SkinTexture fromMojang(UUID uuid) {
        String response = getResponse("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false");
        if(response == null)
            return null;
        JsonObject properties = (new JsonParser()).parse(response).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
        String value = properties.get("value").getAsString();
        return new SkinTexture(base64ToUrl(value));
    }

    /**
     * Gets a SkinTexture from URL
     * @param url the url
     * @return the skin
     */
    public static SkinTexture fromURL(String url){
        return new SkinTexture(url);
    }

    private static String getResponse(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            return IOUtils.toString(url.openStream(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String base64ToUrl(String base64) {
        String content = new String(Base64.decodeBase64(base64.getBytes()));
        JsonObject json = new JsonParser().parse(content).getAsJsonObject().get("textures").getAsJsonObject().get("SKIN").getAsJsonObject();
        return json != null ? json.get("url").getAsString() : null;
    }
}

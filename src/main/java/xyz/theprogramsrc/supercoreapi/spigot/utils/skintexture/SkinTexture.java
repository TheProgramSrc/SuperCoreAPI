package xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.UUID;

@SuppressWarnings("ALL")
public class SkinTexture {
    private String url;
    private long createdAt;

    /**
     * Gets a SkinTexture from a Player
     * @param player the player
     * @return the skin
     */
    public static SkinTexture fromPlayer(Player player) {
        GameProfile gameProfile = PlayerGameProfile.get(player);

        try {
            Property texturesProperty = (Property)gameProfile.getProperties().get("textures").iterator().next();
            String texturesPropertyValue = texturesProperty.getValue();
            String base64ToUrl = base64ToUrl(texturesPropertyValue);
            return base64ToUrl != null && !base64ToUrl.equals("null") ? new SkinTexture(base64ToUrl, System.currentTimeMillis()) : null;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets a SkinTexture from a Player Name
     * @param playerName the name
     * @return the skin
     */
    public static SkinTexture fromMojang(String playerName) {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
        String response = getResponse(url);

        try {
            String uuid = (new JsonParser()).parse(response).getAsJsonObject().get("id").getAsString();
            String fullUUID = Utils.uuidToFullUUID(uuid);
            return fromMojang(UUID.fromString(fullUUID));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets a SkinTexture from a Player UUID
     * @param uuid the uuid
     * @return the skin
     */
    public static SkinTexture fromMojang(UUID uuid) {
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false";
        String response = getResponse(url);
        try {
            JsonObject properties = (new JsonParser()).parse(response).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String value = properties.get("value").getAsString();
            return new SkinTexture(base64ToUrl(value), System.currentTimeMillis());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets a SkinTexture from URL
     * @param url the url
     * @return the skin
     */
    public static SkinTexture fromURL(String url){
        return new SkinTexture(url, System.currentTimeMillis());
    }

    private static String getResponse(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            return IOUtils.toString(url.openStream(), Charset.forName("UTF-8"));
        } catch (Exception ex) {
            return null;
        }
    }

    private static String base64ToUrl(String base64) {
        try {
            String var1 = new String(Base64.decodeBase64(base64.getBytes()));
            JsonObject var2 = (JsonObject)(new JsonParser()).parse(var1);
            JsonObject var3 = var2.getAsJsonObject("textures").getAsJsonObject("SKIN");
            if (var3 == null) {
                return null;
            } else {
                String var4 = var3.get("url").getAsString();
                return var4;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Creates a new SkinTexture
     * @param url the url of the texture
     * @param createdAt Time created {@link System#currentTimeMillis()}
     */
    public SkinTexture(String url, long createdAt) {
        this.url = url;
        this.createdAt = createdAt;
    }

    /**
     * Parse a SkinTexture from serialized format
     * (URL:split:TIME_CREATED)
     * @param serialized
     */
    public SkinTexture(String serialized) {
        this.url = serialized.split(":split:")[0];
        this.createdAt = Long.valueOf(serialized.split(":split:")[1]);
    }

    /**
     * Gets the url of the texture
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Gets when was created the SkinTexture
     * @return creation time in millis
     */
    public long getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Serialize the SkinTexture
     * (URL:split:TIME_CREATED)
     * @return the serialized SkinTexture
     */
    public String toString() {
        return this.url + ":split:" + this.createdAt;
    }
}

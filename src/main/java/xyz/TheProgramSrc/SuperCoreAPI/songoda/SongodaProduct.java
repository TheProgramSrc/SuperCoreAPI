/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.songoda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SongodaProduct {

    private String userId, teamId, name, slug, description, tagline, iconURL, bannerURL, currency, productBody, rating, status, productClass, url;
    private boolean approved;
    private double price;
    private int id, downloads, views, reviews;
    private long createdAt, updatedAt;
    private List<SongodaProductJar> productJars;

    public SongodaProduct(SongodaAPI songodaAPI, String productId){
        try{
            String url = songodaAPI.getProductsApiURL().replace("{ProductID}", productId);
            String jsonString = Utils.readWithInputStream(url);
            if(jsonString == null){
                throw new NullPointerException("Cannot fetch data from '" + url + "'");
            }else{
                JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject().get("data").getAsJsonObject();
                this.id = json.get("id").getAsInt();
                if(!json.get("user_id").isJsonNull()){
                    this.userId = json.get("user_id").getAsString();
                }
                if(!json.get("team_id").isJsonNull()){
                    this.teamId = json.get("team_id").getAsString();
                }
                this.name = json.get("name").getAsString();
                this.slug = json.get("slug").getAsString();
                this.description = json.get("description").getAsString();
                this.tagline = json.get("tagline").getAsString();
                this.iconURL = json.get("icon").getAsString();
                this.bannerURL = json.get("banner").getAsString();
                this.currency = json.get("currency").getAsString();
                this.productBody = json.get("body").getAsString();
                this.rating = json.get("rating").getAsString();
                this.status = json.get("status").getAsString();
                this.productClass = json.get("class").getAsString();
                this.url = json.get("url").getAsString();
                this.approved = json.get("approved").getAsBoolean();
                this.price = json.get("price").getAsDouble();
                this.downloads = json.get("downloads").getAsInt();
                this.views = json.get("views").getAsInt();
                this.reviews = json.get("reviews").getAsInt();
                this.createdAt = json.get("created_at").getAsLong();
                this.updatedAt = json.get("updated_at").getAsLong();
                this.productJars = new ArrayList<>();
                for(JsonElement element : json.get("versions").getAsJsonArray()){
                    JsonObject j = element.getAsJsonObject();
                    int id = j.get("id").getAsInt(), downloads = j.get("downloads").getAsInt();
                    String status = j.get("status").getAsString(), version = j.get("version").getAsString(), size = j.get("size").getAsString(), uploadedBy = j.get("uploaded_by").getAsString(), versionUrl = j.get("url").getAsString(), versionDownloadUrl = j.get("download").getAsString();
                    List<String> versions = new ArrayList<>();
                    j.get("minecraft_version").getAsJsonArray().forEach(e-> versions.add(e.getAsString()));
                    long createdAt = j.get("created_at").getAsLong();
                    this.productJars.add(new SongodaProductJar(id, downloads, status, version, size, uploadedBy, versionUrl, versionDownloadUrl, createdAt, versions));
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getDescription() {
        return description;
    }

    public String getTagline() {
        return tagline;
    }

    public String getIconURL() {
        return iconURL;
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public String getCurrency() {
        return currency;
    }

    public String getProductBody() {
        return productBody;
    }

    public String getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public String getProductClass() {
        return productClass;
    }

    public String getUrl() {
        return url;
    }

    public boolean isApproved() {
        return approved;
    }

    public double getPrice() {
        return price;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getViews() {
        return views;
    }

    public int getReviews() {
        return reviews;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public List<SongodaProductJar> getProductJars() {
        return productJars;
    }
}

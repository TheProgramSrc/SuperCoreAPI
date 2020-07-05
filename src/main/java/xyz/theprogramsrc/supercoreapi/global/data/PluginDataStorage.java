package xyz.theprogramsrc.supercoreapi.global.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.files.JsonConfig;
import xyz.theprogramsrc.supercoreapi.global.notifications.Notification;
import xyz.theprogramsrc.supercoreapi.global.notifications.NotificationCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PluginDataStorage extends JsonConfig {

    public PluginDataStorage(SuperPlugin<?> plugin){
        super(plugin.getPluginFolder(), "PluginData.json");
        if(!this.contains("stats_id")){
            this.add("stats_id", UUID.randomUUID().toString());
        }
    }

    public void saveNotification(Notification notification){
        JsonArray data = this.getOrCreateArray("notifications");
        data.add(notification.toString());
        this.set("notifications", data);
    }

    public void saveNotifications(Notification... notifications){
        Arrays.stream(notifications).forEach(this::saveNotification);
    }

    public NotificationCollection getNotifications(){
        JsonArray data = this.getOrCreateArray("notifications");
        List<Notification> list = new ArrayList<>();
        data.forEach(j->{
            JsonObject json = j.getAsJsonObject();
            Notification not = Notification.builder()
                    .setTitle(json.get("title").getAsString())
                    .setContent(json.get("content").getAsString())
                    .setCreatedAt(json.get("created_at").getAsLong())
                    .build();
            list.add(not);
        });
        return new NotificationCollection(list);
    }

    public Notification[] getNotificationsArray(){
        return this.getNotifications().get();
    }
}

package xyz.theprogramsrc.supercoreapi.global.notifications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class NotificationCollection {

    private final List<Notification> notifications;

    public NotificationCollection(List<Notification> notifications){
        this.notifications = notifications;
    }

    public void forEach(Consumer<Notification> notificationConsumer){
        this.notifications.forEach(notificationConsumer);
    }

    public Notification getLatest(){
        HashMap<Long, Notification> nots = new HashMap<>();
        List<Long> times = new ArrayList<>();
        notifications.forEach(n->{
            long createdAt = n.getCreatedAt();
            nots.put(createdAt, n);
            times.add(createdAt);
        });
        if(nots.size() == 0 || times.size() == 0){
            return null;
        }

        long newest = Collections.min(times);
        return nots.get(newest);
    }

    public Notification get(int index){
        return this.notifications.get(index);
    }

    public Notification[] get(){
        Notification[] notifications = new Notification[this.notifications.size()];
        notifications = this.notifications.toArray(notifications);
        return notifications;
    }
}

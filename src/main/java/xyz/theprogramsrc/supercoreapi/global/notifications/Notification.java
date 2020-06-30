package xyz.theprogramsrc.supercoreapi.global.notifications;

import com.google.gson.JsonObject;

public class Notification {

    private final String title, content;
    private final long createdAt;

    public Notification(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = System.currentTimeMillis();
    }

    public Notification(String title, String content, long createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        JsonObject json = new JsonObject();
        json.addProperty("title", this.getTitle());
        json.addProperty("content", this.getContent());
        return json.toString();
    }

    public static NotificationBuilder builder(){
        return new NotificationBuilder();
    }

    public static class NotificationBuilder{
        private String title, content;
        private long createdAt;

        public NotificationBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public NotificationBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public NotificationBuilder setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Notification build(){
            return new Notification(this.title, this.content, this.createdAt);
        }
    }
}

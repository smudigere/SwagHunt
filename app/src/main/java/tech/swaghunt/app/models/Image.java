package tech.swaghunt.app.models;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Image {
    public String name;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Image() {
    }

    public Image(String name, String url) {
        this.name = name;
        this.url= url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

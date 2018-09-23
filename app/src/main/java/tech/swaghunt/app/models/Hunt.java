package tech.swaghunt.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Hunt {
    String name;
    List<Task> tasks;
    String qrCode;

    public Hunt() {

    }

    public Hunt(String name, List<Task> tasks, String qrCode) {
        this.name = name;
        this.tasks = tasks;
        this.qrCode = qrCode;
    }
}
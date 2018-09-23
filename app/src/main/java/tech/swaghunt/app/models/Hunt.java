package tech.swaghunt.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Hunt {
    String name;
    List<HuntTask> huntTasks;
    String qrCode;

    public Hunt() {

    }

    public Hunt(String name, List<HuntTask> huntTasks, String qrCode) {
        this.name = name;
        this.huntTasks = huntTasks;
        this.qrCode = qrCode;
    }
}

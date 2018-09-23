package tech.swaghunt.app.models;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Player {
    String name;
    String huntPlaying;
    Boolean hasWon;
    List<String> Images;
    List<String> text;
    List<String> qrCodes;
    List<String> tasksIDsCompleted;

    public Player(){

    }

    public Player(String name, String huntPlaying, Boolean hasWon, List<String> images, List<String> text, List<String> qrCodes, List<String> tasksIDsCompleted) {
        this.name = name;
        this.huntPlaying = huntPlaying;
        this.hasWon = hasWon;
        Images = images;
        this.text = text;
        this.qrCodes = qrCodes;
        this.tasksIDsCompleted = tasksIDsCompleted;
    }
}

package tech.swaghunt.app.models;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Player {
    private String name;
    private String huntPlaying;
    private Boolean hasWon;
    private List<String> Images;
    private List<String> text;
    private List<String> qrCodes;
    private List<Task> tasksCompleted;

    public Player(){

    }

    public Player(String name, String huntPlaying, Boolean hasWon, List<String> images, List<String> text, List<String> qrCodes, List<Task> tasksCompleted) {
        this.name = name;
        this.huntPlaying = huntPlaying;
        this.hasWon = hasWon;
        Images = images;
        this.text = text;
        this.qrCodes = qrCodes;
        this.tasksCompleted = tasksCompleted;
    }
}

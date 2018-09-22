package tech.swaghunt.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Task {
    private String clue;
    private String clueAnswer;
    private String image;
    private String text;
    private String qrCodes;
    private String taskType;
    private String huntId;

    public Task() {

    }

    public Task(String clue, String clueAnswer, String image, String text, String qrCodes, String taskType, String huntId) {
        this.clue = clue;
        this.clueAnswer = clueAnswer;
        this.image = image;
        this.text = text;
        this.qrCodes = qrCodes;
        this.taskType = taskType;
        this.huntId = huntId;
    }
}

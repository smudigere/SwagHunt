package tech.swaghunt.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class HuntTask {
    String clue;
    String clueAnswer;
    Image image;
    String text;
    String qrCodes;
    String taskType;
    String huntId;

    public HuntTask() {

    }

    public HuntTask(String clue, String clueAnswer, Image image, String text, String qrCodes, String taskType, String huntId) {
        this.clue = clue;
        this.clueAnswer = clueAnswer;
        this.image = image;
        this.text = text;
        this.qrCodes = qrCodes;
        this.taskType = taskType;
        this.huntId = huntId;
    }
}

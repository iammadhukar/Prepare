package com.preprepare.prepare.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questionTable")
public class QuestionSet {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "optionA")
    private String optionA;

    @ColumnInfo(name = "optionB")
    private String optionB;

    @ColumnInfo(name = "optionC")
    private String optionC;

    @ColumnInfo(name = "optionD")
    private String optionD;

    @ColumnInfo(name = "answer")
    private String answer;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

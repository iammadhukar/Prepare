package com.preprepare.prepare.Model;

public class MyModel {
    private int uid;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String optionSelected;

    public String getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(String optionSelected) {
        this.optionSelected = optionSelected;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public MyModel(int uid, String question, String optionA, String optionB, String optionC, String optionD, String answer, String optionSelected){
        this.uid = uid;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.optionSelected = optionSelected;
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

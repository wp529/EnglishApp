package com.pzhu.english.domain;

public class Vocabulary {
    private String english;
    private String chinese;
    private String example;

    public Vocabulary(String english, String chinese, String example) {
        this.english = english;
        this.chinese = chinese;
        this.example = example;
    }

    public String getExample() {
        return example;
    }

    public String getEnglish() {
        return english;
    }

    public String getChinses() {
        return chinese;
    }
}

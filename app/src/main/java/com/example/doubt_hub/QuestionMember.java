package com.example.doubt_hub;

public class QuestionMember {

    String question, name, date, userid, url, key, time;
    public QuestionMember(){
    }

    public QuestionMember(String question, String userid, String name, String date) {
        this.name = name;
        this.userid = userid;
//        this.url = url;
//        this.key = key;
        this.date = date;
        this.question = question;
//        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
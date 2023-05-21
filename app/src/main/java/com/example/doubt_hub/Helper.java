package com.example.doubt_hub;

import android.widget.ImageView;

public class Helper {
    String name , username, email, password;
//    ImageView imageUrl;

    public Helper(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
//        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public ImageView getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(ImageView imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    public Helper() {
    }
}

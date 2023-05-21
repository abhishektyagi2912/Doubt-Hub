package com.example.doubt_hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(),StartActivity.class);
            startActivity(intent);
            finish();
        },2000);
    }
}
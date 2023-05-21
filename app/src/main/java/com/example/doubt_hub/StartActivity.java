package com.example.doubt_hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            startActivity(new Intent(StartActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void OpenActivity5(View view){
        startActivity((new Intent(StartActivity.this,SignUpActivity.class)));
        finish();
    }
    public void OpenActivity4(View view){
        startActivity((new Intent(StartActivity.this,LoginActivity.class)));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
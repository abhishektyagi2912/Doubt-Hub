package com.example.doubt_hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.doubt_hub.databinding.ActivityAskBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AskActivity extends AppCompatActivity {
    ActivityAskBinding binding;
    FirebaseDatabase database =FirebaseDatabase.getInstance();

    DatabaseReference AllQuestions, UserQuestions;
    FirebaseUser user;

    String userId,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            userId = user.getUid();
        }

        AllQuestions = database.getReference("All Questions");
        UserQuestions =  database.getReference("User Question").child(userId);


        /// condition of anonymous system of this thing

        name = "Anonymous";

        ///In between bro
        binding.backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AskActivity.this,MainActivity.class));
                finish();
            }
        });
//        binding.ivAsk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AskActivity.this,MainActivity.class));
//                finish();
//            }
//        });
        binding.askButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = binding.writeQuestion.getText().toString();
                Calendar calendar =Calendar.getInstance();

                if(TextUtils.isEmpty(question)) {
                    Toast.makeText(AskActivity.this, "Please enter some question", Toast.LENGTH_SHORT).show();
                }else{
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    final String saveDate = currentDate.format(calendar.getTime());
                    QuestionMember questionMember = new QuestionMember(question,userId, name, saveDate);
                    AllQuestions.push().setValue(questionMember);
                    UserQuestions.push().setValue(questionMember);
                    Toast.makeText(AskActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                    binding.writeQuestion.getText().clear();
                }
            }
        });

        //if you want to write a file in the protected Class
    }
    // this is end of protected class
    @Override
    public void onBackPressed() {
        startActivity(new Intent(AskActivity.this,MainActivity.class));
        finish();
    }
}
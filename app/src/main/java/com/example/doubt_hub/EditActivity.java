package com.example.doubt_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doubt_hub.databinding.ActivityEditBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    EditText editName,editUsername,editPassword;
//    EditText editEmail;

    ProgressDialog pd;
    Button saveButton;
    TextView back_profile;

    FirebaseUser user;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

//        editEmail = findViewById(R.id.editEmail);
        editName = findViewById(R.id.editName);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);
        back_profile = findViewById(R.id.back_profile);


    //firebase manipulation start here
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            userId = user.getUid();
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(EditActivity.this);
                pd.setMessage("Please wait");
                pd.show();

                String str_username = editUsername.getText().toString();
                String str_full_name = editName.getText().toString();
        //       String str_email = email.getText().toString();
                String str_password = editPassword.getText().toString();

                if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_full_name) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(EditActivity.this, "All field is required! If you don't want to change then use previous data", Toast.LENGTH_SHORT).show();
                    editUsername.setError("Can't be empty");
                    editName.setError("Can't be empty");
                    editPassword.setError("Can't be empty");

                    pd.dismiss();
                } else if (str_password.length() < 6) {
                    editPassword.setError("Password must contain 6 letter");
                    Toast.makeText(EditActivity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    String userId = "your_user_id";
                    DatabaseReference userRef = database.getReference("users").child(userId);

                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("name", str_full_name); // Update the name field
                    updatedData.put("username", str_username.toLowerCase()); // Update the email field
                    updatedData.put("password", str_password);

                    userRef.updateChildren(updatedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    Toast.makeText(EditActivity.this, "Changed Successfully", Toast.LENGTH_SHORT).show();
                                    editName.getText().clear();
                                    editPassword.getText().clear();
                                    editUsername.getText().clear();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(EditActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }
        });

        back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
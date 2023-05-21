package com.example.doubt_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText username;
    EditText email;
    EditText password, name ;
    Button signButton;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username =findViewById(R.id.username);
        email =findViewById(R.id.email);
        password =findViewById(R.id.password);
        name =findViewById(R.id.name);
        signButton =findViewById(R.id.signButton);

        auth = FirebaseAuth.getInstance();

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(SignUpActivity.this);
                pd.setMessage("Please wait....");
                pd.show();

                String str_username = username.getText().toString();
                String str_full_name = name.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_full_name) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(SignUpActivity.this, "All field is required!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else if (str_password.length() < 6) {
                    password.setError("Password must contain 6 letter");
                    Toast.makeText(SignUpActivity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else{
                    register(str_username,str_full_name,str_email,str_password);
                }
            }
        });
    }
    private  void register(String username, String name, String email, String password ){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
                            HashMap <String,Object> hashMap = new HashMap<>();
                            hashMap.put("id" , userid);
                            hashMap.put("username" , username.toLowerCase());
                            hashMap.put("name" , name);
                            hashMap.put("email" , email);
                            hashMap.put("password" , password);
//                            hashMap.put("imageUrl" , "https://firebasestorage.googleapis.com/v0/b/doubt-hub-fcb62.appspot.com/o/placeholder.png?alt=media&token=7444bc68-d648-431f-bd20-3e4c8de91015");
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(SignUpActivity.this, "You Can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void OpenActivity2(View view){
        Intent intent =  new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUpActivity.this,StartActivity.class));
        finish();
    }
}
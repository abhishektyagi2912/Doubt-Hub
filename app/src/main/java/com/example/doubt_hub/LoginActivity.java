package com.example.doubt_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText username , password ,email;
    Button loginButton;
    TextView forgotPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgetPassword);
//        email = findViewById(R.id.email);

        loginButton = findViewById(R.id.loginButton);

        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Please wait....");
                pd.show();

                String str_username = username.getText().toString();
                String str_password = password.getText().toString();
//                String str_email = email.getText().toString();

                if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(LoginActivity.this, "All field is required!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }else {
                    auth.signInWithEmailAndPassword(str_username,str_password)
                            .addOnCompleteListener(LoginActivity.this, task -> {
                                if(task.isSuccessful()){
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            pd.dismiss();
                                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            pd.dismiss();
                                        }
                                    });
                                }else {
                                    pd.dismiss();
                                    Toast.makeText(LoginActivity.this, "Authentication Failed, Please enter correct email or password", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        forgotPassword.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot,null);
            EditText emailbox = dialogView.findViewById(R.id.emailBox);

            builder.setView(dialogView);

            AlertDialog dialog = builder.create();

            dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userEmail = emailbox.getText().toString();

                    if(TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                        emailbox.setError("Enter Your Register Email");
                        return;
                    }
                    auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Unable to send message", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if(dialog.getWindow() != null){
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            dialog.show();
        });
    }
    public void OpenAcitivity(View view) {
        Intent intent =  new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this,StartActivity.class));
        finish();
    }
}
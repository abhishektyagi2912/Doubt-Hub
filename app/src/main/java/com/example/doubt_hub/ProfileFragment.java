package com.example.doubt_hub;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userId;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         TextView profileName, profileEmail, profileUsername;
         TextView titleName, titleUsername;
         Button editProfile;
         ImageView profile_pg;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

         profileName = view.findViewById(R.id.profileName);
         profileEmail = view.findViewById(R.id.profileEmail);
         profileUsername = view.findViewById(R.id.profileUsername);
         titleName = view.findViewById(R.id.titleName);
         titleUsername = view.findViewById(R.id.titleUsername);
         editProfile = view.findViewById(R.id.editButton);
         profile_pg = view.findViewById(R.id.profile_pg);

         //user take
         user = FirebaseAuth.getInstance().getCurrentUser();
         if (user!=null){
             userId = user.getUid();
         }

         databaseReference = FirebaseDatabase.getInstance().getReference("users");
         if (!userId.isEmpty()){
             databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     Helper helper = snapshot.getValue(Helper.class);
                     String name = helper.getName();
                     String email = helper.getEmail();
                     String username = helper.getUsername();
                     profileName.setText(name);
                     profileEmail.setText(email);
                     profileUsername.setText(username);
                     titleName.setText(name);
                     titleUsername.setText(username);
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });
         }
         profile_pg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                 View dialogView = getLayoutInflater().inflate(R.layout.dialog_exit,null);
                 builder.setView(dialogView);

                 AlertDialog dialog = builder.create();

                 dialogView.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         FirebaseAuth.getInstance().signOut();
                         Intent intent = new Intent(getActivity(),StartActivity.class);
                         startActivity(intent);
                     }
                 });
                 dialogView.findViewById(R.id.logCancel).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         dialog.dismiss();
                     }
                 });
                 if(dialog.getWindow() != null){
                     dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                 }
                 dialog.show();
             }
         });

         editProfile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
//                 passUserData();
                 startActivity(new Intent(getActivity(),EditActivity.class));
             }
         });

         return view;
    }

}
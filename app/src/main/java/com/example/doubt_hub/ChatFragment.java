package com.example.doubt_hub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.Adapter;


public class ChatFragment extends Fragment {
    FirebaseUser user;
    String userId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<QuestionMember> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        /////////////
        reference = database.getReference().child("User Question").child(userId);
        //////////////important

        recyclerView= view.findViewById(R.id.rv_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new Adapter(list,getActivity());
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    QuestionMember questionMember = dataSnapshot.getValue(QuestionMember.class);
                    list.add(0,questionMember); // use for show the latest question first
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }
}
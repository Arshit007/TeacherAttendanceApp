package com.example.arshit.teacherattendanceapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arshit.teacherattendanceapp.Model.Student;
import com.example.arshit.teacherattendanceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    View view;

    private RecyclerView userrv;
    private ArrayList<Student> userdata;
    private StudentChatAdapter studentChatAdapter;
    private DatabaseReference database;
    private FirebaseUser firebaseUser;

    private RecyclerView all_user_rv;

    private  String cuurentUserId;
    private FirebaseAuth mAuth;
    String imageURL;

    public ChatFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);

        intialise(view);
        return view;

    }

    private void intialise(View view) {
        userrv = view.findViewById(R.id.userecyclerview);
        userrv.setHasFixedSize(true);
        userrv.setLayoutManager(new LinearLayoutManager(getContext()));

        userdata = new ArrayList<>();

        display();

        mAuth = FirebaseAuth.getInstance();
        cuurentUserId = mAuth.getCurrentUser().getUid();



    }

    private void display(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("StudentInfo");

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userdata.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Student user = postSnapshot.getValue(Student.class);
                    userdata.add(user);

                }

                studentChatAdapter = new StudentChatAdapter(getContext(),userdata,imageURL);
                userrv.setAdapter(studentChatAdapter);

            }
   @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}

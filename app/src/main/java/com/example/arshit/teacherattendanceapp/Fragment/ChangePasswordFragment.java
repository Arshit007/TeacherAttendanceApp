package com.example.arshit.teacherattendanceapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.arshit.teacherattendanceapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChangePasswordFragment extends Fragment {

    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;

    private DatabaseReference Rootref;
    String selected_user_id;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    private String Subjectvalue, ValueName, UserName, UserSubject;

    private ProgressDialog mProgressDialog;
    private StorageReference imgStorageReference;
    private FloatingActionButton add_teacher_float;

    View view;

    public ChangePasswordFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_change_password, container, false);

        return view;

    }

}
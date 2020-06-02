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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arshit.teacherattendanceapp.R;
import com.example.arshit.teacherattendanceapp.ShowStudentByCourse;
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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AllStudentFragment extends Fragment {

    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;

    private DatabaseReference Rootref;
    String selected_user_id;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    private String Subjectvalue,ValueName,UserName,UserSubject;
    Spinner spin;

    private ProgressDialog mProgressDialog;
    private StorageReference imgStorageReference;
    private FloatingActionButton add_teacher_float;
    private DatabaseReference database;
    String selectedItem;
    Button btnAddTeacher;

    View view;
    public AllStudentFragment() {
     }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_all_student, container, false);

//    intialise();
     return view;

    }
//
//    private void intialise() {
//       mAuth= FirebaseAuth.getInstance();
//        spin = (Spinner) view.findViewById(R.id.spinner);
//
//   btnAddTeacher = view.findViewById(R.id.btn_add_teacher);
//
//        database = FirebaseDatabase.getInstance().getReference().child("Courses");
//
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                final List<String> areas = new ArrayList<String>();
//
//                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                    String areaName = areaSnapshot.child("CourseName").getValue(String.class);
//                    areas.add(areaName);
//                }
//
//
//                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, areas);
//                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spin.setAdapter(areasAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//
//
//        });
//        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                selectedItem = parent.getItemAtPosition(position).toString();
//            }
//
//            public void onNothingSelected(AdapterView<?> parent)
//            {
//
//            }
//        });
//
//        btnAddTeacher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getContext(),ShowStudentByCourse.class);
//                intent.putExtra("CourseSelected",selectedItem);
//                intent.putExtra("id",selectedItem);
//
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//
//
//            }
//        });
//
//    }


}

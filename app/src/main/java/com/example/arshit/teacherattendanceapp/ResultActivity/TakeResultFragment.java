package com.example.arshit.teacherattendanceapp.ResultActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.arshit.teacherattendanceapp.R;
import com.example.arshit.teacherattendanceapp.ResultActivity.ResultActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TakeResultFragment extends Fragment {


Button btnResult;

    private DatabaseReference Rootref;
    FirebaseUser currentFirebaseUser;
    Spinner specSpinner,semSpinner,courseSpinner;
    String specItem,semItem,courseItem;
    View view;
    String currentUserId;
    List<String>  semList,courseList,specList;
    String  CourseCode;



    public TakeResultFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container, false);



//        intialise();
intialise();
        return view;

    }

    private void intialise() {

        specSpinner = view.findViewById(R.id.spinner_spec);
        semSpinner = view.findViewById(R.id.spinner_sem);
        courseSpinner =view.findViewById(R.id.spinner_course);


        specList = new ArrayList<>();
        semList = new ArrayList<>();
        courseList = new ArrayList<>();

        btnResult = view.findViewById(R.id.result_student);
Userdetail();
    }



    private void Userdetail() {


        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        currentUserId = currentFirebaseUser.getUid();


        Rootref = FirebaseDatabase.getInstance().getReference().child("Teachers").child(currentUserId);


        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {



                showCourse(dataSnapshot1.child("DeptName").getValue().toString(),dataSnapshot1.child("DeptField").getValue().toString(),dataSnapshot1.child("DeptSpec").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showCourse(final String deptName, final String deptField, String deptSpec) {

        final DatabaseReference   database1 = FirebaseDatabase.getInstance().getReference("TeacherClass").child(deptName).child(deptField).child(currentUserId).child("Course");


        database1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){


                    specList.add(dataSnapshot1.getKey());

                }


                ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, specList);
                DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                specSpinner.setAdapter(DayAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        specSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                specItem =   adapterView.getItemAtPosition(i).toString();

                DatabaseReference   database2 = FirebaseDatabase.getInstance().getReference("TeacherClass").child(deptName).child(deptField).child(currentUserId).child("Course").child(specItem);

                database2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

semList.clear();
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){


                            semList.add(dataSnapshot1.getKey());

                        }

                        ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, semList);
                        DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        semSpinner.setAdapter(DayAdapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                semSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        semItem =   adapterView.getItemAtPosition(i).toString();


                        DatabaseReference   database2 = FirebaseDatabase.getInstance().getReference("TeacherClass").child(deptName).child(deptField).child(currentUserId).child("Course").child(specItem).child(semItem);

                        database2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
courseList.clear();
                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){


                                      CourseCode = dataSnapshot1.child("CourseCode").getValue().toString();
                                    courseList.add(dataSnapshot1.getKey());

                                }

                                ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, courseList);
                                DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                courseSpinner.setAdapter(DayAdapter);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                courseItem =   adapterView.getItemAtPosition(i).toString();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(getContext(), "Spec \n"+specItem+"Sem \n"+semItem+"Course \n"+courseItem, Toast.LENGTH_SHORT).show();

                                 Intent intent = new Intent(getContext(),ResultActivity.class);

                                 intent.putExtra("DeptSpec",specItem);
                                 intent.putExtra("CourseName",courseItem);
                                 intent.putExtra("Semester",semItem);
                                 intent.putExtra("DeptName",deptName);
                                 intent.putExtra("DeptField",deptField);
                intent.putExtra("CourseCode",CourseCode);

                                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                 startActivity(intent);


            }
        });


    }

}

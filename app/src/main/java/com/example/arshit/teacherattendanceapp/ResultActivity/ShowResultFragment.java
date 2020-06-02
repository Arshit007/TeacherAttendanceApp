package com.example.arshit.teacherattendanceapp.ResultActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arshit.teacherattendanceapp.Model.StudentModel;
import com.example.arshit.teacherattendanceapp.R;
import com.example.arshit.teacherattendanceapp.SideNavigation.HomeMainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShowResultFragment extends Fragment  {


    FirebaseAuth mAuth;

    View view;
    Button btnResult;

    private DatabaseReference Rootref;
    FirebaseUser currentFirebaseUser;
    Spinner specSpinner,semSpinner,courseSpinner;
    String specItem,semItem,courseItem;
    String currentUserId;
    List<String> semList,courseList,specList;
    String  CourseCode;




    FirebaseRecyclerAdapter adapter;

    DatabaseReference database,resultdb;

    FirebaseRecyclerOptions<StudentModel> options;

    String selected_user_id;
    RecyclerView recyclerView;
    String name;
    DatabaseReference db;


    public ShowResultFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_result, container, false);
intialise();
        return view;

    }
    private void intialise () {

        specSpinner = view.findViewById(R.id.spinner_spec1);
        semSpinner = view.findViewById(R.id.spinner_sem1);
        courseSpinner = view.findViewById(R.id.spinner_course1);

recyclerView = view.findViewById(R.id.show_rv_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));;

        specList = new ArrayList<>();
        semList = new ArrayList<>();
        courseList = new ArrayList<>();

        userDetail();
    }


    private void userDetail () {


            currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            currentUserId = currentFirebaseUser.getUid();


            Rootref = FirebaseDatabase.getInstance().getReference().child("Teachers").child(currentUserId);


            Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {


                    showCourse(dataSnapshot1.child("DeptName").getValue().toString(), dataSnapshot1.child("DeptField").getValue().toString(), dataSnapshot1.child("DeptSpec").getValue().toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        private void showCourse (final String deptName, final String deptField, final String deptSpec){

            final DatabaseReference database1 = FirebaseDatabase.getInstance().getReference("TeacherClass").child(deptName).child(deptField).child(currentUserId).child("Course");


            database1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


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

                    specItem = adapterView.getItemAtPosition(i).toString();

                    DatabaseReference database2 = FirebaseDatabase.getInstance().getReference("TeacherClass").child(deptName).child(deptField).child(currentUserId).child("Course").child(specItem);

                    database2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            semList.clear();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


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

                            semItem = adapterView.getItemAtPosition(i).toString();


                            DatabaseReference database2 = FirebaseDatabase.getInstance().getReference("TeacherClass").child(deptName).child(deptField).child(currentUserId).child("Course").child(specItem).child(semItem);

                            database2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    courseList.clear();
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


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


                                    courseItem = adapterView.getItemAtPosition(i).toString();
                                    showStudent(deptName,deptField,deptSpec,courseItem,semItem);

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





        }

    private void showStudent(String deptName, String deptField, String specItem, final String courseItem, final String semItem) {

        resultdb = FirebaseDatabase.getInstance().getReference("Result").child(deptName).child(deptField).child(specItem);

        db = FirebaseDatabase.getInstance().getReference().child("StudentInfo");


        database = FirebaseDatabase.getInstance().getReference("CoursesOpted").child(deptName).child(deptField).child(specItem).child(semItem).child(courseItem);

        options = new FirebaseRecyclerOptions.Builder<StudentModel>()
                .setQuery(database, StudentModel.class).build();

        adapter = new FirebaseRecyclerAdapter<StudentModel, ShowResultFragment.UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(final ShowResultFragment.UserViewHolder holder, final int position, @NonNull final StudentModel studentModel) {




                holder.spinner.setVisibility(View.GONE);
                selected_user_id = getRef(position).getKey();

//                final int finalCount = count;
                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            holder.spinner.setVisibility(View.GONE);
//                            UserId = dataSnapshot1.child("Grade").getValue().toString();
//                            holder.uSubject.setText(UserId);

                            if(position % 2 == 0){
                                holder.result_relative.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                            }

                            else {
                                holder.result_relative.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.fbutton_color_green_sea));

                            }


                            resultdb.child(String.valueOf(dataSnapshot1.getValue())).child(semItem).child(courseItem).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    if (dataSnapshot.child("Grade").getValue()!= null) {
                                        holder.uSubjectValue.setText("Grade : ");
                                        holder.uSubject.setText(dataSnapshot.child("Grade").getValue().toString());
                                    }
                                    else {
                                        holder.uSubjectValue.setText("Grade : ");
                                        holder.uSubject.setText("No Grade Given");
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            db.child(String.valueOf(dataSnapshot1.getValue())).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    name = dataSnapshot.child("Name").getValue().toString();


                                    holder.uName.setText(name);

                                    holder.uNameValue.setText("Student Name : ");


//                                    holder.uSubject.setVisibility(View.INVISIBLE);



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



            }



            @NonNull
            @Override
            public ShowResultFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
                return new ShowResultFragment.UserViewHolder(view);
            }

        };

        recyclerView.setAdapter(adapter);

        adapter.startListening();



    }


    public static class UserViewHolder extends RecyclerView.ViewHolder  {
        public CircleImageView profileImg;
        ListView listView;
        Spinner spinner;
        TextView uName,uStatus,Uid,uNameValue,uSubjectValue,uSubject;
        View  mView;
        LinearLayout linearLayout;
        RelativeLayout result_relative;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            linearLayout = mView.findViewById(R.id.displayLinearLayout);
            uName = mView.findViewById(R.id.course_code);
            uNameValue = mView.findViewById(R.id.course_code_text);
            uSubject = mView.findViewById(R.id.category_name);
            uSubjectValue = mView.findViewById(R.id.course_name);
            spinner  = mView.findViewById(R.id.spinner_result_show);
            result_relative = mView.findViewById(R.id.result_relative);
        }



    }


}
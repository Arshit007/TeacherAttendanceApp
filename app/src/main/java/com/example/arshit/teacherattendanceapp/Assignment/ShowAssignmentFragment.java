package com.example.arshit.teacherattendanceapp.Assignment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.teacherattendanceapp.Fragment.HomeFragment;
import com.example.arshit.teacherattendanceapp.Model.CourseModel;
import com.example.arshit.teacherattendanceapp.R;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.MondayFragment;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.TakeAttendanceActivity;
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
import java.util.List;


public class ShowAssignmentFragment extends Fragment  {

    View view;
    RecyclerView recyclerView;

    String currentUserId,AssignNo,RollNo;
    private DatabaseReference Rootref;
    FirebaseUser currentFirebaseUser;
    Spinner spinnerSem,spinnerSpec,spinnerCourse;
    String itemSem,itemSpec,itemCourse,DeptName,DeptField;
    List<String> semList,courseList,specList;
    FirebaseRecyclerAdapter adapter1;

    public ShowAssignmentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_show_assignment_, container, false);

        intialise();
        return view;

    }

    private void intialise() {


        spinnerSem = view.findViewById(R.id.spinner_show_sem);
        spinnerSpec = view.findViewById(R.id.spinner_show_spec);
        spinnerCourse = view.findViewById(R.id.spinner_show_course);

        specList = new ArrayList<>();
        semList = new ArrayList<>();
        courseList = new ArrayList<>();






        recyclerView = view.findViewById(R.id.all_show_assignment_teacher);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));;

         Userdetail();


    }

    private void Userdetail() {


        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        currentUserId = currentFirebaseUser.getUid();


        Rootref = FirebaseDatabase.getInstance().getReference().child("Teachers").child(currentUserId);


        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {


                DeptName = dataSnapshot1.child("DeptName").getValue().toString();
                 DeptField = dataSnapshot1.child("DeptField").getValue().toString();

                showCourse(dataSnapshot1.child("DeptName").getValue().toString(),dataSnapshot1.child("DeptField").getValue().toString());
//                showAssigment(dataSnapshot1.child("DeptName").getValue().toString(),dataSnapshot1.child("DeptField").getValue().toString(),dataSnapshot1.child("DeptSpec").getValue().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void showAssigment(final String deptname, final String deptfield, final String deptSpec, final String itemSem, final String itemCourse) {



        final DatabaseReference database1 = FirebaseDatabase.getInstance().getReference("SubmitIndividualAssignment")
                    .child(deptname)
                    .child(deptfield)
                    .child(currentUserId)
                    .child(deptSpec)
                    .child(itemSem)
                    .child(itemCourse);


            FirebaseRecyclerOptions<CourseModel> options = new FirebaseRecyclerOptions.Builder<CourseModel>()
                    .setQuery(database1, CourseModel.class).build();

             adapter1 = new FirebaseRecyclerAdapter<CourseModel,ShowAssignmentFragment.UserViewHolder>(options) {


                @Override
                protected void onBindViewHolder(@NonNull final ShowAssignmentFragment.UserViewHolder holder, final int position, @NonNull final CourseModel category) {


                    database1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                    holder.uTopNameValue.setText(" EnrollmentNo : ");
                                    holder.uTop.setText(dataSnapshot1.child("EnrollmentNo").getValue().toString());


                                    AssignNo =  dataSnapshot1.child("AssignNo").getValue().toString();


                                    RollNo = dataSnapshot1.child("EnrollmentNo").getValue().toString();
                                    holder.uSubjectName.setText(dataSnapshot1.child("AssignNo").getValue().toString());

                                    holder.uSubjectNameValue.setText("Assignment No : ");

                                        holder.uTeacherNameValue.setText("Submitted On :");
                                        holder.uTeacherName.setText(dataSnapshot1.child("DateOfSubmissionStudent").getValue().toString());

                                        holder.uclass.setText(dataSnapshot1.child("Grade").getValue().toString());
                                    holder.uclassvalue.setText("Grade : ");

                                        holder.uRoomNumberValue.setText("Given On :");
                                        holder.uRoomNumber.setText(dataSnapshot1.child("SubmitDate").getValue().toString());

                                    holder.uTxtId.setText(dataSnapshot1.child("PdfUrl").getValue().toString());
                                    holder.uTxtId.setVisibility(View.GONE);


                                 holder.giveGrade.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         giveGrade(holder.getAdapterPosition());
                                     }
                                 });

                                    if(position % 2 == 0){
                                        holder.displayLinearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));


                                        holder.uSubjectNameValue.setTextColor(R.color.bg_login);
                                        holder.uTeacherNameValue.setTextColor(R.color.yellow);
                                        holder.uRoomNumberValue.setTextColor(R.color.purple);

                                        holder.uTopNameValue.setTextColor(R.color.colorPrimaryDark);

                                        holder.uclassvalue.setTextColor(R.color.design_default_color_primary_dark);
                                    }


                                    else {
                                        holder.displayLinearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.yellow));


                                        holder.uSubjectNameValue.setTextColor(R.color.blue);
                                        holder.uTeacherNameValue.setTextColor(R.color.orange);

                                        holder.uRoomNumberValue.setTextColor(R.color.colorAccent);
                                        holder.uTopNameValue.setTextColor(R.color.blue);
                                        holder.uclassvalue.setTextColor(R.color.pink);
                                    }



                                }



                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });

                    holder.uDownTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(String.valueOf(holder.uTxtId.getText().toString())));
                            startActivity(intent);
                        }
                    });


                }

                @NonNull
                @Override
                public ShowAssignmentFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_assignment, parent, false);
                    return new ShowAssignmentFragment.UserViewHolder(view);
                }};


            recyclerView.setAdapter(adapter1);
            adapter1.startListening();


    }

    private void giveGrade(int adapterPosition) {
        GiveGradeDialog(adapter1.getRef(adapterPosition).getKey());
    }

    private void showCourse(final String deptName, final String deptField) {

        final DatabaseReference database1 = FirebaseDatabase.getInstance().getReference("SubmitIndividualAssignment").child(deptName).child(deptField).child(currentUserId);


        database1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                specList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    specList.add(dataSnapshot1.getKey());

                }


                ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, specList);
                DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSpec.setAdapter(DayAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinnerSpec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                itemSpec = adapterView.getItemAtPosition(i).toString();

                DatabaseReference database2 = FirebaseDatabase.getInstance().getReference("SubmitIndividualAssignment").child(deptName).child(deptField).child(currentUserId).child(itemSpec);


                database2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        semList.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                            semList.add(dataSnapshot1.getKey());

                        }

                        ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, semList);
                        DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSem.setAdapter(DayAdapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                spinnerSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        itemSem = adapterView.getItemAtPosition(i).toString();


                        DatabaseReference database2 =  FirebaseDatabase.getInstance().getReference("SubmitIndividualAssignment").child(deptName).child(deptField).child(currentUserId).child(itemSpec).child(itemSem);


                        database2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                courseList.clear();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {



                                    courseList.add(dataSnapshot1.getKey());

                                }

                                ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, courseList);
                                DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCourse.setAdapter(DayAdapter);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                itemCourse = adapterView.getItemAtPosition(i).toString();

                                showAssigment(deptName,deptField,itemSpec,itemSem,itemCourse);
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


    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView uSubjectName,uTeacherName,uTimeSlot,uTopNameValue,
                uTimeSlotValue,uRoomNumber,uRoomNumberValue,uSubjectNameValue
                ,uTeacherNameValue,uTop,uDownTxt,uTxtId,uclass,uclassvalue;
        ImageView giveGrade;
        View  mView;
        LinearLayout displayLinearLayout;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            uTop  = mView.findViewById(R.id.course_code2);
            uTopNameValue = mView.findViewById(R.id.course_code_text2);

            displayLinearLayout = mView.findViewById(R.id.displayLinearLayout2);

            uSubjectName = mView.findViewById(R.id.total_attend_value2);
            uSubjectNameValue = mView.findViewById(R.id.total_attend2);


            uclassvalue = mView.findViewById(R.id.class_attend2);
            uclass = mView.findViewById(R.id.class_attend_value2);

            uTeacherName = mView.findViewById(R.id.percentage_value2);
            uTeacherNameValue  = mView.findViewById(R.id.percentage2);

            uRoomNumber = mView.findViewById(R.id.deptNamevalue2);
            uRoomNumberValue = mView.findViewById(R.id.deptName2);


            uDownTxt = mView.findViewById(R.id.deptNameFieldValue2);
            uTxtId = mView.findViewById(R.id.deptNameField2);

            giveGrade = mView.findViewById(R.id.give_grade);

            giveGrade.setOnCreateContextMenuListener(this);




        }



        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.setHeaderTitle("Select The Action");
            contextMenu.add(0,0,getAdapterPosition(),"Give Grade");

        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {


        GiveGradeDialog(adapter1.getRef(item.getOrder()).getKey());


        return super.onContextItemSelected(item);


    }



    private  void GiveGradeDialog(final String Key){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Give Grades : ");
//        builder.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View custom_layout = inflater.inflate(R.layout.custom_dialog_grade_assign,null);

       final EditText txt_category = custom_layout.findViewById(R.id.edit_text_cat);

        builder.setView(custom_layout);

        builder.setIcon(R.drawable.ic_add_circle_black_24dp);


        builder.setPositiveButton("Ok ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                String groupName = txt_category.getText().toString();

                if (TextUtils.isEmpty(groupName)){

                    Toast.makeText(getContext(), "Please Enter the grade", Toast.LENGTH_SHORT).show();
                }

                else {

                    final DatabaseReference database1 = FirebaseDatabase.getInstance().getReference("SubmitIndividualAssignment")
                            .child(DeptName)
                            .child(DeptField)
                            .child(currentUserId)
                            .child(itemSpec)
                            .child(itemSem)
                            .child(itemCourse).child(Key);

                    final DatabaseReference database2 = FirebaseDatabase.getInstance().getReference("IndividualAssignment")
                            .child(DeptName)
                            .child(DeptField)
                            .child(itemSpec)
                            .child(RollNo)
                            .child(itemSem)
                            .child(itemCourse).child(AssignNo);

                    database2.child("Grade").setValue(groupName);

                    database1.child("Grade").setValue(groupName);

                }

            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });



        builder.show();





    }
}

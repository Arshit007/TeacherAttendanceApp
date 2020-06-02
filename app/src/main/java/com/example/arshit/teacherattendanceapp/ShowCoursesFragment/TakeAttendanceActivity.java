package com.example.arshit.teacherattendanceapp.ShowCoursesFragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.teacherattendanceapp.Model.StudentModel;
import com.example.arshit.teacherattendanceapp.R;
import com.example.arshit.teacherattendanceapp.SideNavigation.HomeMainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TakeAttendanceActivity extends AppCompatActivity {

    TextView toolbar_title;
    RecyclerView recyclerView;
    private DatabaseReference Rootref;
    Intent intent;
    String TxtPresent,TxtAbsent;
    String CourseName,UserName;
    private FirebaseRecyclerAdapter adapter;
    String selected_user_id,Id;
    String CName,CCode;
    String name;
    Button btnSubmit;
    DatabaseReference db,db1,attendaceRef,individualRef;
    DatabaseReference database;
    FirebaseRecyclerOptions<StudentModel> options;
    private Query query;
    String txtRadio,userId;
    int Psum =0,Absum=0;
    String title;

    DatabaseReference dbRef;
    HashMap<String, String> hashMap,hashMap1;
    String deptName,deptField,deptSpec,Semester,TimeSlot,Day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        initialise();
        toolbar();

    }



    private void initialise() {

        intent= getIntent();
        btnSubmit = findViewById(R.id.btn_submit_attendance);


hashMap1 = new HashMap<>();

        individualRef = FirebaseDatabase.getInstance().getReference("IndividualAttendance");

        dbRef   = FirebaseDatabase.getInstance().getReference("AttendanceTotal");

        CourseName = intent.getStringExtra("CourseName");

        deptName = intent.getStringExtra("DeptName");
        deptField = intent.getStringExtra("DeptField");
        deptSpec = intent.getStringExtra("DeptSpec");


        Semester = intent.getStringExtra("Semester");

        TimeSlot = intent.getStringExtra("TimeSlot");
        Day = intent.getStringExtra("Day");


        attendaceRef = FirebaseDatabase.getInstance().getReference().child("Attendance");

        db = FirebaseDatabase.getInstance().getReference().child("StudentInfo");

        db1 = FirebaseDatabase.getInstance().getReference().child("CoursesRegister");


        recyclerView  = findViewById(R.id.all_courses_rv1);
        GridLayoutManager glm = new GridLayoutManager(TakeAttendanceActivity.this, 1);
        recyclerView.setLayoutManager(glm);

    }

    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(CourseName);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();


        db = FirebaseDatabase.getInstance().getReference().child("StudentInfo");

        database = FirebaseDatabase.getInstance().getReference("CoursesOpted").child(deptName).child(deptField).child(deptSpec).child(Semester).child(CourseName);

        options = new FirebaseRecyclerOptions.Builder<StudentModel>()
                .setQuery(database,StudentModel.class).build();

        adapter = new FirebaseRecyclerAdapter<StudentModel, TakeAttendanceActivity.UserViewHolder>(options) {

            @Override
            protected void onBindViewHolder(final TakeAttendanceActivity.UserViewHolder holder, final int position, @NonNull final StudentModel studentModel) {


                selected_user_id = getRef(position).getKey();

                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

       for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {



           db.child(String.valueOf(dataSnapshot1.getValue())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    name = dataSnapshot.child("Name").getValue().toString();
                    Id = dataSnapshot.child("Id").getValue().toString();

                    holder.uName.setText(name);
                    holder.Uid.setText(Id);
//                }

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {


                        Intent intent = new Intent(TakeAttendanceActivity.this,HomeMainActivity.class);

                        Toast.makeText(TakeAttendanceActivity.this, "Attendance Added", Toast.LENGTH_SHORT).show();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();

                    }
                });





                holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {

                        TxtPresent = String.valueOf(((RadioButton) radioGroup.findViewById(i)).getText());
                        hashMap = new HashMap<>();




                        switch (i) {
                            case R.id.radio0:

                                long date = System.currentTimeMillis();

                                SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy ");
                                String dateString = sdf.format(date);



                                title = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.course_code)).getText().toString();

                                userId = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.id_Student)).getText().toString();


                                hashMap.put("Name", title);
                                hashMap.put("Date",dateString);
                                hashMap.put("Status",TxtPresent);
                                hashMap.put("TimeSlot",TimeSlot);
                                hashMap.put("UserId",userId);
                                hashMap.put("Day",Day);
                                hashMap.put("Semester",Semester);

                                attendaceRef.child(deptName).child(deptField).child(deptSpec).child(Semester).child(CourseName).child(dateString+TimeSlot+Day).child(userId).setValue(hashMap);

                                individualRef.child(deptName).child(deptField).child(deptSpec).child(userId).child(Semester).child(CourseName).child(dateString+TimeSlot+Day).setValue(hashMap);




                                break;

                            case R.id.radio1:


                                 date = System.currentTimeMillis();

                             sdf = new SimpleDateFormat("MMM MM dd, yyyy ");
                                 dateString = sdf.format(date);

                                title = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.course_code)).getText().toString();

                                userId = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.id_Student)).getText().toString();


                                hashMap.put("Name", title);
                                hashMap.put("Date",dateString);
                                hashMap.put("Status",TxtPresent);
                                hashMap.put("TimeSlot",TimeSlot);
                                hashMap.put("UserId",userId);
                                hashMap.put("Day",Day);
                                hashMap.put("Semester",Semester);


                                attendaceRef.child(deptName).child(deptField).child(deptSpec).child(userId).child(Semester).child(CourseName).child(dateString+TimeSlot+Day).child(userId).setValue(hashMap);

                                individualRef.child(deptName).child(deptField).child(deptSpec).child(userId).child(Semester).child(CourseName).child(dateString+TimeSlot+Day).setValue(hashMap);

                                break;
                            default:

                        }

                    }
                });




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
            public TakeAttendanceActivity.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance, parent, false);
                return new TakeAttendanceActivity.UserViewHolder(view);
            }

        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }




    public static class UserViewHolder extends RecyclerView.ViewHolder   {
        public CircleImageView profileImg;
        ListView listView;
        TextView uName,uStatus,Uid,uNameValue,uSubjectValue;
        View  mView;
        RadioGroup radioGroup;
        RadioButton radioButton;
        private final LinearLayout layout;
        final LinearLayout.LayoutParams params;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            radioGroup = (RadioGroup) mView.findViewById(R.id.radioGroup);
            radioButton =(RadioButton) mView.findViewById(radioGroup.getCheckedRadioButtonId());

            layout =(LinearLayout)mView.findViewById(R.id.displayLinearLayout);

            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            uName = mView.findViewById(R.id.course_code);
            Uid = mView.findViewById(R.id.id_Student);

                       uNameValue = mView.findViewById(R.id.course_code_text);
            uSubjectValue = mView.findViewById(R.id.course_name);

        }

        private void Layout_hide() {
            params.height = 0;
            //itemView.setLayoutParams(params); //This One.
            layout.setLayoutParams(params);   //Or This one.

        }
    }


}

package com.example.arshit.teacherattendanceapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.teacherattendanceapp.Fragment.AllStudentFragment;
import com.example.arshit.teacherattendanceapp.Model.StudentModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowStudentByCourse extends AppCompatActivity {

    TextView toolbar_title;
    RecyclerView recyclerView;
    private DatabaseReference Rootref;
    Intent intent;
    String CourseName,UserName;
    private FirebaseRecyclerAdapter adapter;
    String selected_user_id;
    String CName,CCode;
    String name;
    DatabaseReference db,db1;
    DatabaseReference database;
    FirebaseRecyclerOptions<StudentModel> options;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_by_course);

        initialise();
        toolbar();


      }

    private void initialise() {

        intent= getIntent();

        CourseName = intent.getStringExtra("CourseSelected");

        db = FirebaseDatabase.getInstance().getReference().child("StudentInfo");

        db1 = FirebaseDatabase.getInstance().getReference().child("CoursesRegister");

        recyclerView  = findViewById(R.id.all_courses_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));;
        //
//   recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

    }

    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(CourseName);

//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(false);
////
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                finish();
//
//    }
//});


    }


    @Override
    public void onResume() {
        super.onResume();



        db = FirebaseDatabase.getInstance().getReference().child("StudentInfo");

        database = FirebaseDatabase.getInstance().getReference("CoursesOpted").child(CourseName);




        options = new FirebaseRecyclerOptions.Builder<StudentModel>()
                .setQuery(database, StudentModel.class).build();

        adapter = new FirebaseRecyclerAdapter<StudentModel, UserViewHolder>(options) {



            @Override
            protected void onBindViewHolder(final ShowStudentByCourse.UserViewHolder holder, final int position, @NonNull final StudentModel studentModel) {


               selected_user_id = getRef(position).getKey();


                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {




                            db.child(String.valueOf(dataSnapshot1.getValue())).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    name = dataSnapshot.child("Name").getValue().toString();

                                    holder.uName.setText(name);
                                    holder.uNameValue.setText("Course Name");
                                   holder.uSubjectValue.setText("Class");
                                   holder.uSubject.setText("CS-A");


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
            public ShowStudentByCourse.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_student, parent, false);
                return new ShowStudentByCourse.UserViewHolder(view);
            }};

        recyclerView.setAdapter(adapter);

        adapter.startListening();


    }




    public static class UserViewHolder extends RecyclerView.ViewHolder  {
        public CircleImageView profileImg;
        ListView listView;
        TextView uName,uStatus,Uid,uNameValue,uSubjectValue,uSubject;
        View  mView;
        LinearLayout linearLayout;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


         linearLayout = mView.findViewById(R.id.displayLinearLayout);
            uName = mView.findViewById(R.id.course_code);
            uNameValue = mView.findViewById(R.id.course_code_text);
            uSubject = mView.findViewById(R.id.category_name);
            uSubjectValue = mView.findViewById(R.id.course_name);
        }



    }



}

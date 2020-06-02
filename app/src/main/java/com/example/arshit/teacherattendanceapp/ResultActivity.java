package com.example.arshit.teacherattendanceapp;

//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.support.annotation.NonNull;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.ContextMenu;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.Toolbar;
//
//import com.example.arshit.teacherattendanceapp.Model.StudentModel;
//import com.example.arshit.teacherattendanceapp.SideNavigation.HomeMainActivity;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public  class ResultActivity extends AppCompatActivity {
//
//
//    DatabaseReference RootRef,DbRef,DbRef1,CRef;
//    Intent intent;
//    SharedPreferences myprefs;
//   List<String> grade = new ArrayList<String>();
//    DatabaseReference ResultRef;
//
//    TextView toolbar_title;
//    FirebaseRecyclerAdapter adapter;
//    Button btnResult;
//    RecyclerView recycler_category;
//
//    DatabaseReference database;
//
//    FirebaseRecyclerOptions<StudentModel> options;
//    HashMap<String, String> hashMap;
//    SharedPreferences mypref;
//String currentUserId;
//    String StudentName,UserId,Cname;
//    String selected_user_id,selectedItem,CCredit;
//    RecyclerView recyclerView;
//    private DatabaseReference resultdb;
//    String CourseName,UserName,Name;
//    String CName,CCode;
//    String name;
//    int  Ci = 0,CixGi = 0;
//
//    String Ccredit;
//
//    DatabaseReference db,db1;
//
//    private Query query;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result);
//    intialise();
//    toolbar();
//
//    }
//
//    private void toolbar() {
//
//        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);
//
//        toolbar_title = findViewById(R.id.toolbar_title);
//        toolbar_title.setText(CourseName);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//    }
//
//
//    private void intialise() {
//
//        intent = getIntent();
//
//
//
//        grade.add("Select Grade");
//        grade.add("O(90-100)");
//        grade.add("A+(80-89)");
//        grade.add("A(70-79)");
//        grade.add("B+(60-69)");
//        grade.add("B(50-59)");
//        grade.add("C(40-49)");
//        grade.add("P(30-39)");
//        grade.add("F(29 and below)");
//        grade.add("Ab(Absent)");
//
//        CourseName = intent.getStringExtra("CourseSelected");
//        CCredit = intent.getStringExtra("CourseCredit");
//
//        db = FirebaseDatabase.getInstance().getReference().child("StudentInfo");
//       btnResult = findViewById(R.id.btn_submit_result);
//       btnResult.setVisibility(View.INVISIBLE);
//       db1 = FirebaseDatabase.getInstance().getReference().child("CoursesRegister");
//
//        recyclerView  = findViewById(R.id.recycler_result);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));;
//
//
//        showStudent();
//
//    }
//
//    private void showStudent() {
//
//
//        resultdb = FirebaseDatabase.getInstance().getReference("Result");
//
//    db = FirebaseDatabase.getInstance().getReference().child("StudentInfo");
//
//        database = FirebaseDatabase.getInstance().getReference("CoursesOpted").child(CourseName);
//
//
//
//
//
//        options = new FirebaseRecyclerOptions.Builder<StudentModel>()
//                .setQuery(database, StudentModel.class).build();
//
//        adapter = new FirebaseRecyclerAdapter<StudentModel, ResultActivity.UserViewHolder>(options) {
//
//
//
//            @Override
//            protected void onBindViewHolder(final ResultActivity.UserViewHolder holder, final int position, @NonNull final StudentModel studentModel) {
//
//
//                int count = 0;
//                if (adapter != null) {
//                    count = adapter.getItemCount();
//                }
//
//                selected_user_id = getRef(position).getKey();
//
//
//                final int
//
//                        finalCount = count;
//                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//
//
//
//                            db.child(String.valueOf(dataSnapshot1.getValue())).addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                                    name = dataSnapshot.child("Name").getValue().toString();
//                                 UserId = dataSnapshot.child("Id").getValue().toString();
//
//                                    holder.uName.setText(name);
//
//                                    holder.uNameValue.setText("Student Name");
//                                    holder.uSubjectValue.setText("Select Grade");
//                                    holder.uSubject.setText(UserId);
//                                    holder.uSubject.setVisibility(View.INVISIBLE);
//
//
//                                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.view_spinner_item,grade);
//                                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    holder.spinner.setAdapter(areasAdapter);
//
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//
//
//                        }
//
//
//                        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//                        {
//                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//                            {
//
//
//                                selectedItem = parent.getItemAtPosition(position).toString();
//
//                                for (int i = 0; i< finalCount; i++) {
//                                    if (selectedItem.equals("Select Grade")) {
//                                        btnResult.setVisibility(View.INVISIBLE);
////                                    Toast.makeText(ResultActivity.this, "Error", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                    else {
//                                        Name = String.valueOf(holder.uName.getText());
//                                          UserId = String.valueOf(holder.uSubject.getText());
//
//
//                                        hashMap = new HashMap<>();
//
//                                        hashMap.put("Grade", selectedItem);
//                                        hashMap.put("Name", Name);
//                                        hashMap.put("Id",UserId);
//                                        hashMap.put("CourseCredit",CCredit);
//                                        hashMap.put("CourseName",CourseName);
//
//
//
//                                        resultdb.child(UserId).child(CourseName).setValue(hashMap);
//
//                                        updateResult(UserId);
//                                        btnResult.setVisibility(View.VISIBLE);
//                                        btnResult.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View view) {
//
//                                                Intent intent = new Intent(ResultActivity.this,HomeMainActivity.class);
//                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                startActivity(intent);
//
//                                                finish();
//
//
//                                            }
//                                        });
//
//                                    }
//
//
//                                }
//                            }
//
//                            public void onNothingSelected(AdapterView<?> parent)
//                            {
//
//                            }
//                        });
//                    }
//
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//
//
//
//            }
//
//
//
//            @NonNull
//            @Override
//            public ResultActivity.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
//                return new ResultActivity.UserViewHolder(view);
//            }
//
//        };
//
//        recyclerView.setAdapter(adapter);
//
//        adapter.startListening();
//
//
//    }
//
//    private void updateResult(final String userId) {
//
//        ResultRef = FirebaseDatabase.getInstance().getReference().child("ResultOverall");
//
//
//        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Result").child(userId);
//
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                  String  grade = dataSnapshot1.child("Grade").getValue().toString();
//                  String  Ccredit = dataSnapshot1.child("CourseCredit").getValue().toString();
//
//                   Ci = Ci + Integer.parseInt(String.valueOf(Ccredit));
//
//
//                    if (grade.equals("O(90-100)")) {
//
//                        CixGi = CixGi + Integer.parseInt(String.valueOf(Ccredit)) * 10;
//                    } else if (grade.equals("A+(80-89)")) {
//
//                        CixGi = CixGi + Integer.parseInt(String.valueOf(Ccredit)) * 9;
//                    } else if (grade.equals("A(70-79)")) {
//
//                        CixGi = CixGi + Integer.parseInt(String.valueOf(Ccredit)) * 8;
//                    } else if (grade.equals("B+(60-69)")) {
//
//                        CixGi = CixGi + Integer.parseInt(String.valueOf(Ccredit)) * 7;
//                    } else if (grade.equals("B(50-59)")) {
//
//                        CixGi = CixGi + Integer.parseInt(String.valueOf(Ccredit)) * 6;
//                    } else if (grade.equals("C(40-49)")) {
//
//                        CixGi = CixGi + Integer.parseInt(String.valueOf(Ccredit)) * 5;
//                    } else if (grade.equals("P(30-39)")) {
//
//                        CixGi = CixGi + Integer.parseInt(String.valueOf(Ccredit)) * 4;
//                    } else if (grade.equals("F(29 and below)")) {
//
//                        CixGi = CixGi + Integer.parseInt(String.valueOf(Ccredit)) * 0;
//                    } else
////                        if (grade.equals("Ab(Absent)"))
//                    {
//
//                        CixGi = CixGi + Integer.parseInt(String.valueOf(Ccredit)) * 0;
//                    }
//
////                        Toast.makeText(getContext(), "CI*GI \n "+CixGi, Toast.LENGTH_SHORT).show();
////
//
//                    float sum = Float.parseFloat(String.valueOf(Float.parseFloat(String.valueOf(CixGi)) / Float.parseFloat(String.valueOf(Ci))));
//
//                    DecimalFormat df = new DecimalFormat();
//                    df.setMaximumFractionDigits(2);
//
//                    ResultRef.child(userId).child("Semester1").setValue(df.format(sum));
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) { }
//        });
//
//    }
//
//
//    public static class UserViewHolder extends RecyclerView.ViewHolder  {
//        public CircleImageView profileImg;
//        ListView listView;
//        Spinner spinner;
//        TextView uName,uStatus,Uid,uNameValue,uSubjectValue,uSubject;
//        View  mView;
//        LinearLayout linearLayout;
//
//        public UserViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//
//
//            linearLayout = mView.findViewById(R.id.displayLinearLayout);
//            uName = mView.findViewById(R.id.course_code);
//            uNameValue = mView.findViewById(R.id.course_code_text);
//            uSubject = mView.findViewById(R.id.category_name);
//            uSubjectValue = mView.findViewById(R.id.course_name);
//            spinner  = mView.findViewById(R.id.spinner_result_show);
//        }
//
//
//
//    }




//}

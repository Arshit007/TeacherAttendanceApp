package com.example.arshit.teacherattendanceapp.ShowCoursesFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.teacherattendanceapp.Model.Category;
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


public class WednesdayFragment extends Fragment {

    RecyclerView uRecyclerView;


    private DatabaseReference Rootref;
    FirebaseUser currentFirebaseUser;

    View view;
    FirebaseAuth auth;
    RelativeLayout relativeLayout;
    String currentUserId;

    String deptName,deptField,Semester,deptSpecName;



    public WednesdayFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wednesday, container, false);

        intialise();

        return view;
    }

    private void intialise() {


        relativeLayout = view.findViewById(R.id.txt_wed);
        relativeLayout.setVisibility(View.VISIBLE);


        uRecyclerView = view.findViewById(R.id.rv_wed);
        uRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        Userdetail();
    }




    private void Userdetail() {


        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        currentUserId = currentFirebaseUser.getUid();


        Rootref = FirebaseDatabase.getInstance().getReference().child("Teachers").child(currentUserId);


        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {



                showTue(dataSnapshot1.child("DeptName").getValue().toString(),dataSnapshot1.child("DeptField").getValue().toString(),dataSnapshot1.child("DeptSpec").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void showTue(final String deptName, final String deptField, final String deptSpec) {



        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        currentUserId = currentFirebaseUser.getUid();

        final DatabaseReference   database1 = FirebaseDatabase.getInstance().getReference("TeacherClass").child(deptName).child(deptField).child(currentUserId).child("DailyTimeTable").child("Wednesday");
        database1.keepSynced(true);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(database1, Category.class).build();

        FirebaseRecyclerAdapter       adapter1 = new FirebaseRecyclerAdapter<Category,UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final WednesdayFragment.UserViewHolder holder, int position, @NonNull final Category category) {


                String selected_user_id1 = getRef(position).getKey();

                database1.child(selected_user_id1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        relativeLayout.setVisibility(View.INVISIBLE);


                        holder.uSubjectName.setText(String.valueOf(dataSnapshot.child("CourseName").getValue().toString()));
                        holder.uTeacherName.setText(String.valueOf(dataSnapshot.child("Semester").getValue().toString()));
                        holder.uTimeSlot.setText(String.valueOf(dataSnapshot.child("Time").getValue().toString()));
                        holder.uRoomNumber.setText(String.valueOf(dataSnapshot.child("Room").getValue().toString()));


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(getContext(),TakeAttendanceActivity.class);
                        intent.putExtra("CourseName",String.valueOf(holder.uSubjectName.getText().toString()));
                        intent.putExtra("TimeSlot",String.valueOf(holder.uTimeSlot.getText().toString()));
                        intent.putExtra("Semester",String.valueOf(holder.uTeacherName.getText().toString()));
                        intent.putExtra("Day","Wednesday");
                        intent.putExtra("DeptName",deptName);
                        intent.putExtra("DeptField",deptField);
                        intent.putExtra("DeptSpec",deptSpec);

                        startActivity(intent);

                    }
                });



            }

            @NonNull
            @Override
            public WednesdayFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_teacher_time_table, parent, false);
                return new WednesdayFragment.UserViewHolder(view);

            }

        } ;


        uRecyclerView.setAdapter(adapter1);
        adapter1.startListening();

    }




    public static class UserViewHolder extends RecyclerView.ViewHolder  {

        TextView uSubjectName,uTeacherName,uTimeSlot,uRoomNumber;
        View  mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            uSubjectName = mView.findViewById(R.id.subjectexams);
            uTeacherName = mView.findViewById(R.id.teacherexams);
            uTimeSlot = mView.findViewById(R.id.timeexams);
            uRoomNumber = mView.findViewById(R.id.roomexams);



        }



    }


}
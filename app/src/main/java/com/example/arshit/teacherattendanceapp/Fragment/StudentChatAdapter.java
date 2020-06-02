package com.example.arshit.teacherattendanceapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.arshit.teacherattendanceapp.MessageActivity;
import com.example.arshit.teacherattendanceapp.Model.Student;
import com.example.arshit.teacherattendanceapp.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class StudentChatAdapter extends RecyclerView.Adapter<StudentChatAdapter.UserViewHolder> {

    private Context context;
    private  ArrayList<Student> userdata;
    private String imageURL;
    DatabaseReference database;
    String    image;

    public StudentChatAdapter(Context context, ArrayList<Student> userdata, String imageURL) {
        this.context = context;
        this.userdata = userdata;
        this.imageURL = imageURL;
    }



    @NonNull
    @Override
    public StudentChatAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userdisplay, parent, false);
        return new UserViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {


        final Student user = userdata.get(position);
        holder.uname.setText(user.getName());
        holder.ustatus.setText(user.getDeptField());


//        Picasso.get().load(imageURL).placeholder(R.drawable.profile_avatar_small).into(holder.profileImg);
//        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.profile_avatar_small).into(holder.profileImg);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid",user.getId());
                intent.putExtra("uuId",user.getUserId());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() { return userdata.size(); }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView uname,ustatus;
        public     ImageView profileImg;
        public UserViewHolder(View itemView) {
            super(itemView);

            profileImg = itemView.findViewById(R.id.userdiaplayimg1);
            ustatus = itemView.findViewById(R.id.ustatus);
            uname = itemView.findViewById(R.id.uname);

        }


    }
}

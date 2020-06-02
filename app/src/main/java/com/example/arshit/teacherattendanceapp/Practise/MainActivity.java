package com.example.arshit.teacherattendanceapp.Practise;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.arshit.teacherattendanceapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;
    Button btnClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        showNotification();


        sendEmail();
    }

    private void sendEmail() {

        btnClick = findViewById(R.id.btnClick);


    }

    private void showNotification() {



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.download);
            String description = getString(R.string.minimum_password);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder = new NotificationCompat.Builder(getApplicationContext(), "My Notification");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("StudentInfo");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    builder.setSmallIcon(R.drawable.profile_avatar_small);

                    builder.setContentTitle(dataSnapshot1.child("Name").getValue().toString());
                    builder.setContentText(dataSnapshot1.child("Password").getValue().toString());


//                    builder.setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("Much longer text that cannot fit one line..."));

                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    builder.setContentIntent(pendingIntent);

                    notificationManager = NotificationManagerCompat.from(getApplicationContext());
//// notificationId is a unique int for each notification that you must define//
                    notificationManager.notify(2, builder.build());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }
}

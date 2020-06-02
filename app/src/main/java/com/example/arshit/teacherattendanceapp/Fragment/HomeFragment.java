package com.example.arshit.teacherattendanceapp.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.arshit.teacherattendanceapp.Model.CourseModel;
import com.example.arshit.teacherattendanceapp.R;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.FridayFragment;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.MondayFragment;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.ThursdayFragment;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.TuesdayFragment;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.WednesdayFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements TabLayout.BaseOnTabSelectedListener {



    TabLayout tabLayout;
    MyPagerAdapter adapterViewPager;
    ViewPager vpPager;
    View view;
    String sessionId;
    public HomeFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view = inflater.inflate(R.layout.fragment_home, container, false);


        intialise();

        return view;

    }



    private  void intialise(){

        sessionId = UUID.randomUUID().toString();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", sessionId);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Monday"));

        tabLayout.addTab(tabLayout.newTab().setText("Tuesday"));
        tabLayout.addTab(tabLayout.newTab().setText("Wednesday"));

        tabLayout.addTab(tabLayout.newTab().setText("Thursday"));

        tabLayout.addTab(tabLayout.newTab().setText("Friday"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        vpPager = (ViewPager) view.findViewById(R.id.vpPager);
        tabLayout.setOnTabSelectedListener(HomeFragment.this);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount());
//        showDay();

        vpPager.setAdapter(adapterViewPager);


        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                vpPager.setCurrentItem(position);

                tabLayout.getTabAt(position).select();


            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }


            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        vpPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        int tabCount;

        public MyPagerAdapter(FragmentManager fragmentManager, int tabCount) {
            super(fragmentManager);
            this.tabCount= tabCount;
        }

        @Override
        public int getCount() {
            return tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:


                    MondayFragment mondayFragment = new MondayFragment();
                    return mondayFragment;

                case 1 :
                    TuesdayFragment tuesdayFragment = new TuesdayFragment();
                    return tuesdayFragment;

                case 2:

                    WednesdayFragment wednesdayFragment = new WednesdayFragment();
                    return wednesdayFragment;

                case 3:

                    ThursdayFragment thursdayFragment =  new ThursdayFragment();
                    return  thursdayFragment;
                case 4:

                    FridayFragment fridayFragment = new FridayFragment();
                    return  fridayFragment;

                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }


}


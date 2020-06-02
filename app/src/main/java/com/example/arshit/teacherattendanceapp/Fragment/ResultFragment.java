package com.example.arshit.teacherattendanceapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arshit.teacherattendanceapp.R;
import com.example.arshit.teacherattendanceapp.ResultActivity.ShowResultFragment;
import com.example.arshit.teacherattendanceapp.ResultActivity.TakeResultFragment;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class ResultFragment extends Fragment implements TabLayout.BaseOnTabSelectedListener {


    FirebaseAuth mAuth;

    View view;


    TabLayout tabLayout;
    MyPagerAdapter adapterViewPager;
    ViewPager vpPager;

    public ResultFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result_home, container, false);





        intialise();
        return view;

    }

    private  void intialise(){

        mAuth= FirebaseAuth.getInstance();



        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout1);



        tabLayout.addTab(tabLayout.newTab().setText("Give Marks"));
        tabLayout.addTab(tabLayout.newTab().setText("Show Result"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        vpPager = (ViewPager) view.findViewById(R.id.vpPager1);
        tabLayout.setOnTabSelectedListener(ResultFragment.this);
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


                    TakeResultFragment takeResultFragment = new TakeResultFragment();
                    return takeResultFragment;

                case 1 :
                    ShowResultFragment showResultFragment = new ShowResultFragment();
                    return showResultFragment;


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

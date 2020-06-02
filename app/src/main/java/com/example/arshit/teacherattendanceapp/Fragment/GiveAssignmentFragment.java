package com.example.arshit.teacherattendanceapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.arshit.teacherattendanceapp.Assignment.AddAssignmentActivity;
import com.example.arshit.teacherattendanceapp.Assignment.ShowAssignmentFragment;
import com.example.arshit.teacherattendanceapp.Assignment.SubmissionAssignmentFragment;
import com.example.arshit.teacherattendanceapp.R;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.FridayFragment;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.MondayFragment;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.ThursdayFragment;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.TuesdayFragment;
import com.example.arshit.teacherattendanceapp.ShowCoursesFragment.WednesdayFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;


public class GiveAssignmentFragment extends Fragment implements TabLayout.BaseOnTabSelectedListener {

    View view;

    TabLayout tabLayout;
    MyPagerAdapter adapterViewPager;
    ViewPager vpPager;

    FloatingActionButton floatingActionButton;

    public GiveAssignmentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_give_assignment, container, false);

        intialise();
        return view;

    }

    private void intialise() {


        floatingActionButton = view.findViewById(R.id.add_assigment_float);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(),AddAssignmentActivity.class));
            }
        });
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout2);

        tabLayout.addTab(tabLayout.newTab().setText("Give Grade"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        vpPager = (ViewPager) view.findViewById(R.id.vpPager2);
        tabLayout.setOnTabSelectedListener(GiveAssignmentFragment.this);
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


                    ShowAssignmentFragment  showAssignmentFragment = new ShowAssignmentFragment();
                    return showAssignmentFragment;



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

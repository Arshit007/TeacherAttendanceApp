package com.example.arshit.teacherattendanceapp.Assignment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arshit.teacherattendanceapp.Fragment.HomeFragment;
import com.example.arshit.teacherattendanceapp.R;


public class SubmissionAssignmentFragment extends Fragment {

    View view;



    public SubmissionAssignmentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_submission_assignment, container, false);

        intialise();
        return view;

    }

    private void intialise() {


    }


}

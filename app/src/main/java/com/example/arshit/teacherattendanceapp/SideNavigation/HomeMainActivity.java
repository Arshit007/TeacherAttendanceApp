package com.example.arshit.teacherattendanceapp.SideNavigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.arshit.teacherattendanceapp.Fragment.ChatFragment;
import com.example.arshit.teacherattendanceapp.Fragment.HomeFragment;
import com.example.arshit.teacherattendanceapp.Fragment.ResultFragment;
import com.example.arshit.teacherattendanceapp.Fragment.GiveAssignmentFragment;
import com.example.arshit.teacherattendanceapp.Fragment.ProfileFragment;
import com.example.arshit.teacherattendanceapp.Login.SignIn;
import com.example.arshit.teacherattendanceapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;


public class HomeMainActivity extends AppCompatActivity {



    private DatabaseReference Rootref;
    FirebaseAuth mAuth;
    FirebaseRecyclerAdapter adapter;
    FirebaseUser firebaseUser;
    private String currentUserId;
    EditText txt_category,edit_text_price_discount,edit_food_cat,edit_text_sub_cat,edit_text_price_subcat;
    Button btnSelect,btnUpload;
    private static int Gallery_Pick =1;
    private ProgressDialog mProgressDialog;
    private StorageReference imgStorageReference;
    Uri imageURI;
    RecyclerView recycler_category;
    String subcatdiscount,subcatName,subcatdescription,subcatprice,categoryName;
    Button btn_category,btn_subcat;
    String catName;
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";



    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;


    public static int navItemIndex = 0;

    private static final String TAG_HOME = "Take Attendance";
//    private static final String TAG_PHOTOS = "All Student";
    private static final String Profile = "Profile";
    private static final String GIVE_ASSIGNMENT = "Give Assignment";
//    private static final String TAG_SETTINGS = "Change Password";
    private static final String TAG_RESUlT = "Give RESULT";
    private static final String CHAT = "Chat";

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);


//        intialise();



        mAuth = FirebaseAuth.getInstance();
//        currentUserId = mAuth.getCurrentUser().getUid();

//        final DatabaseReference dbrf = FirebaseDatabase.getInstance().getReference("Token");
//
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String deviceToken = instanceIdResult.getToken();
//                MediaSession.Token
//             Token data = new Token(deviceToken,true);
//                dbrf.child(currentUserId).setValue(data);
//
//            }
//        });







        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.addBtn);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);

        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                startActivity(new Intent(HomeMainActivity.this,AddStudent.class));

            }
        });

        loadNavHeader();

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }


    private void loadNavHeader() {
        // name, website
        txtName.setText("Arshit Jain");



    }

    private void loadHomeFragment() {
        selectNavMenu();

        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:

                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:

                GiveAssignmentFragment giveAssignmentFragment = new GiveAssignmentFragment();
                return giveAssignmentFragment;
            case 2:
               ResultFragment ResultFragment = new ResultFragment();
                return ResultFragment;
            case 3:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            case 4:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_movies:
                        navItemIndex = 1;
                        CURRENT_TAG = GIVE_ASSIGNMENT;
                        break;
                    case R.id.nav_result:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_RESUlT;
                        break;
                    case R.id.nav_chat:
                        navItemIndex = 3;
                        CURRENT_TAG = CHAT;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 4;
                        CURRENT_TAG = Profile;
                        break;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
//            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeMainActivity.this, SignIn.class));
            finish();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }



}

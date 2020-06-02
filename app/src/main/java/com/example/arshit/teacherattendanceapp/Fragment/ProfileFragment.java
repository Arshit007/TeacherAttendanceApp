package com.example.arshit.teacherattendanceapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;



import com.example.arshit.teacherattendanceapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {


    private  String OldPassword;
    private DatabaseReference Rootref;
    ImageView profilePic;
    Button btnprofile;
    SharedPreferences myprefs;
    private static final int GALLERY_PICK = 1;
    String currentUserId;
    StorageReference imgStorageReference;
    TextInputLayout name, email, number;
    private ProgressDialog mProgressDialog;
    String oldName, oldNumber, newName, newEmail, newNumber;
    View view;
    FirebaseUser currentFirebaseUser,user;
    AuthCredential credential;

    String newPassword,UserPassword,UserEmail;


    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        intialise();
        return view;

    }

    private void intialise() {

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        currentUserId = currentFirebaseUser.getUid();


        Rootref = FirebaseDatabase.getInstance().getReference().child("Teachers");




        btnprofile = view.findViewById(R.id.btnprofile);

        profilePic = (ImageView) view.findViewById(R.id.profilePic);

        imgStorageReference = FirebaseStorage.getInstance().getReference();

        name = view.findViewById(R.id.profilename);
        email = view.findViewById(R.id.profileemail);
        number = view.findViewById(R.id.profileno);
        user = FirebaseAuth.getInstance().getCurrentUser();

        oldName = name.getEditText().toString();
//        oldEmail = email.getEditText().toString();
        oldNumber = number.getEditText().toString();


        Rootref.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                oldName = dataSnapshot.child("Name").getValue().toString();
//                oldEmail = dataSnapshot.child("Email").getValue().toString();
                oldNumber = dataSnapshot.child("PhoneNumber").getValue().toString();

                OldPassword  = dataSnapshot.child("Password").getValue().toString();

                newEmail = email.getEditText().getText().toString();
                email.getEditText().setText(OldPassword);


                newName = name.getEditText().getText().toString();
                name.getEditText().setText(oldName);

//                newEmail = email.getEditText().toString();
//                email.getEditText().setText(oldEmail);
//



        if (email.getEditText().getText().toString().length() < 6)

        {
            Toast.makeText(getContext(), "Password should have minimum 5 letter", Toast.LENGTH_SHORT).show();

            return;
        }


else {
            btnprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    changeProfileDetails();

                }
            });

        }

                newNumber = number.getEditText().toString();
                number.getEditText().setText(oldNumber);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeProfilePic();
            }
        });







    }

//    private void changePassword() {
//
//        Rootref.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//}



    private void changeProfilePic() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getContext(), ProfileFragment.this);


    }


    private void changeProfileDetails() {


        name = view.findViewById(R.id.profilename);
        email = view.findViewById(R.id.profileemail);
        number = view.findViewById(R.id.profileno);


        newNumber = number.getEditText().getText().toString();
        number.getEditText().setText(newNumber);

        newName = name.getEditText().getText().toString();
        name.getEditText().setText(newName);

        newEmail = email.getEditText().getText().toString();
        email.getEditText().setText(newEmail);

        if (email.getEditText().getText().toString().length() < 6)

        {
            Toast.makeText(getContext(), "Password should have minimum 5 letter", Toast.LENGTH_SHORT).show();

            return;
        }

        else

        Rootref.child(currentUserId).child("PhoneNumber").setValue(String.valueOf(newNumber)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

//                    Toast.makeText(getContext(), "Phone Number Updated \n ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        Rootref.child(currentUserId).child("Name").setValue(String.valueOf(newName)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

//                    Toast.makeText(getContext(), "Name Updated \n", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


//        Rootref.child(currentUserId).child("Password").setValue(String.valueOf(newEmail)).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful()) {
//
//
//
//
//
//
//
//                }
//            }
//        });
//
        Rootref.child(currentUserId).child("Password").setValue(String.valueOf(newEmail));

        Rootref.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserPassword  = dataSnapshot.child("Password").getValue().toString();
                UserEmail  = dataSnapshot.child("Email").getValue().toString();

                newPassword = email.getEditText().getText().toString();
                email.getEditText().setText(UserPassword);


if (newPassword.length() <6)
{
    Toast.makeText(getContext(), "Password should have minimum 5 letter", Toast.LENGTH_SHORT).show();
    return;
}

                credential = EmailAuthProvider
                        .getCredential(UserEmail, UserPassword);

                user.reauthenticate(credential)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                Rootref.child(currentUserId).child("Password").setValue(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {

                                                            email.getEditText().setText(newPassword);
                                                            Toast.makeText(getContext(), "Password Updated", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });




                                            } else {
                                                Toast.makeText(getContext(), "Error password not updated", Toast.LENGTH_SHORT).show();


                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getContext(),  "Error auth failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {

            Uri imageURI = data.getData();
            CropImage.activity(imageURI).setAspectRatio(1, 1).start(getContext(), ProfileFragment.this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {


                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setTitle("Saving Changes");
                mProgressDialog.setMessage("Wait untill changes are saved");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();


                Uri resultUri = result.getUri();


                StorageReference filepath = imgStorageReference.child("Profile_images").child(currentUserId + ".jpg");

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {


                            imgStorageReference.child("Profile_images").child(currentUserId + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String downloadUrl = uri.toString();

                                    Rootref.child(currentUserId).child("imageURL").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                mProgressDialog.dismiss();
                                                Toast.makeText(getContext(), " Image Updated", Toast.LENGTH_SHORT).show();

                                                Toast.makeText(getContext(), "" + number.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                                                onResume();
                                            }


                                        }
                                    });

                                }
                            });
                        }

//                           Toast.makeText(SettingsActivity.this, "Working", Toast.LENGTH_SHORT).show();
//                     }
                        else {

                            Toast.makeText(getContext(), "Not Working", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }

                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }

        }
    }


    @Override
    public void onResume() {
        super.onResume();


        Rootref.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String image = dataSnapshot.child("imageURL").getValue().toString();

                if (!image.equals("")) {
                    Picasso.get().load(image).placeholder(R.drawable.profile_avatar_small).into(profilePic);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}





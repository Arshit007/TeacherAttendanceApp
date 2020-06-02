package com.example.arshit.teacherattendanceapp.Assignment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.teacherattendanceapp.R;
import com.example.arshit.teacherattendanceapp.SideNavigation.HomeMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AddAssignmentActivity extends AppCompatActivity {

    EditText editTextAssign,dateClick;
    Spinner spinSpec,spinSem,spinCourse;
    Button btnSubmit;
    String spinSpecItem,spinSemItem,spinCourseNameItem,assignmentValue,DateOfSubmit,currentUserId;
    DatabaseReference databaseReference,Rootref,RooRef;
    private int mYear, mMonth, mDay, mHour, mMinute;
String DeptName,DeptField,TeacherName;
    FirebaseUser currentFirebaseUser;
    List<String> semlist,specList,courseNameList;
    HashMap<String,String> hashMap;
    final static int PICK_PDF_CODE = 2342;
    StorageReference imgStorageReference;
    private ProgressDialog progressBar;

    ArrayAdapter<String> SpecAdpater;
    Uri uri1;
    Button btnselect;
    TextView txtSelect,txtSpec,txtCourse,txtSem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        intialise();
    }

    private void intialise() {



        userdetail();

        editTextAssign = findViewById(R.id.edit_txt_assign);

        editTextAssign.setVisibility(View.GONE);
    txtSelect = findViewById(R.id.txt_file_select);
txtSem = findViewById(R.id.txt_sem);
    txtSpec = findViewById(R.id.txt_spec);
    txtCourse = findViewById(R.id.txt_course_name);


         txtSpec.setVisibility(View.GONE);
        txtCourse.setVisibility(View.GONE);
        txtSem.setVisibility(View.GONE);

        spinSpec = findViewById(R.id.spinner_spec_asign);
        spinCourse = findViewById(R.id.spinner_course_name_asign);
        spinSem = findViewById(R.id.spinner_sem_asign);

        btnSubmit = findViewById(R.id.btn_submit_assignment);
btnselect = findViewById(R.id.btn_select_assignment);


btnSubmit.setVisibility(View.GONE);

        spinSem.setVisibility(View.GONE);
        spinCourse.setVisibility(View.GONE);


        dateClick = findViewById(R.id.edit_txt_date_assign);

        dateClick.setVisibility(View.GONE);

        spinSpec.setVisibility(View.GONE);


        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectPdf();

            }
        });



        imgStorageReference =    FirebaseStorage.getInstance().getReference();


        btnSubmit.setVisibility(View.GONE);


specList = new ArrayList<>();
semlist = new ArrayList<>();
courseNameList = new ArrayList<>();
        RooRef = FirebaseDatabase.getInstance().getReference();
        hashMap = new HashMap<>();
        selectDate();
        submit();

    }

    private void selectPdf() {
        choosePdf();
    }

    private void selectDate() {

        dateClick.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dateClick.setText(current);
                    dateClick.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

  private  void   uploadUri(Uri uri1){

    final String uniqueID = UUID.randomUUID().toString();
            progressBar = new ProgressDialog(AddAssignmentActivity.this);
            progressBar.setTitle("Saving Changes");
            progressBar.setMessage("Wait untill changes are saved");
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();



            StorageReference filepath = imgStorageReference.child("Pdf").child(uniqueID+".3gp");

            filepath.putFile(uri1)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                    if (task.isSuccessful()) {
                        imgStorageReference.child("Pdf").child(uniqueID+".3gp").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @SuppressLint("ResourceType")
                            @Override
                            public void onSuccess(Uri uri) {

                                String d = uri.toString();


                                assignmentValue = editTextAssign.getText().toString();


                                hashMap.put("AssignNo",assignmentValue);
                                hashMap.put("SubmitDate",dateClick.getText().toString());

                                hashMap.put("SpecName", AddAssignmentActivity.this.spinSpecItem);

                                hashMap.put("SemName",spinSemItem);
                                hashMap.put("CourseName",spinCourseNameItem);
                                hashMap.put("TeacherId",currentUserId);
                                hashMap.put("DeptName",DeptName);
                                hashMap.put("Status","Not Submitted");
                                hashMap.put("TeacherName",TeacherName);
                                hashMap.put("DeptField",DeptField);
                                hashMap.put("PdfUrl",d);
                                hashMap.put("Grade","");

                                DatabaseReference StudentRef = FirebaseDatabase.getInstance().getReference();

                                StudentRef.child("CoursesOpted")
                                        .child(DeptName).child(DeptField)
                                        .child(spinSpecItem)
                                        .child(spinSemItem)
                                        .child(spinCourseNameItem).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                                            DatabaseReference StudentRef1 = FirebaseDatabase.getInstance().getReference();

                                            DatabaseReference StudentRe1 = FirebaseDatabase.getInstance().getReference();

                                            StudentRef1.child("IndividualAssignment")
                                                    .child(DeptName).child(DeptField)
                                                    .child(spinSpecItem)
                                                    .child(dataSnapshot1.getKey())
                                                    .child(spinSemItem)
                                                    .child(spinCourseNameItem)
                                                    .child(assignmentValue)
                                                    .setValue(hashMap);

                                            StudentRe1.child("TeacherManageAssignment")
                                                    .child(DeptName).child(DeptField)
                                                    .child(currentUserId)
                                                    .child(spinSpecItem)
                                                    .child(dataSnapshot1.getKey())
                                                    .child(spinSemItem)
                                                    .child(spinCourseNameItem)
                                                    .child(assignmentValue)
                                                    .setValue(hashMap);


                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                                RooRef.child("Assignment")
                                        .child(DeptName).child(DeptField)
                                        .child(spinSpecItem)
                                        .child(spinSemItem)
                                        .child(spinCourseNameItem)
                                        .child(assignmentValue).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){

                                            progressBar.dismiss();

                                            Intent intent = new Intent(getApplicationContext(),HomeMainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            progressBar.dismiss();

                                        }
                                    }
                                });



                            }
                        });


                    }
                }
            });

    }


    private void submit() {




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectDate();
                assignmentValue = editTextAssign.getText().toString();


                if (TextUtils.isEmpty(assignmentValue)  || TextUtils.isEmpty(dateClick.getText().toString()) || dateClick.getText().toString().equals("DD/MM/YYYY") ){

                    Toast.makeText(AddAssignmentActivity.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (uri1!=null){
                    uploadUri(uri1);}
                else{
                    Toast.makeText(AddAssignmentActivity.this, "Please Select File to be uploaded", Toast.LENGTH_SHORT).show();
                 return;
                }





            }
        });

    }

    private void choosePdf() {

        Intent intent = new Intent();
        intent.setType("application/pdf");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }

    private void userdetail() {


        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        currentUserId = currentFirebaseUser.getUid();


        Rootref = FirebaseDatabase.getInstance().getReference().child("Teachers").child(currentUserId);


        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {


DeptName = dataSnapshot1.child("DeptName").getValue().toString();
DeptField = dataSnapshot1.child("DeptField").getValue().toString();
           TeacherName =     dataSnapshot1.child("Name").getValue().toString();

                showDetail(dataSnapshot1.child("DeptName").getValue().toString(),dataSnapshot1.child("DeptField").getValue().toString(),dataSnapshot1.child("DeptSpec").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showDetail(final String deptName, final String deptField, final String deptSpec) {

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        currentUserId = currentFirebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("TeacherClass").child(deptName).child(deptField)
                .child(currentUserId).child("Course").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                specList.clear();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    specList.add(dataSnapshot1.getKey());
                }
                SpecAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,specList);
                SpecAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinSpec.setAdapter(SpecAdpater);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});




        spinSpec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

              spinSpecItem = adapterView.getItemAtPosition(i).toString();



                databaseReference.child("TeacherClass").child(deptName).child(deptField)
                        .child(currentUserId).child("Course").child(spinSpecItem).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        semlist.clear();
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){



                            semlist.add(dataSnapshot1.getKey());

                        }



                        ArrayAdapter<String> SemAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,semlist);
                        SemAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinSem.setAdapter(SemAdpater);





                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spinSemItem = adapterView.getItemAtPosition(i).toString();

                databaseReference.child("TeacherClass").child(deptName).child(deptField)
                        .child(currentUserId).child("Course").child(spinSpecItem).child(spinSemItem).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        courseNameList.clear();
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){



                            courseNameList.add(dataSnapshot1.getKey());

                        }



                        ArrayAdapter<String> SemAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,courseNameList);
                        SemAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinCourse.setAdapter(SemAdpater);





                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spinCourseNameItem = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {


             uri1 = data.getData();

             txtSelect.setText("File Select is \n"+data.getData());

            txtSpec.setVisibility(View.VISIBLE);
            spinSpec.setVisibility(View.VISIBLE);

            txtSem.setVisibility(View.VISIBLE);
            spinSem.setVisibility(View.VISIBLE);

            txtCourse.setVisibility(View.VISIBLE);
            spinCourse.setVisibility(View.VISIBLE);

            editTextAssign.setVisibility(View.VISIBLE);
            dateClick.setVisibility(View.VISIBLE);

            btnSubmit.setVisibility(View.VISIBLE);


//            final String uniqueID = UUID.randomUUID().toString();
//            progressBar = new ProgressDialog(AddAssignmentActivity.this);
//            progressBar.setTitle("Saving Changes");
//            progressBar.setMessage("Wait untill changes are saved");
//            progressBar.setCanceledOnTouchOutside(false);
//            progressBar.show();
//
//
//
//            StorageReference filepath = imgStorageReference.child("Pdf").child(uniqueID+".3gp");
//
//            filepath.putFile(uri1)
//                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//
//                    if (task.isSuccessful()) {
//                        imgStorageReference.child("Pdf").child(uniqueID+".3gp").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @SuppressLint("ResourceType")
//                            @Override
//                            public void onSuccess(Uri uri) {
//
//                                String d = uri.toString();
//
//
//                                assignmentValue = editTextAssign.getText().toString();
//
//
//                                Toast.makeText(getApplicationContext(), ""+spinCourseNameItem, Toast.LENGTH_SHORT).show();
//                                hashMap.put("AssignNo",assignmentValue);
//                                hashMap.put("SubmitDate",dateClick.getText().toString());
//
//                                hashMap.put("SpecName",spinSpecItem);
//
////                                hashMap.put("SemName",spinSemItem);
////                                hashMap.put("CourseName",spinCourseNameItem);
//
//                                hashMap.put("DeptName",DeptName);
//                                hashMap.put("TeacherName",TeacherName);
//                                hashMap.put("DeptField",DeptField);
//                                hashMap.put("PdfUrl",d);
//
//
//                                RooRef.child("Assignment").child(DeptName).child(DeptField).child(spinSpecItem)
////                                        .child(spinSemItem).child(spinCourseNameItem)
//                                        .child(assignmentValue).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        if (task.isSuccessful()){
//
//                                            progressBar.dismiss();
//
//                                            Intent intent = new Intent(getApplicationContext(),HomeMainActivity.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);
//                                            progressBar.dismiss();
//
//                                        }
//                                    }
//                                });
//
//
//
//                            }
//                        });
//
//
//                    }
//                }
//            });



        }
    }




}

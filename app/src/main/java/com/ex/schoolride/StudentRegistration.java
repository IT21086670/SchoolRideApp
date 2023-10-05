package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentRegistration extends AppCompatActivity {

    


    private String sName, sSchool, sAddress, sContactNo, sAge;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    String sid = mAuth.getCurrentUser().getUid();


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        getSupportActionBar().setTitle("School Ride");




        //getting instance from firestore

        db = FirebaseFirestore.getInstance();

        edSname = findViewById(R.id.iduvName);
        edsSchool = findViewById(R.id.iduvNic);
        edsAddress = findViewById(R.id.iduvvehicle);
        edsContactNo = findViewById(R.id.iduvLicenseNo);
        edsAge = findViewById(R.id.iduvVNo);
        StdBack = findViewById(R.id.idStdback);
        submitStdBtn = findViewById(R.id.idSbutton);


        submitStdBtn.setOnClickListener(view ->{
            startActivity(new Intent(StudentRegistration.this, StudentHome.class));
        });

        StdBack.setOnClickListener(view ->{
            startActivity(new Intent(StudentRegistration.this, StudentHome.class));
        });

        submitStdBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //getting data from edit text fields
                sName = edSname.getText().toString();
                sSchool = edsSchool.getText().toString();
                sAddress = edsAddress.getText().toString();
                sContactNo = edsContactNo.getText().toString();
                sAge = edsAge.getText().toString();


                //validation

                if (TextUtils.isEmpty(sName)) {
                    edSname.setError("Enter a name ");
                } else if (TextUtils.isEmpty(sSchool)) {
                    edsSchool.setError("school name is required");
                } else if (TextUtils.isEmpty(sContactNo)) {
                    edsContactNo.setError("contact no is required");
                } else if (TextUtils.isEmpty(sAge)) {
                    edsAge.setError("Username is required");
                }  else {
                    //calling mehod to add data to fireabse
                    addDataToFirestore(sName, sSchool,sAddress  ,sContactNo, sAge );
                }

            }
        });
    }
    private void addDataToFirestore(String sName, String sSchool,  String sAddress, String sContactNo , String  sAge)
    {
        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbStudent = db.collection("Students");

        // adding our data to our courses object class.
        Student student = new Student(sName, sSchool, sAddress, sContactNo, sAge);

        // below method is use to add data to Firebase Firestore.
        dbStudent.document(sid).set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(StudentRegistration.this, "Your details has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StudentRegistration.this, StudentProfile.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(StudentRegistration.this, "Fail to add \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
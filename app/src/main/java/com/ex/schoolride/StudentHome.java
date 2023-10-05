package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class StudentHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        
        getSupportActionBar().setTitle("School Ride");

        FSet.setOnClickListener(View ->{
            startActivity(new Intent(StudentHome.this,Settings.class));
        });

       Button Calc=findViewById(R.id.CalBtn);

        Calc.setOnClickListener(View ->{
            startActivity(new Intent(getApplicationContext(),DistanceCalculation.class));
        });
        PayBtn.setOnClickListener(View ->{
            startActivity(new Intent(getApplicationContext(),PaymentAdd.class));
        });

        Sprofile.setOnClickListener(View ->{
            startActivity(new Intent(getApplicationContext(), StudentProfile.class));
        });

        Sregi.setOnClickListener(View ->{
            startActivity(new Intent(StudentHome.this, StudentRegistration.class));
        });


        logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}
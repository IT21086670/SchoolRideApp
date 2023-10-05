package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("School Ride");

                Button logout = findViewById(R.id.slogoutbtn);
                Button Terms = findViewById(R.id.TermsBtn);
                Button USetting=findViewById(R.id.usettingBtn);

                USetting.setOnClickListener(View ->{
                    startActivity(new Intent(getApplicationContext(), UserProfile.class));
                });

                Terms.setOnClickListener(View ->{
                    startActivity(new Intent(getApplicationContext(),termsAndCondition.class));
                });
        logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}
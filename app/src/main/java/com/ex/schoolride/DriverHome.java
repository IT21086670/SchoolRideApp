package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DriverHome extends AppCompatActivity {
    Button logout,Vehic,Dregister, DprofileBtn , Dsettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        getSupportActionBar().setTitle("School Ride");


        logout = findViewById(R.id.Dlogoutbtn);
         Vehic = findViewById(R.id.DvehicBtn);
         Dregister = findViewById(R.id.DregisBtn);
        DprofileBtn =findViewById(R.id.DBtnProfile);
        Dsettings=findViewById(R.id.idDsetting);
        Button Dsettings = findViewById(R.id.idDsetting);
        Button DinEx=findViewById(R.id.iebtn);

        DinEx.setOnClickListener(View ->{
            startActivity(new Intent(getApplicationContext(),incomeAndExpense.class));
        });

        Dsettings.setOnClickListener(View ->{
            startActivity(new Intent(getApplicationContext(), Settings.class));
        });


        Dsettings.setOnClickListener(view->{
            startActivity(new Intent(DriverHome.this,Settings.class));
        });

        DprofileBtn.setOnClickListener(view ->{
            startActivity(new Intent(DriverHome.this,DriverProfile.class));
        });


        logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        Dregister.setOnClickListener(view ->{
            startActivity(new Intent(DriverHome.this, DRegistration.class));
        });
        Vehic.setOnClickListener(view ->{
            startActivity(new Intent(DriverHome.this,vehicleHome.class));
        });
    }
}
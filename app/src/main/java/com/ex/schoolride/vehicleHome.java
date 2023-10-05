package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class vehicleHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_home);
        getSupportActionBar().setTitle("School Ride");


        Button Qrcode=findViewById(R.id.CalBtn);
        Button VRegi = findViewById(R.id.PayBtnP);
        Button VProfileBtn=findViewById(R.id.idvProfile);
        Button BBackHomeV = findViewById(R.id.idPSettingbtn);

        BBackHomeV.setOnClickListener(view ->{
            startActivity(new Intent(vehicleHome.this,DriverHome.class));
        });
        VProfileBtn.setOnClickListener(view ->{
            startActivity(new Intent(vehicleHome.this,VehicleProfile.class));
        });

        VRegi.setOnClickListener(view ->{
            startActivity(new Intent(vehicleHome.this,VehicleRegistration.class));
        });

        Qrcode.setOnClickListener(view ->{
            startActivity(new Intent(vehicleHome.this,VehicleQr.class));

        });


    }
}
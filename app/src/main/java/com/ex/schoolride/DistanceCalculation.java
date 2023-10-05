package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DistanceCalculation extends AppCompatActivity {
    EditText mdistance, km;
    Button Add , Home;
    TextView PResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_calculation);

        mdistance=findViewById(R.id.idsDistance);
        km = findViewById(R.id.idKm);
        PResult = findViewById(R.id.idresult);
        Add=findViewById(R.id.Padd);
        Home = findViewById(R.id.idHome);
        getSupportActionBar().setTitle("School Ride");


        Home.setOnClickListener(View ->{
            startActivity(new Intent(getApplicationContext(), StudentHome.class));
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mdistance.getText().toString().length() == 0) {
                    mdistance.setText("0");
                }

                if (km.getText().toString().length() == 0) {
                    km.setText("50");
                }
                int num1 = Integer.parseInt(mdistance.getText().toString());
                int num2 = Integer.parseInt(km.getText().toString());

                int sum =num1*num2;

                PResult.setText(String.valueOf(sum));

            }
        });


    }
}
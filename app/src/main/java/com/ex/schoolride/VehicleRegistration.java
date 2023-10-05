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

public class VehicleRegistration extends AppCompatActivity {
    private EditText edvname, edvType, etvInsuranceD, etvLicenseNo, etVehicleNo;

    private Button submitvehicleBtn;
    TextView vehiregiBack;


    private  String name, type, insuranceD, vLicenseNo, vehicleNo ;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    String uid = mAuth.getCurrentUser().getUid();


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration);

        getSupportActionBar().setTitle("School Ride");


        //getting instance from firestore

        db = FirebaseFirestore.getInstance();

        edvname = findViewById(R.id.vName);
        edvType = findViewById(R.id.vtype);
        etvInsuranceD = findViewById(R.id.etvInsuranceD);
        etvLicenseNo = findViewById(R.id.vlicense);
        etVehicleNo = findViewById(R.id.etVehicleNo);
        submitvehicleBtn = findViewById(R.id.vsubbutton);
        vehiregiBack = findViewById(R.id.vregiback);

        vehiregiBack.setOnClickListener(view ->{
            startActivity(new Intent(VehicleRegistration.this, vehicleHome.class));
        });

        submitvehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting data from edit text fields
                name = edvname.getText().toString();
                type = edvType.getText().toString();
                insuranceD = etvInsuranceD.getText().toString();
                vLicenseNo = etvLicenseNo.getText().toString();
                vehicleNo = etVehicleNo.getText().toString();

                //validation

                if (TextUtils.isEmpty(name)) {
                    edvname.setError("Enter a name ");
                } else if (TextUtils.isEmpty(type)) {
                    edvType.setError("type is required");
                } else if (TextUtils.isEmpty(insuranceD)) {
                    etvInsuranceD.setError("insurance date is required");
                } else if (TextUtils.isEmpty(vLicenseNo)) {
                    etvLicenseNo.setError("License  is required");
                } else if (TextUtils.isEmpty(vehicleNo)) {
                    etVehicleNo.setError("vehicle no is required");
                } else {
                    //calling mehod to add data to fireabse
                    addDataToFirestore(name, type, insuranceD, vLicenseNo, vehicleNo);
                }

            }
        });
    }
    private void addDataToFirestore( String name,String type,String insuranceD,String vLicenseNo,String vehicleNo )
    {
        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbVehicle = db.collection("Vehicles");

        // adding our data to our courses object class.
        Vehicle vehicle = new Vehicle(name, type, insuranceD, vLicenseNo, vehicleNo);

        // below method is use to add data to Firebase Firestore.
        dbVehicle.document(uid).set(vehicle).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(VehicleRegistration.this, "Your details has been added ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(VehicleRegistration.this, VehicleProfile.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(VehicleRegistration.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class UpdateVehicleProfile extends AppCompatActivity {
    private EditText edvname, edvType, etvInsuranceD, etvLicenseNo, etVehicleNo;

    private Button vSaveBTN , vShowBtn;
    private  String name, type, insuranceD, vLicenseNo, vehicleNo ;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    String uid = mAuth.getCurrentUser().getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vehicle_profile);

        getSupportActionBar().setTitle("School Ride");


        edvname = findViewById(R.id.iduvName);
        edvType = findViewById(R.id.iduvNic);
        etvInsuranceD = findViewById(R.id.iduvvehicle);
        etvLicenseNo = findViewById(R.id.iduvLicenseNo);
        etVehicleNo = findViewById(R.id.iduvVNo);
        vSaveBTN = findViewById(R.id.iduvbutton);
        vShowBtn = findViewById(R.id.iduvShow);


        FirebaseFirestore db  = FirebaseFirestore.getInstance();


        db.collection("Vehicles").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null){
                    Vehicle vehicle =new Vehicle (
                            value.getString("name"),
                            value.getString("type"),
                            value.getString("insuranceD"),
                            value.getString("vLicenseNo"),
                            value.getString("vehicleNo")
                    );
                    edvname.setText(vehicle.getName());
                    edvType.setText(vehicle.getType());
                    etvInsuranceD.setText(vehicle.getinsuranceD());
                    etvLicenseNo.setText(vehicle.getvLicenseNo());
                    etVehicleNo.setText(vehicle.getvehicleNo());


                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            vSaveBTN.setText("Update");
            name = bundle.getString("name");
            type = bundle.getString("type");
            insuranceD = bundle.getString("insuranceD");
            vLicenseNo = bundle.getString("vLicenseNo");
            vehicleNo = bundle.getString("vehicleNo");

            edvname.setText(name);
            edvType.setText(type);
            etvInsuranceD.setText(insuranceD);
            etvLicenseNo.setText(vLicenseNo);
            etVehicleNo.setText(vehicleNo);

        }else{
            vSaveBTN.setText("Save");
        }

        vShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateVehicleProfile.this , VehicleProfile.class));
            }
        });


        vSaveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edvname.getText().toString();
                String type = edvType.getText().toString();
                String insuranceD = etvInsuranceD.getText().toString();
                String vLicenseNo = etvLicenseNo.getText().toString();
                String vehicleNo = etVehicleNo.getText().toString();


                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uid;
                    updateToFireStore(uid ,name, vehicleNo, type, vLicenseNo, insuranceD);
                }else{
                    String id = uid;
                    saveToFireStore(uid ,name, vehicleNo, type, vLicenseNo, insuranceD);
                }

            }
        });
    }

    private void updateToFireStore(String uid, String name, String type, String insuranceD, String vLicenseNo, String vehicleNo){

        db.collection("Vehicles").document(uid).update("name" , name , "type" , type,"insuranceD",insuranceD,"vLicenseNo",vLicenseNo,"vehicleNo",vehicleNo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateVehicleProfile.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateVehicleProfile.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateVehicleProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void saveToFireStore(String uid, String name, String type, String insuranceD, String vLicenseNo, String vehicleNo){

        if (!name.isEmpty() && !type.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("name" , name);
            map.put("insuranceD" , insuranceD);
            map.put("vehicleNo" , vehicleNo);
            map.put("vLicenseNo" , vLicenseNo);
            map.put("type" , type);


            db.collection("Vehicles").document(uid).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UpdateVehicleProfile.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateVehicleProfile.this, "Failed !!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}
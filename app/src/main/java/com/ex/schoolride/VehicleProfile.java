package com.ex.schoolride;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class VehicleProfile extends AppCompatActivity {

    TextView edvname, edvType, etvInsuranceD, etvLicenseNo, etVehicleNo;
    private Button upvehicleBtn;
    TextView VehiHomeB;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String uid;
    private FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);
        getSupportActionBar().setTitle("School Ride");


        edvname = findViewById(R.id.idvname);
        edvType = findViewById(R.id.idvtype);
        etvInsuranceD = findViewById(R.id.idvinsurance);
        etvLicenseNo = findViewById(R.id.idvlicense);
        etVehicleNo = findViewById(R.id.idvno);
        upvehicleBtn = findViewById(R.id.idvrbutton);
        VehiHomeB = findViewById(R.id.vbackBtn);
        Button VdeleteBtn  =findViewById(R.id.idVDeleteBtn);




        upvehicleBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), UpdateVehicleProfile.class));
        });

        VehiHomeB.setOnClickListener(view ->{
            startActivity(new Intent(VehicleProfile.this, vehicleHome.class));
        });
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Vehicles").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                edvname.setText(documentSnapshot.getString("name"));
                edvType.setText(documentSnapshot.getString("type"));
                etvInsuranceD.setText(documentSnapshot.getString("insuranceD"));
                etvLicenseNo.setText(documentSnapshot.getString("vLicenseNo"));
                etVehicleNo.setText(documentSnapshot.getString("vehicleNo"));


            }



        });

        VdeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("Vehicles").document(uid)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Account details successfully deleted!");
                                Toast.makeText(VehicleProfile.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), vehicleHome.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting ", e);
                            }
                        });
            }
        });



    }
}

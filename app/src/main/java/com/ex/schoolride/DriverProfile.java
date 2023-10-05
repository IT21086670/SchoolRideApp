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

public class DriverProfile extends AppCompatActivity {
     TextView edDname, edNic, etDvehicle, etDContactNo, etAge;

    private Button updateDriverBtn;
    TextView DriverHomeB;



    FirebaseAuth mAuth;

    FirebaseFirestore fStore;
    String uid;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        getSupportActionBar().setTitle("School Ride");


        edDname = findViewById(R.id.idvname);
        edNic = findViewById(R.id.idvtype);
        etDvehicle = findViewById(R.id.idvinsurance);
        etDContactNo = findViewById(R.id.idvlicense);
        etAge = findViewById(R.id.idvno);
        updateDriverBtn = findViewById(R.id.idvrbutton);
        DriverHomeB = findViewById(R.id.vbackBtn);
        Button deleteBtn = findViewById(R.id.iddeleteBtn);



        

        updateDriverBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), UpdateDriver.class));
        });

        DriverHomeB.setOnClickListener(view -> {
            startActivity(new Intent(DriverProfile.this, DriverHome.class));
        });
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Drivers").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                edDname.setText(documentSnapshot.getString("dname"));
                edNic.setText(documentSnapshot.getString("dnic"));
                etDvehicle.setText(documentSnapshot.getString("dvehicleNo"));
                etDContactNo.setText(documentSnapshot.getString("dcontactNo"));
                etAge.setText(documentSnapshot.getString("dage"));


            }


        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("Drivers").document(uid)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Account details successfully deleted!");
                                Toast.makeText(DriverProfile.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),DriverHome.class));
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

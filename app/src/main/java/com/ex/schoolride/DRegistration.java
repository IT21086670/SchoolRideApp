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

public class DRegistration extends AppCompatActivity {

    private EditText edDname, edNic, etDvehicle, etDContactNo, etAge;

    private Button submitDriverBtn;
    TextView DriverBack;


    private  String DName, DNIC,DVehicle, DContactNo, DAge ;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    String uid = mAuth.getCurrentUser().getUid();


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dregistration);

        getSupportActionBar().setTitle("School Ride");


        //getting instance from firestore

        db = FirebaseFirestore.getInstance();

        edDname = findViewById(R.id.idDName);
        edNic = findViewById(R.id.idNic);
        etDvehicle = findViewById(R.id.idDvehicle);
        etDContactNo = findViewById(R.id.idDContactNo);
        etAge = findViewById(R.id.idDAge);
        submitDriverBtn = findViewById(R.id.idDbutton);
        DriverBack = findViewById(R.id.DriverbackBtn);

        DriverBack.setOnClickListener(view ->{
            startActivity(new Intent(DRegistration.this, DriverHome.class));
        });

        submitDriverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting data from edit text fields
                DName = edDname.getText().toString();
                DNIC = edNic.getText().toString();
                DVehicle = etDvehicle.getText().toString();
                DContactNo = etDContactNo.getText().toString();
                DAge = etAge.getText().toString();

                //validation

                if (TextUtils.isEmpty(DName)) {
                    edDname.setError("Enter a name ");
                } else if (TextUtils.isEmpty(DNIC)) {
                    edNic.setError("Username is required");
                } else if (TextUtils.isEmpty(DVehicle)) {
                    etDvehicle.setError("Username is required");
                } else if (TextUtils.isEmpty(DContactNo)) {
                    etDContactNo.setError("Username is required");
                } else if (TextUtils.isEmpty(DAge)) {
                    etAge.setError("Username is required");
                } else {
                    //calling mehod to add data to fireabse
                    addDataToFirestore(DName, DNIC, DVehicle, DContactNo, DAge);
                }

            }
        });
    }
        private void addDataToFirestore(String DName, String DNIC,String DVehicle,String DContactNo,String DAge )
        {
    // creating a collection reference
    // for our Firebase Firetore database.
    CollectionReference dbDrivers = db.collection("Drivers");

    // adding our data to our courses object class.
    Driver driver = new Driver(DName, DNIC, DVehicle, DContactNo, DAge);

    // below method is use to add data to Firebase Firestore.
        dbDrivers.document(uid).set(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DRegistration.this, "Your details has been added ", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(DRegistration.this, DriverProfile.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                    // displaying a toast message when data addition is failed.
                    Toast.makeText(DRegistration.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
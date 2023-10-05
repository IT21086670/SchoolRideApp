package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentAdd extends AppCompatActivity {

    private EditText pDate, pfees;

    private Button submitpayment,ShowP;

    private String date;
    private String fee;


    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    String uid = mAuth.getCurrentUser().getUid();


    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_add);

        getSupportActionBar().setTitle("School Ride");


        pDate= findViewById(R.id.idDatep);
        pfees=findViewById(R.id.idpFees);
        ShowP=findViewById(R.id.showPayBtn);

        ShowP.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(),StudentProfile.class));
        });
        submitpayment=findViewById(R.id.subPayBtn);

        submitpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting data from edit text fields
                date = pDate.getText().toString();
                fee = pfees.getText().toString();

                //validation

                if (TextUtils.isEmpty(date)) {
                    pDate.setError("Enter a date ");
                } else {
                    //calling mehod to add data to fireabse
                    addDataToFirestore(date, fee);
                }

            }
        });
    }
    private void addDataToFirestore(String date, String fee )
    {
        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbPayment = db.collection("Payment");

        // adding our data to our courses object class.
        Payment pay = new Payment(date,fee);

        // below method is use to add data to Firebase Firestore.
        dbPayment.document(uid).set(pay).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(PaymentAdd.this, "Your details has been added ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaymentAdd.this, PaymentAdd.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(PaymentAdd.this, "Fail to add fess \n" + e, Toast.LENGTH_SHORT).show();
            }
        });



    }
}
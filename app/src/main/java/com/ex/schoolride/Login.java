package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText etLoginEmail;
    EditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;
    boolean valid = true;


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("School Ride");

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


//        btnLogin.setOnClickListener(view -> {
//            loginUser();
//        });
        tvRegisterHere.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
        });

        btnLogin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(etLoginEmail);
                checkField(etLoginPassword);
                if (valid) {
                    fAuth.signInWithEmailAndPassword(etLoginEmail.getText().toString(), etLoginPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "Logged success", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            }
        }));

    }
    public void checkUserAccessLevel(String uid){
//System.out.println("UID is "+uid);
       // startActivity(new Intent(getApplicationContext(),StudentHome.class));
        DocumentReference df =fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d(TAG, "Onsuccess"+ documentSnapshot.getData());

                //IDENTIFY THE USER ACCESS

                if(documentSnapshot.getString("isDriver") !=null){
                    //user admin
                    startActivity(new Intent(getApplicationContext(),DriverHome.class));
                    finish();

                }

                if(documentSnapshot.getDouble("isStudent") !=null){
                    startActivity(new Intent (getApplicationContext(),StudentHome.class));
                    finish();

                }
            }
        });
    }

    public boolean checkField(EditText textField){

        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
    protected   void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=   null){
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.getString("isDriver") !=null){
                        startActivity(new Intent(getApplicationContext(),DriverHome.class));
                        finish();

                    }
                    if(documentSnapshot.getDouble("isStudent") !=null){
                        startActivity(new Intent(getApplicationContext(),StudentHome.class));
                        finish();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),Login.class));

                }
            });
        }
    }
}
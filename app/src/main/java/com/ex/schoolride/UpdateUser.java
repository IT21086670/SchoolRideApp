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

public class UpdateUser extends AppCompatActivity {

    private EditText efullname, ephoneNumber, eUseremail;

    private Button userSaveBtn, userShowBtn;


    private String FullName, PhoneNumber, UserEmail;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    String uid = mAuth.getCurrentUser().getUid();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        getSupportActionBar().setTitle("School Ride");


        efullname = findViewById(R.id.idFullname);
        ephoneNumber = findViewById(R.id.idPhoneNo);
        eUseremail = findViewById(R.id.ideUserEmail);

        userSaveBtn = findViewById(R.id.idusersavebutton);
        userShowBtn = findViewById(R.id.iduerShow);


        FirebaseFirestore db  = FirebaseFirestore.getInstance();


        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null){
                    Users user =new Users (
                            value.getString("FullName"),
                            value.getString("PhoneNumber"),
                            value.getString("UserEmail")
                    );
                    efullname.setText(user.getFullName());
                    ephoneNumber.setText(user.getPhoneNumber());
                    eUseremail.setText(user.getUserEmails());



                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            userSaveBtn.setText("Update");
            FullName = bundle.getString("FullName");
            PhoneNumber  = bundle.getString("PhoneNumber");
            UserEmail = bundle.getString("UserEmail");


            efullname.setText(FullName);
            ephoneNumber.setText(PhoneNumber);
            eUseremail.setText(UserEmail);


        }else{
            userSaveBtn.setText("Save");
        }

        userShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateUser.this , UserProfile.class));
            }
        });


        userSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FullName = efullname.getText().toString();
                String PhoneNumber = ephoneNumber.getText().toString();
                String UserEmail = eUseremail.getText().toString();



                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uid;
                    updateToFireStore(uid ,FullName, PhoneNumber,UserEmail);
                }else{
                    String id = uid;
                    saveToFireStore(uid ,FullName, PhoneNumber,UserEmail);
                }

            }
        });
    }

    private void updateToFireStore(String uid, String FullName,String  PhoneNumber,String UserEmail){

        db.collection("Vehicles").document(uid).update("FullName" , FullName , "PhoneNumber" , PhoneNumber,"UserEmail",UserEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateUser.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateUser.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void saveToFireStore(String uid,String FullName,String  PhoneNumber,String UserEmail){

        if (!FullName.isEmpty() && !PhoneNumber.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("FullName" , FullName);
            map.put("PhoneNumber" , PhoneNumber);
            map.put("UserEmail" , UserEmail);



            db.collection("Users").document(uid).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UpdateUser.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateUser.this, "Failed !!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}
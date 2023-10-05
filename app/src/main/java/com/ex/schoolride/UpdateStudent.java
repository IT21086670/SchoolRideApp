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

public class UpdateStudent extends AppCompatActivity {
    private EditText edSname, edsSchool, edsAddress, edsContactNo, edsAge;

    private Button mSaveBtn, mShowBtn;


    private String sName, sSchool, sAddress, sContactNo, sAge;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    String sid = mAuth.getCurrentUser().getUid();


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        getSupportActionBar().setTitle("School Ride");

        db = FirebaseFirestore.getInstance();

        edSname = findViewById(R.id.iduvName);
        edsSchool = findViewById(R.id.iduvNic);
        edsAddress = findViewById(R.id.iduvvehicle);
        edsContactNo = findViewById(R.id.iduvLicenseNo);
        edsAge = findViewById(R.id.iduvVNo);
        mSaveBtn = findViewById(R.id.iduvbutton);
        mShowBtn = findViewById(R.id.iduvShow);

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("Students").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null){
                    Student student =new Student (
                            value.getString("sName"),
                            value.getString("sSchool"),
                            value.getString("sAddress"),
                            value.getString("sContactNo"),
                            value.getString("sAge")
                    );
                    edSname.setText(student.getsName());
                    edsSchool.setText(student.getsSchool());
                    edsAddress.setText(student.getsAddress());
                    edsContactNo.setText(student.getsContactNo());
                    edsAge.setText(student.getsAge());


                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mSaveBtn.setText("Update");
            sName = bundle.getString("sName");
            sSchool = bundle.getString("sSchool");
            sAddress = bundle.getString("sAddress");
            sContactNo = bundle.getString("sContactNo");
            sAge = bundle.getString("sAge");

            edSname.setText(sName);
            edsSchool.setText(sSchool);
            edsAddress.setText(sAddress);
            edsContactNo.setText(sContactNo);
            edsAge.setText(sAge);

        }else{
            mSaveBtn.setText("Save");
        }

        mShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateStudent.this , StudentProfile.class));
            }
        });


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sName = edSname.getText().toString();
                String sAddress = edsAddress.getText().toString();
                String sAge = edsAge.getText().toString();
                String sContactNo = edsContactNo.getText().toString();
                String sSchool = edsSchool.getText().toString();


                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = sid;
                    updateToFireStore(sid ,sName, sSchool, sAddress, sContactNo, sAge);
                }else{
                    String id = sid;
                    saveToFireStore(sid ,sName, sSchool, sAddress, sContactNo, sAge);
                }

            }
        });
    }

    private void updateToFireStore(String sid, String sName, String sSchool, String sAddress, String sContactNo, String sAge){

        db.collection("Students").document(sid).update("sName" , sName , "sSchool" , sSchool,"sAddress",sAddress,"sContactNo",sContactNo,"sAge",sAge)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateStudent.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateStudent.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void saveToFireStore(String sid, String sName, String sSchool, String sAddress, String sContactNo, String sAge){

        if (!sName.isEmpty() && !sSchool.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("sName" , sName);
            map.put("sAddress" , sAddress);
            map.put("sAge" , sAge);
            map.put("sContactNo" , sContactNo);
            map.put("sSchool" , sSchool);


            db.collection("Students").document(sid).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UpdateStudent.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateStudent.this, "Failed !!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}
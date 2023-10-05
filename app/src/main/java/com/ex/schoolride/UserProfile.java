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

public class UserProfile extends AppCompatActivity {
   private TextView efullname, ephoneNumber, eUseremail;

    private Button updateDriverBtn;
    TextView DriverHomeB;



    FirebaseAuth mAuth;

    FirebaseFirestore fStore;
    String uid;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        efullname = findViewById(R.id.idfullname);
        ephoneNumber = findViewById(R.id.idphoneno);
        eUseremail = findViewById(R.id.iduser);


        Button SdeleteBtn=findViewById(R.id.userDeleteBtn);


        Button userUpdateBtn = findViewById(R.id.idSuserUpdateBtn);
//        SProfileBack = findViewById(R.id.idSProfilebackBtn);
//
//        SProfileBack.setOnClickListener(view -> {
//            startActivity(new Intent(getApplicationContext(), StudentHome.class));
//        });
//
//
        userUpdateBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), UpdateUser.class));
        });

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();



        DocumentReference documentReference=fStore.collection("Users").document(uid);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                efullname.setText(documentSnapshot.getString("FullName"));
                ephoneNumber.setText(documentSnapshot.getString("PhoneNumber"));
                eUseremail.setText(documentSnapshot.getString("UserEmail"));



            }

        });


        SdeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("Users").document(uid)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Account details successfully deleted!");
                                Toast.makeText(UserProfile.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Register.class));
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

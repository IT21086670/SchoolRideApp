package com.ex.schoolride;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class VehicleQr extends AppCompatActivity {

    private ImageView qrCodeIv;
    private EditText dataEdt;
    private Button generateQrBtn;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_qr);

        getSupportActionBar().setTitle("School Ride");

        Button QrBack= findViewById(R.id.QrBackBtn);

        QrBack.setOnClickListener(view ->{
            startActivity(new Intent(VehicleQr.this,vehicleHome.class));
        });

        qrCodeIv = findViewById(R.id.idIVQrcode);
        dataEdt = findViewById(R.id.idEdt);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);


        //initialize onclick listner for button
        generateQrBtn.setOnClickListener(v -> {
            generateQR();

        });
    }
        private void generateQR(){

            String text = dataEdt.getText().toString().trim();
            MultiFormatWriter writer = new MultiFormatWriter();
            try{
                BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE,400, 400);

                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap= encoder.createBitmap(matrix);
                qrCodeIv.setImageBitmap(bitmap);

        }catch(WriterException e){
                e.printStackTrace();
            }


    }
}
package com.ex.schoolride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class incomeAndExpense extends AppCompatActivity {

    EditText income, Expense;
    Button Cal , BHome;
    TextView IEexpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_and_expense);

        income=findViewById(R.id.idIncomeBtn);
        Expense=findViewById(R.id.idExpenseBtn);
        Cal = findViewById(R.id.idCalBtn);
        BHome=findViewById(R.id.idBhomeBtn);
        IEexpense=findViewById(R.id.idIExpense);
        BHome.setOnClickListener(View ->{
            startActivity(new Intent(getApplicationContext(),DriverHome.class));
        });

        Cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num1 = Integer.parseInt(income.getText().toString());
                int num2 = Integer.parseInt(Expense.getText().toString());

                int sum = num1 - num2;

                IEexpense.setText(String.valueOf(sum));
            }
        });

    }
}
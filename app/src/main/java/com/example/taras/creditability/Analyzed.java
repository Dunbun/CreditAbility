package com.example.taras.creditability;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Analyzed extends AppCompatActivity {
    double dTotalIncome = 0;
    double dTotalCosts = 0;
    double percentage = 0;
    double neededTerm = 0;
    double dneededSum = 0;
    String sCurrency = "";
    String sName = "";
    String sSurname = "";
    String sFathername = "";
    String sDateOfBirth = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzed);

        Bundle extras = getIntent().getExtras();

        dTotalIncome = extras.getDouble("totalIcome");
        dTotalCosts = extras.getDouble("totalCosts");
        percentage = extras.getDouble("percentage");
        neededTerm = extras.getDouble("term");
        dneededSum = extras.getDouble("neededSum");
        sCurrency = extras.getString("currency");
        sName = extras.getString("name");;
        sSurname = extras.getString("surname");;
        sFathername = extras.getString("fathername");;
        sDateOfBirth = extras.getString("date");;

        Toast.makeText(this,extras.getString("surname") + extras.getString("name"),Toast.LENGTH_SHORT).show();
    }
}

package com.example.taras.creditability;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Questionary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionary);

        EditText wantedSum = (EditText)findViewById(R.id.wantedSum);
        RadioGroup currencyRadioGroup = (RadioGroup)findViewById(R.id.currencygroup);
        RadioGroup creditTypeRadioGroup = (RadioGroup)findViewById(R.id.typeofcredit);
        EditText termOfCredit = (EditText)findViewById(R.id.term);
        EditText name = (EditText)findViewById(R.id.name);
        EditText surname = (EditText)findViewById(R.id.surnameET);
        EditText fathername = (EditText)findViewById(R.id.fathername);
        EditText dateOfBirth = (EditText)findViewById(R.id.dateOfBirth);
        EditText salary = (EditText)findViewById(R.id.salary);
        EditText pension = (EditText)findViewById(R.id.pension);
        EditText reward = (EditText)findViewById(R.id.reward);
        EditText anotherIncome = (EditText)findViewById(R.id.anotherIncome);
        TextView totalIncome = (TextView)findViewById(R.id.textViewtotalincome);
        TextView totalSpend = (TextView)findViewById(R.id.textViewtotalspend);
        EditText curencyCosts = (EditText)findViewById(R.id.currentcosts);
        EditText utilityCosts = (EditText)findViewById(R.id.utilitycosts);
        EditText insurancePayments = (EditText)findViewById(R.id.insurancepayments);
        EditText anotherCosts = (EditText)findViewById(R.id.anothercosts);
        Button analyzeBtn = (Button)findViewById(R.id.analyze);
        ImageView totalIncomeImage= (ImageView)findViewById(R.id.imageViewIncome);
        ImageView totalSpendingImage= (ImageView)findViewById(R.id.imageViewSpend);


    }
}

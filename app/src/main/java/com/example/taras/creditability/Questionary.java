package com.example.taras.creditability;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Questionary extends AppCompatActivity {
    double dTotalIncome = 0;//
    double dTotalCosts = 0;//
    double percentage = 0;//
    double maxterm = 0;
    double neededTerm = 0;//
    double dneededSum = 0;//
    String sTypeOfCredit = "";
    String sCurrency = "";
    String sName = "";//
    String sSurname = "";//
    String sFathername = "";//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionary);

        final EditText wantedSum = (EditText)findViewById(R.id.wantedSum);
        final RadioGroup currencyRadioGroup = (RadioGroup)findViewById(R.id.currencygroup);
        final RadioGroup creditTypeRadioGroup = (RadioGroup)findViewById(R.id.typeofcredit);
        final EditText termOfCredit = (EditText)findViewById(R.id.term);
        final EditText name = (EditText)findViewById(R.id.name);
        final EditText surname = (EditText)findViewById(R.id.surnameET);
        final EditText fathername = (EditText)findViewById(R.id.fathername);
        final TextView totalIncome = (TextView)findViewById(R.id.textViewtotalincome);
        final TextView totalSpend = (TextView)findViewById(R.id.textViewtotalspend);
        Button analyzeBtn = (Button)findViewById(R.id.analyze);
        ImageView totalIncomeImage= (ImageView)findViewById(R.id.imageViewIncome);
        ImageView totalSpendingImage= (ImageView)findViewById(R.id.imageViewSpend);

        totalIncomeImage.setClickable(true);

        totalIncomeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dTotalIncome = getTotalIncome();

                totalIncome.setText("" + dTotalIncome);
            }
        });

        totalSpendingImage.setClickable(true);

        totalSpendingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dTotalCosts = getTotalCosts();

                totalSpend.setText("" + dTotalCosts);
            }
        });

        analyzeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonID = currencyRadioGroup.getCheckedRadioButtonId();
                View radioButton = currencyRadioGroup.findViewById(radioButtonID);
                int idx = currencyRadioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) currencyRadioGroup.getChildAt(idx);
                sCurrency = r.getText().toString();

                radioButtonID = creditTypeRadioGroup.getCheckedRadioButtonId();
                radioButton = creditTypeRadioGroup.findViewById(radioButtonID);
                idx = creditTypeRadioGroup.indexOfChild(radioButton);
                r = (RadioButton) creditTypeRadioGroup.getChildAt(idx);
                sTypeOfCredit = r.getText().toString();
                String tmp = r.getTag().toString();
                String arr[] = tmp.split(",");

                percentage = Double.parseDouble(arr[0]);
                maxterm = Double.parseDouble(arr[1]) * 12;

                if(termOfCredit.getText().toString().equals("")){
                    neededTerm = maxterm;
                }else {
                    neededTerm = Double.parseDouble(termOfCredit.getText().toString());

                    if(neededTerm > maxterm){
                        neededTerm = maxterm;
                    }
                }

                sSurname = surname.getText().toString();
                sName = name.getText().toString();
                sFathername = fathername.getText().toString();

                if(!wantedSum.getText().toString().equals("")) {
                    dneededSum = Double.parseDouble(wantedSum.getText().toString());
                }else{
                    dneededSum = 0;
                }

                Intent newIntent = new Intent(Questionary.this, Analyzed.class);

                newIntent.putExtra("name",sName);
                newIntent.putExtra("surname",sSurname);
                newIntent.putExtra("fathername",sFathername);
                newIntent.putExtra("totalIncome",dTotalIncome);
                newIntent.putExtra("totalCosts",dTotalCosts);
                newIntent.putExtra("neededSum",dneededSum);
                newIntent.putExtra("percentage",percentage);
                newIntent.putExtra("term",neededTerm);
                newIntent.putExtra("currency",sCurrency);
                newIntent.putExtra("creditType",sTypeOfCredit);

                startActivity(newIntent);

            }
        });

    }

    public double getTotalIncome(){
        double totalIncome = 0;
        EditText salary = (EditText)findViewById(R.id.salary);
        EditText pension = (EditText)findViewById(R.id.pension);
        EditText reward = (EditText)findViewById(R.id.reward);
        EditText anotherIncome = (EditText)findViewById(R.id.anotherIncome);

        double dSalary = 0;
        double dPension = 0;
        double dReward = 0;
        double dAnotherIncome = 0;

        if(!salary.getText().toString().equals("")) {
            dSalary = Double.parseDouble(salary.getText().toString());
        }

        if(!pension.getText().toString().equals("")) {
            dPension = Double.parseDouble(pension.getText().toString());
        }

        if(!reward.getText().toString().equals("")) {
            dReward = Double.parseDouble(reward.getText().toString());
        }

        if(!anotherIncome.getText().toString().equals("")) {
            dAnotherIncome = Double.parseDouble(anotherIncome.getText().toString());
        }

        totalIncome = dAnotherIncome + dPension + dReward + dSalary;

        return totalIncome;
    }

    public double getTotalCosts(){
        double totalCosts = 0;
        EditText curencyCosts = (EditText)findViewById(R.id.currentcosts);
        EditText utilityCosts = (EditText)findViewById(R.id.utilitycosts);
        EditText insurancePayments = (EditText)findViewById(R.id.insurancepayments);
        EditText anotherCosts = (EditText)findViewById(R.id.anothercosts);

        double dCurencyCosts = 0;
        double dUtilityCosts = 0;
        double dInsurancePayments = 0;
        double dAnotherCosts = 0;

        if(!curencyCosts.getText().toString().equals("")) {
            dCurencyCosts = Double.parseDouble(curencyCosts.getText().toString());
        }

        if(!utilityCosts.getText().toString().equals("")) {
            dUtilityCosts = Double.parseDouble(utilityCosts.getText().toString());
        }

        if(!insurancePayments.getText().toString().equals("")) {
            dInsurancePayments = Double.parseDouble(insurancePayments.getText().toString());
        }

        if(!anotherCosts.getText().toString().equals("")) {
            dAnotherCosts = Double.parseDouble(anotherCosts.getText().toString());
        }

        totalCosts = dAnotherCosts + dCurencyCosts + dInsurancePayments + dUtilityCosts;

        return totalCosts;
    }
}

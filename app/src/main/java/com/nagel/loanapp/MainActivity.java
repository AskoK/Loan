package com.nagel.loanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Issue 16
    public EditText txtCost, txtLoan, txtRate, txtPaym, txtYear, txtTerm;
    public Button btnAmortisation, btnCalculate, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAmortisation = findViewById(R.id.btnAmortisation);

        btnAmortisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Issue 2
                if (Loan.getInstance().getPeriods() > 0) {
                    //Issue 3
                    Intent intent = new Intent(MainActivity.this, PlanActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        txtCost = findViewById(R.id.txtCost);
        txtLoan = findViewById(R.id.txtLoan);
        txtRate = findViewById(R.id.txtRate);
        txtPaym = findViewById(R.id.txtPaym);
        txtYear = findViewById(R.id.txtYear);
        txtTerm = findViewById(R.id.txtTerm);

    }

    //Issue 18
   private void disable(EditText view) {
        view.setKeyListener(null);
        view.setEnabled(false);
    }


    public void onClear(View view) {
        //Issue 4
        txtCost.setText("");
        txtLoan.setText("");
        txtRate.setText("");
        txtPaym.setText("");
        txtYear.setText("");
        txtTerm.setText("");
        //Issue 5
        Loan.getInstance().setPrincipal(1);
        Loan.getInstance().setInterestRate(1);
        Loan.getInstance().setPeriods(1);
        txtCost.requestFocus();
        //Issue 18
        disable(txtPaym);

    }

    public void onCalculate(View view) {
        double cost = 0;
        double loan;
        double rate;
        //Issue 6
        int year;
        int term;
        try {
            String text = txtCost.getText().toString().trim();
            if (text.length() > 0) {
                cost = Double.parseDouble(text);
                if (cost < 0) throw new Exception();
            }
        } catch (Exception ex) {
            //Issue 7
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_LONG).show();
            txtCost.requestFocus();
            return;
        }
        try {
            loan = Double.parseDouble(txtLoan.getText().toString().trim());
            if (loan < 0) throw new Exception();
        } catch (Exception ex) {
            //Issue 7
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_LONG).show();
            txtLoan.requestFocus();
            return;
        }
        try {
            rate = Double.parseDouble(txtRate.getText().toString().trim());
            //Issue 15
            if (rate <= 0 || rate > 50) throw new Exception();
        } catch (Exception ex) {
            //Issue 7
            Toast.makeText(this, "Error: "+ ex, Toast.LENGTH_LONG).show();
            txtRate.requestFocus();
            return;
        }
        try {
            year = Integer.parseInt( txtYear.getText().toString().trim());
            //Issue 8
            //Issue 14
            if (year <= 0 || year > 60) throw new Exception();
        } catch (Exception ex) {
            //Issue 7
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_LONG).show();
            txtYear.requestFocus();
            return;
        }
        try {
            term = Integer.parseInt(txtTerm.getText().toString().trim());
            //Issue 13
            if (term <= 0 || term > 12) throw new Exception();
        } catch (Exception ex) {
            //Issue 7
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_LONG).show();            txtTerm.requestFocus();
            return;
        }
        Loan.getInstance().setPrincipal(loan + cost);
        Loan.getInstance().setInterestRate(rate / 100 / term);
        Loan.getInstance().setPeriods(year * term);
        txtPaym.setText(String.format("%1.2f", Loan.getInstance().payment()));
    }

    public void onAmort(View view) {
        if (Loan.getInstance().getPeriods() > 0) {
            Intent intent = new Intent(this, PlanActivity.class);

            startActivity(intent);
        }
    }
}


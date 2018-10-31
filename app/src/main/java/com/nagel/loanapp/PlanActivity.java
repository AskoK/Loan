package com.nagel.loanapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private List<String> items = new ArrayList();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        //Issue 12
        items.add(getResources().getString(R.string.per));
        items.add(getResources().getString(R.string.intr));
        items.add(getResources().getString(R.string.rep));
        items.add(getResources().getString(R.string.outs));
        //Issue 9
        for (int n = 1; n <= Loan.getInstance().getPeriods(); n++)
        {
            items.add("" + n);
            items.add(String.format("%1.2f", Loan.getInstance().interest(n)));
            items.add(String.format("%1.2f", Loan.getInstance().repayment(n)));
            items.add(String.format("%1.2f", Math.abs(Loan.getInstance().outstanding(n))));
        }
        GridView grid = findViewById(R.id.grid);
        //Issue 20
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items);


        //Issue 10
        grid.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //Issue 22
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

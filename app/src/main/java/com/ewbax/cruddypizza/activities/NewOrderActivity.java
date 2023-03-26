package com.ewbax.cruddypizza.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ewbax.cruddypizza.R;

public class NewOrderActivity extends AppCompatActivity {

    TextView sizeTV;
    TextView toppingsTV;
    TextView customerNameTV;
    EditText customerNameET;
    Spinner sizeSpin;
    Spinner top1Spin;
    Spinner top2Spin;
    Spinner top3Spin;
    Button submitOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        sizeTV = findViewById(R.id.sizeTV);
        toppingsTV = findViewById(R.id.toppingsTV);
        customerNameTV = findViewById(R.id.customerNameTV);
        customerNameET = findViewById(R.id.customerNameET);
        sizeSpin = findViewById(R.id.sizeSpin);
        top1Spin = findViewById(R.id.top1Spin);
        top2Spin = findViewById(R.id.top2Spin);
        top3Spin = findViewById(R.id.top3Spin);
        submitOrderBtn = findViewById(R.id.submitOrderBtn);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.main_menu) {
            Intent i = new Intent(NewOrderActivity.this, MainActivity.class);
            startActivity(i);
        }

        if (item.getItemId() == R.id.order_history) {
            Intent i = new Intent(NewOrderActivity.this, OrderHistoryActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
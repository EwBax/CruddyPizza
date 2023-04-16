package com.ewbax.cruddypizza.activities;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ewbax.cruddypizza.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import utils.DBAdapter;

public class NewOrderActivity extends BaseActivity {

    protected TextView sizeTV;
    protected TextView toppingsTV;
    protected TextView customerNameTV;
    protected EditText customerNameET;
    protected Spinner sizeSpin;
    protected Spinner top1Spin;
    protected Spinner top2Spin;
    protected Spinner top3Spin;
    protected Button submitOrderBtn;
    protected MenuItem mainMenuLink;
    protected MenuItem orderHistoryLink;

    protected ArrayAdapter<CharSequence> sizeAdapter;
    protected ArrayAdapter<CharSequence> toppingAdapter;


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
        submitOrderBtn.setOnClickListener(submitOrder);


    }


    // Creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_order_menu, menu);

        languageSelector = menu.findItem(R.id.language_selector);
        mainMenuLink = menu.findItem(R.id.main_menu);
        orderHistoryLink = menu.findItem(R.id.order_history);

        updateTextLanguage();

        return true;
    }


    // Listener for menu item being selected
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


    // Method to update the text of every view using the new context with updated language
    @Override
    protected void updateTextLanguage() {

        super.updateTextLanguage();

        setTitle(context.getResources().getString(R.string.new_order));
        mainMenuLink.setTitle(context.getResources().getString(R.string.main_menu));
        orderHistoryLink.setTitle(context.getResources().getString(R.string.order_history));

        sizeTV.setText(context.getResources().getString(R.string.size));
        toppingsTV.setText(context.getResources().getString(R.string.topping));
        customerNameTV.setText(context.getResources().getString(R.string.customer_name));
        submitOrderBtn.setText(context.getResources().getString(R.string.submit));

        // Updating spinner adapters
        toppingAdapter = ArrayAdapter.createFromResource(context, R.array.topping_list, R.layout.spinner_item);
        toppingAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sizeAdapter = ArrayAdapter.createFromResource(context, R.array.size_list, R.layout.spinner_item);
        sizeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        updateSpinners();

    }


    // Updates the spinners to set to the updated adapters, and maintains selection
    protected void updateSpinners() {

        Spinner[] spinners = {sizeSpin, top1Spin, top2Spin, top3Spin};
        int[] spinnerStates = new int[spinners.length];

        for (int i = 0; i < spinners.length; i++) {
            spinnerStates[i] = spinners[i].getSelectedItemPosition();
        }

        sizeSpin.setAdapter(sizeAdapter);
        top1Spin.setAdapter(toppingAdapter);
        top2Spin.setAdapter(toppingAdapter);
        top3Spin.setAdapter(toppingAdapter);

        for (int i = 0; i < spinners.length; i++) {
            spinners[i].setSelection(spinnerStates[i]);
        }

    }


    // Returns boolean representing whether all required fields are filled
    protected boolean validateFields() {

        // Checking that a size has been chosen
        if (sizeSpin.getSelectedItemPosition() == 0) {return false;}

        // Checking that at least one topping has been chosen
        if ((top1Spin.getSelectedItemPosition() == 0)
                && (top2Spin.getSelectedItemPosition() == 0)
                && (top3Spin.getSelectedItemPosition() == 0)) {

            return false;
        }

        // Checking that customer name has been entered
        return !customerNameET.getText().toString().trim().isEmpty();

    }


    // Checks that the required information has been filled out, then creates a new database entry for the order
    protected View.OnClickListener submitOrder = v -> {

        // Validating
        if (!validateFields()) {
            Toast.makeText(this, context.getResources().getString(R.string.validation_error), Toast.LENGTH_LONG).show();
        } else {

            // For order_date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            // Opening db
            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.open();

            // Creating the order and storing result
            long result = dbAdapter.insertOrder(
                    sizeSpin.getSelectedItemPosition(),
                    top1Spin.getSelectedItemPosition(),
                    top2Spin.getSelectedItemPosition(),
                    top3Spin.getSelectedItemPosition(),
                    customerNameET.getText().toString().trim(),
                    dtf.format(now)
            );

            // Closing db
            dbAdapter.close();

            // -1 will be returned if there is an error
            if (result < 0) {
                Toast.makeText(this, context.getResources().getString(R.string.order_failed), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, context.getResources().getString(R.string.order_submitted), Toast.LENGTH_LONG).show();

                // Returning to the last activity
                finish();
            }
        }

    };
}
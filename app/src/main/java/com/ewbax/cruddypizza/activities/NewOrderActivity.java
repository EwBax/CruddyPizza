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

    @Override
    protected void updateTextLanguage() {

        setTitle(context.getResources().getString(R.string.new_order));
        languageSelector.setTitle(context.getResources().getString(R.string.language));
        mainMenuLink.setTitle(context.getResources().getString(R.string.main_menu));
        orderHistoryLink.setTitle(context.getResources().getString(R.string.order_history));

        sizeTV.setText(context.getResources().getString(R.string.size));
        toppingsTV.setText(context.getResources().getString(R.string.topping));
        customerNameTV.setText(context.getResources().getString(R.string.customer_name));
        submitOrderBtn.setText(context.getResources().getString(R.string.submit));



        toppingAdapter = ArrayAdapter.createFromResource(context, R.array.topping_list, R.layout.spinner_item);
        toppingAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sizeAdapter = ArrayAdapter.createFromResource(context, R.array.size_list, R.layout.spinner_item);
        sizeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);


        updateSpinners();

    }


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

    // TODO implement validation for fields
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

    // TODO implement toast to show order is valid and submitted
    protected View.OnClickListener submitOrder = v -> {

        Toast submitOrderMsg = new Toast(this);
        submitOrderMsg.setDuration(Toast.LENGTH_LONG);

        if (!validateFields()) {
            submitOrderMsg.setText(context.getResources().getString(R.string.validation_error));
            submitOrderMsg.show();
        } else {
            submitOrderMsg.setText(context.getResources().getString(R.string.order_submitted));
            submitOrderMsg.show();
            finish();
        }

    };
}
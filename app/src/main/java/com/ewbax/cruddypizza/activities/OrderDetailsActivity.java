package com.ewbax.cruddypizza.activities;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ewbax.cruddypizza.R;

import models.OrderModel;
import utils.DBAdapter;

public class OrderDetailsActivity extends NewOrderActivity {

    Button deleteOrderBtn;
    OrderModel order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        sizeTV = findViewById(R.id.sizeTV);
        toppingsTV = findViewById(R.id.toppingsTV);
        customerNameTV = findViewById(R.id.customerNameTV);
        customerNameET = findViewById(R.id.customerNameET);
        sizeSpin = findViewById(R.id.sizeSpin);
        top1Spin = findViewById(R.id.top1Spin);
        top2Spin = findViewById(R.id.top2Spin);
        top3Spin = findViewById(R.id.top3Spin);
        submitOrderBtn = findViewById(R.id.submitOrderBtn);
        deleteOrderBtn = findViewById(R.id.deleteOrderBtn);

        // Getting the order details from the intent bundle and storing them in an order model object
        order = new OrderModel();
        order.setOrderNum(getIntent().getIntExtra("ORDER_NUM", 0));
        order.setSize(getIntent().getIntExtra("SIZE", 0));
        order.setTop1(getIntent().getIntExtra("TOP1", 0));
        order.setTop2(getIntent().getIntExtra("TOP2", 0));
        order.setTop3(getIntent().getIntExtra("TOP3", 0));
        order.setCustomerName(getIntent().getStringExtra("CUSTOMER_NAME"));

        // Setting the spinners and customer name to the selections from the order model
        sizeSpin.setSelection(order.getSize());
        top1Spin.setSelection(order.getTop1());
        top2Spin.setSelection(order.getTop2());
        top3Spin.setSelection(order.getTop3());
        customerNameET.setText(order.getCustomerName());

        submitOrderBtn.setOnClickListener(updateOrder);
        deleteOrderBtn.setOnClickListener(deleteOrder);


    }


    // We only need to update the two buttons here because everything else is updated in the super class
    @Override
    protected void updateTextLanguage() {
        super.updateTextLanguage();

        setTitle(context.getResources().getString(R.string.order_details));
        deleteOrderBtn.setText(context.getResources().getString(R.string.delete));
        submitOrderBtn.setText(context.getResources().getString(R.string.update));
    }


    // Validates then updates order in database
    protected View.OnClickListener updateOrder = v -> {

        // Validating
        if (!validateFields()) {
            Toast.makeText(this, context.getResources().getString(R.string.validation_error), Toast.LENGTH_LONG).show();
        } else {

            // Opening db
            DBAdapter dbAdapter = new DBAdapter(this);

            try {
                dbAdapter.open();
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, context.getResources().getString(R.string.update_failed), Toast.LENGTH_LONG).show();
                return;
            }

            // Updating order with new info
            boolean result = dbAdapter.updateOrder(
                    order.getOrderNum(),
                    sizeSpin.getSelectedItemPosition(),
                    top1Spin.getSelectedItemPosition(),
                    top2Spin.getSelectedItemPosition(),
                    top3Spin.getSelectedItemPosition(),
                    customerNameET.getText().toString().trim()
            );

            // Closing db
            dbAdapter.close();

            // Checking if the update was successful
            if (result) {
                Toast.makeText(this, context.getResources().getString(R.string.order_updated), Toast.LENGTH_LONG).show();

                // Returning to previous activity
                finish();
            } else {
                Toast.makeText(this, context.getResources().getString(R.string.update_failed), Toast.LENGTH_LONG).show();
            }
        }

    };


    // Deletes order from database
    protected View.OnClickListener deleteOrder = v -> {

        // Opening db
        DBAdapter dbAdapter = new DBAdapter(this);

        try {
            dbAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, context.getResources().getString(R.string.delete_failed), Toast.LENGTH_LONG).show();
            return;
        }

        boolean result = dbAdapter.deleteOrder(order.getOrderNum());

        if (result) {

            Toast.makeText(this, context.getResources().getString(R.string.order_deleted), Toast.LENGTH_LONG).show();

            // Returning to previous activity
            finish();

        } else {
            Toast.makeText(this, context.getResources().getString(R.string.delete_failed), Toast.LENGTH_LONG).show();
        }

    };

}
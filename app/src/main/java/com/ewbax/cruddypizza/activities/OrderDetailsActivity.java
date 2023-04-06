package com.ewbax.cruddypizza.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ewbax.cruddypizza.R;

import models.OrderModel;

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


    // This method just does a toast to show that validation is taking place and the order is updated
    // It will call the database adapter to update the order in the database
    protected View.OnClickListener updateOrder = v -> {
        Toast updateOrderMsg = new Toast(this);
        updateOrderMsg.setDuration(Toast.LENGTH_LONG);

        if (!validateFields()) {
            updateOrderMsg.setText(context.getResources().getString(R.string.validation_error));
            updateOrderMsg.show();
        } else {
            updateOrderMsg.setText(context.getResources().getString(R.string.order_updated));
            updateOrderMsg.show();
            finish();
        }

    };


    // This method just shows a message that the order was deleted, but it will eventually use the
    // database adapter to actually delete the order from the database
    protected View.OnClickListener deleteOrder = v -> {
        Toast updateOrderMsg = new Toast(this);
        updateOrderMsg.setDuration(Toast.LENGTH_LONG);
        updateOrderMsg.setText(context.getResources().getString(R.string.order_deleted));
        updateOrderMsg.show();
        finish();

    };

}
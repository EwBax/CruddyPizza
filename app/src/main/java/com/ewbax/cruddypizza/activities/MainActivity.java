package com.ewbax.cruddypizza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ewbax.cruddypizza.R;

public class MainActivity extends BaseActivity {

    private Button newOrderBtn;
    private Button orderHistoryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newOrderBtn = findViewById(R.id.newOrderBtn);
        orderHistoryBtn = findViewById(R.id.orderHistoryBtn);
        newOrderBtn.setOnClickListener(onButtonClicked);
        orderHistoryBtn.setOnClickListener(onButtonClicked);

    }

    // Shared listener for both buttons, launches activity based on which button is pressed
    public View.OnClickListener onButtonClicked = v -> {

        if (v.getId() == R.id.newOrderBtn) {
            Intent intent = new Intent(MainActivity.this, NewOrderActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.orderHistoryBtn) {
            Intent intent = new Intent(MainActivity.this, OrderHistoryActivity.class);
            startActivity(intent);
        }
    };


    // Method to update all the text views displayed when language is changed
    @Override
    protected void updateTextLanguage() {

        super.updateTextLanguage();

        setTitle(context.getResources().getString(R.string.main_menu));
        newOrderBtn.setText(context.getString(R.string.new_order));
        orderHistoryBtn.setText(context.getString(R.string.order_history));


    }

}
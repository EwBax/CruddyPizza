package com.ewbax.cruddypizza.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ewbax.cruddypizza.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import utils.DBAdapter;
import utils.OrderHistoryAdapter;
import utils.RecyclerViewInterface;
import models.OrderModel;

public class OrderHistoryActivity extends BaseActivity implements RecyclerViewInterface {

    private ArrayList<OrderModel> orderModels;
    private MenuItem mainMenuLink;
    private MenuItem newOrderLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);



    }


    // Refreshing order model data when resuming
    @Override
    protected void onResume() {
        super.onResume();

        // Refreshing order history
        RecyclerView orderHistoryRV = findViewById(R.id.orderHistoryRV);

        orderModels = new ArrayList<>();

        setUpOrderModels();

        OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(this, orderModels, this);
        orderHistoryRV.setAdapter(orderHistoryAdapter);
        orderHistoryRV.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_history_menu, menu);

        languageSelector = menu.findItem(R.id.language_selector);
        mainMenuLink = menu.findItem(R.id.main_menu);
        newOrderLink = menu.findItem(R.id.new_order);

        updateTextLanguage();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.main_menu) {
            Intent i = new Intent(OrderHistoryActivity.this, MainActivity.class);
            startActivity(i);
        }

        if (item.getItemId() == R.id.new_order) {
            Intent i = new Intent(OrderHistoryActivity.this,NewOrderActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    // Pulls all order from the database and adds each one to orderModels list
    private void setUpOrderModels() {

        // Opening db
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        Cursor data = dbAdapter.getAllOrders();

        data.moveToFirst();

        // Looping through each row in cursor
        for (int i = 0; i < data.getCount(); i++) {
            // Creating order model
            // Data columns are in the same order as the parameters for OrderModel constructor
            orderModels.add(new OrderModel(
                    data.getInt(0),
                    data.getInt(1),
                    data.getInt(2),
                    data.getInt(3),
                    data.getInt(4),
                    data.getString(5),
                    data.getString(6)
                    )
            );
            data.moveToNext();
        }

        dbAdapter.close();
    }


    // When an order is clicked we use an intent bundle to pass it's information to the order
    // details activity
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(OrderHistoryActivity.this, OrderDetailsActivity.class);

        intent.putExtra("ORDER_NUM", orderModels.get(position).getOrderNum());
        intent.putExtra("DATE", orderModels.get(position).getDate());
        intent.putExtra("SIZE", orderModels.get(position).getSize());
        intent.putExtra("TOP1", orderModels.get(position).getTop1());
        intent.putExtra("TOP2", orderModels.get(position).getTop2());
        intent.putExtra("TOP3", orderModels.get(position).getTop3());
        intent.putExtra("CUSTOMER_NAME", orderModels.get(position).getCustomerName());

        startActivity(intent);

    }



    @Override
    protected void updateTextLanguage() {
        super.updateTextLanguage();

        setTitle(context.getResources().getString(R.string.order_history));
        mainMenuLink.setTitle(context.getResources().getString(R.string.main_menu));
        newOrderLink.setTitle(context.getResources().getString(R.string.new_order));


    }
}
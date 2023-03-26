package com.ewbax.cruddypizza.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ewbax.cruddypizza.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Util.OrderHistoryAdapter;
import Util.RecyclerViewInterface;
import models.OrderModel;

public class OrderHistoryActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<OrderModel> orderModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

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

    private void setUpOrderModels() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        orderModels.add(new OrderModel(1, 0, 0, 1, 2, dtf.format(now), "Ewan Baxter"));
        orderModels.add(new OrderModel(1, 0, 0, 1, 2, "2023-03-24 12:21:03", "David Russell"));
    }

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
}
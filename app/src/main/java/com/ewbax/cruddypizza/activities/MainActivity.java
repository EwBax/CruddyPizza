package com.ewbax.cruddypizza.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ewbax.cruddypizza.R;

import Util.LocaleHelper;

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
    private static final String ENGLISH = "EN";
    private static final String FRENCH = "FR";

    private Button newOrderBtn;
    private Button orderHistoryBtn;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newOrderBtn = findViewById(R.id.newOrderBtn);
        orderHistoryBtn = findViewById(R.id.orderHistoryBtn);
        newOrderBtn.setOnClickListener(onButtonClicked);
        orderHistoryBtn.setOnClickListener(onButtonClicked);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.language_selector) {

            prefs = getSharedPreferences("language", MODE_PRIVATE);

            // Checking if the shared preference is set to english. If shared preference has not been
            // set yet this also sets it to english as the default
            if (prefs.getString(SELECTED_LANGUAGE, ENGLISH).equals(ENGLISH)) {
                LocaleHelper.setLocale(this, FRENCH);
            } else {
                LocaleHelper.setLocale(this, ENGLISH);
            }



        }
        return super.onOptionsItemSelected(item);
    }


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
}
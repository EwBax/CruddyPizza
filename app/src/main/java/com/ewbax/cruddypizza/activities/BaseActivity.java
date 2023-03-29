package com.ewbax.cruddypizza.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ewbax.cruddypizza.R;

import utils.LocaleHelper;

// Base activity that other activities will inherit from
public abstract class BaseActivity extends AppCompatActivity {

    protected SharedPreferences prefs;
    protected MenuItem languageSelector;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting the language from shared preferences then generating new context for updated language resources
        prefs = getSharedPreferences(LocaleHelper.LANG_PREFS_KEY, MODE_PRIVATE);
        // Setting the locale using value from shared preferences, or default of english
        context = LocaleHelper.setLocale(this.getBaseContext(), prefs.getString(LocaleHelper.SELECTED_LANGUAGE, LocaleHelper.ENGLISH));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);
        languageSelector = menu.findItem(R.id.language_selector);

        // Calling updateText here instead of in onCreate because languageSelector menu item would
        // be null before this point
        updateTextLanguage();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.language_selector) {

            // Calling helper class to handle updating shared preferences and locale, then returning a new context with
            // appropriate language resources
            if (prefs.getString(LocaleHelper.SELECTED_LANGUAGE, LocaleHelper.ENGLISH).equals(LocaleHelper.ENGLISH)) {
                context = LocaleHelper.setLocale(this.getBaseContext(), LocaleHelper.FRENCH);
            } else {
                context = LocaleHelper.setLocale(this.getBaseContext(), LocaleHelper.ENGLISH);
            }

            // Updating the text after changing languages
            updateTextLanguage();


        }

        return super.onOptionsItemSelected(item);
    }

    protected void updateTextLanguage() {

        languageSelector.setTitle(context.getResources().getString(R.string.language));

    }
}

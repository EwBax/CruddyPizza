package utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

// Code based on https://infyom.com/blog/how-to-change-app-language-in-android-programmatically
public class LocaleHelper {

    public static final String LANG_PREFS_KEY = "language";
    public static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
    public static final String ENGLISH = "en";
    public static final String FRENCH = "fr";

    // This method is used to set the language at runtime
    public static Context setLocale(Context context, String language) {
        persist(context, language);

        return updateResources(context, language);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    // the method is used update the language of application by creating
    // object of inbuilt Locale class and passing language argument to it
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);


        return context.createConfigurationContext(configuration);
    }
}

package com.example.grocerease.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/** PreferencesHelper simplifies our required interaction with the Shared Preferences */
public class PreferencesHelper {
    private SharedPreferences sharedPreferences;


    /** Constructor to instantiate a PreferencesHelper in an activity */
    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
    }

    /** writing a String to our Shared Preferences */
    public void writeString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /** reading a String from our Shared Preferences */
    public String readString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
}

package com.example.grocerease.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private SharedPreferences sharedPreferences;

    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
    }
    public void writeString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String readString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
}

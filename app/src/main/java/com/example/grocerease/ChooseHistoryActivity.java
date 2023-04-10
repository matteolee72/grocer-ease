package com.example.grocerease;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

public class ChooseHistoryActivity extends AppCompatActivity {

    private PreferencesHelper preferencesHelper;
    private UserDatabaseObject userObject;
    Gson gson = new Gson();

    @Override
    protected void onResume(){ //making sure history is re-rendered in the event it is updated
        super.onResume();
        // reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_history);

        // reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);

        RecyclerView recyclerView = findViewById(R.id.historyRecyclerView);
        RecyclerView.Adapter<HistoryAdapter.ViewHolder> historyAdapter = new HistoryAdapter(this, userObject.getUserHistory());
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

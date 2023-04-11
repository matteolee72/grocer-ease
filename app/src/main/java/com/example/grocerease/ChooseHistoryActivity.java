package com.example.grocerease;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerease.Objects.UserDatabaseObject;
import com.example.grocerease.Utils.HistoryAdapter;
import com.example.grocerease.Utils.PreferencesHelper;
import com.google.gson.Gson;

/*** Choose History Activity
 * This Activity is created when the user comes from Single Item Analyse and wants to
 * compare an item with one from their History. ***/

public class ChooseHistoryActivity extends AppCompatActivity {
    private PreferencesHelper preferencesHelper;
    private UserDatabaseObject userObject;
    Gson gson = new Gson();

    /** onResume() ensures that favourites is re-rendered in the event it is updated **/
    @Override
    protected void onResume(){ //making sure history is re-rendered in the event it is updated
        super.onResume();
        // reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);
    }

    /** onCreate() method **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_history);

        // Reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);

        // Instantiating the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.historyRecyclerView);
        RecyclerView.Adapter<HistoryAdapter.ViewHolder> historyAdapter = new HistoryAdapter(this, userObject.getUserHistory());
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

package com.example.grocerease;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerease.Objects.UserDatabaseObject;
import com.example.grocerease.Utils.FavouritesAdapter;
import com.example.grocerease.Utils.PreferencesHelper;
import com.google.gson.Gson;

/*** Choose Favourites Activity
 * This Activity is created when the user comes from Single Item Analyse and wants to
 * compare an item with one from their Favourites. ***/

public class ChooseFavouritesActivity extends AppCompatActivity {
    private PreferencesHelper preferencesHelper;
    private UserDatabaseObject userObject;
    Gson gson = new Gson();

    /** onResume() ensures that favourites is re-rendered in the event it is updated **/
    @Override
    protected void onResume(){
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
        setContentView(R.layout.activity_choose_favourites);

        // Reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);

        // Instantiating the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.favouritesRecyclerView);
        RecyclerView.Adapter<FavouritesAdapter.ViewHolder> favouritesAdapter = new FavouritesAdapter(this, userObject.getUserFavourites());
        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

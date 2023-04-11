package com.example.grocerease;

import static com.example.grocerease.Utils.SingleItemRating.caloriesRating;
import static com.example.grocerease.Utils.SingleItemRating.cholesterolRating;
import static com.example.grocerease.Utils.SingleItemRating.dietaryFibreRating;
import static com.example.grocerease.Utils.SingleItemRating.ironRating;
import static com.example.grocerease.Utils.SingleItemRating.percCalculator;
import static com.example.grocerease.Utils.SingleItemRating.proteinRating;
import static com.example.grocerease.Utils.SingleItemRating.saturatedFatRating;
import static com.example.grocerease.Utils.SingleItemRating.sodiumRating;
import static com.example.grocerease.Utils.SingleItemRating.sugarRating;
import static com.example.grocerease.Utils.SingleItemRating.totalCarbohydratesRating;
import static com.example.grocerease.Utils.SingleItemRating.totalFatRating;
import static com.example.grocerease.Utils.SingleItemRating.transFatRating;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.grocerease.Objects.FoodDatabaseObject;
import com.example.grocerease.Objects.UserDatabaseObject;
import com.example.grocerease.Objects.UserFavouritesObject;
import com.example.grocerease.Objects.UserHistoryObject;
import com.example.grocerease.Objects.UserPreferencesObject;
import com.example.grocerease.Utils.CaptureAct;
import com.example.grocerease.Utils.PreferencesHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class SingleItemAnalyzeActivity extends AppCompatActivity {

    //realtime database
    private DatabaseReference databaseReference;
    //for images
    private StorageReference foodImageStorageReference;
    private FirebaseStorage storage;

    UserPreferencesObject userPreferences;
    
    TextView itemName, company, mass, calories, percentage, totalFat, saturatedFat, transFat, cholesterol,
            sodium, totalCarbs, dietaryFibres, totalSugars, protein, iron;
    FoodDatabaseObject foodObject;
    Button historyFromSingle;
    Button favouritesFromSingle;
    Button scan_button;
    private String username;
    private UserDatabaseObject userObject;
    private PreferencesHelper preferencesHelper;
    Gson gson = new Gson();

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SingleItemAnalyzeActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        // DatabaseReference provides a handle to the firebase database such that we can access the
        // information contained at the key <barcodeNum>
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // StorageReference provides a handle to the firebase storage service
        storage = FirebaseStorage.getInstance();

        // reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);
        userPreferences = userObject.getUserPreferences();
        
        username = preferencesHelper.readString("username","error");

        Log.d("username", "onCreate: " + username);


        // Get a handle on all the items on the page
        itemName = findViewById(R.id.itemName);
        company = findViewById(R.id.itemCompany);
        mass = findViewById(R.id.grams_of_contents);
        calories = findViewById(R.id.number_of_calories);
        percentage = findViewById(R.id.perc_of_daily_intake_value);
        totalFat = findViewById(R.id.total_fat_value);
        saturatedFat = findViewById(R.id.saturated_fat_value);
        transFat = findViewById(R.id.trans_fat_value);
        cholesterol = findViewById(R.id.cholesterol_value);
        sodium = findViewById(R.id.sodium_value);
        totalCarbs = findViewById(R.id.total_carbohydrates_value);
        dietaryFibres = findViewById(R.id.dietary_fibres_value);
        totalSugars = findViewById(R.id.total_sugars_value);
        protein = findViewById(R.id.protein_value);
        iron = findViewById(R.id.iron_value);

        // buttons to navigate to other activities to obtain 2nd item for compare
        historyFromSingle = findViewById(R.id.history_button_single);
        favouritesFromSingle = findViewById(R.id.favourties_button_single);

        // navigate to History Activity from Single Item to choose next item
        historyFromSingle.setOnClickListener(v -> {
            Intent intent = new Intent(SingleItemAnalyzeActivity.this, ChooseHistoryActivity.class);
            intent.putExtra(MainActivity.FIRSTBARCODEKEY, foodObject);
            startActivity(intent);
        });

        // navigate to Favourites Activity from Single Item to choose next item
        favouritesFromSingle.setOnClickListener(v -> {
            Intent intent = new Intent(SingleItemAnalyzeActivity.this, ChooseFavouritesActivity.class);
            intent.putExtra(MainActivity.FIRSTBARCODEKEY, foodObject);
            startActivity(intent);
        });

        // Get the barcode number from the previous intent
        // so that we can pass the information to the database reference
        Intent intent = getIntent();
        String barcodeNum = intent.getStringExtra(MainActivity.FIRSTBARCODEKEY);

        Log.i("SingleItemAnalyse", "Intent barcode received: "+ barcodeNum);

        ImageView imageView = findViewById(R.id.card1_foodImage_ImageView);

        ToggleButton likeButton = findViewById(R.id.likeButton);

        UserFavouritesObject favourites = userObject.getUserFavourites();
        UserHistoryObject userHistory = userObject.getUserHistory();

        if(favourites.getFoodFavourites().contains(barcodeNum)){
            likeButton.setChecked(true);
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = likeButton.isChecked();
                if (isChecked) {
                    favourites.addToFavourites(barcodeNum);

                    //making sure to update local userObject so that it updates the database correctly
                    String jsonString = gson.toJson(userObject); // returns a Json String object
                    preferencesHelper.writeString("userObject", jsonString);
                    Log.d("userObject", "onCreate: "+userObject.getUserFavourites());

                    databaseReference.child("Users").child(username).child("userFavourites").setValue(favourites);

                    //like action
                    Log.d("liking", "onClick: ");
                } else {
                    //unlike action
                    Log.d("unliking", "onClick: ");
                    favourites.removeFromFavourites(barcodeNum);

                    // making sure to update local userObject
                    String jsonString = gson.toJson(userObject); // returns a Json String object
                    preferencesHelper.writeString("userObject", jsonString);
                    Log.d("userObject", "onCreate: "+userObject.getUserFavourites());

                    databaseReference.child("Users").child(username).child("userFavourites").setValue(favourites);

                }
            }
        });
        databaseReference.child("Food").child(barcodeNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else if (task.getResult().getValue(Object.class) == null) {
                    Log.e("firebase", "Item does not exist in database");
                    Toast.makeText(SingleItemAnalyzeActivity.this, barcodeNum + " is not in database", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SingleItemAnalyzeActivity.this);
                    builder.setMessage("Would you like to add it to the database?").setTitle("Item does not exist in the database");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent addActivityIntent = new Intent(SingleItemAnalyzeActivity.this, DatabaseAddActivity.class);
                            addActivityIntent.putExtra(MainActivity.FIRSTBARCODEKEY,barcodeNum);
                            startActivity(addActivityIntent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(SingleItemAnalyzeActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    foodObject = task.getResult().getValue(FoodDatabaseObject.class); // get food object from database

                    userHistory.addToHistory(barcodeNum);
                    //making sure to update local userObject so that it updates the database correctly
                    String jsonString = gson.toJson(userObject); // returns a Json String object
                    preferencesHelper.writeString("userObject", jsonString);
                    Log.d("userObject", "onCreate: "+userObject.getUserHistory().getFoodHistory());
                    databaseReference.child("Users").child(username).child("userHistory").setValue(userHistory);

                    // Get the result from the database and populate a foodObject of type FoodDatabaseObject

                    itemName.setText(foodObject.getFoodName());
                    company.setText(foodObject.getFoodCompany());
                    mass.setText(foodObject.getFoodMass() + "g");

                    String caloriesColor = caloriesRating(foodObject,userPreferences);
                    calories.setText(foodObject.getFoodCalories() + "kcal");
                    calories.setTextColor(Color.parseColor(caloriesColor));

                    String perc = percCalculator(foodObject,userPreferences);
                    percentage.setText(perc + "%");

                    String totalFatColor = totalFatRating(foodObject,userPreferences);
                    totalFat.setText(foodObject.getFoodTotalFat() + "g");
                    totalFat.setTextColor(Color.parseColor(totalFatColor));

                    String saturatedFatColor = saturatedFatRating(foodObject,userPreferences);
                    saturatedFat.setText(foodObject.getFoodSaturatedFat() + "g");
                    saturatedFat.setTextColor(Color.parseColor(saturatedFatColor));

                    String transFatColor = transFatRating(foodObject,userPreferences);
                    transFat.setText(foodObject.getFoodTransFat() + "g");
                    transFat.setTextColor(Color.parseColor(transFatColor));

                    String cholesterolColor = cholesterolRating(foodObject,userPreferences);
                    cholesterol.setText(foodObject.getFoodCholesterol() + "mg");
                    cholesterol.setTextColor(Color.parseColor(cholesterolColor));

                    String sodiumColor = sodiumRating(foodObject,userPreferences);
                    sodium.setText(foodObject.getFoodSodium() + "mg");
                    sodium.setTextColor(Color.parseColor(sodiumColor));

                    String totalCarbohydratesColor = totalCarbohydratesRating(foodObject,userPreferences);
                    totalCarbs.setText(foodObject.getFoodCarbohydrate() + "g");
                    totalCarbs.setTextColor(Color.parseColor(totalCarbohydratesColor));

                    String dietaryFibresColor = dietaryFibreRating(foodObject,userPreferences);
                    dietaryFibres.setText(foodObject.getFoodDietaryFibre() + "g");
                    dietaryFibres.setTextColor(Color.parseColor(dietaryFibresColor));

                    String sugarColor = sugarRating(foodObject,userPreferences);
                    totalSugars.setText(foodObject.getFoodTotalSugar() + "g");
                    totalSugars.setTextColor(Color.parseColor(sugarColor));

                    String proteinColor = proteinRating(foodObject,userPreferences);
                    protein.setText(foodObject.getFoodProtein() + "g");
                    protein.setTextColor(Color.parseColor(proteinColor));

                    String ironColor = ironRating(foodObject,userPreferences);
                    iron.setText(foodObject.getFoodIron() + "mg");
                    iron.setTextColor(Color.parseColor(ironColor));

                    String foodImageLink = foodObject.getFoodImageURL();
                    foodImageStorageReference = storage.getReference().child(foodImageLink);
                    Log.d("food image", "onComplete: " + foodImageStorageReference);
                    GlideApp.with(getApplicationContext())
                            .load(foodImageStorageReference)
                            .into(imageView); //TODO: implement placeholder
                }
            }
        });
        scan_button = findViewById(R.id.scan_button_single);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });

    }
    // Set the settings to create the scanning activity and eventually call the barLauncher
    public void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
    {
        // Check if we get a valid output from the barcode scanner
        if(result.getContents() != null)
        {
            // Prepare a new intent singleItemToScan so we can get the result from the previous
            // activity and store it to firstFoodItem
            FoodDatabaseObject firstFoodItem = foodObject;

            // Make a log of what we receive from the server
            String barcodeNum = result.getContents();

            // If firstFoodItem contains something, then we assume that we are now
            // scanning the second barcode. So we run this block of code.
            Intent intent = new Intent(SingleItemAnalyzeActivity.this, TwoItemCompareActivity.class);
            intent.putExtra(MainActivity.FIRSTBARCODEKEY, firstFoodItem); //Using putExtra, implement mPreferences next
            intent.putExtra(MainActivity.SECONDBARCODEKEY, barcodeNum);
            startActivity(intent);
            finish();
        }
    });
}
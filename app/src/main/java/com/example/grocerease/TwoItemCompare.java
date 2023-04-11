package com.example.grocerease;

import static com.example.grocerease.DoubleItemRating.caloriesSeed;
import static com.example.grocerease.DoubleItemRating.carbSeed;
import static com.example.grocerease.DoubleItemRating.cholesterolSeed;
import static com.example.grocerease.DoubleItemRating.dietarySeed;
import static com.example.grocerease.DoubleItemRating.ironSeed;
import static com.example.grocerease.DoubleItemRating.proteinSeed;
import static com.example.grocerease.DoubleItemRating.saturatedFatSeed;
import static com.example.grocerease.DoubleItemRating.sodiumSeed;
import static com.example.grocerease.DoubleItemRating.sugarSeed;
import static com.example.grocerease.DoubleItemRating.totalFatSeed;
import static com.example.grocerease.DoubleItemRating.transFatSeed;
import static com.example.grocerease.SingleItemRating.caloriesRating;
import static com.example.grocerease.SingleItemRating.cholesterolRating;
import static com.example.grocerease.SingleItemRating.dietaryFibreRating;
import static com.example.grocerease.SingleItemRating.ironRating;
import static com.example.grocerease.SingleItemRating.percCalculator;
import static com.example.grocerease.SingleItemRating.proteinRating;
import static com.example.grocerease.SingleItemRating.saturatedFatRating;
import static com.example.grocerease.SingleItemRating.sodiumRating;
import static com.example.grocerease.SingleItemRating.sugarRating;
import static com.example.grocerease.SingleItemRating.totalCarbohydratesRating;
import static com.example.grocerease.SingleItemRating.totalFatRating;
import static com.example.grocerease.SingleItemRating.transFatRating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.Objects;

public class TwoItemCompare extends AppCompatActivity {
    TextView itemName_1, company_1, mass_1, calories_1, percentage_1, totalfat_1, saturatedfat_1,
            transfat_1, cholesterol_1, sodium_1, totalcarbs_1, dietaryfibres_1, totalsugars_1, protein_1, iron_1;
    TextView itemName_2, company_2, mass_2, calories_2, percentage_2, totalfat_2, saturatedfat_2,
            transfat_2, cholesterol_2, sodium_2, totalcarbs_2, dietaryfibres_2, totalsugars_2, protein_2, iron_2;

    final String grams = "g";
    private StorageReference foodImageStorageReference;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    FoodDatabaseObject foodObject1;
    FoodDatabaseObject foodObject2;
    String barcodeNum2;
    Gson gson = new Gson();
    private UserDatabaseObject userObject;
    UserPreferencesObject userPreferences;
    private PreferencesHelper preferencesHelper;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TwoItemCompare.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.double_item_activity);

        // reading from preferences to obtain userObject
        preferencesHelper = new PreferencesHelper(this);
        String userObjectString = preferencesHelper.readString("userObject","error");
        String username = preferencesHelper.readString("username","error");
        userObject = gson.fromJson(userObjectString, UserDatabaseObject.class);

        storage = FirebaseStorage.getInstance();

        itemName_1 = findViewById(R.id.food_name_1);
        company_1 = findViewById(R.id.food_company_1);
        mass_1 = findViewById(R.id.food_mass_1);
        calories_1 = findViewById(R.id.calories_1);
        percentage_1 = findViewById(R.id.perc_1);
        totalfat_1 = findViewById(R.id.total_fats_1);
        saturatedfat_1 = findViewById(R.id.saturated_fats_1);
        transfat_1 = findViewById(R.id.trans_fat_1);
        cholesterol_1 = findViewById(R.id.cholesterol_1);
        sodium_1 = findViewById(R.id.sodium_1);
        totalcarbs_1 = findViewById(R.id.total_carbohydrates_1);
        dietaryfibres_1 = findViewById(R.id.dietary_fibres_1);
        totalsugars_1 = findViewById(R.id.total_sugars_1);
        protein_1 = findViewById(R.id.protein_1);
        iron_1 = findViewById(R.id.iron_1);
        ImageView food_image_1 = findViewById(R.id.food_image_1);

        itemName_2 = findViewById(R.id.food_name_2);
        company_2 = findViewById(R.id.food_company_2);
        mass_2 = findViewById(R.id.food_mass_2);
        calories_2 = findViewById(R.id.calories_2);
        percentage_2 = findViewById(R.id.perc_2);
        totalfat_2 = findViewById(R.id.total_fats_2);
        saturatedfat_2 = findViewById(R.id.saturated_fats_2);
        transfat_2 = findViewById(R.id.trans_fat_2);
        cholesterol_2 = findViewById(R.id.cholesterol_2);
        sodium_2 = findViewById(R.id.sodium_2);
        totalcarbs_2 = findViewById(R.id.total_carbohydrates_2);
        dietaryfibres_2 = findViewById(R.id.dietary_fibres_2);
        totalsugars_2 = findViewById(R.id.total_sugars_2);
        protein_2 = findViewById(R.id.protein_2);
        iron_2 = findViewById(R.id.iron_2);
        ImageView food_image_2 = findViewById(R.id.food_image_2);

        Intent intent = getIntent();
        foodObject1 = (FoodDatabaseObject) intent.getSerializableExtra(MainActivity.FIRSTBARCODEKEY);
        barcodeNum2 = intent.getStringExtra(MainActivity.SECONDBARCODEKEY);
        Log.i("TwoItemCompare", "Barcode received: "+ foodObject1.toString());
        Log.i("TwoItemCompare", "Barcode received: "+ barcodeNum2);

        UserHistoryObject userHistory = userObject.getUserHistory();
        userHistory.addToHistory(barcodeNum2);
        //making sure to update local userObject so that it updates the database correctly
        String jsonString = gson.toJson(userObject); // returns a Json String object
        preferencesHelper.writeString("userObject", jsonString);
        Log.d("userObject", "onCreate: "+userObject.getUserHistory().getFoodHistory());

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Food").child(barcodeNum2).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else if (task.getResult().getValue(Object.class) == null) {
                    Log.e("firebase", "Item does not exist in database");
                    Toast.makeText(TwoItemCompare.this, barcodeNum2.toString() + " is not in database", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(TwoItemCompare.this);
                    builder.setMessage("Would you like to add it to the database?").setTitle("Item does not exist in database");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent addActivityIntent = new Intent(TwoItemCompare.this, DatabaseAddActivity.class);
                            addActivityIntent.putExtra(MainActivity.FIRSTBARCODEKEY, barcodeNum2); // just because FIRSTBARCODEKEY is what add activity uses
                            startActivity(addActivityIntent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }

                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    foodObject2 = task.getResult().getValue(FoodDatabaseObject.class);
                    Log.i("TwoItemCompare", "Object1 from intent" + foodObject1.toString());
                    Log.i("TwoItemCompare", "Object2 from database" + foodObject2.toString());

                    userPreferences = userObject.getUserPreferences();

                    userHistory.addToHistory(barcodeNum2);
                    //making sure to update local userObject so that it updates the database correctly
                    String jsonString = gson.toJson(userObject); // returns a Json String object
                    preferencesHelper.writeString("userObject", jsonString);
                    Log.d("userObject", "onCreate: "+userObject.getUserHistory().getFoodHistory());
                    databaseReference.child("Users").child(username).child("userHistory").setValue(userHistory);

                    /** All compare functions */
                    String sugarBold_1 = sugarSeed(foodObject1,foodObject2,userPreferences)[0];
                    String sugarBold_2 = sugarSeed(foodObject1,foodObject2,userPreferences)[1];
                    String sodiumBold_1 = sodiumSeed(foodObject1,foodObject2,userPreferences)[0];
                    String sodiumBold_2 = sodiumSeed(foodObject1,foodObject2,userPreferences)[1];
                    String saturatedFatBold_1 = saturatedFatSeed(foodObject1,foodObject2,userPreferences)[0];
                    String saturatedFatBold_2 = saturatedFatSeed(foodObject1,foodObject2,userPreferences)[1];
                    String totalFatBold_1 = totalFatSeed(foodObject1,foodObject2,userPreferences)[0];
                    String totalFatBold_2 = totalFatSeed(foodObject1,foodObject2,userPreferences)[1];
                    String transFatBold_1 = transFatSeed(foodObject1,foodObject2,userPreferences)[0];
                    String transFatBold_2 = transFatSeed(foodObject1,foodObject2,userPreferences)[1];
                    String carbBold_1 = carbSeed(foodObject1,foodObject2,userPreferences)[0];
                    String carbBold_2 = carbSeed(foodObject1,foodObject2,userPreferences)[1];
                    String cholBold_1 = cholesterolSeed(foodObject1,foodObject2,userPreferences)[0];
                    String cholBold_2 = cholesterolSeed(foodObject1,foodObject2,userPreferences)[1];
                    String dietBold_1 = dietarySeed(foodObject1,foodObject2,userPreferences)[0];
                    String dietBold_2 = dietarySeed(foodObject1,foodObject2,userPreferences)[1];
                    String calBold_1 = caloriesSeed(foodObject1,foodObject2,userPreferences)[0];
                    String calBold_2 = caloriesSeed(foodObject1,foodObject2,userPreferences)[1];
                    String proteinBold_1 = proteinSeed(foodObject1,foodObject2,userPreferences)[0];
                    String proteinBold_2 = proteinSeed(foodObject1,foodObject2,userPreferences)[1];
                    String ironBold_1 = ironSeed(foodObject1,foodObject2,userPreferences)[0];
                    String ironBold_2 = ironSeed(foodObject1,foodObject2,userPreferences)[1];

                    /** FoodObject1 */
                    itemName_1.setText(foodObject1.getFoodName());
                    company_1.setText(foodObject1.getFoodCompany());
                    mass_1.setText(foodObject1.getFoodMass() + "g");

                    String caloriesColor_1 =  caloriesRating(foodObject1,userPreferences);
                    calories_1.setText(foodObject1.getFoodCalories() + "kcal");
                    calories_1.setTextColor(Color.parseColor(caloriesColor_1));
                    if (Objects.equals(calBold_1, "BOLD")) {
                        calories_1.setTypeface(calories_1.getTypeface(), Typeface.BOLD);
                    } else {
                        calories_1.setTypeface(calories_1.getTypeface(), Typeface.NORMAL);
                    }

                    String perc_1 = percCalculator(foodObject1,userPreferences);
                    percentage_1.setText(perc_1 + "%");

                    String totalFatColor_1 = totalFatRating(foodObject1,userPreferences);
                    totalfat_1.setText(foodObject1.getFoodTotalFat() + "g");
                    totalfat_1.setTextColor(Color.parseColor(totalFatColor_1));
                    if (Objects.equals(totalFatBold_1, "BOLD")) {
                        totalfat_1.setTypeface(totalfat_1.getTypeface(), Typeface.BOLD);
                    } else {
                        totalfat_1.setTypeface(totalfat_1.getTypeface(), Typeface.NORMAL);
                    }

                    String saturatedFatColor_1 = saturatedFatRating(foodObject1,userPreferences);
                    saturatedfat_1.setText(foodObject1.getFoodSaturatedFat() + "g");
                    saturatedfat_1.setTextColor(Color.parseColor(saturatedFatColor_1));
                    if (Objects.equals(saturatedFatBold_1, "BOLD")) {
                        saturatedfat_1.setTypeface(saturatedfat_1.getTypeface(), Typeface.BOLD);
                    } else {
                        saturatedfat_1.setTypeface(saturatedfat_1.getTypeface(), Typeface.NORMAL);
                    }

                    String transFatColor_1 = transFatRating(foodObject1,userPreferences);
                    transfat_1.setText(foodObject1.getFoodTransFat() + "g");
                    transfat_1.setTextColor(Color.parseColor(transFatColor_1));
                    if (Objects.equals(transFatBold_1, "BOLD")) {
                        transfat_1.setTypeface(transfat_1.getTypeface(), Typeface.BOLD);
                    } else {
                        transfat_1.setTypeface(transfat_1.getTypeface(), Typeface.NORMAL);
                    }

                    String cholesterolColor_1 = cholesterolRating(foodObject1,userPreferences);
                    cholesterol_1.setText(foodObject1.getFoodCholesterol() + "mg");
                    cholesterol_1.setTextColor(Color.parseColor(cholesterolColor_1));
                    if (Objects.equals(cholBold_1, "BOLD")) {
                        cholesterol_1.setTypeface(cholesterol_1.getTypeface(), Typeface.BOLD);
                    } else {
                        cholesterol_1.setTypeface(cholesterol_1.getTypeface(), Typeface.NORMAL);
                    }

                    String sodiumColor_1 = sodiumRating(foodObject1,userPreferences);
                    sodium_1.setText(foodObject1.getFoodSodium() + "mg");
                    sodium_1.setTextColor(Color.parseColor(sodiumColor_1));
                    if (Objects.equals(sodiumBold_1, "BOLD")) {
                        sodium_1.setTypeface(sodium_1.getTypeface(), Typeface.BOLD);
                    } else {
                        sodium_1.setTypeface(sodium_1.getTypeface(), Typeface.NORMAL);
                    }

                    String totalCarbohydratesColor_1 = totalCarbohydratesRating(foodObject1,userPreferences);
                    totalcarbs_1.setText(foodObject1.getFoodCarbohydrate() + "g");
                    totalcarbs_1.setTextColor(Color.parseColor(totalCarbohydratesColor_1));
                    if (Objects.equals(carbBold_1, "BOLD")) {
                        totalcarbs_1.setTypeface(totalcarbs_1.getTypeface(), Typeface.BOLD);
                    } else {
                        totalcarbs_1.setTypeface(totalcarbs_1.getTypeface(), Typeface.NORMAL);
                    }

                    String dietaryFibresColor_1 = dietaryFibreRating(foodObject1,userPreferences);
                    dietaryfibres_1.setText(foodObject1.getFoodDietaryFibre() + "g");
                    dietaryfibres_1.setTextColor(Color.parseColor(dietaryFibresColor_1));
                    if (Objects.equals(dietBold_1, "BOLD")) {
                        dietaryfibres_1.setTypeface(dietaryfibres_1.getTypeface(), Typeface.BOLD);
                    } else {
                        dietaryfibres_1.setTypeface(dietaryfibres_1.getTypeface(), Typeface.NORMAL);
                    }

                    String sugarColor_1 = sugarRating(foodObject1,userPreferences);
                    totalsugars_1.setText(foodObject1.getFoodTotalSugar() + "g");
                    totalsugars_1.setTextColor(Color.parseColor(sugarColor_1));
                    if (Objects.equals(sugarBold_1, "BOLD")) {
                        totalsugars_1.setTypeface(totalsugars_1.getTypeface(), Typeface.BOLD);
                    } else {
                        totalsugars_1.setTypeface(totalsugars_1.getTypeface(), Typeface.NORMAL);
                    }

                    String proteinColor_1 = proteinRating(foodObject1,userPreferences);
                    protein_1.setText(foodObject1.getFoodProtein() + "g");
                    protein_1.setTextColor(Color.parseColor(proteinColor_1));
                    if (Objects.equals(proteinBold_1, "BOLD")) {
                        protein_1.setTypeface(protein_1.getTypeface(), Typeface.BOLD);
                    } else {
                        protein_1.setTypeface(protein_1.getTypeface(), Typeface.NORMAL);
                    }

                    String ironColor_1 = ironRating(foodObject1,userPreferences);
                    iron_1.setText(foodObject1.getFoodIron() + "mg");
                    iron_1.setTextColor(Color.parseColor(ironColor_1));
                    if (Objects.equals(ironBold_1, "BOLD")) {
                        iron_1.setTypeface(iron_1.getTypeface(), Typeface.BOLD);
                    } else {
                        iron_1.setTypeface(iron_1.getTypeface(), Typeface.NORMAL);
                    }

                    String foodImageLink1 = foodObject1.getFoodImageURL();
                    foodImageStorageReference = storage.getReference().child(foodImageLink1);
                    GlideApp.with(getApplicationContext())
                            .load(foodImageStorageReference)
                            .into(food_image_1); //implement placeholder


                    /** FoodObject2 */
                    itemName_2.setText(foodObject2.getFoodName());
                    company_2.setText(foodObject2.getFoodCompany());
                    mass_2.setText(foodObject2.getFoodMass() + "g");

                    String caloriesColor_2 =  caloriesRating(foodObject2,userPreferences);
                    calories_2.setText(foodObject2.getFoodCalories() + "kcal");
                    calories_2.setTextColor(Color.parseColor(caloriesColor_2));
                    if (Objects.equals(calBold_2, "BOLD")) {
                        calories_2.setTypeface(calories_2.getTypeface(), Typeface.BOLD);
                    } else {
                        calories_2.setTypeface(calories_2.getTypeface(), Typeface.NORMAL);
                    }

                    String perc_2 = percCalculator(foodObject2,userPreferences);
                    percentage_2.setText(perc_2 + "%");

                    String totalFatColor_2 = totalFatRating(foodObject2,userPreferences);
                    totalfat_2.setText(foodObject2.getFoodTotalFat() + "g");
                    totalfat_2.setTextColor(Color.parseColor(totalFatColor_2));
                    if (Objects.equals(totalFatBold_2, "BOLD")) {
                        totalfat_2.setTypeface(totalfat_2.getTypeface(), Typeface.BOLD);
                    } else {
                        totalfat_2.setTypeface(totalfat_2.getTypeface(), Typeface.NORMAL);
                    }

                    String saturatedFatColor_2 = saturatedFatRating(foodObject2,userPreferences);
                    saturatedfat_2.setText(foodObject2.getFoodSaturatedFat() + "g");
                    saturatedfat_2.setTextColor(Color.parseColor(saturatedFatColor_2));
                    if (Objects.equals(saturatedFatBold_2, "BOLD")) {
                        saturatedfat_2.setTypeface(saturatedfat_2.getTypeface(), Typeface.BOLD);
                    } else {
                        saturatedfat_2.setTypeface(saturatedfat_2.getTypeface(), Typeface.NORMAL);
                    }

                    String transFatColor_2 = transFatRating(foodObject2,userPreferences);
                    transfat_2.setText(foodObject2.getFoodTransFat() + "g");
                    transfat_2.setTextColor(Color.parseColor(transFatColor_2));
                    if (Objects.equals(transFatBold_2, "BOLD")) {
                        transfat_2.setTypeface(transfat_2.getTypeface(), Typeface.BOLD);
                    } else {
                        transfat_2.setTypeface(transfat_2.getTypeface(), Typeface.NORMAL);
                    }

                    String cholesterolColor_2 = cholesterolRating(foodObject2,userPreferences);
                    cholesterol_2.setText(foodObject2.getFoodCholesterol() + "mg");
                    cholesterol_2.setTextColor(Color.parseColor(cholesterolColor_2));
                    if (Objects.equals(cholBold_2, "BOLD")) {
                        cholesterol_2.setTypeface(cholesterol_2.getTypeface(), Typeface.BOLD);
                    } else {
                        cholesterol_2.setTypeface(cholesterol_2.getTypeface(), Typeface.NORMAL);
                    }

                    String sodiumColor_2 = sodiumRating(foodObject2,userPreferences);
                    sodium_2.setText(foodObject2.getFoodSodium() + "mg");
                    sodium_2.setTextColor(Color.parseColor(sodiumColor_2));
                    if (Objects.equals(sodiumBold_2, "BOLD")) {
                        sodium_2.setTypeface(sodium_2.getTypeface(), Typeface.BOLD);
                    } else {
                        sodium_2.setTypeface(sodium_2.getTypeface(), Typeface.NORMAL);
                    }

                    String totalCarbohydratesColor_2 = totalCarbohydratesRating(foodObject2,userPreferences);
                    totalcarbs_2.setText(foodObject2.getFoodCarbohydrate() + "g");
                    totalcarbs_2.setTextColor(Color.parseColor(totalCarbohydratesColor_2));
                    if (Objects.equals(carbBold_2, "BOLD")) {
                        totalcarbs_2.setTypeface(totalcarbs_2.getTypeface(), Typeface.BOLD);
                    } else {
                        totalcarbs_2.setTypeface(totalcarbs_2.getTypeface(), Typeface.NORMAL);
                    }

                    String dietaryFibresColor_2 = dietaryFibreRating(foodObject2,userPreferences);
                    dietaryfibres_2.setText(foodObject2.getFoodDietaryFibre() + "g");
                    dietaryfibres_2.setTextColor(Color.parseColor(dietaryFibresColor_2));
                    if (Objects.equals(dietBold_2, "BOLD")) {
                        dietaryfibres_2.setTypeface(dietaryfibres_2.getTypeface(), Typeface.BOLD);
                    } else {
                        dietaryfibres_2.setTypeface(dietaryfibres_2.getTypeface(), Typeface.NORMAL);
                    }

                    String sugarColor_2 = sugarRating(foodObject2,userPreferences);
                    totalsugars_2.setText(foodObject2.getFoodTotalSugar() + "g");
                    totalsugars_2.setTextColor(Color.parseColor(sugarColor_2));
                    if (Objects.equals(sugarBold_2, "BOLD")) {
                        totalsugars_2.setTypeface(totalsugars_2.getTypeface(), Typeface.BOLD);
                    } else {
                        totalsugars_2.setTypeface(totalsugars_2.getTypeface(), Typeface.NORMAL);
                    }

                    String proteinColor_2 = proteinRating(foodObject2,userPreferences);
                    protein_2.setText(foodObject2.getFoodProtein() + "g");
                    protein_2.setTextColor(Color.parseColor(proteinColor_2));
                    if (Objects.equals(proteinBold_2, "BOLD")) {
                        protein_2.setTypeface(protein_2.getTypeface(), Typeface.BOLD);
                    } else {
                        protein_2.setTypeface(protein_2.getTypeface(), Typeface.NORMAL);
                    }

                    String ironColor_2 = ironRating(foodObject2,userPreferences);
                    iron_2.setText(foodObject2.getFoodIron() + "mg");
                    iron_2.setTextColor(Color.parseColor(ironColor_2));
                    if (Objects.equals(ironBold_2, "BOLD")) {
                        iron_2.setTypeface(iron_2.getTypeface(), Typeface.BOLD);
                    } else {
                        iron_2.setTypeface(iron_2.getTypeface(), Typeface.NORMAL);
                    }

                    String foodImageLink2 = foodObject2.getFoodImageURL();
                    foodImageStorageReference = storage.getReference().child(foodImageLink2);
                    GlideApp.with(getApplicationContext())
                            .load(foodImageStorageReference)
                            .into(food_image_2); //implement placeholder
                }
            }
        });
    }
}
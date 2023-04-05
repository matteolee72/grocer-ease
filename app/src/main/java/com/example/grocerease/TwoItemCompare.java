package com.example.grocerease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TwoItemCompare.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.double_item_activity);

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
                        }

                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    foodObject2 = task.getResult().getValue(FoodDatabaseObject.class);
                    Log.i("TwoItemCompare", "Object1 from intent" + foodObject1.toString());
                    Log.i("TwoItemCompare", "Object2 from database" + foodObject2.toString());

                    itemName_1.setText(foodObject1.getFoodName());
                    company_1.setText(foodObject1.getFoodCompany());
                    mass_1.setText(foodObject1.getFoodMass() + "g");
                    calories_1.setText(foodObject1.getFoodCalories() + "kcal");
                    /**TODO: update function
                    percentage_1.setText(foodObject1.getFoodPercentage() + "%");
                     */
                    totalfat_1.setText(foodObject1.getFoodTotalFat() + "g");
                    cholesterol_1.setText(foodObject1.getFoodCholesterol() + "mg");
                    sodium_1.setText(foodObject1.getFoodSodium() + "mg");
                    totalcarbs_1.setText(foodObject1.getFoodCarbohydrate() + "g");
                    dietaryfibres_1.setText(foodObject1.getFoodDietaryFibre() + "g");
                    totalsugars_1.setText(foodObject1.getFoodTotalSugar() + "g");
                    protein_1.setText(foodObject1.getFoodProtein() + "g");
                    iron_1.setText(foodObject1.getFoodIron() + "mg");
                    String foodImageLink1 = foodObject1.getFoodImageURL();
                    foodImageStorageReference = storage.getReference().child(foodImageLink1);
                    GlideApp.with(getApplicationContext())
                            .load(foodImageStorageReference)
                            .into(food_image_1); //implement placeholder

                    itemName_2.setText(foodObject2.getFoodName());
                    company_2.setText(foodObject2.getFoodCompany());
                    mass_2.setText(foodObject2.getFoodMass() + "g");
                    calories_2.setText(foodObject2.getFoodCalories() + "kcal");
                    /**TODO: update function
                    percentage_2.setText(foodObject2.getFoodPercentage() + "%");
                     */
                    totalfat_2.setText(foodObject2.getFoodTotalFat() + "g");
                    cholesterol_2.setText(foodObject2.getFoodCholesterol() + "mg");
                    sodium_2.setText(foodObject2.getFoodSodium() + "mg");
                    totalcarbs_2.setText(foodObject2.getFoodCarbohydrate() + "g");
                    dietaryfibres_2.setText(foodObject2.getFoodDietaryFibre() + "g");
                    totalsugars_2.setText(foodObject2.getFoodTotalSugar() + "g");
                    protein_2.setText(foodObject2.getFoodProtein() + "g");
                    iron_2.setText(foodObject2.getFoodIron() + "mg");
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
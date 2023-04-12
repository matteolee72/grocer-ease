package com.example.grocerease.Utils;

import com.example.grocerease.Objects.FoodDatabaseObject;
import com.example.grocerease.Objects.UserPreferencesObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;

/** SingleItemRating will take on userpreferences and foodobjects to determine whether to shift the threshold of each
 *  Each nutrient's threshold will be shifted by 10% or wtv we decide. The functions here will be applied within singleitemanalyze*/

public class SingleItemRating {

    /** High Blood Pressure - Avoid Sodium, Saturated Fat, Total Sugars

    High Blood Sugar - Avoid Saturated Fat, Total Sugars

    High Cholesterol - Avoid all Fats, Total Carbohydrates and Sugars, better dietary fibres

    Wanting to Bulk - More Protein, Calories */

    private SingleItemRating(){};  // Private No Arg Constructor to prevent instantiation

    // per 100g
    // low - 5g and below, high - 22.5g
    public static String percCalculator(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference){
        double perc;
        int womanCalories = 2000;
        int manCalories = 2500;
        double foodcalories = Double.parseDouble(foodObject1.getFoodCalories());
        String gender = userPreference.getSex();
        if (gender.equals("Female")) {
            perc = Math.round(foodcalories / womanCalories * 100);
        }
        else {
            perc = Math.round(foodcalories / manCalories * 100);
        }
        return Double.toString(perc);
    }

    public static String sodiumRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 400;
        double lowThreshold = 120;

        String sodiumColor;
        double sodiumLevel = Double.parseDouble(foodObject1.getFoodSodium());
        // Low 120mg, 400mg
        if (userPreference.getBloodPressure().equals("Low")) {
            // Unhealthy levels, shift lower threshold higher
            if (sodiumLevel < lowThreshold - 90 || sodiumLevel > highThreshold + 200) {
                sodiumColor = "#D60000";
                // maintain, normal threshold
            } else if (sodiumLevel >= lowThreshold && sodiumLevel < highThreshold) {
                sodiumColor = "#000000";
                // good for user to increase blood pressure
            } else {
                sodiumColor = "#00A877";
            }

        } else if (userPreference.getBloodPressure().equals("High")) {
            // Unhealthy levels, shift upper threshold lower
            if (sodiumLevel > highThreshold - 100) {
                sodiumColor = "#D60000";
                // maintain normal threshold
            } else if (sodiumLevel >= lowThreshold - 80 && sodiumLevel <= highThreshold - 100) {
                sodiumColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                sodiumColor = "#00A877";
            }
        } else {
            // Unhealthy levels, shift upper threshold lower
            if (sodiumLevel > highThreshold) {
                sodiumColor = "#D60000";
                // maintain normal threshold
            } else if (sodiumLevel >= lowThreshold) {
                sodiumColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                sodiumColor = "#00A877";
            }
        } return sodiumColor;
    }



    public static String sugarRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 22.5;
        double lowThreshold = 5;

        String sugarColor;
        double sugarlevel = Double.parseDouble(foodObject1.getFoodTotalSugar());
        // Low - 8, 25.5 (g)
        if (userPreference.getBloodSugarLevels().equals("Low")) {
            // Unhealthy levels, shift lower threshold higher
            if (sugarlevel < lowThreshold + 3 || sugarlevel > highThreshold) {
                sugarColor = "#D60000";
                // maintain, normal threshold
            } else if (sugarlevel >= lowThreshold+3 && sugarlevel < highThreshold - 6) {
                sugarColor = "#000000";
                // good for user to increase blood sugar
            } else {
                sugarColor = "#00A877";
            }
        } else if (userPreference.getBloodSugarLevels().equals("High")) {
            // Unhealthy levels, shift upper threshold lower
            if (sugarlevel > highThreshold - 7.5) {
                sugarColor = "#D60000";
                // maintain normal threshold
            } else if (sugarlevel >= lowThreshold - 2 && sugarlevel < highThreshold - 7.5) {
                sugarColor = "#000000";
                // good for user to decrease blood sugar
            } else {
                sugarColor = "#00A877";
            }
        } else {
            // Unhealthy levels, shift upper threshold lower
            if (sugarlevel > highThreshold) {
                sugarColor = "#D60000";
                // maintain normal threshold
            } else if (sugarlevel >= lowThreshold && sugarlevel < highThreshold) {
                sugarColor = "#000000";
                // good for user to decrease blood sugar
            } else {
                sugarColor = "#00A877";
            }
        } return sugarColor;
    }

    public static String saturatedFatRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 5;
        double lowThreshold = 1.5;

        String saturatedFatColor;
        double saturatedFatLevel = Double.parseDouble(foodObject1.getFoodSaturatedFat());

        if (userPreference.getBloodPressure().equals("Low") || userPreference.getBloodSugarLevels().equals("Low")) {
            // Unhealthy levels, shift lower threshold higher
            if (saturatedFatLevel > highThreshold + 1) {
                saturatedFatColor = "#D60000";
                // maintain, normal threshold
            } else if (saturatedFatLevel >= lowThreshold + 1 && saturatedFatLevel < highThreshold + 1) {
                saturatedFatColor = "#000000";
                // good for user to increase blood pressure
            } else {
                saturatedFatColor = "#00A877";
            }

        } else if (userPreference.getBloodPressure().equals("High")  || userPreference.getBloodSugarLevels().equals("High") || userPreference.getHighCholesterol().equals("Yes")) {
            // Unhealthy levels, shift upper threshold lower
            if (saturatedFatLevel > highThreshold - 3) {
                saturatedFatColor = "#D60000";
                // maintain normal threshold
            } else if (saturatedFatLevel >= lowThreshold - 1 && saturatedFatLevel <= highThreshold - 3) {
                saturatedFatColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                saturatedFatColor = "#00A877";
            }
        } else {
            // Unhealthy levels, shift upper threshold lower
            if (saturatedFatLevel > highThreshold) {
                saturatedFatColor = "#D60000";
                // maintain normal threshold
            } else if (saturatedFatLevel >= lowThreshold) {
                saturatedFatColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                saturatedFatColor = "#00A877";
            }
        } return saturatedFatColor;
    }

    public static String transFatRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 2;
        double lowThreshold = 0.5;

        String transFatColor;
        double transFatLevel = Double.parseDouble(foodObject1.getFoodTransFat());

        if (transFatLevel > highThreshold) {
                transFatColor = "#D60000";
                // maintain normal threshold
            } else if (transFatLevel >= lowThreshold) {
                transFatColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                transFatColor = "#00A877";
            }
        return transFatColor;
    }

    public static String totalFatRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 12;
        double lowThreshold = 3;

        String totalFatColor;
        double totalFatLevel = Double.parseDouble(foodObject1.getFoodTotalFat());

        if (userPreference.getHighCholesterol().equals("No")) {
            // Unhealthy levels, shift lower threshold higher
            if (totalFatLevel > highThreshold + 3) {
                totalFatColor = "#D60000";
                // maintain, normal threshold
            } else if (totalFatLevel >= lowThreshold + 1 && totalFatLevel < highThreshold + 3) {
                totalFatColor = "#000000";
                // good for user to increase blood pressure
            } else {
                totalFatColor = "#00A877";
            }

        } else if (userPreference.getHighCholesterol().equals("Yes")) {
            // Unhealthy levels, shift upper threshold lower
            if (totalFatLevel > highThreshold - 5) {
                totalFatColor = "#D60000";
                // maintain normal threshold
            } else if (totalFatLevel >= lowThreshold - 2 && totalFatLevel <= highThreshold - 5) {
                totalFatColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                totalFatColor = "#00A877";
            }
        } else {
            // Unhealthy levels, shift upper threshold lower
            if (totalFatLevel > highThreshold) {
                totalFatColor = "#D60000";
                // maintain normal threshold
            } else if (totalFatLevel >= lowThreshold) {
                totalFatColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                totalFatColor = "#00A877";
            }
        } return totalFatColor;
    }

    public static String totalCarbohydratesRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 20;
        double lowThreshold = 10;

        String totalCarbohydratesColor;
        double totalCarbohydratesLevel = Double.parseDouble(foodObject1.getFoodCarbohydrate());
        if (userPreference.getHighCholesterol().equals("No")) {
            // Unhealthy levels, shift lower threshold higher
            if (totalCarbohydratesLevel > highThreshold + 5) {
                totalCarbohydratesColor = "#D60000";
                // maintain, normal threshold
            } else if (totalCarbohydratesLevel >= lowThreshold + 2 && totalCarbohydratesLevel < highThreshold + 5) {
                totalCarbohydratesColor = "#000000";
                // good for user to increase blood pressure
            } else {
                totalCarbohydratesColor = "#00A877";
            }

        } else if (userPreference.getHighCholesterol().equals("Yes")) {
            // Unhealthy levels, shift upper threshold lower
            if (totalCarbohydratesLevel > highThreshold - 5) {
                totalCarbohydratesColor = "#D60000";
                // maintain normal threshold
            } else if (totalCarbohydratesLevel >= lowThreshold - 2 && totalCarbohydratesLevel <= highThreshold - 5) {
                totalCarbohydratesColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                totalCarbohydratesColor = "#00A877";
            }
        } else {
            // Unhealthy levels, shift upper threshold lower
            if (totalCarbohydratesLevel > highThreshold) {
                totalCarbohydratesColor = "#D60000";
                // maintain normal threshold
            } else if (totalCarbohydratesLevel >= lowThreshold) {
                totalCarbohydratesColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                totalCarbohydratesColor = "#00A877";
            }
        } return totalCarbohydratesColor;
    }

    public static String cholesterolRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 15;
        double lowThreshold = 5;

        String cholesterolColor;
        double cholesterolLevel = Double.parseDouble(foodObject1.getFoodCholesterol());
        if (userPreference.getHighCholesterol().equals("No")) {
            // Unhealthy levels, shift lower threshold higher
            if (cholesterolLevel > highThreshold + 3) {
                cholesterolColor = "#D60000";
                // maintain, normal threshold
            } else if (cholesterolLevel >= lowThreshold + 2 && cholesterolLevel < highThreshold + 3) {
                cholesterolColor = "#000000";
                // good for user to increase blood pressure
            } else {
                cholesterolColor = "#00A877";
            }

        } else if (userPreference.getHighCholesterol().equals("Yes")) {
            // Unhealthy levels, shift upper threshold lower
            if (cholesterolLevel > highThreshold - 5) {
                cholesterolColor = "#D60000";
                // maintain normal threshold
            } else if (cholesterolLevel >= lowThreshold - 2 && cholesterolLevel <= highThreshold - 5) {
                cholesterolColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                cholesterolColor = "#00A877";
            }
        } else {
            // Unhealthy levels, shift upper threshold lower
            if (cholesterolLevel > highThreshold) {
                cholesterolColor = "#D60000";
                // maintain normal threshold
            } else if (cholesterolLevel >= lowThreshold) {
                cholesterolColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                cholesterolColor = "#00A877";
            }
        } return cholesterolColor;
    }

    public static String dietaryFibreRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 5;
        double lowThreshold = 1;

        String dietaryFibreColor;
        double dietaryFibreLevel = Double.parseDouble(foodObject1.getFoodDietaryFibre());
        if (userPreference.getHighCholesterol().equals("No")) {
            // Unhealthy levels, shift lower threshold higher
            if (dietaryFibreLevel < lowThreshold + 1 || dietaryFibreLevel > highThreshold + 2) {
                dietaryFibreColor = "#D60000";
                // maintain, normal threshold
            } else if (dietaryFibreLevel >= lowThreshold + 1 && dietaryFibreLevel < highThreshold) {
                dietaryFibreColor = "#000000";
                // good for user to increase blood pressure
            } else {
                dietaryFibreColor = "#00A877";
            }

        } else if (userPreference.getHighCholesterol().equals("Yes")) {
            // Unhealthy levels, shift upper threshold lower
            if (dietaryFibreLevel < lowThreshold || dietaryFibreLevel > highThreshold - 1) {
                dietaryFibreColor = "#D60000";
                // maintain normal threshold
            } else if (dietaryFibreLevel >= lowThreshold && dietaryFibreLevel <= highThreshold - 3) {
                dietaryFibreColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                dietaryFibreColor = "#00A877";
            }
        } else {
            // Unhealthy levels, shift upper threshold lower
            if (dietaryFibreLevel > highThreshold) {
                dietaryFibreColor = "#D60000";
                // maintain normal threshold
            } else if (dietaryFibreLevel >= lowThreshold) {
                dietaryFibreColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                dietaryFibreColor = "#00A877";
            }
        } return dietaryFibreColor;
    }

    public static String caloriesRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 300;
        double lowThreshold = 100;

        String caloriesColor;
        double caloriesLevel = Double.parseDouble(foodObject1.getFoodCalories());
        if (userPreference.getWeightGoals().equals("Gain Weight")) {
            // Unhealthy levels, shift lower threshold higher
            if (caloriesLevel > highThreshold + 100 || caloriesLevel < lowThreshold) {
                caloriesColor = "#D60000";
                // maintain, normal threshold
            } else if (caloriesLevel >= lowThreshold && caloriesLevel < highThreshold - 100) {
                caloriesColor = "#000000";
                // good for user to gain weight
            } else {
                caloriesColor = "#00A877";
            }

        } else if (userPreference.getWeightGoals().equals("Lose Weight")) {
            // Unhealthy levels, shift upper threshold lower
            if (caloriesLevel > highThreshold - 100 || caloriesLevel < lowThreshold - 50) {
                caloriesColor = "#D60000";
                // maintain normal threshold
            } else if (caloriesLevel >= lowThreshold + 50 && caloriesLevel <= highThreshold - 50) {
                caloriesColor = "#000000";
                // good for user to reduce weight
            } else {
                caloriesColor = "#00A877";
            }
        } else {
            // Unhealthy levels, shift upper threshold lower
            if (caloriesLevel > highThreshold) {
                caloriesColor = "#D60000";
                // maintain normal threshold
            } else if (caloriesLevel >= lowThreshold && caloriesLevel <= highThreshold) {
                caloriesColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                caloriesColor = "#00A877";
            }
        } return caloriesColor;
    }

    public static String proteinRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 30;
        double lowThreshold = 15;

        String proteinColor;
        double proteinLevel = Double.parseDouble(foodObject1.getFoodProtein());
        if (userPreference.getWeightGoals().equals("Gain Weight")) {
            // Unhealthy levels, shift lower threshold higher
            if (proteinLevel > highThreshold + 100 || proteinLevel < lowThreshold) {
                proteinColor = "#D60000";
                // maintain, normal threshold
            } else if (proteinLevel >= lowThreshold && proteinLevel < highThreshold) {
                proteinColor = "#000000";
                // good for user to increase blood pressure
            } else {
                proteinColor = "#00A877";
            }

        } else if (userPreference.getWeightGoals().equals("Lose Weight")) {
            // Unhealthy levels, shift upper threshold lower
            if (proteinLevel > highThreshold || proteinLevel < lowThreshold - 10) {
                proteinColor = "#D60000";
                // maintain normal threshold
            } else if (proteinLevel >= lowThreshold) {
                proteinColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                proteinColor = "#00A877";
            }
        } else {
            // Unhealthy levels, shift upper threshold lower
            if (proteinLevel > highThreshold || proteinLevel < lowThreshold) {
                proteinColor = "#D60000";
                // maintain normal threshold
            } else if (proteinLevel >= lowThreshold) {
                proteinColor = "#000000";
                // good for user to decrease blood pressure
            } else {
                proteinColor = "#00A877";
            }
        } return proteinColor;
    }

    public static String ironRating(FoodDatabaseObject foodObject1, UserPreferencesObject userPreference) {

        double highThreshold = 5;
        double lowThreshold = 0.5;

        String ironColor;
        double ironLevel = Double.parseDouble(foodObject1.getFoodIron());

        if (ironLevel > highThreshold) {
            ironColor = "#D60000";
            // maintain normal threshold
        } else if (ironLevel <= lowThreshold) {
            ironColor = "#000000";
            // good for user to decrease blood pressure
        } else {
            ironColor = "#00A877";
        }
        return ironColor;
    }
}

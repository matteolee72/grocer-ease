package com.example.grocerease.Utils;

import com.example.grocerease.Objects.FoodDatabaseObject;
import com.example.grocerease.Objects.UserPreferencesObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;

public class DoubleItemRating{

    private DatabaseReference databaseReference;
    private FirebaseStorage storage;

    static FoodDatabaseObject foodObject1;
    static FoodDatabaseObject foodObject2;

    UserPreferencesObject userPreference;

    /** works such that for example if bloodpressure is high then run accordingly,this should be done ins scanacitvity*/

    /** If foodobject1 better, return 1, if foodobject2 higher, return 2. put both into twoitemcompare*/

    public static int compareObjects(double obj1, double obj2) {
        if (obj1 > obj2) {
            return 1;
        } else if (obj2 > obj1){
            return 2;
        } else {
            return 0;
        }
    }public static boolean highIsBetter(String[] highConditions, String condition){
         ArrayList<String> highConditionsList = new ArrayList<>(Arrays.asList(highConditions));
        if (highConditionsList.contains(condition)){
            return true;
        }
        return false;
    }
    public static String[] Seed(String nutrient,
                                String foodNutrient1,
                                String foodNutrient2,
                                String[] highConditions,
                                String condition){

        boolean highBetter = highIsBetter(highConditions,condition);

        String line_1;
        String line_2;
        double level_1 = Double.parseDouble(foodNutrient1);
        double level_2 = Double.parseDouble(foodNutrient2);
        int result = compareObjects(level_1,level_2);

        //default case: low is better
        //case: high is better
        if (result == 0){
            line_1 = "NORMAL";
            line_2 = "NORMAL";
        }
        else if(result == 1){
            line_1 = "NORMAL";
            line_2 = "BOLD";
        }
        else{ // result is 2
            line_1 = "BOLD";
            line_2 = "NORMAL";
        }
        if (highBetter == true){
            return new String[]{line_2,line_1};
        }
        return new String[]{line_1, line_2};
    }

    public static String[] sugarSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String sugarline_1;
        String sugarline_2;
        double sugarlevel_1 = Double.parseDouble(foodObject1.getFoodTotalSugar());
        double sugarlevel_2 = Double.parseDouble(foodObject2.getFoodTotalSugar());
        int sugarres = compareObjects(sugarlevel_1, sugarlevel_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (sugarres == 0) {
            sugarline_1 = "NORMAL";
            sugarline_2 = "NORMAL";
        } else {
            if (userPreference.getBloodSugarLevels().equals("Low")) { // if i have low bloodsugar, i want more sugar
                if (sugarres == 1) {
                    sugarline_1 = "BOLD";
                    sugarline_2 = "NORMAL";
                } else {
                    sugarline_1 = "NORMAL";
                    sugarline_2 = "BOLD";
                }
            } else {
                // high blood sugar and normal is the same, we want minimal sugar in food
                if (sugarres == 1) {
                    sugarline_1 = "NORMAL";
                    sugarline_2 = "BOLD";
                } else {
                    sugarline_1 = "BOLD";
                    sugarline_2 = "NORMAL";
                }
            }
        }
        return new String[]{sugarline_1, sugarline_2};
    }

    public static String[] sodiumSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String sodiumline_1;
        String sodiumline_2;
        double sodiumlevel_1 = Double.parseDouble(foodObject1.getFoodSodium());
        double sodiumlevel_2 = Double.parseDouble(foodObject2.getFoodSodium());
        int sodiumres = compareObjects(sodiumlevel_1, sodiumlevel_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (sodiumres == 0) {
            sodiumline_1 = "NORMAL";
            sodiumline_2 = "NORMAL";
        } else {
            if (userPreference.getBloodPressure().equals("Low")) { // if i have low bloodsugar, i want more sugar
                if (sodiumres == 1) {
                    sodiumline_1 = "BOLD";
                    sodiumline_2 = "NORMAL";
                } else {
                    sodiumline_1 = "NORMAL";
                    sodiumline_2 = "BOLD";
                }
            } else {
                // high blood sugar and normal is the same, we want minimal sodium in food
                if (sodiumres == 1) {
                    sodiumline_1 = "NORMAL";
                    sodiumline_2 = "BOLD";
                } else {
                    sodiumline_1 = "BOLD";
                    sodiumline_2 = "NORMAL";
                }
            }
        }
        return new String[]{sodiumline_1, sodiumline_2};
    }
    public static String[] transFatSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String transFatline_1;
        String transFatline_2;
        double transFatlevel_1 = Double.parseDouble(foodObject1.getFoodTransFat());
        double transFatlevel_2 = Double.parseDouble(foodObject2.getFoodTransFat());
        int transFatres = compareObjects(transFatlevel_1, transFatlevel_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (transFatres == 0) {
            transFatline_1 = "NORMAL";
            transFatline_2 = "NORMAL";
        } else if (transFatres == 1) {
            transFatline_1 = "NORMAL";
            transFatline_2 = "BOLD";
        } else {
            transFatline_1 = "BOLD";
            transFatline_2 = "NORMAL";
        }
        return new String[]{transFatline_1, transFatline_2};
    }


    public static String[] saturatedFatSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String saturatedFatline_1;
        String saturatedFatline_2;
        double saturatedFatlevel_1 = Double.parseDouble(foodObject1.getFoodSaturatedFat());
        double saturatedFatlevel_2 = Double.parseDouble(foodObject2.getFoodSaturatedFat());
        int saturatedFatres = compareObjects(saturatedFatlevel_1, saturatedFatlevel_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (saturatedFatres == 0) {
            saturatedFatline_1 = "NORMAL";
            saturatedFatline_2 = "NORMAL";
        } else if (saturatedFatres == 1) {
            saturatedFatline_1 = "NORMAL";
            saturatedFatline_2 = "BOLD";
        } else {
            saturatedFatline_1 = "BOLD";
            saturatedFatline_2 = "NORMAL";
        }
        return new String[]{saturatedFatline_1, saturatedFatline_2};
    }

    public static String[] totalFatSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String totalFatline_1;
        String totalFatline_2;
        double totalFatlevel_1 = Double.parseDouble(foodObject1.getFoodTotalFat());
        double totalFatlevel_2 = Double.parseDouble(foodObject2.getFoodTotalFat());
        int totalFatres = compareObjects(totalFatlevel_1, totalFatlevel_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (totalFatres == 0) {
            totalFatline_1 = "NORMAL";
            totalFatline_2 = "NORMAL";
        } else if (totalFatres == 1) {
            totalFatline_1 = "NORMAL";
            totalFatline_2 = "BOLD";
        } else {
            totalFatline_1 = "BOLD";
            totalFatline_2 = "NORMAL";
        }
        return new String[]{totalFatline_1, totalFatline_2};
    }

    public static String[] carbSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String carbline_1;
        String carbline_2;
        double carblevel_1 = Double.parseDouble(foodObject1.getFoodCarbohydrate());
        double carblevel_2 = Double.parseDouble(foodObject2.getFoodCarbohydrate());
        int carbres = compareObjects(carblevel_1, carblevel_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (carbres == 0) {
            carbline_1 = "NORMAL";
            carbline_2 = "NORMAL";
        } else {
            if (userPreference.getHighCholesterol().equals("Yes")) { // if i have high chol, less carbs
                if (carbres == 1) {
                    carbline_1 = "NORMAL";
                    carbline_2 = "BOLD";
                } else {
                    carbline_1 = "BOLD";
                    carbline_2 = "NORMAL";
                }
            } else {
                // low carbs and normal same
                if (carbres == 1) {
                    carbline_1 = "NORMAL";
                    carbline_2 = "BOLD";
                } else {
                    carbline_1 = "BOLD";
                    carbline_2 = "NORMAL";
                }
            }
        }
        return new String[]{carbline_1, carbline_2};
    }

    public static String[] cholesterolSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String cholesterolline_1;
        String cholesterolline_2;
        double cholesterol_1 = Double.parseDouble(foodObject1.getFoodCholesterol());
        double cholesterol_2 = Double.parseDouble(foodObject2.getFoodCholesterol());
        int cholres = compareObjects(cholesterol_1, cholesterol_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (cholres == 0) {
            cholesterolline_1 = "NORMAL";
            cholesterolline_2 = "NORMAL";
        } else if (cholres == 1) {
            cholesterolline_1 = "NORMAL";
            cholesterolline_2 = "BOLD";
        } else {
            cholesterolline_1 = "BOLD";
            cholesterolline_2 = "NORMAL";
        }
        return new String[]{cholesterolline_1, cholesterolline_2};
    }

    public static String[] dietarySeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String dietline_1;
        String dietline_2;
        double dietlevel_1 = Double.parseDouble(foodObject1.getFoodDietaryFibre());
        double dietlevel_2 = Double.parseDouble(foodObject2.getFoodDietaryFibre());
        int dietres = compareObjects(dietlevel_1, dietlevel_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (dietres == 0) {
            dietline_1 = "NORMAL";
            dietline_2 = "NORMAL";
        } else if (dietres == 1) {
            dietline_1 = "BOLD";
            dietline_2 = "NORMAL";
        } else {
            dietline_1 = "NORMAL";
            dietline_2 = "BOLD";
        }
        return new String[]{dietline_1, dietline_2};
    }

    public static String[] caloriesSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String caloriesline_1;
        String caloriesline_2;
        double cal_1 = Double.parseDouble(foodObject1.getFoodCalories());
        double cal_2 = Double.parseDouble(foodObject2.getFoodCalories());
        int calres = compareObjects(cal_1, cal_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (calres == 0) {
            caloriesline_1 = "NORMAL";
            caloriesline_2 = "NORMAL";
        } else {
            if (userPreference.getWeightGoals().equals("Gain Weight")) { // if i have high chol, less chol
                if (calres == 1) {
                    caloriesline_1 = "BOLD";
                    caloriesline_2 = "NORMAL";
                } else {
                    caloriesline_1 = "NORMAL";
                    caloriesline_2 = "BOLD";
                }
            } else {
                // gain weight and normal same
                if (calres == 1) {
                    caloriesline_1 = "NORMAL";
                    caloriesline_2 = "BOLD";
                } else {
                    caloriesline_1 = "BOLD";
                    caloriesline_2 = "NORMAL";
                }
            }
        }
        return new String[]{caloriesline_1, caloriesline_2};
    }

    public static String[] proteinSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String proteinline_1;
        String proteinline_2;
        double protein_1 = Double.parseDouble(foodObject1.getFoodProtein());
        double protein_2 = Double.parseDouble(foodObject2.getFoodProtein());
        int proteinres = compareObjects(protein_1, protein_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (proteinres == 0) {
            proteinline_1 = "NORMAL";
            proteinline_2 = "NORMAL";
        } else {
            if (userPreference.getWeightGoals().equals("Lose Weight")) { // if i have high chol, less chol
                if (proteinres == 1) {
                    proteinline_1 = "NORMAL";
                    proteinline_2 = "BOLD";
                } else {
                    proteinline_1 = "BOLD";
                    proteinline_2 = "NORMAL";
                }
            } else {
                // gain weight and normal same
                if (proteinres == 1) {
                    proteinline_1 = "BOLD";
                    proteinline_2 = "NORMAL";
                } else {
                    proteinline_1 = "NORMAL";
                    proteinline_2 = "BOLD";
                }
            }
        }
        return new String[]{proteinline_1, proteinline_2};
    }

    public static String[] ironSeed(FoodDatabaseObject foodObject1, FoodDatabaseObject foodObject2, UserPreferencesObject userPreference){
        String ironline_1;
        String ironline_2;
        double iron_1 = Double.parseDouble(foodObject1.getFoodIron());
        double iron_2 = Double.parseDouble(foodObject2.getFoodIron());
        int dietres = compareObjects(iron_1, iron_2); // this returns 1 if 1 is higher, 2 if 2 is higher
        if (dietres == 0) {
            ironline_1 = "NORMAL";
            ironline_2 = "NORMAL";
        } else if (dietres == 1) {
            ironline_1 = "BOLD";
            ironline_2 = "NORMAL";
        } else {
            ironline_1 = "NORMAL";
            ironline_2 = "BOLD";
        }
        return new String[]{ironline_1, ironline_2};
    }
}
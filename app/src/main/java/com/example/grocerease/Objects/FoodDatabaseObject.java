package com.example.grocerease.Objects;

import java.io.Serializable;

/*** Food Database Object
 * This is a food object from our Firebase.
 * It contains all the nutritional information of a single Food item
 * We have each of these nutritional fields as private attributes, each with getters
 * We also have arg/no arg constructors to populate the Database ***/

public class FoodDatabaseObject implements Serializable {

    /** ATTRIBUTES **/
    private String foodName;
    private String foodProtein;
    private String foodTotalFat;
    private String foodSaturatedFat;
    private String foodTransFat;
    private String foodCholesterol;
    private String foodCarbohydrate;
    private String foodTotalSugar;
    private String foodDietaryFibre;
    private String foodSodium;
    private String foodIron;
    private String foodCalories;
    private String foodMass;
    private String foodCompany;
    private String foodImageURL;

    /** NO ARG CONSTRUCTOR **/
    public FoodDatabaseObject() {
    }

    /** WITH ARG CONSTRUCTOR **/
    public FoodDatabaseObject(String foodName,
                              String foodMass,
                              String foodProtein,
                              String foodTotalFat,
                              String foodSaturatedFat,
                              String foodTransFat,
                              String foodCholesterol,
                              String foodCarbohydrate,
                              String foodTotalSugar,
                              String foodDietaryFibre,
                              String foodSodium,
                              String foodIron,
                              String foodCalories,
                              String foodImageURL,
                              String foodCompany) {

        this.foodName = foodName;
        this.foodProtein = foodProtein;
        this.foodTotalFat = foodTotalFat;
        this.foodSaturatedFat = foodSaturatedFat;
        this.foodTransFat = foodTransFat;
        this.foodCholesterol = foodCholesterol;
        this.foodCarbohydrate = foodCarbohydrate;
        this.foodTotalSugar = foodTotalSugar;
        this.foodDietaryFibre = foodDietaryFibre;
        this.foodSodium = foodSodium;
        this.foodIron = foodIron;
        this.foodCalories = foodCalories;
        this.foodImageURL = foodImageURL;
        this.foodMass = foodMass;
        this.foodCompany = foodCompany;

    }

    /** toString() method **/
    @Override
    public String toString() {
        return "DatabaseItemObject{" +
                "foodName='" + foodName + '\'' +
                ", foodProtein='" + foodProtein + '\'' +
                ", foodTotalFat='" + foodTotalFat + '\'' +
                ", foodSaturatedFat='" + foodSaturatedFat + '\'' +
                ", foodTransFat='" + foodTransFat + '\'' +
                ", foodCholesterol='" + foodCholesterol + '\'' +
                ", foodCarbohydrate='" + foodCarbohydrate + '\'' +
                ", foodTotalSugar='" + foodTotalSugar + '\'' +
                ", foodDietaryFibre='" + foodDietaryFibre + '\'' +
                ", foodSodium='" + foodSodium + '\'' +
                ", foodIron='" + foodIron + '\'' +
                ", foodCalories='" + foodCalories + '\'' +
                ", foodImageURL='" + foodImageURL + '\'' +
                ", foodCompany='" + foodCompany + '\'' +
                ", foodMass='" + foodMass + '\'' +
                '}';
    }

    /** Getters **/
    public String getFoodName() {
        return foodName;
    }
    public String getFoodProtein() {
        return foodProtein;
    }
    public String getFoodTotalFat() {
        return foodTotalFat;
    }
    public String getFoodSaturatedFat() {
        return foodSaturatedFat;
    }
    public String getFoodTransFat() {
        return foodTransFat;
    }
    public String getFoodCholesterol() {
        return foodCholesterol;
    }
    public String getFoodCalories() {
        return foodCalories;
    }
    public String getFoodCarbohydrate() {
        return foodCarbohydrate;
    }
    public String getFoodTotalSugar() {
        return foodTotalSugar;
    }
    public String getFoodDietaryFibre() {
        return foodDietaryFibre;
    }
    public String getFoodSodium() {
        return foodSodium;
    }
    public String getFoodIron() {
        return foodIron;
    }
    public String getFoodImageURL() {
        return foodImageURL;
    }
    public String getFoodMass() {
        return foodMass;
    }
    public String getFoodCompany() {
        return foodCompany;
    }

}

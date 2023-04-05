package com.example.grocerease;

import java.io.Serializable;

// Cephas - added all foodCompany, foodMass for each class and method

public class FoodDatabaseObject implements Serializable {
    private String foodName;
    private String foodProtein;// higher
    private String foodTotalFat;
    private String foodSaturatedFat;
    private String foodTransFat;
    private String foodCholesterol;
    private String foodCarbohydrate;
    private String foodTotalSugar;
    private String foodDietaryFibre;// higher
    private String foodSodium;
    private String foodIron;// higher
    private String foodCalories;
    private String foodMass;
    private String foodCompany;
    private String foodImageURL;

    public FoodDatabaseObject() {
    }

    public FoodDatabaseObject(String foodName, String foodProtein, String foodTotalFat, String foodSaturatedFat, String foodTransFat,
                              String foodCholesterol, String foodCarbohydrate, String foodTotalSugar, String foodDietaryFibre, String foodSodium,
                              String foodIron, String foodCalories, String foodImageURL, String foodMass, String foodCompany) {

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

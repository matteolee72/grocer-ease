package com.example.grocerease;

public class DatabaseItemObject {
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

    public DatabaseItemObject() {
    }

    public DatabaseItemObject(String foodName, String foodProtein, String foodTotalFat, String foodSaturatedFat, String foodTransFat, String foodCholesterol, String foodCarbohydrate, String foodTotalSugar, String foodDietaryFibre, String foodSodium, String foodIron) {
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
    }

    @Override
    public String toString() {
        return "databaseItemObject{" +
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
}

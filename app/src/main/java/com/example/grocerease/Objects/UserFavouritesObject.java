package com.example.grocerease.Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class UserFavouritesObject implements Serializable {
    private ArrayList<String> foodFavourites;

    public String getID(int i){
        return this.getFoodFavourites().get(i);
    }
    public void removeID(int i){}

    public int getSize(){
        return foodFavourites.size();
    }

    public ArrayList<String> getFoodFavourites() {return this.foodFavourites;}

    public ArrayList<String> addToFavourites(String foodItem){
        if (!foodFavourites.contains(foodItem)) {
            foodFavourites.add(foodItem);
        }
        return foodFavourites;
    }
    public ArrayList<String> removeFromFavourites(String foodItem){
        if (foodFavourites.contains(foodItem)) {
            foodFavourites.remove(foodItem);
        }
        return foodFavourites;
    }

    public UserFavouritesObject() {
        this.foodFavourites = new ArrayList<>();
    }

}
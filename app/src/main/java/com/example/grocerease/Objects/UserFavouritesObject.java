package com.example.grocerease.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/*** User Favourites Object
 * This is a favourites object attached to each user in our Firebase.
 * It contains an arraylist of food IDs which users have added to their favourites.
 * We use it to interface with both our Adapters (for recyclerView) and our Database***/

public class UserFavouritesObject implements Serializable {

    /** Attribute: String Arraylist **/
    private ArrayList<String> foodFavourites;

    /** NO ARG CONSTRUCTOR **/
    public UserFavouritesObject() {
        this.foodFavourites = new ArrayList<>();
    }

    /** Getters for interfacing with FavouritesAdapter **/
    public String getID(int i){
        return this.getFoodFavourites().get(i);
    }
    public int getSize(){
        return foodFavourites.size();
    }
    public ArrayList<String> getFoodFavourites() {return this.foodFavourites;}

    /** Methods to modify Arraylist **/
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
}
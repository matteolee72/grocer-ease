package com.example.grocerease.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/*** User Favourites Object
 * This is a favourites object attached to each user in our Firebase.
 * It contains an arraylist of food IDs which users have added to their favourites.
 * We use it to interface with both our Adapters (for recyclerView) and our Database***/

public class UserFavouritesObject implements Serializable,UserFoodInterface {

    /** Attribute: String Arraylist **/
    private ArrayList<String> foodList;

    /** NO ARG CONSTRUCTOR **/
    public UserFavouritesObject() {
        this.foodList = new ArrayList<>();
    }

    /** Getters for interfacing with FavouritesAdapter **/
    public String getID(int i){
        return this.getFoodList().get(i);
    }
    public int getSize(){
        return foodList.size();
    }
    public ArrayList<String> getFoodList() {return this.foodList;}

    /** Methods to modify Arraylist **/
    public ArrayList<String> addToFavourites(String foodItem){
        if (!foodList.contains(foodItem)) {
            foodList.add(foodItem);
        }
        return foodList;
    }
    public ArrayList<String> removeFromFavourites(String foodItem){
        if (foodList.contains(foodItem)) {
            foodList.remove(foodItem);
        }
        return foodList;
    }
}
package com.example.grocerease;

import java.io.Serializable;

public class UserDatabaseObject implements Serializable {
    private String userName;
    private String userPassword;
    private UserFavouritesObject userFavourites;
    private UserHistoryObject userHistory;
    private UserPreferencesObject userPreferences;
    public UserDatabaseObject(){
    }
    // Partial Arg Constructor - we use this in the create account page
    public UserDatabaseObject(String userPassword) {
        this.userName = null;
        this.userPassword = userPassword;
        this.userFavourites = new UserFavouritesObject();
        this.userHistory = new UserHistoryObject();
        this.userPreferences = null;
    }
    // With Arg Constructor -  we use this once a user has filled in their username/password
    public UserDatabaseObject(String userName, String userPassword, UserFavouritesObject userFavourites, UserHistoryObject userHistory, UserPreferencesObject userPreferences) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userFavourites = userFavourites;
        this.userHistory = userHistory;
        this.userPreferences = userPreferences;
    }
    // Getters
    public String getUserName() {return userName;}
    public String getUserPassword() {return userPassword;}
    public UserFavouritesObject getUserFavourites() {return userFavourites;}
    public UserHistoryObject getUserHistory() {return userHistory;}
    public UserPreferencesObject getUserPreferences() {return userPreferences;}

}

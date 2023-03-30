package com.example.grocerease;

import java.io.Serializable;

public class UserDatabaseObject implements Serializable {
    private String userName;
    private String userPassword;
    private String userFavourites;
    private String userHistory;
    private String userPreferences;
    public UserDatabaseObject(){
    }
    // Partial Arg Constructor - we use this in the create account page
    public UserDatabaseObject(String userPassword) {
        this.userName = null;
        this.userPassword = userPassword;
        this.userFavourites = null;
        this.userHistory = null;
        this.userPreferences = null;
    }
    // With Arg Constructor -  we use this once a user has filled in their username/password
    public UserDatabaseObject(String userName, String userPassword, String userFavourites, String userHistory, String userPreferences) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userFavourites = userFavourites;
        this.userHistory = userHistory;
        this.userPreferences = userPreferences;
    }
    // Getters
    public String getUserName() {return userName;}
    public String getUserPassword() {return userPassword;}
    public String getUserFavourites() {return userFavourites;}
    public String getUserHistory() {return userHistory;}
    public String getUserPreferences() {return userPreferences;}
}

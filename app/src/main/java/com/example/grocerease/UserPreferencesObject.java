package com.example.grocerease;

import java.io.Serializable;
import java.util.Date;

public class UserPreferencesObject implements Serializable {

//    /**
//     *
//     * @param datePicker
//     * @return a java.util.Date
//     */
//    public static java.util.Date getDateFromDatePicket(DatePicker datePicker){
//        int day = datePicker.getDayOfMonth();
//        int month = datePicker.getMonth();
//        int year =  datePicker.getYear();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day);
//
//        return calendar.getTime();
//    }

    private String bloodPressure;
    private String bloodSugarLevels;
    private String highCholesterol;
    private String weightGoals;
    private String name;
    private String sex;
    private int height;
    private int weight;
    private Date birthday;


    // getting Date object from DatePicker: new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
    static class PrefBuilder{
        private String bloodPressure;
        private String bloodSugarLevels;
        private String highCholesterol;
        private String weightGoals;
        private String name;
        private String sex;
        private int height;
        private int weight;
        private Date birthday;

        PrefBuilder(){}
    }




}

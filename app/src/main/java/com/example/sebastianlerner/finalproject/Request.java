package com.example.sebastianlerner.finalproject;

/**
 * Created by Michael on 4/13/2016.
 */ 
public class Request {
    private String name;
    private String startLocation;
    private String endLocation;
    private int month;
    private int day;
    private int year;
    private int riders;
    private int time;
    private boolean driving;


    public Request(String location1, String location2, int riders1, boolean driving1, int time1){
        this.name = location1;
        this.startLocation = location1;
        this.endLocation = location2;
        this.riders = riders1;
        this.driving = driving1;
        this.time = time1;

      //  String months = date1.substring(0,2);
      //  String days = date1.substring(2,4);
      //  String years = date1.substring(4,6);

       // this.month = Integer.parseInt(months);
       // this.day = Integer.parseInt(days);
       // this.year = Integer.parseInt(years);
    }
    public String toString() {
        if(this.driving == true){
            return  "- "+ MainActivity.username + " is driving from " + this.startLocation + " to " + this.endLocation + " with " +  this.riders  + " people at " + this.time;
        } else {
            return  "+ "+ MainActivity.username + " is requesting a ride from " + this.startLocation + " to " + this.endLocation + " with " +  this.riders + " people at " + this.time;
        }

    }
}

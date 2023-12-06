package com.example.howtotrackprj;

public class ProtsCals {

    private String date;
    private int proteinCalories;
    private float protein;
    private int calorie;
    private String user;

    public ProtsCals(String user, float protein,int calorie,String date) {
        this.date = date;
      this.calorie=calorie;
      this.protein=protein;
      this.user=user;
    }



    public String getDate() {
        return date;
    }

    public int getProteinCalories() {
        return proteinCalories;
    }
}


package com.example.howtotrackprj;

public class foodItem {

    private int calories;
    private double protein;
    private String name;

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    private Integer quantity;



    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }



    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public foodItem(String name,int calories,double protein,Integer quantity) {
        this.calories = calories;
        this.protein=protein;
        this.name=name;
        this.quantity=quantity;
    }




}

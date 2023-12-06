package com.example.howtotrackprj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.howtotrackprj.foodItem;

import java.util.List;

// Update the FoodAdapter class
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FoodAdapter extends BaseAdapter {

    private Context context;
    private List<foodItem> foodItems;
    private int layoutResourceId;

    public FoodAdapter(Context context, int layoutResourceId, List<foodItem> foodItems) {
        this.context = context;
        this.foodItems = foodItems;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return foodItems.size();
    }

    @Override
    public Object getItem(int position) {
        return foodItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.nameTextView = row.findViewById(R.id.nameTextView);
            holder.caloriesTextView = row.findViewById(R.id.caloriesTextView);
            holder.proteinTextView = row.findViewById(R.id.proteinTextView);
            holder.quantityTextView = row.findViewById(R.id.quantityTextView);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        foodItem foodItem = foodItems.get(position);
        holder.nameTextView.setText(foodItem.getName());
        holder.caloriesTextView.setText("Calories: " + foodItem.getCalories());
        holder.proteinTextView.setText("Protein: " + foodItem.getProtein());
        holder.quantityTextView.setText("Quantity: " + foodItem.getQuantity());

        return row;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView caloriesTextView;
        TextView proteinTextView;
        TextView quantityTextView;
    }
}



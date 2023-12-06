package com.example.howtotrackprj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.howtotrackprj.ProtsCals;

import java.util.List;

public class goalsAdapter extends ArrayAdapter<ProtsCals> {

    private Context context;
    private int resource;

    public goalsAdapter(Context context, int resource, List<ProtsCals> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        ProtsCals proteinData = getItem(position);

        if (proteinData != null) {
            TextView textViewDate = view.findViewById(R.id.txtViewDate);
            TextView textViewProteinCalories = view.findViewById(R.id.txtProteinCalories);

            if (textViewDate != null) {
                textViewDate.setText("Date: " + proteinData.getDate());
            }

            if (textViewProteinCalories != null) {
                textViewProteinCalories.setText("Protein Calories: " + proteinData.getProteinCalories());
            }
        }

        return view;
    }
}


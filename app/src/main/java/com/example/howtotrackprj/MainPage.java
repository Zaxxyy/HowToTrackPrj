package com.example.howtotrackprj;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity  {
String User;
TextView nom;

private EditText name;
private EditText Calories;
private EditText protein;
private Button btnAdd;
private String foodName;
private Integer cals;
private Double prots;
private TextView Total;

private foodItemsDataGateway foodItemsDataGateway;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        foodItemsDataGateway = new foodItemsDataGateway(this,"foodItems",null,1);

        SQLiteDatabase db = foodItemsDataGateway.getWritableDatabase();
        foodItemsDataGateway.Open();

        foodItemsDataGateway.onCreate(db);

        name = findViewById(R.id.foodNameTxt);
        Calories = findViewById(R.id.caloriesTxt);
        protein = findViewById(R.id.proteinTxt);
        btnAdd = findViewById(R.id.saveButton);

        Total=findViewById(R.id.txtProCal);








        nom = findViewById(R.id.txtName);
        Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {

                User = extras.getString("User");
            }
        }
        nom.setText(User);


        Double protss=foodItemsDataGateway.calculateProteinForUser(User);
        Integer calss= foodItemsDataGateway.calculateCaloriesForUser(User);

        Total.setText("Total Protein:" + protss + "g\nTotal Calories: "+calss);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                foodName = name.getText().toString();
                cals = Integer.parseInt(Calories.getText().toString());
                prots = Double.parseDouble(protein.getText().toString());



                showConfirmationDialog();


            }

    });





    }


    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to add this : " + foodName)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        foodItemsDataGateway.InsertOrUpdateFoodItem(foodName,cals,prots,User);

                        Double protss=foodItemsDataGateway.calculateProteinForUser(User);
                        Integer calss= foodItemsDataGateway.calculateCaloriesForUser(User);

                        Total.setText("Total Protein:" + protss + "g\nTotal Calories: "+calss);

                        name.setText("");
                        Calories.setText("");
                        protein.setText("");


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked No, do nothing
                        dialog.dismiss();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }





    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


        getMenuInflater().inflate(R.drawable.menu3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_tracking:
                intent = new Intent(this, ObjectivePage.class);
                intent.putExtra("User", User);
                startActivity(intent);
                return true;

            case R.id.menu_goals:
                intent = new Intent(this, TrackingPage.class);
                intent.putExtra("User", User);
                startActivity(intent);
                return true;

            case R.id.menu_setting:
                intent = new Intent(this, settingPage.class);
                intent.putExtra("User", User);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


    }





}
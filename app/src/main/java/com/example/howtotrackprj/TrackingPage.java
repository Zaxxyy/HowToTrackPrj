package com.example.howtotrackprj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.time.LocalDate;


import java.util.Date;
import java.util.List;

public class TrackingPage extends AppCompatActivity {

    private infosMembersDataGateway infosMembersDataGateway;
    private foodItemsDataGateway foodItemsDataGateway;

    private String User;

    private Integer calories;
    private double proteins;

    private TextView cals;
    private TextView prots;

    private Button btnEndDay;

    private double protsTotal;
    private double protsDay;
    private List<ProtsCals> protsCalsList;
    private goalsAdapter customAdapter;

    private userGoals userGoals;

    private ListView listView;
    private Double protsTots;
    private int calTots;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_page);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        cals = findViewById(R.id.textViewCaloriesValue);
        prots = findViewById(R.id.textViewProteinValue);
        btnEndDay = findViewById(R.id.btnEndDay);


        infosMembersDataGateway = new infosMembersDataGateway(this,"infosMembers",null,1);
        foodItemsDataGateway = new foodItemsDataGateway(this,"foodItems",null,1);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {

                User = extras.getString("User");
            }
        }


       protsTots= foodItemsDataGateway.calculateProteinForUser(User);
        calTots = foodItemsDataGateway.calculateCaloriesForUser(User);


       Pair<Integer, Double> result =  infosMembersDataGateway.calculateProteinCals(User);

        calories = result.first;
        proteins = result.second;

        String p = String.valueOf(proteins);

        cals.setText(calories.toString());
        prots.setText(p + "g");





        btnEndDay.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                showConfirmationDialog();
            }
        });




    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to end the day? All your calories and protein will be delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        foodItemsDataGateway.deleteUserData(User);
                        Intent i = new Intent(TrackingPage.this,MainPage.class);
                        i.putExtra("User",User);

                        startActivity(i);

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

        getMenuInflater().inflate(R.drawable.menu, menu);
        return true;



    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_add_food:
                intent = new Intent(this, MainPage.class);
                intent.putExtra("User", User);
                startActivity(intent);
                return true;

            case R.id.menu_tracking:
                intent = new Intent(this, ObjectivePage.class);
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
package com.example.howtotrackprj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class ObjectivePage extends AppCompatActivity {


    private foodItemsDataGateway foodItemsDataGateway;
    private infosMembersDataGateway infosMembersDataGateway;
    private ListView listView;
    private String User;
    private double protsTotal;
    private double protsDay;
    private Integer calsTotal;
    private Integer calsDay;
    private ProgressBar progressBarProts;
    private ProgressBar progressBarCals;
    private TextView TxtprotsPourcentage;
    private TextView TxtcalsPourcentage;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective_page);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {

                User = extras.getString("User");
            }
        }

         listView = findViewById(R.id.listViewFood);

TxtcalsPourcentage = findViewById(R.id.calsText);
TxtprotsPourcentage = findViewById(R.id.protsText);


         foodItemsDataGateway = new foodItemsDataGateway(this,"foodItems",null,1);
        infosMembersDataGateway = new infosMembersDataGateway(this,"infosMembers",null,1);




        protsDay=foodItemsDataGateway.calculateProteinForUser(User);
        calsDay = foodItemsDataGateway.calculateCaloriesForUser(User);
        Pair<Integer, Double> result =  infosMembersDataGateway.calculateProteinCals(User);
        protsTotal = result.second;
        calsTotal = result.first;

        progressBarProts = findViewById(R.id.progressBarProts);
        progressBarCals = findViewById(R.id.progressBarCal);

        progressBarProts.setProgress(Integer.valueOf((int) (protsDay*100/protsTotal)));
        progressBarCals.setProgress(calsDay*100/calsTotal);
        TxtprotsPourcentage.setText("Protein Goal: "+protsDay*100/protsTotal+"%");
        TxtcalsPourcentage.setText("Calorie Goal: "+calsDay*100/calsTotal+"%");

        List<foodItem> foodItems = foodItemsDataGateway.fetchDataForUser(User);


        FoodAdapter adapter = new FoodAdapter(this, R.layout.lstview, foodItems);


        listView.setAdapter(adapter);



    }




    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        super.onCreateOptionsMenu(menu);



        getMenuInflater().inflate(R.drawable.menu2, menu);
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
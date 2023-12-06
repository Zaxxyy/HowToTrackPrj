package com.example.howtotrackprj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class settingPage extends AppCompatActivity {

    private infosMembersDataGateway infosMembersDataGateway;
    private String idName;
    private Double weight;
    private Double height;
    private String objective;
    private String User;

    private TextView txtHeight;
    private TextView txtWeight;
    private TextView txtTrainingGoal;
    private Button btnDisco;

private TextView txtUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        txtHeight = findViewById(R.id.textViewHeight);
        txtWeight = findViewById(R.id.textViewWeight);
        txtTrainingGoal = findViewById(R.id.textViewTrainingGoal);
        txtUsername = findViewById(R.id.textViewUsername);
        btnDisco = findViewById(R.id.btnDisco);



        infosMembersDataGateway = new infosMembersDataGateway(this,"infosMembers",null,1);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {

                User = extras.getString("User");
            }
        }


        ContentValues cv = infosMembersDataGateway.getInfoForUser(User);

        if (cv.size() > 0) {
            // Now you have the ContentValues, you can access its values
             idName = cv.getAsString("idName");
             weight = cv.getAsDouble("weight");
             height = cv.getAsDouble("height");
             objective = cv.getAsString("objective");


        }







        txtWeight.setText("Height: " + weight + " lbs");
        txtHeight.setText("Height: " + height + " cm");
        txtTrainingGoal.setText("Training Goal: " + objective);
        txtUsername.setText("Username: " + idName);


        btnDisco.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                showConfirmationDialog();



            }

        });


    }


    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to disconnect?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent i = new Intent(settingPage.this,MainActivity.class);
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



        getMenuInflater().inflate(R.drawable.menu1, menu);
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

            case R.id.menu_goals:
                intent = new Intent(this, TrackingPage.class);
                intent.putExtra("User", User);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
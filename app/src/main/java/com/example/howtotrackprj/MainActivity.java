package com.example.howtotrackprj;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity
 {
    private Button BtnLogin;
    private EditText user;
    private EditText pswd;
    private MembersTableDataGateway membersTable;
    private Context context;

    private Button btnSignUp;
    private String UserTest;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BtnLogin = findViewById(R.id.btnLogin);


        btnSignUp = findViewById(R.id.signUpBtn);


        user = findViewById(R.id.username);
        pswd = findViewById(R.id.password);




         membersTable = new MembersTableDataGateway(this,"members",null,1);





         SQLiteDatabase db = membersTable.getWritableDatabase();
            membersTable.Open();
            membersTable.logAllRecords("members");

             Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {

                UserTest = extras.getString("UserTest");
            }
        }
        user.setText(UserTest);


        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User = user.getText().toString();
                String Password = pswd.getText().toString();

                Intent i;


                boolean isMatch = membersTable.isUserAndPasswordMatch(User,Password);


                if (isMatch) {


                    if(membersTable.isUserRegistered(User,Password)==true){

                        i = new Intent(MainActivity.this,MainPage.class);
                        i.putExtra("User",User);

                        startActivity(i);

                    }

                    else {
                        membersTable.updateUserRegistrationStatus(User,true);

                        i = new Intent(MainActivity.this,MainActivity2.class);
                        i.putExtra("User",User);
                        startActivity(i);

                    }




                }

                else {
                    Toast.makeText(MainActivity.this, "User and password is incorrect please try again!", Toast.LENGTH_SHORT).show();
                }




            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,SignUpPage.class);
                startActivity(i);
            }
        });

    }



    }



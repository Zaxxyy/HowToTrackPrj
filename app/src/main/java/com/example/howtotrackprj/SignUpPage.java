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



public class SignUpPage extends AppCompatActivity
         {
    private Button btnLogIn;
    private Button btnCreate;
    private EditText user;
    private EditText pswd;
    private MembersTableDataGateway membersTable;
    private Context context;
    private String User;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        btnLogIn = findViewById(R.id.logInBtn);


        btnCreate = findViewById(R.id.CreateButton);




        user = findViewById(R.id.usernameTest);
        pswd = findViewById(R.id.passwordTest);



        context = this;

        membersTable = new MembersTableDataGateway(context, "members", null, 1);
        SQLiteDatabase db = membersTable.getWritableDatabase();
            membersTable.Open();




        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User = user.getText().toString();
                password = pswd.getText().toString();
                boolean isMatch = membersTable.isUserExist(User);
                    if(User.isEmpty()){
                        Toast.makeText(SignUpPage.this, "Please enter a username try and again!", Toast.LENGTH_SHORT).show();

                    } else if (password.isEmpty()) {
                        Toast.makeText(SignUpPage.this, "Please enter a password and try again!", Toast.LENGTH_SHORT).show();
                    } else if (User.isEmpty()&&password.isEmpty()) {
                        Toast.makeText(SignUpPage.this, "Please enter a password and username and try again!", Toast.LENGTH_SHORT).show();
                    } else{

                        if(isMatch){

                            Toast.makeText(SignUpPage.this, "This username is not available please try again!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            membersTable.InsertMember(User,password);
                            Intent i = new Intent(SignUpPage.this,MainActivity.class);
                            i.putExtra("UserTest",User);
                            i.putExtra("pswdTest",password);
                            startActivity(i);


                        }


                    }

               }


        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignUpPage.this,MainActivity.class);
                startActivity(i);
            }
        });

    }




    }

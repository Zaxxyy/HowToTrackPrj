package com.example.howtotrackprj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class formPage extends AppCompatActivity implements View.OnClickListener {
    private String User;
    private Button btnEnter;
    private EditText weight;
  private   EditText height;
   private Integer gymDays;
   private String objective;
   private RadioGroup days;
   private RadioGroup obj;
   private RadioButton one;
   private RadioButton two;
   private RadioButton three;
   private RadioButton four;
   private RadioButton five;
   private RadioButton fivePlus;
   private RadioButton gain;
   private RadioButton lose;
    private infosMembersDataGateway infosMembers;
    private MembersTableDataGateway tableMembers;
    private  Integer weightValue;
    private Integer heightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_page);

        btnEnter = findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);

        weight = findViewById(R.id.txtW);
        height = findViewById(R.id.txtH);
        days = findViewById(R.id.radioGroupGym);
        one=findViewById(R.id.radioBtnOne);
        two=findViewById(R.id.radioBtnTwo);
        three=findViewById(R.id.radioBtnThree);
        four=findViewById(R.id.radioBtnFour);
        five=findViewById(R.id.radioBtnFive);
        fivePlus=findViewById(R.id.radioBtnFivePlus);
        obj=findViewById(R.id.radioGroupObj);
        gain=findViewById(R.id.radioBtnGain);
        lose=findViewById(R.id.radioBtnLose);











        Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {

                User = extras.getString("User");
            }
        }

        tableMembers = new MembersTableDataGateway(this,"members",null,1);

        SQLiteDatabase db = tableMembers.getWritableDatabase();
        tableMembers.Open();


        infosMembers = new infosMembersDataGateway(this, "infosMembers", null, 1);
        SQLiteDatabase db2 = infosMembers.getWritableDatabase();
        infosMembers.Open();

    }


    @Override
    public void onClick(View view) {

        if(one.isChecked()){

            gymDays=1;
        } else if (two.isChecked()) {
            gymDays=2;
        } else if (three.isChecked()) {
            gymDays=3;
        } else if (four.isChecked()) {
            gymDays=4;
        } else if (five.isChecked()) {
            gymDays=5;
        }
        else {
            gymDays=6;
        }


        if(gain.isChecked()==true){
            objective="GainWeight";
        }
        else{
            objective="LoseWeight";
        }




        Intent i;
        switch (view.getId()) {
            case R.id.btnEnter:

                String Weight = weight.getText().toString();
                if(Weight.isEmpty()){
                    Toast.makeText(this, "Please enter you're weight before clicking enter!", Toast.LENGTH_SHORT).show();

                }
                else{

                    try {
                        weightValue = Integer.parseInt(Weight);
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(this, "Please enter a number for you're weight!", Toast.LENGTH_SHORT).show();
                    }

                }




                String Height = height.getText().toString();
                if(Height.isEmpty()){
                    Toast.makeText(this, "Please enter you're height before clicking enter!", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        heightValue = Integer.parseInt(Height);
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(this, "Please enter a number for you're height!", Toast.LENGTH_SHORT).show();
                    }

                }



                if(days.getCheckedRadioButtonId()==-1){

                    Toast.makeText(this, "Please select how many days you are working out before clicking enter!", Toast.LENGTH_SHORT).show();


                }


                if(obj.getCheckedRadioButtonId()==-1){

                    Toast.makeText(this, "Please select you're objective before clicking enter!", Toast.LENGTH_SHORT).show();

                }


                try{
                    Double.parseDouble(Weight);
                    Double.parseDouble(Height);
                    if(!Weight.isEmpty()&& !Height.isEmpty()&&days.getCheckedRadioButtonId()!=-1&&obj.getCheckedRadioButtonId()!=-1){
                        infosMembers.InsertInfos(User,weightValue,heightValue,gymDays,objective);

                        tableMembers.updateUserRegistrationStatus(User,true);
                        i = new Intent(this, MainPage.class);
                        i.putExtra("User",User);
                        startActivity(i);

                }}
                catch (NumberFormatException e){}












        }
    }
}
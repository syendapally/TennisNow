package com.example.syend.tennisnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
TextView name;
    ImageView profilepic;
    TextView rank;
    ImageView country;
    TextView countryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);try{
        setContentView(R.layout.activity_profile);
        name = (TextView) findViewById(R.id.playerName);
        profilepic = (ImageView) findViewById(R.id.profilepic);
        rank = (TextView) findViewById(R.id.rank);
        country = (ImageView)findViewById(R.id.country);
        countryName = (TextView) findViewById(R.id.countryName);
        Bundle extras = getIntent().getExtras();
        String prev = "";
        if (extras != null) {
            //emailStr = extras.getString("key");
            prev = extras.getString("key");
        }
        String arr[] = prev.split("#");
        name.setText(arr[1]);
        rank.setText("RANK: " + arr[2]);
        countryName.setText("Country: "+ arr[arr.length-2]);
        if(arr[1].contains("Nadal")){
            profilepic.setImageResource(R.drawable.nadal);
            country.setImageResource(R.drawable.spain);
        }
      else if(arr[1].contains("Federer")){
            profilepic.setImageResource(R.drawable.federer);
            country.setImageResource(R.drawable.swiss);
        }
      else  if(arr[1].contains("Dimitrov")){
            profilepic.setImageResource(R.drawable.dimitrov);
            country.setImageResource(R.drawable.bulgaria);
        }
      else  if(arr[1].contains("Zverev")){
            profilepic.setImageResource(R.drawable.zverev);
            country.setImageResource(R.drawable.germany);
        }
      else  if(arr[1].contains("Thiem")){
            profilepic.setImageResource(R.drawable.thiem);
            country.setImageResource(R.drawable.austria);
        }
      else  if(arr[1].contains("Cilic")){
            profilepic.setImageResource(R.drawable.cilic);
            country.setImageResource(R.drawable.croatia);
        }
      else  if(arr[1].contains("Wawrinka")){
            profilepic.setImageResource(R.drawable.wawrinka);
            country.setImageResource(R.drawable.swiss);
        }
      else  if(arr[1].contains("Goffin")){
            profilepic.setImageResource(R.drawable.goffin);
            country.setImageResource(R.drawable.belgium);
        }
       else if(arr[1].contains("Sock")){
            profilepic.setImageResource(R.drawable.sock);
            country.setImageResource(R.drawable.usa);
        }
            else{
            finish();
        }
    }catch (Exception e){
            Toast.makeText(this, "Player Profile Not Available", Toast.LENGTH_SHORT).show();
        }}
}


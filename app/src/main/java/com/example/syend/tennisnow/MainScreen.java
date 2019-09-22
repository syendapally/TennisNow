package com.example.syend.tennisnow;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Thread t = new Thread(new Runnable() {
            public void run() {
                handler.postDelayed(new Runnable() {
                    public void run() {

                        Intent intent = new Intent(MainScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, ((long)(1000)));
            }
        });

        t.start();

    }
    public void start (View view){

    }
   }

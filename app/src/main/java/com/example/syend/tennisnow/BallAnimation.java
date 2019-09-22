package com.example.syend.tennisnow;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

public class BallAnimation extends AppCompatActivity {

ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_animation);
        imageView = (ImageView)findViewById(R.id.imageView);
        ((AnimationDrawable) imageView.getDrawable()).start();
        //Intent intent = new Intent(BallAnimation.this, MainActivity.class);
        //startActivity(intent);
    }
}

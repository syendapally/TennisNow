package com.example.syend.tennisnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class LogSign extends AppCompatActivity {
    private String medium;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign);
        mAuth= FirebaseAuth.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            medium = extras.getString("chat");
        }
        if (mAuth.getCurrentUser()!=null){
            Intent chatIntent = new Intent(LogSign.this, Chat.class);
            chatIntent.putExtra("chat", medium);
            startActivity(chatIntent);
            finish();
        }
    }
    public void sign(View view){
        Intent intent = new Intent(LogSign.this, SignUp.class);
        intent.putExtra("chat", medium);
        startActivity(intent);
        finish();
    }

    public void login(View view){
        Intent intent = new Intent(LogSign.this, Login.class);
        intent.putExtra("chat", medium);
        startActivity(intent);
        finish();
    }
}

package com.example.syend.tennisnow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syend.tennisnow.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ProgressDialog progressBar;
    private EditText pwd;
    private EditText email;
    private Button signUp;
    private TextView login;
    private String medium;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
        pwd= (EditText) findViewById(R.id.editTextPassword);
        email = (EditText) findViewById(R.id.editTextEmail);
        signUp = (Button) findViewById(R.id.sign);
        login = (TextView) findViewById(R.id.login);
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            medium = extras.getString("chat");
        }
    }
    public void registerUser(){
        final String emailStr = email.getText().toString().trim();
        String pwdStr = pwd.getText().toString().trim();
        if (TextUtils.isEmpty(emailStr)){
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwdStr)){
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailStr, pwdStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Signed in", Toast.LENGTH_SHORT).show();
                            Intent intentChat = new Intent(Login.this, Chat.class);
                            intentChat.putExtra("chat", medium);
                            startActivity(intentChat);
                            finish();
                        }
                        else {
                            Toast.makeText(Login.this, "Could not sign in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    @Override
    public void onClick(View v) {
        if (v==signUp){
            registerUser();
            /*Intent intentChat = new Intent(Login.this, Chat.class);
            intentChat.putExtra("chat", medium);
            startActivity(intentChat);*/
        }
        if(v==login){
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
            finish();
        }
    }

}

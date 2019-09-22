package com.example.syend.tennisnow;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_sign_up);
        mAuth= FirebaseAuth.getInstance();
      /*  if (mAuth.getCurrentUser()!=null){
            Intent chatIntent = new Intent(SignUp.this, Chat.class);
            startActivity(chatIntent);
        }*/
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

        mAuth.createUserWithEmailAndPassword(emailStr, pwdStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Registerd User", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent (SignUp.this, Chat.class);
                            intent2.putExtra("chat", medium);
                            startActivity(intent2);
                            finish();
                        }
                        else {
                            Toast.makeText(SignUp.this, "Could not Register User... Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    @Override
    public void onClick(View v) {
        if (v==signUp){
            registerUser();
            /*Intent intentChat = new Intent(SignUp.this, Chat.class);
            intentChat.putExtra("chat", medium);
            startActivity(intentChat);*/

        }
        if(v==login){
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}

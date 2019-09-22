package com.example.syend.tennisnow;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;


import com.example.syend.tennisnow.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.*;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import android.widget.ScrollView;


public class Chat extends AppCompatActivity implements SensorEventListener {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView message;
    private ImageView send;
    private Button ring;
    private EditText input_msg;
    private DatabaseReference root;
    private String temp_key;
    private String emailStr;
    private String chatName;
    private SensorManager sm;
    private Sensor proxSensor;
    private ScrollView scrollView;
    private boolean change = false;

    ArrayList<String> values = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //emailStr = extras.getString("key");
            chatName = extras.getString("chat");
        }
        mAuth = FirebaseAuth.getInstance();
        send = (ImageView) findViewById(R.id.send);
        //ring = (Button) findViewById(R.id.ring);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        proxSensor= sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener(this,proxSensor,SensorManager.SENSOR_DELAY_NORMAL);

        input_msg = (EditText) findViewById(R.id.input);
        message = (TextView) findViewById(R.id.messageChat);
        setTitle("Chat Room- " + chatName);

        //FirebaseDatabase fb1 = FirebaseDatabase.getInstance();  //getReference().child("Chat Room");
        chatName = withoutString(chatName, "$");
        chatName = withoutString(chatName, "#");
        chatName = withoutString(chatName, ".");
        chatName = withoutString(chatName, "[");
        chatName = withoutString(chatName, "]");
        root = FirebaseDatabase.getInstance().getReference().child(chatName);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("email", mAuth.getCurrentUser().getEmail());
                map2.put("message", input_msg.getText().toString());
                message_root.updateChildren(map2);
                input_msg.setText("");


            }
        });

        root.addChildEventListener(new ChildEventListener        () {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private String chat_msg, chat_user_name;
    private void append_chat_conversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()){
            chat_user_name = (String)((DataSnapshot)i.next()).getValue();
            chat_msg = (String)((DataSnapshot)i.next()).getValue();
            message.append(chat_user_name+ "\n" + chat_msg +" \n" + "\n" );

        }
    }
    public static String withoutString(String base, String remove) {
        int blen = base.length();

        int rlen = remove.length();
        String lowbase = base.toLowerCase();

        String lowrem = remove.toLowerCase();

        String fin = "";
        for (int i = 0; i < blen; i++) {
            if (i <= blen - rlen) {
                String tmp = lowbase.substring(i,i+rlen);
                if (!tmp.equals(lowrem))
                    fin += base.substring(i,i+1);
                else {
                    i += rlen-1;
                }
            }
            else {
                String tmp2 = lowbase.substring(i,i+1);
                if (!tmp2.equals(lowrem))
                    fin += base.substring(i,i+1);
            }
        }

        return fin;

    }
    public void diff(View view){
        mAuth.signOut();
        finish();
        Intent intent = new Intent(Chat.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.color){
        if(!change) {
                    change = true;
            }
            else{
            change = false;
        }
            }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(change)
        message.setTextColor((int)(Math.random()*Integer.MAX_VALUE+Integer.MIN_VALUE));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}





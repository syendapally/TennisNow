package com.example.syend.tennisnow;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    TextView score;
    ListView scoreList;
    ImageView atpwta;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    final ArrayList<String> scores= new ArrayList<>();
    EditText searchbar;
    Node n;
    public static String str;
    public static ArrayList<MatchC> matches= new ArrayList<>();
    String hi = "";
    String pls = "";
    String playertype;
    ArrayList<String> dummy;
    ArrayAdapter<String> adapter;
    private SensorManager sm;
    private Sensor proxSensor;
    boolean hov;
    ArrayList<String> tourn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hov = true;

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        proxSensor= sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener(this,proxSensor,SensorManager.SENSOR_DELAY_NORMAL);
        int x = (int)(Math.random()*6);
        RelativeLayout layout =(RelativeLayout)findViewById(R.id.activity_main);
        if(x==0) {
            layout.setBackgroundResource(R.drawable.rftint);
        }
        else if(x==1){
            layout.setBackgroundResource(R.drawable.rntint);
        }
        else if(x==2){
            layout.setBackgroundResource(R.drawable.gdting);
        }
        else if(x==3){
            layout.setBackgroundResource(R.drawable.dttint);
        }
        else if(x==4){
            layout.setBackgroundResource(R.drawable.aztint);
        }
        else
            layout.setBackgroundResource(R.drawable.mctint);


        dummy =  new ArrayList<>();

        scoreList = (ListView) findViewById(R.id.scorelistview);
        atpwta = (ImageView) findViewById(R.id.atpwta);
        searchbar = (EditText) findViewById(R.id.search_bar);
        searchbar.setVisibility(View.INVISIBLE);
       // listView = (ExpandableListView) findViewById(R.id.expandablelw);
       // initData();
        //listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        //listView.setAdapter(listAdapter);
        str  = "";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try{
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://betimize.com/tennis/tennis_livescore.xml");
            Document doc2 = b.parse("http://104.236.84.91/xml/2d267179e36e8dda0d4467c25c2ea95/tennis_scores/wta_singles");
            Document doc3 = b.parse("http://betimize.com/tennis/atp_singles_shedule.xml");
            doc.getDocumentElement().normalize();
            doc2.getDocumentElement().normalize();
            doc3.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("player");
            NodeList itemsrw = doc2.getElementsByTagName("player");
            NodeList itemsrf = doc3.getElementsByTagName("player");



            for (int i = 0; i < itemsr.getLength(); i++)
            {
                Node n = itemsr.item(i);
                for(int j = 9; j>=0; j--) {
                    str += (" # " + n.getAttributes().item(j).getNodeValue().toString());

                }

                scores.add(str);
                str = "";
            }
            for (int i = 0; i < itemsrw.getLength(); i++)
            {
                Node n = itemsrw.item(i);
                for(int j = 9; j>=0; j--) {
                    str += (" # " + n.getAttributes().item(j).getNodeValue().toString());

                }

                scores.add(str);
                str = "";
            }


            for (int k = 0; k<(scores.size()-1); k++){
                scores.add(k, scores.remove(k) + scores.remove(k));
            }
            for(int m = 0; m<scores.size(); m++){
                dummy.add(scores.get(m));
            }
            for(int e = 0; e<dummy.size(); e++){
                String[] arr = dummy.get(e).split("#");
                dummy.set(e, arr[10] + " vs. " + arr[20] + "    " + arr[2]+" - " + arr[12]);
            }

        }

        catch(Exception e){
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        try{
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://betimize.com/tennis/tennis_livescore.xml");

            doc.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("match");
            NodeList categories = doc.getElementsByTagName("category");
            NodeList playersList;
            String tournament = "";
         //   boolean ch = false;
            //ArrayList<String> player1 = new ArrayList<>();
            //ArrayList<String> player2 = new ArrayList<>();

                for (int i = 0; i < itemsr.getLength(); i++) {  // Match loop
                    Node n = itemsr.item(i);
                    String date = "";
                    String time = "";
                    String status = "";
                    String tb = "";
                    //Node p = itemsp.item(i);
                    MatchC match;
                    for (int j = 0; j < 5; j++) {

                        if ((n.getAttributes().item(j).getNodeName().toString()).contains("date")) {
                            date = n.getAttributes().item(j).getNodeValue().toString();
                        }
                        if ((n.getAttributes().item(j).getNodeName().toString()).contains("time")) {
                            time = n.getAttributes().item(j).getNodeValue().toString();
                        }
                        if ((n.getAttributes().item(j).getNodeName().toString()).contains("status")) {
                            status = n.getAttributes().item(j).getNodeValue().toString();
                        }
                        if ((n.getAttributes().item(j).getNodeName().toString()).contains("tb")) {
                            tb = n.getAttributes().item(j).getNodeValue().toString();
                        }
                    }
                    //Match details completed
                    playersList = n.getChildNodes();
                    ArrayList<String> player1 = new ArrayList<>();
                    ArrayList<String> player2 = new ArrayList<>();
                    Node p1 = playersList.item(1);
                    Node p2 = playersList.item(3);
                    for (int k = 0; k < 11; k++) {
                        player1.add(p1.getAttributes().item(k).getNodeValue().toString());
                    }
                    for (int k = 0; k < 11; k++) {
                        player2.add(p2.getAttributes().item(k).getNodeValue().toString());
                    }

                    match = new MatchC(date, time, status, tb, player1, player2);

                    matches.add(match);
                }
            }
        catch(Exception e){
            System.out.println(e.toString());
        }
            for(int i = matches.size()-1; i>=0; i--) {
                scores.add(0,matches.get(i).toString());
                String[] arrsrikar = matches.get(i).toString().split("#");
                dummy.add(0,"LIVE: " + arrsrikar[1] + " vs " + arrsrikar[12] + "Set Score: " + arrsrikar[9] + " - " + arrsrikar[20]);
            }



        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, dummy);
            scoreList.setAdapter(adapter);
            scoreList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int intposition = position;
                    String clickedValue = scores.get(intposition);
                    Toast hi = Toast.makeText(getApplicationContext(), clickedValue, Toast.LENGTH_LONG);
                    // hi.show();
                    Intent intent = new Intent(MainActivity.this, match.class);
                    intent.putExtra("key",clickedValue);
                    startActivity(intent);
                }
            });



    atpwta.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, Rank.class);
            startActivity(intent);
        }
    });
scoreList.setVisibility(View.VISIBLE);
    }
    public void rank (View view){
        Intent intent = new Intent(MainActivity.this, Rank.class);
        startActivity(intent);
    }
    private void initData(){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.searchp) {
            searchbar.setVisibility(View.VISIBLE);
            searchbar.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void afterTextChanged(Editable mEdit)
                {
                    playertype = mEdit.toString();
                    for(int i = 0; i<dummy.size(); i++) {
                        if (dummy.get(i).toLowerCase().contains(playertype.toLowerCase())){
                            dummy.add(0,dummy.remove(i));
                            scores.add(0,scores.remove(i));
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after){}

                public void onTextChanged(CharSequence s, int start, int before, int count){}
            });
        }
        if(item.getItemId()==R.id.hovering){
            promptSpeechInput();
        }

    scoreList.setAdapter(adapter);

        return super.onOptionsItemSelected(item);
    }
    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say start to enable the hover feature and say stop to disable it, or say a player's name to set their picture for your background!");
        try{
            startActivityForResult(i,100);
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this, "No Voice Recognition Available", Toast.LENGTH_SHORT);
        }
    }
    public void onActivityResult(int request_code, int result_code, Intent i){
        super.onActivityResult(request_code, result_code, i);
        switch (request_code){
            case 100: if(result_code==RESULT_OK&&i!=null)
            {
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if(result.get(0).toLowerCase().contains("stop")){
                    hov = false;
                }
                if(result.get(0).toLowerCase().contains("start")){
                    hov = true;
                }
                if(result.get(0).toLowerCase().contains("federer")){
                    hov = false;
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
                    layout.setBackgroundResource(R.drawable.rftint);
                }
                if(result.get(0).toLowerCase().contains("federer")){
                    hov = false;
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
                    layout.setBackgroundResource(R.drawable.rftint);
                }
                if(result.get(0).toLowerCase().contains("cilic")){
                    hov = false;
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
                    layout.setBackgroundResource(R.drawable.mctint);
                }
                if(result.get(0).toLowerCase().contains("nadal")){
                    hov = false;
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
                    layout.setBackgroundResource(R.drawable.rntint);
                }
                if(result.get(0).toLowerCase().contains("dominic")){
                    hov = false;
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
                    layout.setBackgroundResource(R.drawable.dttint);
                }
                if(result.get(0).toLowerCase().contains("zverev")){
                    hov = false;
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
                    layout.setBackgroundResource(R.drawable.aztint);
                }
                if(result.get(0).toLowerCase().contains("dimitrov")){
                    hov = false;
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
                    layout.setBackgroundResource(R.drawable.gdting);
                }

            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(hov) {
            int x = (int) (Math.random() * 6);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
            if (x == 0) {
                layout.setBackgroundResource(R.drawable.rftint);
            } else if (x == 1) {
                layout.setBackgroundResource(R.drawable.rntint);
            } else if (x == 2) {
                layout.setBackgroundResource(R.drawable.gdting);
            } else if (x == 3) {
                layout.setBackgroundResource(R.drawable.dttint);
            } else if (x == 4) {
                layout.setBackgroundResource(R.drawable.aztint);
            } else
                layout.setBackgroundResource(R.drawable.mctint);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}



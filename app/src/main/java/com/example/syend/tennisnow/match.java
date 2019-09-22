package com.example.syend.tennisnow;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class match extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{
    TextView player1;
    TextView player2;
    TextView setscore;
    TextView player1g;
    TextView player2g;
    TextView p1s1;
    TextView p1s2;
    TextView p1s3;
    TextView p1s4;
    TextView p1s5;
    TextView p2s1;
    TextView p2s2;
    TextView p2s3;
    TextView p2s4;
    TextView p2s5;
    TextView gs;
    String med;
    String prev;
    TextView outcome;
    ImageView chat;
    ImageView p1serve;
    ImageView p2serve;
    ImageView refresh;
    double rank1;
    double rank2;
    double ud1;
    double ud2;
    String transf1;
    String transf2;
    ArrayList<MatchC> matches = new ArrayList<>();
    ArrayList<String> dummy = new ArrayList<>();
    ArrayList<String> scores = new ArrayList<>();
    Handler handler = new Handler();
    ArrayList<String> p5 = new ArrayList<>();
    ArrayList<Last5> last5ArrayList = new ArrayList<>();
    ArrayList<Last5> last5ArrayList2 = new ArrayList<>();
    ArrayList<ConstructH2H> headtoheadarraylist = new ArrayList<>();
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private static DecimalFormat df2 = new DecimalFormat(".##");
    int player1h2h =0;
    int player2h2h = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Bundle extras = getIntent().getExtras();
        str = "";
        p1serve = (ImageView) findViewById(R.id.p1Serve);
        p2serve = (ImageView) findViewById(R.id.p2Serve);
        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://betimize.com/tennis/atp_singles_shedule.xml");

            doc.getDocumentElement().normalize();


            NodeList itemsr = doc.getElementsByTagName("player");


            for (int i = 0; i < itemsr.getLength(); i++) {
                Node n = itemsr.item(i);
                for (int j = 9; j >= 0; j--) {
                    str += (" # " + n.getAttributes().item(j).getNodeValue().toString());

                }

                scores.add(str);
                str = "";
            }
            for (int k = 0; k < scores.size() - 1; k++) {
                scores.add(k, scores.remove(k) + scores.remove(k));
            }
            for (int m = 0; m < scores.size(); m++) {
                dummy.add(scores.get(m));
            }
            for (int e = 0; e < dummy.size(); e++) {
                String[] arr = dummy.get(e).split("#");
                dummy.set(e, arr[10] + " vs. " + arr[20] + "    " + arr[2] + " - " + arr[12]);
            }

        } catch (Exception e) {
            }
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://betimize.com/tennis/tennis_livescore.xml");

            doc.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("match");
            NodeList playersList;
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
                if (!(status.toLowerCase().contains("cancelled"))) {
                    matches.add(match);
                }
            }
            for (int i = 0; i < matches.size(); i++) {
                scores.add(0, matches.get(i).toString());
                dummy.add(0, matches.get(i).toString());
            }

        } catch (Exception e) {

        }
        if (extras != null) {

            try {
                player1 = (TextView) findViewById(R.id.player1);
                player2 = (TextView) findViewById(R.id.player2);
                setscore = (TextView) findViewById(R.id.setscore);
                p1s1 = (TextView) findViewById(R.id.player1s1);
                p1s2 = (TextView) findViewById(R.id.player1s2);
                p1s3 = (TextView) findViewById(R.id.player1s3);
                p1s4 = (TextView) findViewById(R.id.player1s4);
                p1s5 = (TextView) findViewById(R.id.player1s5);
                p2s1 = (TextView) findViewById(R.id.player2s1);
                p2s2 = (TextView) findViewById(R.id.player2s2);
                p2s3 = (TextView) findViewById(R.id.player2s3);
                p2s4 = (TextView) findViewById(R.id.player2s4);
                p2s5 = (TextView) findViewById(R.id.player2s5);
                gs = (TextView) findViewById(R.id.gamescore);
                player1g = (TextView) findViewById(R.id.player1namegrid);
                player2g = (TextView) findViewById(R.id.player2namegrid);
                prev = extras.getString("key");

                if (!(prev.contains("LIVE"))) {


                    outcome = (TextView) findViewById(R.id.outcome);
                    int player2beg = prev.indexOf("vs");
                    //int player2start = prev.indexOf("vs") + 5;
                    //int player2end = prev.indexOf(":");
                    String[] arr = prev.split("#");
                    player1.setText(arr[10]);

                    // player2s = arr[4].substring(0,arr[4].indexOf(":"));

                    // setscores = arr[4].substring(arr[4].indexOf(":")+1);
                    // player2.setText(prev.substring(player2start, player2end));
                    player2.setText(arr[20]);
                    setscore.setText(arr[2] + " - " + arr[12]);
                    player1g.setText(player1.getText());
                    p1s1.setText(arr[7]);
                    p1s2.setText(arr[6]);
                    p1s3.setText(arr[5]);
                    p1s4.setText(arr[4]);
                    p1s5.setText(arr[3]);
                    player2g.setText(player2.getText());
                    p2s1.setText(arr[17]);
                    p2s2.setText(arr[16]);
                    p2s3.setText(arr[15]);
                    p2s4.setText(arr[14]);
                    p2s5.setText(arr[13]);
                } else {
                    for (int i = 0; i < scores.size(); i++) {
                        String[] ma = scores.get(i).split("#");
                        String name1 = ma[1];
                        if (prev.contains(ma[1])) {
                            prev = scores.get(i);
                            break;
                        }
                    }
                    String[] arr = prev.split("#");
                    outcome = (TextView) findViewById(R.id.outcome);
                    int player2beg = prev.indexOf("vs");
                    //int player2start = prev.indexOf("vs") + 5;
                    //int player2end = prev.indexOf(":");
                    player1.setText(arr[1]);
                    if (arr[2].contains("True")) {
                        p1serve.setImageResource(R.mipmap.rb1);
                    } else {
                        p1serve.setImageResource(R.color.colorPrimaryk);
                    }
                    if (arr[13].contains("True")) {
                        p2serve.setImageResource(R.mipmap.rb1);
                    } else {
                        p2serve.setImageResource(R.color.colorPrimaryk);
                    }

                    // player2s = arr[4].substring(0,arr[4].indexOf(":"));

                    // setscores = arr[4].substring(arr[4].indexOf(":")+1);
                    // player2.setText(prev.substring(player2start, player2end));
                    player2.setText(arr[12]);
                    setscore.setText(arr[9] + " - " + arr[20]);
                    player1g.setText(player1.getText());
                    p1s1.setText(arr[4]);
                    p1s2.setText(arr[5]);
                    p1s3.setText(arr[6]);
                    p1s4.setText(arr[7]);
                    p1s5.setText(arr[8]);
                    player2g.setText(player2.getText());
                    p2s1.setText(arr[15]);
                    p2s2.setText(arr[16]);
                    p2s3.setText(arr[17]);
                    p2s4.setText(arr[18]);
                    p2s5.setText(arr[19]);
                    if(arr[3].trim().equalsIgnoreCase("")&&!arr[14].trim().equalsIgnoreCase("")){
                        arr[3] = "A";
                    }
                    if(arr[14].trim().equalsIgnoreCase("")&&!arr[3].trim().equalsIgnoreCase("")){
                        arr[14] = "A";
                    }
                    gs.setText(arr[3] + " - " + arr[14]);
                    gs.setTextColor(Color.BLACK);
                }
            } catch (Exception e) {

            }
        }


        player1.setTextColor(Color.BLACK);

        player2.setTextColor(Color.BLACK);
        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stat = new Intent(match.this, Statistics.class);
                stat.putExtra("player",player1.getText().toString());
                startActivity(stat);
            }
        });
        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stat = new Intent(match.this, Statistics.class);
                stat.putExtra("player",player2.getText().toString());
                startActivity(stat);
            }
        });
    }

    String str;

    public void predict(View view) {
        String[] arrp1 = player1.getText().toString().replace("-", " ").split(". ");
        String p1predict = arrp1[1];
        String[] arrp2 = player2.getText().toString().replace("-", " ").split(". ");
        String p2predict = arrp2[1];
        ArrayList<String> scores = new ArrayList<>();
        ArrayList<String> dummy = new ArrayList<>();
        int p1str = 0;
        int p2str = 0;
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://betimize.com/tennis/atp_singles_shedule_h2h.xml");

            doc.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("last5");
            NodeList playersList;
            NodeList buff;
            NamedNodeMap attr;
            //ArrayList<String> player1 = new ArrayList<>();
            //ArrayList<String> player2 = new ArrayList<>();

            NodeList pnames = doc.getElementsByTagName("player");
            for(int y = 0; y<pnames.getLength(); y++) {
                String playernametemp = pnames.item(y).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                if(playernametemp.contains(player1.getText().toString().toLowerCase().trim()))  {
                    for (int i = 0; i < itemsr.getLength(); i++) {
                        String result = "";
                        String date = "";
                        String tournament = "";
                        String player1 = "";
                        String player2 = "";
                        String score = "";// Match loop
                        String playerN = "";
                        Node n = itemsr.item(y);
                        playersList = n.getChildNodes();
                        for (int k = 1; k < playersList.getLength(); k += 2) {
                            Node s = playersList.item(k);
                            Node l;
                            //Node p = itemsp.item(i);

                            Last5 last5;
                            for (int j = 0; j < 9; j++) {

                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("date")) {
                                    date = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("result")) {
                                    result = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("tournament")) {
                                    tournament = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player1")) {
                                    player1 = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player2")) {
                                    player2 = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("score")) {
                                    score = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                l = pnames.item(y);
                                attr = l.getAttributes();
                                playerN = l.getAttributes().item(0).getNodeValue();

                            }

                            last5 = new Last5(result, date, tournament, player1, player2, score, playerN);
                            last5ArrayList.add(last5);
                        }
                    }
                }
            }
            for(int u = 0; u<10; u++){
                if(last5ArrayList.get(u).getResult().toLowerCase().trim().contains(("Win").toLowerCase())){
                    p1str++;
                }
            }
        }
        catch(Exception e){
            }
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://betimize.com/tennis/atp_singles_shedule_h2h.xml");

            doc.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("last5");
            NodeList playersList;
            NodeList buff;
            NamedNodeMap attr;
            //ArrayList<String> player1 = new ArrayList<>();
            //ArrayList<String> player2 = new ArrayList<>();

            NodeList pnames = doc.getElementsByTagName("player");
            for(int y = 0; y<pnames.getLength(); y++) {
                String playernametemp = pnames.item(y).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                if(playernametemp.contains(player2.getText().toString().toLowerCase().trim()))  {
                    for (int i = 0; i < itemsr.getLength(); i++) {
                        String result = "";
                        String date = "";
                        String tournament = "";
                        String player1 = "";
                        String player2 = "";
                        String score = "";// Match loop
                        String playerN = "";
                        Node n = itemsr.item(y);
                        playersList = n.getChildNodes();
                        for (int k = 1; k < playersList.getLength(); k += 2) {
                            Node s = playersList.item(k);
                            Node l;
                            //Node p = itemsp.item(i);

                            Last5 last5;
                            for (int j = 0; j < 9; j++) {

                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("date")) {
                                    date = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("result")) {
                                    result = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("tournament")) {
                                    tournament = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player1")) {
                                    player1 = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player2")) {
                                    player2 = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("score")) {
                                    score = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                l = pnames.item(y);
                                attr = l.getAttributes();
                                playerN = l.getAttributes().item(0).getNodeValue();

                            }

                            last5 = new Last5(result, date, tournament, player1, player2, score, playerN);
                            last5ArrayList2.add(last5);
                        }
                    }
                }
            }
            for(int u = 0; u<10; u++){
                med = last5ArrayList2.get(u).getResult().toLowerCase().trim();
                if(last5ArrayList2.get(u).getResult().toLowerCase().trim().contains(("Win").toLowerCase())){
                    p2str++;
                }
            }
        }
        catch(Exception e){
        }
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://104.236.84.91/xml/d2d267179e36e8dda0d4467c25c2ea95/tennis_scores/wta_singles_h2h");

            doc.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("last5");
            NodeList playersList;
            NodeList buff;
            NamedNodeMap attr;
            //ArrayList<String> player1 = new ArrayList<>();
            //ArrayList<String> player2 = new ArrayList<>();

            NodeList pnames = doc.getElementsByTagName("player");
            for(int y = 0; y<pnames.getLength(); y++) {
                String playernametemp = pnames.item(y).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                if(playernametemp.contains(player1.getText().toString().toLowerCase().trim()))  {
                    for (int i = 0; i < itemsr.getLength(); i++) {
                        String result = "";
                        String date = "";
                        String tournament = "";
                        String player1 = "";
                        String player2 = "";
                        String score = "";// Match loop
                        String playerN = "";
                        Node n = itemsr.item(y);
                        playersList = n.getChildNodes();
                        for (int k = 1; k < playersList.getLength(); k += 2) {
                            Node s = playersList.item(k);
                            Node l;
                            //Node p = itemsp.item(i);

                            Last5 last5;
                            for (int j = 0; j < 9; j++) {

                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("date")) {
                                    date = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("result")) {
                                    result = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("tournament")) {
                                    tournament = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player1")) {
                                    player1 = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player2")) {
                                    player2 = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("score")) {
                                    score = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                l = pnames.item(y);
                                attr = l.getAttributes();
                                playerN = l.getAttributes().item(0).getNodeValue();

                            }

                            last5 = new Last5(result, date, tournament, player1, player2, score, playerN);
                            last5ArrayList.add(last5);
                        }
                    }
                }
            }
            p1str =0;
            for(int u = 0; u<10; u++){
                if(last5ArrayList.get(u).getResult().toLowerCase().trim().contains(("Win").toLowerCase())){
                    p1str++;
                }
            }
        }
        catch(Exception e){
        }
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://104.236.84.91/xml/d2d267179e36e8dda0d4467c25c2ea95/tennis_scores/wta_singles_h2h");

            doc.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("last5");
            NodeList playersList;
            NodeList buff;
            NamedNodeMap attr;
            //ArrayList<String> player1 = new ArrayList<>();
            //ArrayList<String> player2 = new ArrayList<>();

            NodeList pnames = doc.getElementsByTagName("player");
            for(int y = 0; y<pnames.getLength(); y++) {
                String playernametemp = pnames.item(y).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                if(playernametemp.contains(player2.getText().toString().toLowerCase().trim()))  {
                    for (int i = 0; i < itemsr.getLength(); i++) {
                        String result = "";
                        String date = "";
                        String tournament = "";
                        String player1 = "";
                        String player2 = "";
                        String score = "";// Match loop
                        String playerN = "";
                        Node n = itemsr.item(y);
                        playersList = n.getChildNodes();
                        for (int k = 1; k < playersList.getLength(); k += 2) {
                            Node s = playersList.item(k);
                            Node l;
                            //Node p = itemsp.item(i);

                            Last5 last5;
                            for (int j = 0; j < 9; j++) {

                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("date")) {
                                    date = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("result")) {
                                    result = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("tournament")) {
                                    tournament = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player1")) {
                                    player1 = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player2")) {
                                    player2 = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((s.getAttributes().item(j).getNodeName().toString()).contains("score")) {
                                    score = s.getAttributes().item(j).getNodeValue().toString();
                                }
                                l = pnames.item(y);
                                attr = l.getAttributes();
                                playerN = l.getAttributes().item(0).getNodeValue();

                            }

                            last5 = new Last5(result, date, tournament, player1, player2, score, playerN);
                            last5ArrayList2.add(last5);
                        }
                    }
                }
            }
            p2str =0;
            for(int u = 0; u<10; u++){
                med = last5ArrayList2.get(u).getResult().toLowerCase().trim();
                if(last5ArrayList2.get(u).getResult().toLowerCase().trim().contains(("Win").toLowerCase())){
                    p2str++;
                }
            }
        }
        catch(Exception e){
        }


        str = "";
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://betimize.com/tennis/atp_singles_shedule.xml");

            doc.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("player");
            for (int i = 0; i < itemsr.getLength(); i++) {
                Node n = itemsr.item(i);

                for (int j = 0; j < 6; j++) {
                    str += ("#" + n.getAttributes().item(j).getNodeValue().toString());
                }

                scores.add(str);
                str = "";
            }
            try {
                DocumentBuilderFactory f2 = DocumentBuilderFactory.newInstance();
                DocumentBuilder b2 = f2.newDocumentBuilder();
                Document doc2 = b2.parse("http://104.236.84.91/xml/d2d267179e36e8dda0d4467c25c2ea95/tennis_scores/wta");

                doc2.getDocumentElement().normalize();

                NodeList itemsr2 = doc2.getElementsByTagName("player");
                for (int i = 0; i < itemsr2.getLength(); i++) {
                    Node n2 = itemsr2.item(i);

                    for (int j = 0; j < 6; j++) {
                        str += ("#" + n2.getAttributes().item(j).getNodeValue().toString());
                    }

                    scores.add(str);
                    str = "";
                }
            } catch (Exception e) {
                }
            for (int m = 0; m < scores.size(); m++) {
                dummy.add(scores.get(m));
            }
            for (int e = 0; e < dummy.size(); e++) {
                if (dummy.get(e).contains(p1predict)) {
                    String[] arr = dummy.get(e).split("#");
                    //Toast.makeText(match.this, arr[2].trim(), Toast.LENGTH_SHORT).show();
                    rank1 = Integer.parseInt(arr[2].trim());
                    if (arr[3].contains("same")) {
                        ud1 = 1;
                    } else if (arr[3].contains("up")) {
                        ud1 = Integer.parseInt(arr[3].substring(3, 4));
                    } else {
                        ud1 = -1 * Integer.parseInt(arr[3].substring(5, 6));
                    }
                }
            }
            for (int e = 0; e < dummy.size(); e++) {
                if (dummy.get(e).contains(p2predict)) {

                    String[] arr = dummy.get(e).split("#");
                    // Toast.makeText(match.this, arr[2].trim(), Toast.LENGTH_SHORT).show();
                    rank2 = Integer.parseInt(arr[2].trim());
                    if (arr[3].contains("same")) {
                        ud2 = 1;
                    } else if (arr[3].contains("up")) {
                        ud2 = Integer.parseInt(arr[3].substring(3, 4));
                    } else {
                        ud2 = -1 * Integer.parseInt(arr[3].substring(5, 6));
                    }
                }
            }

            try {
                DocumentBuilderFactory fo = DocumentBuilderFactory.newInstance();
                DocumentBuilder bo = fo.newDocumentBuilder();
                Document doco = bo.parse("http://betimize.com/tennis/atp_singles_shedule_h2h.xml");

                doco.getDocumentElement().normalize();


                //ArrayList<String> player1 = new ArrayList<>();
                //ArrayList<String> player2 = new ArrayList<>();
                NodeList matchl = doco.getElementsByTagName("category");
                Node main = matchl.item(0);
                NodeList mlist = main.getChildNodes();
                NodeList pnames = doco.getElementsByTagName("player");

                for(int y = 0; y<mlist.getLength()-1; y+=2) {
                    String playernametemp1 = pnames.item(y).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                    String playernametemp2 = pnames.item(y+1).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                    if(playernametemp1.contains(player1.getText().toString().toLowerCase().trim())&&playernametemp2.contains(player2.getText().toString().toLowerCase().trim())) {
                        // NodeList h2hl;
                        String date = "";
                        String tournament = "";
                        String player1 = "";
                        String player2 = "";
                        String score = "";
                        NodeList mk =mlist.item(y+1).getChildNodes();
                        NodeList h2hnL = mk.item(5).getChildNodes();
                        Node h2hn;
                        ConstructH2H h2hmatch;
                        for(int g = 1; g<h2hnL.getLength(); g+=2){
                            h2hn = h2hnL.item(g);
                            for (int j = 0; j < 8; j++) {

                                if ((h2hn.getAttributes().item(j).getNodeName().toString()).contains("date")) {
                                    date = h2hn.getAttributes().item(j).getNodeValue().toString();
                                }

                                if ((h2hn.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("tournament")) {
                                    tournament = h2hn.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((h2hn.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player1")) {
                                    player1 = h2hn.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((h2hn.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player2")) {
                                    player2 = h2hn.getAttributes().item(j).getNodeValue().toString();
                                }
                                if ((h2hn.getAttributes().item(j).getNodeName().toString()).contains("score")) {
                                    score = h2hn.getAttributes().item(j).getNodeValue().toString();
                                }
                            }
                            h2hmatch = new ConstructH2H(date, tournament, player1, player2, score);
                            headtoheadarraylist.add(h2hmatch);
                        }


                 /*   for (int g = 0; g < h2hl.getLength(); g++) {
                        ConstructH2H h2hmatch;
                        Node s = h2hl.item(g);
                        for (int j = 0; j < 7; j++) {

                            if ((s.getAttributes().item(j).getNodeName().toString()).contains("date")) {
                                date = s.getAttributes().item(j).getNodeValue().toString();
                            }

                            if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("tournament")) {
                                tournament = s.getAttributes().item(j).getNodeValue().toString();
                            }
                            if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player1")) {
                                player1 = s.getAttributes().item(j).getNodeValue().toString();
                            }
                            if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player2")) {
                                player2 = s.getAttributes().item(j).getNodeValue().toString();
                            }
                            if ((s.getAttributes().item(j).getNodeName().toString()).contains("score")) {
                                score = s.getAttributes().item(j).getNodeValue().toString();
                            }


                        }

                        h2hmatch = new ConstructH2H(date, tournament, player1, player2, score);
                        headtoheadarraylist.add(h2hmatch);
                    }*/
                        break;
                    }
                }
            }
            catch(Exception e){
                Toast.makeText(match.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            if(headtoheadarraylist.size()==0) {
                try {
                    DocumentBuilderFactory fo = DocumentBuilderFactory.newInstance();
                    DocumentBuilder bo = fo.newDocumentBuilder();
                    Document doco = bo.parse("http://betimize.com/tennis/atp_singles_shedule_h2h.xml");

                    doco.getDocumentElement().normalize();


                    //ArrayList<String> player1 = new ArrayList<>();
                    //ArrayList<String> player2 = new ArrayList<>();
                    NodeList matchl = doco.getElementsByTagName("category");
                    Node main = matchl.item(0);
                    NodeList mlist = main.getChildNodes();
                    NodeList pnames = doco.getElementsByTagName("player");

                    for (int y = 0; y < mlist.getLength() - 1; y += 2) {
                        String playernametemp1 = pnames.item(y).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                        String playernametemp2 = pnames.item(y + 1).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                        if (playernametemp1.contains(player1.getText().toString().toLowerCase().trim()) && playernametemp2.contains(player2.getText().toString().toLowerCase().trim())) {
                            // NodeList h2hl;
                            String date = "";
                            String tournament = "";
                            String player1 = "";
                            String player2 = "";
                            String score = "";
                            NodeList mk = mlist.item(y + 1).getChildNodes();
                            NodeList h2hnL = mk.item(5).getChildNodes();
                            Node h2hn;
                            ConstructH2H h2hmatch;
                            for (int g = 1; g < h2hnL.getLength(); g += 2) {
                                h2hn = h2hnL.item(g);
                                for (int j = 0; j < 8; j++) {

                                    if ((h2hn.getAttributes().item(j).getNodeName().toString()).contains("date")) {
                                        date = h2hn.getAttributes().item(j).getNodeValue().toString();
                                    }

                                    if ((h2hn.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("tournament")) {
                                        tournament = h2hn.getAttributes().item(j).getNodeValue().toString();
                                    }
                                    if ((h2hn.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player1")) {
                                        player1 = h2hn.getAttributes().item(j).getNodeValue().toString();
                                    }
                                    if ((h2hn.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player2")) {
                                        player2 = h2hn.getAttributes().item(j).getNodeValue().toString();
                                    }
                                    if ((h2hn.getAttributes().item(j).getNodeName().toString()).contains("score")) {
                                        score = h2hn.getAttributes().item(j).getNodeValue().toString();
                                    }
                                }
                                h2hmatch = new ConstructH2H(date, tournament, player1, player2, score);
                                headtoheadarraylist.add(h2hmatch);
                            }


                 /*   for (int g = 0; g < h2hl.getLength(); g++) {
                        ConstructH2H h2hmatch;
                        Node s = h2hl.item(g);
                        for (int j = 0; j < 7; j++) {

                            if ((s.getAttributes().item(j).getNodeName().toString()).contains("date")) {
                                date = s.getAttributes().item(j).getNodeValue().toString();
                            }

                            if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("tournament")) {
                                tournament = s.getAttributes().item(j).getNodeValue().toString();
                            }
                            if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player1")) {
                                player1 = s.getAttributes().item(j).getNodeValue().toString();
                            }
                            if ((s.getAttributes().item(j).getNodeName().toString()).equalsIgnoreCase("player2")) {
                                player2 = s.getAttributes().item(j).getNodeValue().toString();
                            }
                            if ((s.getAttributes().item(j).getNodeName().toString()).contains("score")) {
                                score = s.getAttributes().item(j).getNodeValue().toString();
                            }


                        }

                        h2hmatch = new ConstructH2H(date, tournament, player1, player2, score);
                        headtoheadarraylist.add(h2hmatch);
                    }*/
                            break;
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(match.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            for(int i = 0; i<headtoheadarraylist.size(); i++) {
                String[] arr = headtoheadarraylist.get(i).toString().split("#");
               int p1score = Integer.parseInt(arr[10].substring(0,1));
               int p2score = Integer.parseInt(arr[10].substring(4));
                if(arr[6].trim().equalsIgnoreCase(player1.getText().toString())){
                    if(p1score>p2score){
                        player1h2h++;
                    }
                    else{
                        player2h2h++;
                    }
                }
                else{
                    if(p1score>p2score){
                        player2h2h++;
                    }
                    else{
                        player1h2h++;
                    }
                }
            }
            double player1chance = 0;
            double player2chance = 0;
 /*           if (ud1 > 0) {
                player1chance = (.2 * ud1) / (rank1);
            }
            if (ud1 < 0) {
                player1chance = .1 / ((rank1) * (-0.2 * ud1));
            }
            if (ud2 > 0) {
                player2chance = (.2 * ud2) / (rank2);
            }
            if (ud2 < 0) {
                player2chance = 0.1 / ((rank2) * (-0.2 * ud2));
            }*/
            if(rank2 == 0){
                rank2 = 101;
            }
            if(rank1 ==0){
                rank1 = 101;
            }
            if(player1h2h==0&&player2h2h==0){
                player1h2h =1;
                player2h2h =1;
            }
            if(p1str==0&&p2str==0){
                player1chance = 100*(rank2/(rank1+rank2));
            }

            else {
                double rc =  ((rank2 / (rank1 + rank2)) * 50);
                double strc = (20 * ((p1str * 1.0) / (p1str * 1.0 + p2str * 1.0)));
                double h2hc = (30*((player1h2h*1.0)/((player1h2h*1.0)+(player2h2h*1.0))));
               player1chance = rc+strc+h2hc;
            }
            player2chance = 100-player1chance;
           if(player1chance==0||player2chance==0){
               outcome.setText("Match is finished or player is unranked or there are no statistics for recent form available yet.");
           }
            else if (player1chance >= player2chance) {
                double d = player1chance;
                outcome.setText("Tennis Predict predicts that there is a " + df2.format(d) + "% chance that " + player1.getText() + " will win");
                outcome.setTextColor(Color.BLACK);
                Intent intent = new Intent(match.this, Predicted.class);
                intent.putExtra("predicted", d);
                intent.putExtra("predictedw", player1.getText().toString());
                intent.putExtra("predictedl", player2.getText().toString());
              //  Toast.makeText(match.this, rank1+" " + rank2 + "--" + p1str + " " + p2str, Toast.LENGTH_SHORT).show();
                startActivity(intent);

            } else {
                double d = player2chance;
                outcome.setText("Tennis Predict predicts that there is a " + df2.format(d) + "% chance that " + player2.getText() + " will win");
                outcome.setTextColor(Color.BLACK);
                Intent intent = new Intent(match.this, Predicted.class);
                intent.putExtra("predicted", d);
                intent.putExtra("predictedw", player2.getText().toString());
                intent.putExtra("predictedl", player1.getText().toString());
               // Toast.makeText(match.this, rank1+" " + rank2 + "--" + p1str + " " + p2str, Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.matchmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.video) {
            Intent searchv = new Intent(match.this, Search.class);
            searchv.putExtra("player1", player1.getText().toString());
            searchv.putExtra("player2", player2.getText().toString());
            startActivity(searchv);
        }
        if (item.getItemId() == R.id.chaticon) {
            Intent intent = new Intent(match.this, LogSign.class);
            intent.putExtra("chat", player1.getText() + " vs " + player2.getText());
            startActivity(intent);
        }
        if (item.getItemId() == R.id.refreshicon) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        if(item.getItemId() == R.id.notify){
          Intent stat = new Intent(match.this, H2H.class);
            stat.putExtra("p1",player1.getText().toString());
            stat.putExtra("p2",player2.getText().toString());
            startActivity(stat);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Intent stat = new Intent(match.this, Statistics.class);
        stat.putExtra("p1",player1.getText().toString());
        stat.putExtra("p2",player2.getText().toString());
        startActivity(stat);
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
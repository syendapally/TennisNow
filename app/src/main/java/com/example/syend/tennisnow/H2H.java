package com.example.syend.tennisnow;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class H2H extends AppCompatActivity {
    ListView h2hL;
    ArrayList<String> h2h = new ArrayList<>();
    ArrayList<ConstructH2H> headtoheadarraylist = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TextView p1n;
    TextView p2n;
    TextView p1h;
    TextView p2h;
    int player1h2h = 0;
    int player2h2h = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h2_h);
        Bundle extras = getIntent().getExtras();
        h2hL = (ListView)findViewById(R.id.h2hlist);
        p1n = (TextView) findViewById(R.id.p1n);
        p1h = (TextView) findViewById(R.id.p1h);
        p2h = (TextView) findViewById(R.id.p2h);
        p2n = (TextView) findViewById(R.id.p2n);
        String player1n = "";
        String player2n = "";
        if (extras != null) {
            player1n = extras.getString("p1");
            player2n = extras.getString("p2");
        }
        p1n.setText(player1n);
        p2n.setText(player2n);
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://betimize.com/tennis/atp_singles_shedule_h2h.xml");

            doc.getDocumentElement().normalize();


            //ArrayList<String> player1 = new ArrayList<>();
            //ArrayList<String> player2 = new ArrayList<>();
            NodeList matchl = doc.getElementsByTagName("category");
            Node main = matchl.item(0);
            NodeList mlist = main.getChildNodes();
            NodeList pnames = doc.getElementsByTagName("player");

            for(int y = 0; y<mlist.getLength()-1; y+=2) {
                String playernametemp1 = pnames.item(y).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                String playernametemp2 = pnames.item(y+1).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                if(playernametemp1.contains(player1n.toLowerCase().trim())&&playernametemp2.contains(player2n.toLowerCase().trim())) {
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
            Toast.makeText(H2H.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        if(headtoheadarraylist.size()==0) {
            try {
                DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                DocumentBuilder b = f.newDocumentBuilder();
                Document doc = b.parse("http://betimize.com/tennis/atp_singles_shedule_h2h.xml");

                doc.getDocumentElement().normalize();


                //ArrayList<String> player1 = new ArrayList<>();
                //ArrayList<String> player2 = new ArrayList<>();
                NodeList matchl = doc.getElementsByTagName("category");
                Node main = matchl.item(0);
                NodeList mlist = main.getChildNodes();
                NodeList pnames = doc.getElementsByTagName("player");

                for (int y = 0; y < mlist.getLength() - 1; y += 2) {
                    String playernametemp1 = pnames.item(y).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                    String playernametemp2 = pnames.item(y + 1).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                    if (playernametemp1.contains(player1n.toLowerCase().trim()) && playernametemp2.contains(player2n.toLowerCase().trim())) {
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
                Toast.makeText(H2H.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        for(int i = 0; i<headtoheadarraylist.size(); i++) {
            String[] arr = headtoheadarraylist.get(i).toString().split("#");
            h2h.add(arr[6] + " vs " + arr[8] + "- Tournament: " +arr[4]+" | "+ "Score: " +arr[10]);
            int p1score = Integer.parseInt(arr[10].substring(0,1));
            int p2score = Integer.parseInt(arr[10].substring(4));
            if(arr[6].contains(player1n)){
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
        p2h.setText(Integer.toString(player2h2h));
        p1h.setText(Integer.toString(player1h2h));
        if(player1h2h>player2h2h){
            p1h.setTextColor(Color.GREEN);
        }

        if(player1h2h<player2h2h){
            p2h.setTextColor(Color.GREEN);
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, h2h);
        h2hL.setAdapter(adapter);
        h2hL.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}

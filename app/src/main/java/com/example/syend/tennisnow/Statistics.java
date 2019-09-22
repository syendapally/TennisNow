package com.example.syend.tennisnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Statistics extends AppCompatActivity {
    ListView last5l;
    ArrayList<String> p5 = new ArrayList<>();
    ArrayList<Last5> last5ArrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Bundle extras = getIntent().getExtras();
        last5l = (ListView) findViewById(R.id.last5list);

        String player = "";

        if (extras != null) {
            player = extras.getString("player");
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
            for (int y = 0; y < pnames.getLength(); y++) {
                String playernametemp = pnames.item(y).getAttributes().item(0).getNodeValue().toLowerCase().trim();
                if (playernametemp.contains(player.toLowerCase().trim())) {
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
        } catch (Exception e) {
            Toast.makeText(Statistics.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        try {


            for (int i = 0; i < 10; i++) {
                String[] arr = last5ArrayList.get(i).toString().split("#");
                p5.add(arr[8] + ": " + arr[10] + " vs " + arr[12] + " - Score: " + arr[14] + " | " + arr[4]);

            }
            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, p5);
            last5l.setAdapter(adapter);
            last5l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
        catch (Exception e){
            Toast.makeText(Statistics.this, "Recent Form Unavailable", Toast.LENGTH_SHORT).show();
        }
    }


    }


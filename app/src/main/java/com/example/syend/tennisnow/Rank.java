package com.example.syend.tennisnow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Rank extends AppCompatActivity {
    public static String str;
    ListView rankList;
    ListView wtaRank;
   final ArrayList<String> scores= new ArrayList<>();
   final ArrayList<String> dummy= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        rankList = (ListView) findViewById(R.id.scorelistviewrank);
        wtaRank = (ListView) findViewById(R.id.wtarank);

        str = "";
        try{
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://betimize.com/tennis/ranking.xml");

            doc.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("player");
            for (int i = 0; i < itemsr.getLength(); i++)
            {
                Node n = itemsr.item(i);

                for(int j = 0; j<5; j++) {
                    str += ("#" +n.getAttributes().item(j).getNodeValue().toString());



                }

                scores.add(str);
                str = "";
            }
            for(int m = 0; m<scores.size(); m++){
                dummy.add(scores.get(m));
            }
            for(int e = 0; e<dummy.size(); e++){
                String[] arr = dummy.get(e).split("#");
                if(arr[3].contains("same"))
                    arr[3] = "";
                dummy.set(e, arr[1]  + "   Rank: " + arr[2]+ "   Points : "+arr[5] + "   Country: " + arr[4]);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, dummy);
            rankList.setAdapter(adapter);
            rankList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int intposition = position;
                    String clickedValue = scores.get(intposition);
                    Toast hi = Toast.makeText(getApplicationContext(), clickedValue, Toast.LENGTH_LONG);
                    // hi.show();
                    Intent intent = new Intent(Rank.this, Profile.class);
                    intent.putExtra("key",clickedValue);
                    startActivity(intent);
                }
            });
        }

        catch(Exception e){
            System.out.println(e.toString());
        }
        ArrayList<String> wtascores= new ArrayList<>();
        ArrayList<String> wtadummy= new ArrayList<>();
        str = "";
        try{
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("http://104.236.84.91/xml/d2d267179e36e8dda0d4467c25c2ea95/tennis_scores/wta");

            doc.getDocumentElement().normalize();

            NodeList itemsr = doc.getElementsByTagName("player");
            for (int i = 0; i < itemsr.getLength(); i++)
            {
                Node n = itemsr.item(i);

                for(int j = 0; j<5; j++) {
                    str += ("#" +n.getAttributes().item(j).getNodeValue().toString());



                }

                wtascores.add(str);
                str = "";
            }
            for(int m = 0; m<wtascores.size(); m++){
                wtadummy.add(wtascores.get(m));
            }
            for(int e = 0; e<wtadummy.size(); e++){
                String[] arr = wtadummy.get(e).split("#");
                if(arr[3].contains("same"))
                    arr[3] = "";
                wtadummy.set(e, arr[1] + "   Rank: " + arr[2]+ "   Points : "+arr[5] + "   Country: " + arr[4]);
            }
            ArrayAdapter<String> wtaadapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, wtadummy);
            wtaRank.setAdapter(wtaadapter);

        }

        catch(Exception e){
            System.out.println(e.toString());
        }
    }


}


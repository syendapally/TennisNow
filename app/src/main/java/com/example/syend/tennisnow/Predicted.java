package com.example.syend.tennisnow;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class Predicted extends AppCompatActivity {
    private double prediction;
    private String winner;
    private String loser;
    PieChart pieChart;
    private String[] players = new String[2];
    private double[] percentages = new double[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predicted);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //emailStr = extras.getString("key");
           prediction = 80;
           prediction = extras.getDouble("predicted");
            winner = extras.getString("predictedw");
            loser = extras.getString("predictedl");
            players[0] = winner;
            players[1] = loser;
            percentages[0] = prediction;
            percentages[1] = 100 - prediction;
        }
       pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart.setDescription("Prediction Based on Recent Form and Ranking");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Predicted Winner is " + winner);
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        addDataSet();
      /*  pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1+7);
                for(int i = 0; i <percentages.length; i++){
                    if(percentages[i]==Integer.parseInt(sales)){
                        pos1=i;
                        break;
                    }
                }
                String player = players[pos1+1];
                Toast.makeText(Predicted.this, "Player: " + player + "\n" + " Chance of Winning: " + sales,Toast.LENGTH_LONG);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    */
    }


    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        for (int i = 0; i < percentages.length; i++) {
            yEntrys.add(new PieEntry((float)percentages[i]));
        }

        for (int i = 0; i < players.length; i++) {
            xEntrys.add(players[i]);
        }
        PieDataSet set = new PieDataSet(yEntrys, "Players");
        set.setSliceSpace(2);
        set.setValueTextSize(12);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        set.setColors(colors);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        PieData pieData = new PieData(set);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

}

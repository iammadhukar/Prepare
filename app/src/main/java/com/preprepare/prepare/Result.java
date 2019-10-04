package com.preprepare.prepare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.preprepare.prepare.Model.FetchCorrectAndSelectedAnswer;
import com.preprepare.prepare.ViewModel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    private MyViewModel myViewModel;
    private TextView result, totalQuestion, attemted;
    private int numberOfCorrectAnswers=0;
    private int numberOfAttempts=0;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        myViewModel = MyViewModel.getInstance(this);
        result = findViewById(R.id.result);
        totalQuestion = findViewById(R.id.numberOfQuestion);
        attemted = findViewById(R.id.noOfAttempts);
        pieChart = findViewById(R.id.pieChart);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleAlpha(0);

    }

    @Override
    protected void onResume() {
        super.onResume();


        myViewModel.calculateResult().observe(this, new Observer<ArrayList<FetchCorrectAndSelectedAnswer>>() {
            ArrayList<FetchCorrectAndSelectedAnswer> dataCaptured;
            @Override
            public void onChanged(ArrayList<FetchCorrectAndSelectedAnswer> arrayList) {
                dataCaptured = arrayList;
                if (arrayList.size()>0){
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getOptionSelected()!=null) {
                            numberOfAttempts++;
                            Log.d(TAG, arrayList.get(i).getOptionSelected() + " " + arrayList.get(i).getAnswer());
                            if ((arrayList.get(i).getOptionSelected()).equals(arrayList.get(i).getAnswer())) {
                                numberOfCorrectAnswers++;
                            }
                        }
                    }
                }

                int wrongAnswers=0;
                result.setText(String.valueOf(numberOfCorrectAnswers)+"/"+String.valueOf(arrayList.size()));
                result.setVisibility(View.GONE);
                totalQuestion.setText(String.valueOf(arrayList.size()));
                attemted.setText(String.valueOf(numberOfAttempts));
                wrongAnswers = numberOfAttempts-numberOfCorrectAnswers;
                createPieChart(numberOfCorrectAnswers,wrongAnswers, arrayList.size()-numberOfAttempts);
                myViewModel.myRepository.deleteData();
            }
        });
    }

    private void createPieChart(int result, int wrongAnswers, int totalQuestion){
        List<String> xData = new ArrayList<>();
        xData.add("Result");
        xData.add("Total Question");
        List<PieEntry> yData = new ArrayList<>();
        yData.add(new PieEntry(result, "Correct"));
        yData.add(new PieEntry(wrongAnswers, "Wrong"));
        yData.add(new PieEntry(totalQuestion, "Not Attempted"));

        PieDataSet pieDataSet = new PieDataSet(yData, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);

        ArrayList<Integer> color = new ArrayList<>();
        color.add(Color.GREEN);
        color.add(Color.RED);
        color.add(Color.YELLOW);
        pieDataSet.setColors(color);

        PieData pieData = new PieData(pieDataSet);

        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
        pieChart.setCenterTextSize(35f);
        pieChart.setCenterText(String.valueOf(numberOfCorrectAnswers));
    }

    public void goToDashboard(View view){
        Intent intent = new Intent(this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myViewModel.myRepository.deleteData();
    }
}

package com.preprepare.prepare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.preprepare.prepare.Model.FetchCorrectAndSelectedAnswer;
import com.preprepare.prepare.ViewModel.MyViewModel;

import java.util.ArrayList;

public class Result extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    private MyViewModel myViewModel;
    private TextView result, totalQuestion, attemted;
    private int numberOfCorrectAnswers=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        myViewModel = MyViewModel.getInstance(this);
        result = findViewById(R.id.result);
        totalQuestion = findViewById(R.id.totalQuestion);
        attemted = findViewById(R.id.attempted);

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
                            Log.d(TAG, arrayList.get(i).getOptionSelected() + " " + arrayList.get(i).getAnswer());
                            if ((arrayList.get(i).getOptionSelected()).equals(arrayList.get(1).getAnswer())) {
                                numberOfCorrectAnswers++;
                            }
                        }
                    }
                }

                result.setText(String.valueOf(numberOfCorrectAnswers)+"/"+String.valueOf(arrayList.size()));
                myViewModel.myRepository.deleteData();
            }
        });
    }
}

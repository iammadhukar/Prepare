package com.preprepare.prepare;

import android.content.Intent;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.preprepare.prepare.Model.MyModel;
import com.preprepare.prepare.ViewModel.MyViewModel;

public class Test extends AppCompatActivity {

    private static String TAG = "MyTest";

    TextView textTimer;
    private MyViewModel myViewModel;
    private TextView question, number;
    private int count = 1;
    private ProgressBar progressBar;
    private RadioButton optionA,optionB,optionC,optionD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        myViewModel = new MyViewModel(this);
//        myViewModel.accessDatabase();
        textTimer = findViewById(R.id.timeLeft);
        question = findViewById(R.id.question);
        progressBar = findViewById(R.id.progressBar);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        number = findViewById(R.id.number);

        timer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "Inside onStart method");
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        myViewModel.getQuestionLiveData(count).observe(this, new Observer<MyModel>() {
            @Override
            public void onChanged(MyModel myModel) {
                if (myModel != null) {
                    question.setText(myModel.getQuestion());
                    optionA.setText(myModel.getOptionA());
                    optionB.setText(myModel.getOptionB());
                    optionC.setText(myModel.getOptionC());
                    optionD.setText(myModel.getOptionD());
                    number.setText(count+".");
                    progressBar.setVisibility(View.INVISIBLE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Log.d(TAG, "value is " + myModel.getQuestion());
                } else {
                    question.setText(" ");
                }
            }
        });
    }


    private void timer(){

        new CountDownTimer(1500000, 1000) {
            int time=25;
            int sec=00;

            public void onTick(long millisUntilFinished) {
                textTimer.setText(checkDigit(time)+":"+ checkDigit(sec));
                sec--;
                if(sec< 0){
                    time--;
                    sec=59;
                }
            }

            public void onFinish() {
                textTimer.setText("try again");
            }

            public String checkDigit(int number) {
                return number <= 9 ? "0" + number : String.valueOf(number);
            }

        }.start();

    }

    public void onSubmit(View view){
        myViewModel.myRepository.deleteData();
        Toast.makeText(this, "Data deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);
    }

    public void onNextClick(View view){
        count++;
        deSelectButton();
        myViewModel.getQuestionLiveData(count);
    }

    public void onPreviousClick(View view){
        if(count!=1){
            count--;
            deSelectButton();
            myViewModel.getQuestionLiveData(count);
        }
    }

    private void deSelectButton(){
        if (optionA.isChecked())
            optionA.setChecked(false);
        if (optionB.isChecked())
            optionB.setChecked(false);
        if (optionC.isChecked())
            optionC.setChecked(false);
        if (optionD.isChecked())
            optionD.setChecked(false);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}

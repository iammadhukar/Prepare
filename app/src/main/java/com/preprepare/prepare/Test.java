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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.preprepare.prepare.Model.MyModel;
import com.preprepare.prepare.Room.MyAppDatabase;
import com.preprepare.prepare.ViewModel.MyViewModel;

public class Test extends AppCompatActivity {

    private static String TAG = "MyTest";

    TextView textTimer;
    private MyViewModel myViewModel;
    private TextView question, number;
    private int count = 1;
    private ProgressBar progressBar;
    private RadioButton optionA,optionB,optionC,optionD;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        myViewModel = MyViewModel.getInstance(this);
//        myViewModel.accessDatabase();
        textTimer = findViewById(R.id.timeLeft);
        question = findViewById(R.id.question);
        progressBar = findViewById(R.id.progressBar);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        number = findViewById(R.id.number);
        radioGroup = findViewById(R.id.radioGrp);

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

                    Log.d(TAG, "Selected answer is " + myModel.getOptionSelected());

                    /*
                    * Checking for selected answer
                     */
                    if (myModel.getOptionSelected()!=null)
                        setSelectedAnswer(myModel);

                    progressBar.setVisibility(View.INVISIBLE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Log.d(TAG, "value is " + myModel.getQuestion());
                } else {
                    question.setText(" ");
                }
            }
        });
    }

    private void setSelectedAnswer(MyModel myModel){
        if (myModel.getOptionA().equals(myModel.getOptionSelected())){
            radioGroup.check(R.id.optionA);
        }
        if (myModel.getOptionB().equals(myModel.getOptionSelected())){
            radioGroup.check(R.id.optionB);
        }
        if (myModel.getOptionC().equals(myModel.getOptionSelected())){
            radioGroup.check(R.id.optionC);
        }
        if (myModel.getOptionD().equals(myModel.getOptionSelected())){
            radioGroup.check(R.id.optionD);
        }
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

//        calculateResult();
//
//        myViewModel.myRepository.deleteData();
        count=0;
        Toast.makeText(this, "Data deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);
    }

    public void onNextClick(View view){

        if(radioGroup.getCheckedRadioButtonId()!=-1){
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            String selectedAnswer = radioButton.getText().toString();
            myViewModel.updateSelectedAnswer(selectedAnswer);
        }

        if (count!=50) {
            count++;
            radioGroup.clearCheck();
            myViewModel.getQuestionLiveData(count);
        }
    }

    public void onPreviousClick(View view){
        if(count!=1){
            count--;
            radioGroup.clearCheck();
            myViewModel.getQuestionLiveData(count);
        }
    }

    private void calculateResult(){
        myViewModel.calculateResult();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myViewModel.myRepository.deleteData();

    }

}

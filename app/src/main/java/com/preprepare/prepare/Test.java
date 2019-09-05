package com.preprepare.prepare;

import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.preprepare.prepare.VIewModel.MyViewModel;

public class Test extends AppCompatActivity {

    TextView textTimer;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        myViewModel = new MyViewModel(this);
//        myViewModel.accessDatabase();
        textTimer = findViewById(R.id.timeLeft);
        timer();
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

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}

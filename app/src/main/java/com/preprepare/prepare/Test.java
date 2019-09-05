package com.preprepare.prepare;

import android.content.Intent;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.preprepare.prepare.ViewModel.MyViewModel;

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

    public void onSubmit(View view){
        myViewModel.myRepository.deleteData();
        Toast.makeText(this, "Data deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}

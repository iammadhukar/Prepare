package com.preprepare.prepare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Welcome extends AppCompatActivity {

    private static final int SPLASH_SCREEEN_TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
//                    startActivity(new Intent(Welcome.this, LoginPage.class));
                    startActivity(new Intent(Welcome.this, LoginPage.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, SPLASH_SCREEEN_TIMEOUT);
    }
}

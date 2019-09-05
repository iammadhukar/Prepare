package com.preprepare.prepare;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    Button bpsc, upsc, railways, bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bpsc = findViewById(R.id.bpsc);
        upsc = findViewById(R.id.upsc);
        railways = findViewById(R.id.railways);
        bank = findViewById(R.id.bank);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        bpsc.setVisibility(View.VISIBLE);
        upsc.setVisibility(View.VISIBLE);
        railways.setVisibility(View.VISIBLE);
        bank.setVisibility(View.VISIBLE);
    }

    public void openBpscActivity(View view) {

        Intent intent = new Intent(this, Bpsc.class);
        upsc.setVisibility(view.GONE);
        railways.setVisibility(view.GONE);
        bank.setVisibility(view.GONE);
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in_towards_left, R.anim.slid_out_towards_left);
    }
}

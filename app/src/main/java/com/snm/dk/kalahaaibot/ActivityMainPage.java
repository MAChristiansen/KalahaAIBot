package com.snm.dk.kalahaaibot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ActivityMainPage extends AppCompatActivity {

    private TextView tvPlayer1Pit, tvPlayer2Pit, tvStatus;
    private Button btnP1_1, btnP1_2, btnP1_3, btnP1_4, btnP1_5, btnP1_6,
                    btnP2_1, btnP2_2, btnP2_3, btnP2_4, btnP2_5, btnP2_6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Player 1
        tvPlayer1Pit = findViewById(R.id.tvPlayer1Pit);
        btnP1_1 = findViewById(R.id.btnP1_1);
        btnP1_2 = findViewById(R.id.btnP1_1);
        btnP1_3 = findViewById(R.id.btnP1_1);
        btnP1_4 = findViewById(R.id.btnP1_1);
        btnP1_5 = findViewById(R.id.btnP1_1);
        btnP1_6 = findViewById(R.id.btnP1_1);

        // Player 2
        tvPlayer2Pit = findViewById(R.id.tvPlayer2Pit);
        btnP2_1 = findViewById(R.id.btnP2_1);
        btnP2_2 = findViewById(R.id.btnP2_2);
        btnP2_3 = findViewById(R.id.btnP2_3);
        btnP2_4 = findViewById(R.id.btnP2_4);
        btnP2_5 = findViewById(R.id.btnP2_5);
        btnP2_6 = findViewById(R.id.btnP2_6);

        tvStatus = findViewById(R.id.tvStatus);

    }
}

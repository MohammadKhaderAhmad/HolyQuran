package com.example.holyquran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Button btnSurahInfo = findViewById(R.id.btnSurahInfo);
        Button btnListenQuran = findViewById(R.id.btnListenQuran);
        Button btnListenQuran1 = findViewById(R.id.btnListenQuran1);
        Button btnListenQuran2 = findViewById(R.id.btnListenQuran2);

        btnSurahInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start QuranActivity
                Intent intent = new Intent(ChooseActivity.this, QuranActivity.class);
                startActivity(intent);
            }
        });

        btnListenQuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AudioActivity
                Intent intent = new Intent(ChooseActivity.this, AudioActivity.class);
                startActivity(intent);
            }
        });

        btnListenQuran1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AudioActivity
                Intent intent = new Intent(ChooseActivity.this, AudioActivity1.class);
                startActivity(intent);
            }
        });

        btnListenQuran2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AudioActivity
                Intent intent = new Intent(ChooseActivity.this, AudioActivity2.class);
                startActivity(intent);
            }
        });
    }
}

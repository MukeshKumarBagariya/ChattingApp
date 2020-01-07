package com.example.chatforall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import static android.net.sip.SipErrorCode.TIME_OUT;

public class IntializationActivity extends AppCompatActivity {
    private ProgressBar spinner;
    private static int TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intialization);
        spinner = findViewById(R.id.progressBar1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(IntializationActivity.this, WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }
}

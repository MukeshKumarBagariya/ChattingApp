package com.example.chatforall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class FrontPageActivity extends AppCompatActivity {
    private ImageView backgroundImage,logo;
    private TextView splitText,welcomeText,termsText,dontWorrytext;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        setTitle("Chat to all");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        if (ParseUser.getCurrentUser() != null){
            tranjectionActivity();
        }
        backgroundImage = findViewById(R.id.backgroundImage);
        logo = findViewById(R.id.logoImage);
        splitText = findViewById(R.id.splitText);
        welcomeText = findViewById(R.id.welcomeText);
        termsText = findViewById(R.id.termsText);
        dontWorrytext = findViewById(R.id.dontworrytext);
        submitButton = findViewById(R.id.submitButton);
        backgroundImage.animate().alpha(0.0f);
        logo.animate().alpha(0.0f).setDuration(2000);
        splitText.animate().alpha(0.0f).setDuration(2000);
        backgroundImage.animate().alpha(1.0f).setDuration(2000);
        welcomeText.animate().alpha(0.0f);
        termsText.animate().alpha(0.0f);
        dontWorrytext.animate().alpha(0.0f);
        submitButton.animate().alpha(0.0f);
        welcomeText.animate().alpha(1.0f).setDuration(2000);
        logo.animate().alpha(1.0f).setDuration(2000);
        termsText.animate().alpha(1.0f).setDuration(2000);
        dontWorrytext.animate().alpha(1.0f).setDuration(2000);
        submitButton.animate().alpha(1.0f).setDuration(2000);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPageActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void tranjectionActivity(){
        Intent intent = new Intent(FrontPageActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}

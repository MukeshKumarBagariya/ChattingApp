package com.example.chatforall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    private EditText phoneNumberText,countryCodeText,nameText,userPassword,userEmail;
    private TextView profileText, photoText;
    private ImageView photoImage,backgroundImage;
    private Button confirmButton,nextButton;
    private TextInputLayout phoneNumber,passwordText,emailText;
    private String uPhoneNumber,uName, uPassword,uMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        phoneNumberText = findViewById(R.id.phoneNumberText);
        confirmButton = findViewById(R.id.coinformButton);
        countryCodeText = findViewById(R.id.countrycodeText);
        profileText = findViewById(R.id.profileText);
        nameText = findViewById(R.id.nameText);
        photoText = findViewById(R.id.photoText);
        photoImage = findViewById(R.id.photoImage);
        backgroundImage = findViewById(R.id.backgroundImage);
        nextButton = findViewById(R.id.nextButton);
        userPassword = findViewById(R.id.userPassword);
        userEmail = findViewById(R.id.userEmail);
        phoneNumber = findViewById(R.id.phoneNumber);
        passwordText = findViewById(R.id.passwordText);
        emailText = findViewById(R.id.emailText);
        nameText.animate().translationXBy(-4000);
        photoImage.animate().translationXBy(-4000);
        nextButton.animate().translationXBy(-4000);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uPhoneNumber = phoneNumberText.getText().toString();
                uPassword = userPassword.getText().toString();
                uMail = userEmail.getText().toString();
                if (uMail.matches("") || uPhoneNumber.matches("") || uPassword.matches("")) {
                    Toast.makeText(SignUpActivity.this, "None of these field can be empty", Toast.LENGTH_SHORT).show();
                } else {
                    backgroundImage.animate().alpha(0.0f).setDuration(1000);
                    phoneNumberText.animate().alpha(0.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    countryCodeText.animate().alpha(0.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    userPassword.animate().alpha(0.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    phoneNumber.animate().alpha(0.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    passwordText.animate().alpha(0.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    emailText.animate().alpha(0.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    userEmail.animate().alpha(0.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    confirmButton.animate().alpha(0.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    photoImage.animate().alpha(1.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    nameText.animate().alpha(1.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    nextButton.animate().alpha(1.0f).setDuration(1000).translationXBy(4000).setDuration(2000);
                    profileText.animate().alpha(1.0f).setDuration(2000);
                    photoText.animate().alpha(1.0f).setDuration(2000);
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uName = nameText.getText().toString();
                if (uName.matches("")){
                    Toast.makeText(SignUpActivity.this,"This field can't be empty",Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                    progressDialog.setMessage("Loading.....");
                    progressDialog.show();
                    ParseUser chatForAll = new ParseUser();
                    chatForAll.setUsername(nameText.getText().toString());
                    chatForAll.setEmail(userEmail.getText().toString());
                    chatForAll.setPassword(userPassword.getText().toString());
                    chatForAll.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SignUpActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                                ParseObject parseObject = new ParseObject("chatUser");
                                parseObject.put("userName", nameText.getText().toString());
                                parseObject.put("phoneNumber", phoneNumberText.getText().toString());
                                parseObject.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(SignUpActivity.this, "done", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(SignUpActivity.this, "undone", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(SignUpActivity.this, "Fuck Off", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
    }
});
    }
}
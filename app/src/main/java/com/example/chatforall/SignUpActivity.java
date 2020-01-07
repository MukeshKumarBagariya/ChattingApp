package com.example.chatforall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

public class SignUpActivity extends AppCompatActivity {
    private EditText phoneNumberText,countryCodeText,nameText,userPassword,userEmail;
    private TextView profileText, photoText;
    private ImageView photoImage,backgroundImage;
    private Button confirmButton,nextButton;
    private TextInputLayout phoneNumber,passwordText,emailText;
    private String uPhoneNumber,uName, uPassword,uMail;
    private Bitmap receivedImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (ParseUser.getCurrentUser() != null){
            tranjetionActivity();
        }
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

        photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                builder.setTitle("Upload or take a photo");
                builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Upload photo

                        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                        } else {
                            captureImage();
                        }
                    }
                });
                builder.setNegativeButton("Take a photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Take a photo
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
                                try {
                                    if (receivedImg != null) {
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        receivedImg.compress(Bitmap.CompressFormat.PNG,25,byteArrayOutputStream);
                                        byte[] bytes = byteArrayOutputStream.toByteArray();
                                        ParseFile parseFile = new ParseFile("img.png",bytes);
                                        ParseObject parseObject = new ParseObject("chatUser");
                                        parseObject.put("phoneNumber", phoneNumberText.getText().toString());
                                        parseObject.put("userName",nameText.getText().toString());
                                        parseObject.put("photo",parseFile);
                                        parseObject.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    Toast.makeText(SignUpActivity.this,"Image uploaded successfully",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    } else {
                                        ParseObject parseObject = new ParseObject("chatUser");
                                        parseObject.put("phoneNumber",phoneNumberText.getText().toString());
                                        parseObject.put("userName",nameText.getText().toString());
                                        parseObject.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null){
                                                    Toast.makeText(SignUpActivity.this,"Done",Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(SignUpActivity.this,"Fuck off",Toast.LENGTH_SHORT).show();
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                                    }

                                } catch (Exception e1){
                                    e1.printStackTrace();
                                }
                                Intent inten = new Intent(SignUpActivity.this, IntializationActivity.class);
                                startActivity(inten);
                                }else {
                                Toast.makeText(SignUpActivity.this, "Fuck Off", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }

    }
});
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000){
            if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                captureImage();
            }
        }
    }

    public void tranjetionActivity(){
        Intent i = new Intent(SignUpActivity.this,WelcomeActivity.class);
        startActivity(i);
    }
    private void captureImage(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && resultCode == RESULT_OK && data != null){
            try {
                Uri capturedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(capturedImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                receivedImg =  MediaStore.Images.Media.getBitmap(getContentResolver(), capturedImage);
                photoImage.setImageBitmap(receivedImg);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

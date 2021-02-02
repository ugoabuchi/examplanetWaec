package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.security.SecureRandom;
import java.util.Random;

public class VerificationPageActivity extends AppCompatActivity {

    private session mySession;
    private String email, name, passcode, gender, dob, code, picture;
    private Button confirm;
    private EditText v1, v2, v3, v4, v5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verification_page);

        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == true) {
            Intent intent = new Intent(VerificationPageActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();

        }
        confirm = findViewById(R.id.page_six);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v5 = findViewById(R.id.v5);
        Intent passed = getIntent();
        email = passed.getStringExtra("email");
        name = passed.getStringExtra("name");
        passcode = passed.getStringExtra("passcode");
        gender = passed.getStringExtra("gender");
        dob = passed.getStringExtra("dob");
        picture = "";
        code = "";

        //send verification code
        new datarequest(this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {
                    code = response[1];
                    new LovelyInfoDialog(VerificationPageActivity.this).setCancelable(false)
                            .setTitle("Code:")
                            .setMessage(code)
                            .show();
                } else {
                    new LovelyInfoDialog(VerificationPageActivity.this).setCancelable(false)
                            .setTitle("Oops:")
                            .setMessage("Something happened, please try again")
                            .show();
                    finish();
                }
            }
        }).verifyEmail(email);


        v1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                v2.requestFocus();
            }
        });

        v2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                v3.requestFocus();
            }
        });
        v3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                v4.requestFocus();
            }
        });
        v4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                v5.requestFocus();
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ucode = v1.getText().toString() + "" + v2.getText().toString() + v3.getText().toString() + v4.getText().toString() + v5.getText().toString();
                if (ucode.equals(code)) {

                    String URL = new constants().getAddress() ;
                    if(gender.equals("Male"))
                    {
                        picture = URL+"/login/images/boy.png";
                    }
                    else {
                        picture = URL+"/login/images/girl.png";
                    }
                    //signup
                    new datarequest(VerificationPageActivity.this, new customListener() {
                        @Override
                        public void onResponse(String[] response) {
                            if (response[0].equals("success")) {

                                mySession.createlog(name, email, gender, dob, picture);
                                Intent intent = new Intent(VerificationPageActivity.this, HomePageActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                new LovelyInfoDialog(VerificationPageActivity.this).setCancelable(false)
                                        .setTitle("Ooops:")
                                        .setMessage("Something happened, please try again")
                                        .show();
                                finish();
                            }
                        }
                    }).signup(name, email, passcode, gender, dob, picture);

                } else {
                    new LovelyInfoDialog(VerificationPageActivity.this).setCancelable(false)
                            .setTitle("Ooops:")
                            .setMessage("Invalid verification code")
                            .show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VerificationPageActivity.this, SignUpPageActivity.class);
        startActivity(intent);
        finish();
    }
}
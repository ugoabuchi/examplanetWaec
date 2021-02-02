package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class splashActivity extends AppCompatActivity {

    private Button signUp, signIn;
    private session mySession;
    int closeAttempt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        mySession = new session(getApplicationContext());
        if(mySession.checkLog() == true)
        {
            Intent intent = new Intent(splashActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();

        }
        signUp = findViewById(R.id.button);
        signIn = findViewById(R.id.button2);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(splashActivity.this, SignUpPageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(splashActivity.this, SignInPageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(closeAttempt == 0)
        {
            Toast.makeText(this, "Press the back button again to exit", Toast.LENGTH_LONG).show();
            closeAttempt = 1;
        }
        else
        {
            finish();
        }

    }
}
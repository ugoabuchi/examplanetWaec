package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class OnboardFourActivity extends AppCompatActivity {

    private ImageView next;
    private TextView skip;
    private session mySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboard_four);
        mySession = new session(getApplicationContext());
        if(mySession.checkLog() == true)
        {
            Intent intent = new Intent(OnboardFourActivity.this, HomePageActivity.class);
            startActivity(intent);

        }

        next = findViewById(R.id.page_ten);
        skip = findViewById(R.id.textView7);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardFourActivity.this, splashActivity.class);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardFourActivity.this, OnboardFiveActivity.class);
                startActivity(intent);
            }
        });
    }
}
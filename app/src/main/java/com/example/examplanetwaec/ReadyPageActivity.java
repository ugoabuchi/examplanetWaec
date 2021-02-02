package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ReadyPageActivity extends AppCompatActivity {

    private session mySession;
    private TextView tv21;
    private Button btn;
    private ImageView back;
    private  String sid, sname, time, qnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ready_page);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(ReadyPageActivity.this, splashActivity.class);
            startActivity(intent);
        }

        Intent intent = getIntent();
        sid = intent.getStringExtra("sid");
        sname = intent.getStringExtra("sname");
        time = intent.getStringExtra("time");
        qnos = intent.getStringExtra("qnos");

        tv21 = findViewById(R.id.textView21);
        btn = findViewById(R.id.button12);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv21.setText(sname);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadyPageActivity.this, exam.class);
                intent.putExtra("sid", sid);
                intent.putExtra("sname", sname);
                intent.putExtra("time", time);
                intent.putExtra("qnos", qnos);
                startActivity(intent);
                finish();
            }
        });



    }
}
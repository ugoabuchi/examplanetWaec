package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExamPractisePageActivity extends AppCompatActivity {

    private RelativeLayout next;
    private String time, qnos, subjectid, subjectname;
    private session mySession;
    private RelativeLayout option1, option2;
    private TextView sname;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_exam_practise_page);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(ExamPractisePageActivity.this, splashActivity.class);
            startActivity(intent);
        }
        Intent intent = getIntent();
        subjectid = intent.getStringExtra("subjectid");
        subjectname = intent.getStringExtra("subjectname");

        sname = findViewById(R.id.textView21);

        sname.setText(subjectname);

        option1 = findViewById(R.id.relativeLayout3);
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = "60";
                qnos = "50";
                Intent intent = new Intent(ExamPractisePageActivity.this, ReadyPageActivity.class);
                intent.putExtra("sid", subjectid);
                intent.putExtra("sname", subjectname);
                intent.putExtra("time", time);
                intent.putExtra("qnos", qnos);
                startActivity(intent);
            }
        });

        option2 = findViewById(R.id.relativeLayout4);
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = "30";
                qnos = "25";
                Intent intent = new Intent(ExamPractisePageActivity.this, ReadyPageActivity.class);
                intent.putExtra("sid", subjectid);
                intent.putExtra("sname", subjectname);
                intent.putExtra("time", time);
                intent.putExtra("qnos", qnos);
                startActivity(intent);
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


    }

}
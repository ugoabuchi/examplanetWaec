package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AnalysisPageActivity extends AppCompatActivity {

    private  session mySession;
    String status, sname, cans, rans, tqa, tqna, qnos, tops, spc, as, asp, tsp;
    ImageView back;
    TextView sview, correct, wrong, answered, unanswered, aspeed, tspent;
    ProgressBar spercent, avarage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_page);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(AnalysisPageActivity.this, splashActivity.class);
            startActivity(intent);
        }

        back = findViewById(R.id.imageView21);
        spercent = findViewById(R.id.spercent);
        sview = findViewById(R.id.textView40);
        tspent = findViewById(R.id.textView44);

        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        sname = intent.getStringExtra("sname");
        cans = intent.getStringExtra("cans");
        rans = intent.getStringExtra("rans");
        tqa = intent.getStringExtra("tqa");
        tqna = intent.getStringExtra("tqna");
        qnos = intent.getStringExtra("qnos");
        tops = intent.getStringExtra("tops");
        spc = intent.getStringExtra("spc");
        as = intent.getStringExtra("as");
        asp = intent.getStringExtra("asp");
        tsp = intent.getStringExtra("tsp");

        correct = findViewById(R.id.correct);
        wrong = findViewById(R.id.wrong);
        answered = findViewById(R.id.answered);
        unanswered = findViewById(R.id.uanswered);

        avarage = findViewById(R.id.average);
        aspeed = findViewById(R.id.aspeed);


        spercent.setProgress(Integer.parseInt(spc));
        avarage.setProgress((int)Double.parseDouble(asp));
        sview.setText(spc+"%");
        aspeed.setText(as);
        tspent.setText(tsp);

        correct.setText(cans+" Correct");
        wrong.setText(rans+" Incorrect");
        answered.setText(tqa+" Answered");
        unanswered.setText(tqna+" Unanswered");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultPageActivity extends AppCompatActivity {
private  session mySession;
RelativeLayout mainS;
String status, sname, cans, rans, tqa, tqna, qnos, tops, spc, as, asp, tsp;
TextView sview, scoreAnalysis, scoreGrade, correct, wrong, answered, unanswered;
ImageView pics, stat, back, csol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(ResultPageActivity.this, splashActivity.class);
            startActivity(intent);
        }

        Intent intent = getIntent();

        mainS = findViewById(R.id.mainer);
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


        sview = findViewById(R.id.textView21);
        scoreAnalysis = findViewById(R.id.textView23);
        pics = findViewById(R.id.imageView16);
        scoreGrade = findViewById(R.id.textView15);
        correct = findViewById(R.id.correct);
        wrong = findViewById(R.id.wrong);
        answered = findViewById(R.id.answered);
        unanswered = findViewById(R.id.uanswered);
        stat = findViewById(R.id.stat);

        back = findViewById(R.id.imageView21);
        csol = findViewById(R.id.csolution);


        correct.setText(cans+" Correct");
        wrong.setText(rans+" Incorrect");
        answered.setText(tqa+" Answered");
        unanswered.setText(tqna+" Unanswered");

        sview.setText(sname);

        if(!status.equals("failed"))
        {
            scoreAnalysis.setText("Weldone "+mySession.getName().split(" ")[0] +", \nkeep practising!");
            scoreGrade.setText(cans+" / "+qnos);
            pics.setImageResource(R.drawable.ic_result_img);
        }
        else
        {
            scoreAnalysis.setText("You can do better "+mySession.getName().split(" ")[0] +", \nkeep practising!");
            scoreGrade.setText(cans+" / "+qnos);
            pics.setImageResource(R.drawable.wrongstat);
        }

        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent(ResultPageActivity.this, AnalysisPageActivity.class);
                result.putExtra("status", status+"");
                result.putExtra("sname", sname);
                result.putExtra("cans", cans+"");
                result.putExtra("rans", rans+"");
                result.putExtra("tqa", tqa+"");
                result.putExtra("tqna", tqna+"");
                result.putExtra("qnos", qnos);
                result.putExtra("tops", tops);
                result.putExtra("spc", spc);
                result.putExtra("as", as);
                result.putExtra("asp", asp);
                result.putExtra("tsp", tsp);
                startActivity(result);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        csol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ResultPageActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.solutionoption);
                mainS.setAlpha(0.2f);
                TextView allq = dialog.findViewById(R.id.allq);
                TextView wans = dialog.findViewById(R.id.wans);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mainS.setAlpha(1f);
                    }
                });

                allq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent result = new Intent(ResultPageActivity.this, videosolution.class);
                        result.putExtra("tops", tops);
                        result.putExtra("sname", sname);
                        result.putExtra("type", "allq");
                        startActivity(result);
                    }
                });

                wans.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent result = new Intent(ResultPageActivity.this, videosolution.class);
                        result.putExtra("tops", tops);
                        result.putExtra("sname", sname);
                        result.putExtra("type", "wans");
                        startActivity(result);
                    }
                });



                ImageView cancel = dialog.findViewById(R.id.exit);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });





    }
}
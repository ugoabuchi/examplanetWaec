package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class QuestionPageActivity extends AppCompatActivity {
    private session mySession;
    private String sname, tname, tid, ttype, currentAnswer;
    private TextView subdisplay, displayname;
    private JSONArray tops;
    private WebView displayquestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page2);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(QuestionPageActivity.this, splashActivity.class);
            startActivity(intent);

        }

        //intent variables
        Intent tdata = getIntent();
        sname = tdata.getStringExtra("subjectname");
        tname = tdata.getStringExtra("topicname");
        tid = tdata.getStringExtra("topicid");
        ttype = tdata.getStringExtra("topictype");
        currentAnswer = "";


        subdisplay = findViewById(R.id.textView32);
        subdisplay.setText(sname);
        displayname = findViewById(R.id.textView26);
        displayname.setText(tname);

        displayquestion = findViewById(R.id.dquestion);
        displayquestion.getSettings().setJavaScriptEnabled(true);
        displayquestion.getSettings().setLoadWithOverviewMode(true);
        displayquestion.getSettings().setUseWideViewPort(true);
        displayquestion.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        displayquestion.setScrollbarFadingEnabled(true);
        displayquestion.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        //load question
        new datarequest(QuestionPageActivity.this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {
                    try {

                        tops = new JSONArray(response[1]);
                        if (tops.length() > 0) {
                            try {

                                final JSONObject topquery = tops.getJSONObject(0);
                                displayquestion.loadData(new String(Base64.decode(topquery.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replace("width", "style='width:100% !important;' width"), "text/html; charset=utf-8", "UTF-8");
                                currentAnswer = topquery.getString("qans");

                            } catch (Throwable t) {
                                new LovelyInfoDialog(QuestionPageActivity.this).setCancelable(false)
                                        .setTitle("Ooops, Can't Load Questions")
                                        .setMessage("Seems like you have a poor network connection !!!, try again")
                                        .show();
                            }
                        } else {
                            new LovelyInfoDialog(QuestionPageActivity.this).setCancelable(false)
                                    .setTitle("Ooops, Can't Load Questions")
                                    .setMessage("Questions not available, check back later..")
                                    .show();
                        }


                    } catch (JSONException t) {
                        new LovelyInfoDialog(QuestionPageActivity.this).setCancelable(false)
                                .setTitle("Ooops, Can't Load Questions")
                                .setMessage("Seems like you have a poor network connection !!!, try again")
                                .show();
                    }
                } else {
                    new LovelyInfoDialog(QuestionPageActivity.this).setCancelable(false)
                            .setTitle("Ooops, Can't Load Questions")
                            .setMessage("Seems like you have a poor network connection !!!, try again")
                            .show();
                }
            }
        }).getQuestions(tid);

    }
}
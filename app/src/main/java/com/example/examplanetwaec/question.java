package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class question extends AppCompatActivity {
    private session mySession;
    private String sname, tname, tid, ttype, currentQuestion, vlink;
    private TextView subdisplay, displayname;
    private JSONArray tops;
    private WebView displayquestion;
    GridLayout l80;
    private LinearLayout bot;
    private HorizontalScrollView gcont;
    int pad = 0;
    RelativeLayout qbox;
    private Button currentQbutton;
    private ImageView backbtn, checkvideo;
    boolean loaded = false;

    private ImageView noption, prevbutton, nextbutton;
    private Button a, b, c, d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(question.this, splashActivity.class);
            startActivity(intent);

        }

        //intent variables
        Intent tdata = getIntent();
        sname = tdata.getStringExtra("subjectname");
        tname = tdata.getStringExtra("topicname");
        tid = tdata.getStringExtra("topicid");
        ttype = tdata.getStringExtra("topictype");
        currentQuestion = "";
        bot = findViewById(R.id.bot);
        l80 = findViewById(R.id.l80);
        gcont = findViewById(R.id.gcont);
        noption = findViewById(R.id.noption);
        prevbutton = findViewById(R.id.prevbutton);
        nextbutton = findViewById(R.id.nextbutton);
        checkvideo = findViewById(R.id.checkvideo);
        qbox = findViewById(R.id.qbox);
        a = findViewById(R.id.aoption);
        b = findViewById(R.id.boption);
        c = findViewById(R.id.coption);
        d = findViewById(R.id.doption);

        if(!ttype.equals("OBJ"))
        {
            bot.setVisibility(View.INVISIBLE);
        }

        backbtn = findViewById(R.id.imageView15);
        displayname = findViewById(R.id.textView26);
        displayname.setText(tname);

        displayquestion = findViewById(R.id.dquestion);
        displayquestion.getSettings().setUseWideViewPort(true);
        displayquestion.getSettings().setBuiltInZoomControls(true);
        displayquestion.getSettings().setSupportZoom(true);
        displayquestion.getSettings().setJavaScriptEnabled(true);
        displayquestion.getSettings().setLoadWithOverviewMode(true);
        displayquestion.getSettings().setLoadsImagesAutomatically(true);
        displayquestion.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        checkvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent checkV = new Intent(question.this, checkVideo.class);
                checkV.putExtra("sname", sname);
                checkV.putExtra("vlink", vlink);
                startActivity(checkV);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pad == 0)
                {
                    finish();
                }
                else {
                    pad = 0;
                    displayquestion.setVisibility(View.VISIBLE);
                    gcont.setVisibility(View.INVISIBLE);
                    bot.setVisibility(View.VISIBLE);
                }
            }
        });
        noption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pad == 0) {
                    pad = 1;
                    displayquestion.setVisibility(View.INVISIBLE);
                    gcont.setVisibility(View.VISIBLE);
                    bot.setVisibility(View.INVISIBLE);
                }
            }
        });

        prevbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(loaded == true)
               {
                   for (int i = 0; i < tops.length(); i++) {
                       final int finalJ = i;
                       try {
                           final JSONObject sobj = tops.getJSONObject(finalJ);
                           if (sobj.getString("id").equals(currentQuestion)) {
                               if (i > 0) {
                                   final int nfianlj = i - 1;
                                   final JSONObject nsobj = tops.getJSONObject(nfianlj);
                                   displayquestion.loadData(new String(Base64.decode(nsobj.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replaceFirst("<", "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body style=\"padding-bottom: 100px;\"><").concat("</body></html>"), "text/html; charset=utf-8", "UTF-8");
                                   currentQuestion = nsobj.getString("id");
                                   currentQbutton = (Button) l80.findViewWithTag(nsobj.getString("id"));
                                   vlink = new constants().getAddress()+"/global/tsol/"+nsobj.getString("un")+"."+nsobj.getString("ext");

                               } else {
                                   displayquestion.loadData(new String(Base64.decode(sobj.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replaceFirst("<", "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body style=\"padding-bottom: 100px;\"><").concat("</body></html>"), "text/html; charset=utf-8", "UTF-8");
                                   currentQuestion = sobj.getString("id");
                                   currentQbutton = (Button) l80.findViewWithTag(sobj.getString("id"));
                                   vlink = new constants().getAddress()+"/global/tsol/"+sobj.getString("un")+"."+sobj.getString("ext");

                               }
                               displayquestion.setVisibility(View.VISIBLE);
                               gcont.setVisibility(View.INVISIBLE);


                               break;
                           }
                       } catch (JSONException e) {

                       }
                   }
                   resetButton();
               }

            }
        });


        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loaded == true)
                {
                    for (int i = 0; i < tops.length(); i++) {
                        final int finalJ = i;
                        try {
                            final JSONObject sobj = tops.getJSONObject(finalJ);
                            if (sobj.getString("id").equals(currentQuestion)) {
                                if (i < tops.length()) {
                                    final int nfianlj = i + 1;
                                    final JSONObject nsobj = tops.getJSONObject(nfianlj);
                                    displayquestion.loadData(new String(Base64.decode(nsobj.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replaceFirst("<", "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body style=\"padding-bottom: 100px;\"><").concat("</body></html>"), "text/html; charset=utf-8", "UTF-8");
                                    currentQuestion = nsobj.getString("id");
                                    currentQbutton = (Button) l80.findViewWithTag(nsobj.getString("id"));
                                    vlink = new constants().getAddress()+"/global/tsol/"+nsobj.getString("un")+"."+nsobj.getString("ext");

                                } else {
                                    displayquestion.loadData(new String(Base64.decode(sobj.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replaceFirst("<", "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body style=\"padding-bottom: 100px;\"><").concat("</body></html>"), "text/html; charset=utf-8", "UTF-8");
                                    currentQuestion = sobj.getString("id");
                                    currentQbutton = (Button) l80.findViewWithTag(sobj.getString("id"));
                                    vlink = new constants().getAddress()+"/global/tsol/"+sobj.getString("un")+"."+sobj.getString("ext");

                                }
                                displayquestion.setVisibility(View.VISIBLE);
                                gcont.setVisibility(View.INVISIBLE);


                                break;
                            }
                        } catch (JSONException e) {

                        }
                    }

                    resetButton();
                }

            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAnswer(v);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAnswer(v);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAnswer(v);
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAnswer(v);
            }
        });


       loadQuestion();

    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private void setCurrentQuestion(String tagid, View qbutton) {
        for (int i = 0; i < tops.length(); i++) {
            final int finalJ = i;
            try {
                final JSONObject sobj = tops.getJSONObject(finalJ);
                if (sobj.getString("id").equals(tagid)) {
                    displayquestion.loadData(new String(Base64.decode(sobj.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replaceFirst("<", "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body style=\"padding-bottom: 100px;\"><").concat("</body></html>"), "text/html; charset=utf-8", "UTF-8");
                    currentQuestion = sobj.getString("id");
                    displayquestion.setVisibility(View.VISIBLE);
                    gcont.setVisibility(View.INVISIBLE);
                    currentQbutton = (Button) qbutton;
                    vlink = new constants().getAddress()+"/global/tsol/"+sobj.getString("un")+"."+sobj.getString("ext");

                    break;
                }
            } catch (JSONException e) {
                setCurrentQuestion(tagid, qbutton);
            }
        }

        if (pad == 1) {
            pad = 0;
            displayquestion.setVisibility(View.VISIBLE);
            gcont.setVisibility(View.INVISIBLE);
            bot.setVisibility(View.VISIBLE);
        }

        resetButton();

    }

    private void validateAnswer(View v) {
       if(loaded == true)
       {
           a.setEnabled(false);
           b.setEnabled(false);
           c.setEnabled(false);
           d.setEnabled(false);
           for (int i = 0; i < tops.length(); i++) {
               final int finalJ = i;
               try {
                   final JSONObject sobj = tops.getJSONObject(finalJ);
                   if (sobj.getString("id").equals(currentQuestion)) {
                       mySession.addAnsweredQuestion(sobj.getString("id"), v.getTag().toString());
                       if (sobj.getString("qans").toUpperCase().equals(v.getTag().toString())) {
                           //correct answer gotten
                           Button correct = (Button) v;
                           correct.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                           correct.setTextColor(Color.parseColor("#FFFFFF"));


                       } else {
                           //wrong answer gottent
                           Button wrong = (Button) v;
                           wrong.setBackground(ContextCompat.getDrawable(question.this, R.drawable.wrong));
                           wrong.setTextColor(Color.parseColor("#FFFFFF"));

                           //get correct answer
                           if (sobj.getString("qans").toUpperCase().equals("A")) {
                               a.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                               a.setTextColor(Color.parseColor("#FFFFFF"));
                           } else if (sobj.getString("qans").toUpperCase().equals("B")) {
                               b.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                               b.setTextColor(Color.parseColor("#FFFFFF"));
                           } else if (sobj.getString("qans").toUpperCase().equals("C")) {
                               c.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                               c.setTextColor(Color.parseColor("#FFFFFF"));
                           } else {
                               d.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                               d.setTextColor(Color.parseColor("#FFFFFF"));
                           }

                       }
                       currentQbutton.setBackground(ContextCompat.getDrawable(question.this, R.drawable.done));
                       currentQbutton.setTextColor(Color.parseColor("#FFFFFF"));
                       vlink = new constants().getAddress()+"/global/tsol/"+sobj.getString("un")+"."+sobj.getString("ext");

                       Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                       vibrate.vibrate(100);
                       break;
                   }

               } catch (JSONException e) {

               }
           }
       }
    }

    private void validateAnswer(String id) {
        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        d.setEnabled(false);
        for (int i = 0; i < tops.length(); i++) {
            final int finalJ = i;
            try {
                final JSONObject sobj = tops.getJSONObject(finalJ);
                if (sobj.getString("id").equals(currentQuestion)) {
                    if (sobj.getString("qans").toUpperCase().equals(id)) {
                        //correct answer gotten
                        Button correct = (Button) bot.findViewWithTag(id);
                        correct.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                        correct.setTextColor(Color.parseColor("#FFFFFF"));

                    } else {
                        //wrong answer gottent
                        Button wrong = (Button) bot.findViewWithTag(id);;
                        wrong.setBackground(ContextCompat.getDrawable(question.this, R.drawable.wrong));
                        wrong.setTextColor(Color.parseColor("#FFFFFF"));

                        //get correct answer
                        if (sobj.getString("qans").toUpperCase().equals("A")) {
                            a.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                            a.setTextColor(Color.parseColor("#FFFFFF"));
                        } else if (sobj.getString("qans").toUpperCase().equals("B")) {
                            b.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                            b.setTextColor(Color.parseColor("#FFFFFF"));
                        } else if (sobj.getString("qans").toUpperCase().equals("C")) {
                            c.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                            c.setTextColor(Color.parseColor("#FFFFFF"));
                        } else {
                            d.setBackground(ContextCompat.getDrawable(question.this, R.drawable.correct));
                            d.setTextColor(Color.parseColor("#FFFFFF"));
                        }

                    }
                    currentQbutton.setBackground(ContextCompat.getDrawable(question.this, R.drawable.done));
                    currentQbutton.setTextColor(Color.parseColor("#FFFFFF"));
                    break;
                }

            } catch (JSONException e) {

            }
        }
    }

    public void loadQuestion()
    {
        //load question
        new datarequest(question.this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {
                    try {

                        tops = new JSONArray(response[1]);
                        if (tops.length() > 0) {
                            try {
//.replace("width", "style='width:100% !important;' width")
                                Typeface type = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");


                                for (int i = 0; i < tops.length(); i++) {
                                    final int finalJ = i;
                                    final JSONObject sobj = tops.getJSONObject(finalJ);

                                    if (i == 0) {
                                        displayquestion.loadData(new String(Base64.decode(sobj.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replaceFirst("<", "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body style=\"padding-bottom: 100px;\"><").concat("</body></html>"), "text/html; charset=utf-8", "UTF-8");
                                        currentQuestion = sobj.getString("id");
                                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                                        params.height = dpToPx(50, question.this);
                                        params.width = dpToPx(50, question.this);

                                        Button btntag = new Button(question.this);
                                        btntag.setLayoutParams(params);
                                        btntag.setText(sobj.getString("no"));
                                        btntag.setTag(sobj.getString("id"));
                                        btntag.setTextColor(Color.parseColor("#080366"));
                                        btntag.setBackground(ContextCompat.getDrawable(question.this, R.drawable.rounrbblue));
                                        btntag.setGravity(Gravity.CENTER);
                                        btntag.setTypeface(type);
                                        vlink = new constants().getAddress()+"/global/tsol/"+sobj.getString("un")+"."+sobj.getString("ext");

                                        btntag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                setCurrentQuestion(v.getTag().toString(), v);
                                                currentQbutton = (Button) v;
                                            }
                                        });
                                        l80.addView(btntag);
                                        currentQbutton = (Button) l80.findViewWithTag(sobj.getString("id"));
                                        ;
                                    } else {
                                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                                        params.height = dpToPx(50, question.this);
                                        params.width = dpToPx(50, question.this);

                                        Button btntag = new Button(question.this);
                                        btntag.setLayoutParams(params);
                                        btntag.setText(sobj.getString("no"));
                                        btntag.setTag(sobj.getString("id"));
                                        btntag.setTextColor(Color.parseColor("#080366"));
                                        btntag.setBackground(ContextCompat.getDrawable(question.this, R.drawable.rounrbblue));
                                        btntag.setGravity(Gravity.CENTER);
                                        btntag.setTypeface(type);

                                        btntag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                setCurrentQuestion(v.getTag().toString(), v);
                                                currentQbutton = (Button) v;
                                            }
                                        });
                                        l80.addView(btntag);
                                    }

                                    if(!mySession.getAnsweredQuestion(sobj.getString("id")).equals(""))
                                    {
                                        //Delete has an answer
                                        mySession.deleteAnsweredQuestion(sobj.getString("id"));
                                    }


                                }


                                loaded = true;



                            } catch (Throwable t) {
                                new MyFunctions().retry(question.this, new Runnable() {
                                    @Override
                                    public void run() {
                                        loadQuestion();
                                    }
                                }, qbox);
                            }
                        } else {
                            new MyFunctions().retry(question.this, new Runnable() {
                                @Override
                                public void run() {
                                    loadQuestion();
                                }
                            }, qbox);
                        }


                    } catch (JSONException t) {
                        new MyFunctions().retry(question.this, new Runnable() {
                            @Override
                            public void run() {
                                loadQuestion();
                            }
                        }, qbox);
                    }
                } else {
                    new MyFunctions().retry(question.this, new Runnable() {
                        @Override
                        public void run() {
                            loadQuestion();
                        }
                    }, qbox);
                }
            }
        }).getQuestions(tid);
    }

    public void resetButton() {
        a.setBackground(ContextCompat.getDrawable(question.this, R.drawable.rounrbblue));
        a.setTextColor(Color.parseColor("#080366"));
        b.setBackground(ContextCompat.getDrawable(question.this, R.drawable.rounrbblue));
        b.setTextColor(Color.parseColor("#080366"));
        c.setBackground(ContextCompat.getDrawable(question.this, R.drawable.rounrbblue));
        c.setTextColor(Color.parseColor("#080366"));
        d.setBackground(ContextCompat.getDrawable(question.this, R.drawable.rounrbblue));
        d.setTextColor(Color.parseColor("#080366"));

        //check if answered before

        //for answers
        for (int i = 0; i < tops.length(); i++) {
            final int finalJ = i;
            try {
                final JSONObject sobj = tops.getJSONObject(finalJ);
                if (sobj.getString("id").equals(currentQuestion)) {

                    if(!mySession.getAnsweredQuestion(sobj.getString("id")).equals(""))
                    {
                        //has an answer
                        validateAnswer(mySession.getAnsweredQuestion(sobj.getString("id")));
                    }
                    else
                    {
                        a.setEnabled(true);
                        b.setEnabled(true);
                        c.setEnabled(true);
                        d.setEnabled(true);
                    }

                    break;
                }

            } catch (JSONException e) {

            }
        }

        //for list
        for (int i = 0; i < tops.length(); i++) {
            final int finalJ = i;
            try {
                final JSONObject sobj = tops.getJSONObject(finalJ);

                    if(!mySession.getAnsweredQuestion(sobj.getString("id")).equals(""))
                    {
                        //has an answer
                        Button qbtn = (Button) l80.findViewWithTag(sobj.getString("id"));
                        qbtn.setBackground(ContextCompat.getDrawable(question.this, R.drawable.done));
                        qbtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }

            } catch (JSONException e) {

            }
        }
    }

    @Override
    public void onBackPressed() {
        if(pad == 0)
        {
            finish();
        }
        else {
            pad = 0;
            displayquestion.setVisibility(View.VISIBLE);
            gcont.setVisibility(View.INVISIBLE);
            bot.setVisibility(View.VISIBLE);
        }
    }
}
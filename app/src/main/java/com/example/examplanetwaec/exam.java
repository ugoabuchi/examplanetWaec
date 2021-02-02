package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
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
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class exam extends AppCompatActivity {
    private session mySession;
    private String sname, sid, qnos, time, currentQuestion, stops;
    public TextView subdisplay, displayname, dtimer;
    private JSONArray tops;
    private WebView displayquestion;
    GridLayout l80;
    private LinearLayout bot;
    private HorizontalScrollView gcont;
    int pad = 0;
    private Button currentQbutton;
    private ImageView backbtn, mySubmit;
    private Boolean isAnswered;
    Long[] arlist;
    private RelativeLayout exam_box;
    Long TimeUsed;
    boolean loaded = false;

    private Long luseconds, ltanswered;

    private ImageView noption, prevbutton, nextbutton;
    private Button a, b, c, d;

    public CountDownTimer timeEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(exam.this, splashActivity.class);
            startActivity(intent);

        }

        //intent variables
        Intent tdata = getIntent();
        sname = tdata.getStringExtra("sname");
        qnos = tdata.getStringExtra("qnos");
        sid = tdata.getStringExtra("sid");
        time = tdata.getStringExtra("time");
        currentQuestion = "";
        bot = findViewById(R.id.bot);
        l80 = findViewById(R.id.l80);
        gcont = findViewById(R.id.gcont);
        noption = findViewById(R.id.noption);
        prevbutton = findViewById(R.id.prevbutton);
        nextbutton = findViewById(R.id.nextbutton);
        a = findViewById(R.id.aoption);
        b = findViewById(R.id.boption);
        c = findViewById(R.id.coption);
        d = findViewById(R.id.doption);


        backbtn = findViewById(R.id.imageView15);
        mySubmit = findViewById(R.id.mysubmit);
        displayname = findViewById(R.id.textView26);
        displayname.setText(sname);

        dtimer = findViewById(R.id.ourtimer);

        exam_box = findViewById(R.id.exam_box);

        isAnswered = false;

        displayquestion = findViewById(R.id.dquestion);
        displayquestion.getSettings().setUseWideViewPort(true);
        displayquestion.getSettings().setBuiltInZoomControls(true);
        displayquestion.getSettings().setSupportZoom(true);
        displayquestion.getSettings().setJavaScriptEnabled(true);
        displayquestion.getSettings().setLoadWithOverviewMode(true);
        displayquestion.getSettings().setLoadsImagesAutomatically(true);
        displayquestion.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pad == 0)
                {
                    final Dialog dialog = new Dialog(exam.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.examexitprompt);
                    exam_box.setAlpha(0.2f);
                    TextView resume = dialog.findViewById(R.id.resume);
                    TextView quit = dialog.findViewById(R.id.quit);
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            exam_box.setAlpha(1f);
                        }
                    });

                    resume.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });

                    quit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            finish();
                        }
                    });



                    ImageView cancel = dialog.findViewById(R.id.exit);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
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
                               } else {
                                   displayquestion.loadData(new String(Base64.decode(sobj.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replaceFirst("<", "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body style=\"padding-bottom: 100px;\"><").concat("</body></html>"), "text/html; charset=utf-8", "UTF-8");
                                   currentQuestion = sobj.getString("id");
                                   currentQbutton = (Button) l80.findViewWithTag(sobj.getString("id"));
                               }
                               displayquestion.setVisibility(View.VISIBLE);
                               gcont.setVisibility(View.INVISIBLE);
                               ltanswered = luseconds;

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
                               } else {
                                   displayquestion.loadData(new String(Base64.decode(sobj.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replaceFirst("<", "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body style=\"padding-bottom: 100px;\"><").concat("</body></html>"), "text/html; charset=utf-8", "UTF-8");
                                   currentQuestion = sobj.getString("id");
                                   currentQbutton = (Button) l80.findViewWithTag(sobj.getString("id"));
                               }
                               displayquestion.setVisibility(View.VISIBLE);
                               gcont.setVisibility(View.INVISIBLE);
                               ltanswered = luseconds;

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

        mySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeEvent.onFinish();
            }
        });

        mySubmit.setEnabled(false);

        Runnable questionLoader = new Runnable() {
            public void run() {
                loadQuestions();
            }
        };

        questionLoader.run();

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
                    ltanswered = luseconds;
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
            a.setEnabled(true);
            b.setEnabled(true);
            c.setEnabled(true);
            d.setEnabled(true);
            for (int i = 0; i < tops.length(); i++) {
                final int finalJ = i;
                try {
                    final JSONObject sobj = tops.getJSONObject(finalJ);
                    if (sobj.getString("id").equals(currentQuestion)) {
                        mySession.addAnsweredQuestion(sobj.getString("id"), v.getTag().toString());
                        Button correct = (Button) v;
                        correct.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.done));
                        correct.setTextColor(Color.parseColor("#FFFFFF"));
                        currentQbutton.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.done));
                        currentQbutton.setTextColor(Color.parseColor("#FFFFFF"));
                        addAverageSpeed();
                        Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrate.vibrate(100);
                        nextbutton.performClick();
                        break;
                    }

                } catch (JSONException e) {

                }
            }
        }
    }

    private void validateAnswer(String id) {

        for (int i = 0; i < tops.length(); i++) {
            final int finalJ = i;
            try {
                final JSONObject sobj = tops.getJSONObject(finalJ);
                if (sobj.getString("id").equals(currentQuestion)) {
                    Button correct = (Button) bot.findViewWithTag(id);
                    correct.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.done));
                    correct.setTextColor(Color.parseColor("#FFFFFF"));
                    currentQbutton.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.done));
                    currentQbutton.setTextColor(Color.parseColor("#FFFFFF"));
                    isAnswered = true;
                    break;
                }

            } catch (JSONException e) {

            }
        }
    }

    public void resetButton() {
        a.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.rounrbblue));
        a.setTextColor(Color.parseColor("#080366"));
        b.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.rounrbblue));
        b.setTextColor(Color.parseColor("#080366"));
        c.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.rounrbblue));
        c.setTextColor(Color.parseColor("#080366"));
        d.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.rounrbblue));
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
                        isAnswered = true;
                    }
                    else
                    {
                        a.setEnabled(true);
                        b.setEnabled(true);
                        c.setEnabled(true);
                        d.setEnabled(true);

                        isAnswered = false;

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
                    qbtn.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.done));
                    qbtn.setTextColor(Color.parseColor("#FFFFFF"));
                }

            } catch (JSONException e) {

            }
        }
    }

    public void loadQuestions()
    {
        //load question
        new datarequest(exam.this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {
                    try {

                        tops = new JSONArray(response[1]);
                        stops = response[1];
                        if (tops.length() > 0) {
                            try {
//.replace("width", "style='width:100% !important;' width")
                                Typeface type = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
                                arlist = new Long[tops.length()];

                                for (int i = 0; i < tops.length(); i++) {
                                    final int finalJ = i;
                                    final JSONObject sobj = tops.getJSONObject(finalJ);

                                    arlist[i] = Long.parseLong("0");
                                    if (i == 0) {
                                        displayquestion.loadData(new String(Base64.decode(sobj.getString("qtext"), Base64.DEFAULT), StandardCharsets.UTF_8).replace("src=\"", "src=\"" + new constants().getAddress()).replaceFirst("<", "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body style=\"padding-bottom: 100px;\"><").concat("</body></html>"), "text/html; charset=utf-8", "UTF-8");
                                        currentQuestion = sobj.getString("id");
                                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                                        params.height = dpToPx(50, exam.this);
                                        params.width = dpToPx(50, exam.this);

                                        Button btntag = new Button(exam.this);
                                        btntag.setLayoutParams(params);
                                        btntag.setText((i+1)+"");
                                        btntag.setTag(sobj.getString("id"));
                                        btntag.setTextColor(Color.parseColor("#080366"));
                                        btntag.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.rounrbblue));
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
                                        currentQbutton = (Button) l80.findViewWithTag(sobj.getString("id"));
                                        ;
                                    } else {
                                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                                        params.height = dpToPx(50, exam.this);
                                        params.width = dpToPx(50, exam.this);

                                        Button btntag = new Button(exam.this);
                                        btntag.setLayoutParams(params);
                                        btntag.setText((i+1)+"");
                                        btntag.setTag(sobj.getString("id"));
                                        btntag.setTextColor(Color.parseColor("#080366"));
                                        btntag.setBackground(ContextCompat.getDrawable(exam.this, R.drawable.rounrbblue));
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

                                    mySubmit.setEnabled(true);
                                    ltanswered = Long.parseLong((Integer.parseInt(time) * 60000)+"");
                                    timeEvent = new CountDownTimer((Integer.parseInt(time) * 60000), 1000) { // adjust the milli seconds here

                                        public void onTick(long millisUntilFinished) {
                                            luseconds = millisUntilFinished;

                                            dtimer.setText(String.format("%02d:%02d",
                                                    TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                        }

                                        public void onFinish() {

                                            submit();
                                            dtimer.setText("done!");
                                            cancel();
                                            //timeEvent = null;
                                        }
                                    };

                                    timeEvent.start();






                                }


                                loaded = true;



                            } catch (Throwable t) {
                                new MyFunctions().retry(exam.this, new Runnable() {
                                    @Override
                                    public void run() {
                                        loadQuestions();
                                    }
                                }, exam_box);
                            }
                        } else {
                            new MyFunctions().retry(exam.this, new Runnable() {
                                @Override
                                public void run() {
                                    loadQuestions();
                                }
                            }, exam_box);
                        }


                    } catch (JSONException t) {
                        new MyFunctions().retry(exam.this, new Runnable() {
                            @Override
                            public void run() {
                                loadQuestions();
                            }
                        }, exam_box);
                    }
                } else {
                    new MyFunctions().retry(exam.this, new Runnable() {
                        @Override
                        public void run() {
                            loadQuestions();
                        }
                    }, exam_box);
                }
            }
        }).getExamQuestions(sid, qnos);
    }

    @Override
    public void onBackPressed() {
        if(pad == 0)
        {
            final Dialog dialog = new Dialog(exam.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.examexitprompt);
            exam_box.setAlpha(0.2f);
            TextView resume = dialog.findViewById(R.id.resume);
            TextView quit = dialog.findViewById(R.id.quit);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    exam_box.setAlpha(1f);
                }
            });

            resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();finish();
                }
            });



            ImageView cancel = dialog.findViewById(R.id.exit);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else {
            pad = 0;
            displayquestion.setVisibility(View.VISIBLE);
            gcont.setVisibility(View.INVISIBLE);
            bot.setVisibility(View.VISIBLE);
        }
    }

    private void addAverageSpeed()
    {
        if(isAnswered == false)
        {
            //create array list of
            for (int i = 0; i < tops.length(); i++) {
                final int finalJ = i;
                try {
                    final JSONObject sobj = tops.getJSONObject(finalJ);
                    if (sobj.getString("id").equals(currentQuestion)) {
                        arlist[i] = ltanswered - luseconds;
                        isAnswered = true;
                        break;
                    }

                } catch (JSONException e) {

                }
            }

            ltanswered = luseconds;

        }
    }

    public void submit()
    {

        Long taverage = Long.parseLong("0");
        String averageSpeed;
        String averageSpeedp;
        int tqa = 0;
        int tqna = 0;
        int cans = 0;
        int rans = 0;
        int scorepercent = 0;
        String status = "";

        int counter = 1;
        //calculatem avaerage
        for (int i = 0; i < tops.length(); i++) {
            taverage = taverage + arlist[i];
            if(arlist[i] != Long.parseLong("0"))
            {
                counter++;
            }

            //get questions answered


            final int finalJ = i;
            try {
                final JSONObject sobj = tops.getJSONObject(finalJ);

                    if(!mySession.getAnsweredQuestion(sobj.getString("id")).equals(""))
                    {
                        //Question answered
                        tqa++;

                        //get correct ans
                        if(sobj.getString("qans").toUpperCase().equals(mySession.getAnsweredQuestion(sobj.getString("id"))))
                        {
                            cans++;
                        }
                        else {
                            rans++;
                        }
                    }
                    else
                    {
                       //empty question
                        tqna++;
                    }


            } catch (JSONException e) {

            }


        }

        scorepercent = (100 * cans) / Integer.parseInt(qnos);
        averageSpeed = String.format("%.2f", (((double)taverage / 60000) / counter));
        averageSpeedp = String.format("%.2f", 100 - ((((double)taverage / 60000) / counter) * 50));
        String tspent = (Integer.parseInt(time) - Integer.parseInt(dtimer.getText().toString().split(":")[0]))+":"+dtimer.getText().toString().split(":")[1];
       // Log.e("Time Spent: ", tspent);
        if(scorepercent < 50)
        {
            status = "failed";
        }
        else {
            status = "passed";
        }

        dtimer.setVisibility(View.INVISIBLE);
        mySubmit.setVisibility(View.INVISIBLE);
        timeEvent.cancel();

        //Log.e("Values: ", "Score Percent: "+scorepercent+"\n Average Speed:"+averageSpeed+"\n Average Speed Percent:"+averageSpeedp+"\n Total Question Answered: "+tqa+"\n Total Questions Not answered: "+tqna+"\n Correctly Answered: "+cans+"\n Wrongly Answered: "+rans+"\nQnos: "+qnos);
        Intent result = new Intent(exam.this, ResultPageActivity.class);
        result.putExtra("status", status+"");
        result.putExtra("sname", sname);
        result.putExtra("cans", cans+"");
        result.putExtra("rans", rans+"");
        result.putExtra("tqa", tqa+"");
        result.putExtra("tqna", tqna+"");
        result.putExtra("qnos", qnos);
        result.putExtra("tops", stops);
        result.putExtra("spc", scorepercent+"");
        result.putExtra("as", averageSpeed+"");
        result.putExtra("asp", averageSpeedp+"");
        result.putExtra("tsp", tspent);
        startActivity(result);
        finish();




    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}
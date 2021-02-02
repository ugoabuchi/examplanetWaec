package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private session mySession;
    private TextView home_username, topicname, ttt, tv16, drawerpname;
    int closeAttempt = 0;
    private ImageView picture, myback;
    private CardView english, maths, biology, chemistry, blog_card, engbox, mathbox, biobox, chembox, phybox, govbox, geobox, ecobox, acctbox, combox, litbox, fmathbox, crkbox, frenchbox;
    private Button hbtn, sbtn, cbtn, mbtn, pbtn,practice_exam_btn = null;
    private ScrollView home, subject, topics, blog = null;
    private ImageView himage, simage, bimage, tback, hide, drawerpimage;
    private ConstraintLayout mainscreen, footer;
    private LinearLayout linearLayout3, replacer, tcontent, bcontent, waec, jamb, scholar, admission, reminder;
    private TextView drawerplogout, today, wtext, wauthor, jtext, jauthor, stext, sauthor, atext, aauthor;
    private String smode, history;
    private RelativeLayout blogbtn;
    DrawerLayout drawerLayout;
    GoogleSignInClient mGoogleSignInClient;
    int bmode, waecid, jambid, scholarid, admisionid;
    boolean isContent, isContentj, isContents, isContenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(HomePageActivity.this, splashActivity.class);
            startActivity(intent);

        }

        wtext = findViewById(R.id.wtext);
        wauthor = findViewById(R.id.wauthor);

        jtext = findViewById(R.id.jtext);
        jauthor = findViewById(R.id.jauthor);

        stext = findViewById(R.id.stext);
        sauthor = findViewById(R.id.sauthor);

        atext = findViewById(R.id.atext);
        aauthor = findViewById(R.id.aauthor);

        waec = findViewById(R.id.waec);
        jamb = findViewById(R.id.jamb);
        scholar = findViewById(R.id.scholarship);
        admission = findViewById(R.id.admissions);



        drawerLayout = findViewById(R.id.drawer_Layout);
        hide = drawerLayout.findViewById(R.id.imageView13);
        drawerpimage = drawerLayout.findViewById(R.id.imageView12);
        drawerpname = drawerLayout.findViewById(R.id.textView17);
        reminder = drawerLayout.findViewById(R.id.reminder);
        drawerplogout = drawerLayout.findViewById(R.id.textView3);

        smode = "practice";
        //initiallizing variables
        mainscreen = findViewById(R.id.mainscreen);
        home = findViewById(R.id.home);
        footer = findViewById(R.id.constraintLayout3);
        subject = findViewById(R.id.subject);
        topics = findViewById(R.id.topics);
        blog = findViewById(R.id.blogs);
        linearLayout3 = findViewById(R.id.linearLayout3);
        replacer = findViewById(R.id.replaceback);
        myback = findViewById(R.id.myback);
        tcontent = findViewById(R.id.tcontent);
        bcontent = findViewById(R.id.bcontent);
        blogbtn = findViewById(R.id.blogbutton);

        pbtn = findViewById(R.id.practice_exam_btn);
        hbtn = findViewById(R.id.homebtn);
        sbtn = findViewById(R.id.sub_btn);
        cbtn = findViewById(R.id.chatbtn);
        mbtn = findViewById(R.id.menubtn);
        practice_exam_btn = findViewById(R.id.practice_exam_btn);

        himage = findViewById(R.id.himage);
        simage = findViewById(R.id.simage);
        bimage = findViewById(R.id.bimage);
        tback = findViewById(R.id.topic_back);
        ttt = findViewById(R.id.ttt);
        tv16 = findViewById(R.id.textView16);
        today = findViewById(R.id.textView19);


        home_username = findViewById(R.id.home_username);
        topicname = findViewById(R.id.topicname);
        picture = findViewById(R.id.imageView10);
        english = findViewById(R.id.english);
        maths = findViewById(R.id.maths);
        biology = findViewById(R.id.biology);
        chemistry = findViewById(R.id.chemistry);
        blog_card = findViewById(R.id.blog_card);


        engbox = findViewById(R.id.engbox);
        mathbox = findViewById(R.id.mathbox);
        biobox = findViewById(R.id.biobox);
        chembox = findViewById(R.id.chembox);
        phybox = findViewById(R.id.phybox);
        govbox = findViewById(R.id.govbox);
        geobox = findViewById(R.id.geobox);
        ecobox = findViewById(R.id.ecobox);
        acctbox = findViewById(R.id.acctbox);
        combox = findViewById(R.id.combox);
        litbox = findViewById(R.id.litbox);
        fmathbox = findViewById(R.id.fmathbox);
        crkbox = findViewById(R.id.crkbox);
        frenchbox = findViewById(R.id.frenchbox);

        maths.setBackgroundResource(R.drawable.ic_mathematics);

        biology.setBackgroundResource(R.drawable.ic_biology);

        chemistry.setBackgroundResource(R.drawable.ic_chemistry);
        english.setBackgroundResource(R.drawable.ic_english);


        //initializing variables
        bmode = 0;
        history = "home";

        isContent = false;
        isContentj = false;
        isContents = false;
        isContenta = false;
        waecid = 73;
        jambid = 58;
        scholarid = 62;
        admisionid = 60;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        //sent onclick functions
        waec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, BlogActivity.class);
                intent.putExtra("id", ""+waecid);
                intent.putExtra("blogcat", "WAEC");
                startActivity(intent);
            }
        });

        jamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, BlogActivity.class);
                intent.putExtra("id", ""+jambid);
                intent.putExtra("blogcat", "JAMB");
                startActivity(intent);
            }
        });

        scholar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, BlogActivity.class);
                intent.putExtra("id", ""+scholarid);
                intent.putExtra("blogcat", "SCHOLARSHIP");
                startActivity(intent);
            }
        });

        admission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, BlogActivity.class);
                intent.putExtra("id", ""+admisionid);
                intent.putExtra("blogcat", "ADMISSIONS");
                startActivity(intent);
            }
        });


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        drawerplogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                mySession.deleteUserLog();
                Intent intent = new Intent(HomePageActivity.this, splashActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if(isMyServiceRunning(HomePageActivity.class))
                    {
                        Intent intent = new Intent(HomePageActivity.this, MyReminder.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);

                    }
                    else
                    {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent i= new Intent(HomePageActivity.this, aservice.class);
                                        startService(i);

                                    }
                                }).start();


                            }
                        });
                    }



            }
        });






        home_username.setText(mySession.getName().split(" ")[0] + "!");
        drawerpname.setText(mySession.getName().split(" ")[0] + " "+mySession.getName().split(" ")[0]);
        Picasso.get().load(mySession.getPicture()).transform(new CircleTransform()).into(picture);
        Picasso.get().load(mySession.getPicture()).transform(new CircleTransform()).into(drawerpimage);
        maths.setPreventCornerOverlap(false);
        english.setPreventCornerOverlap(false);
        biology.setPreventCornerOverlap(false);
        chemistry.setPreventCornerOverlap(false);


        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawer(drawerLayout);
            }
        });

        //setting onclicklisteners
        hbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject();
                smode = "practice";
            }
        });
        blogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blog();
            }
        });
        myback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bmode == 1)
                {

                    if(history.equals("home"))
                    {
                        home();

                    }
                    else
                    {
                        subject();
                    }

                }
                else
                {
                    if (closeAttempt == 0) {
                        Toast.makeText(HomePageActivity.this, "Press the back button again to exit", Toast.LENGTH_LONG).show();
                        closeAttempt = 1;
                    } else {
                        finish();
                    }
                }
            }
        });
        practice_exam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject();
                smode = "exam";
            }
        });

        engbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if exam mode or subject mode
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "34");
                    intent.putExtra("subjectname", "English Language");
                    startActivity(intent);
                }
                else {
                    showMenu("English Language", "34", "47", R.drawable.english_topics);
                }
            }
        });

        mathbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "31");
                    intent.putExtra("subjectname", "Mathematics");
                    startActivity(intent);
                }
                else {
                    showMenu("Mathematics", "31", "46", R.drawable.maths_topic);
                }

            }
        });

        biobox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "32");
                    intent.putExtra("subjectname", "Biology");
                    startActivity(intent);
                }
                else {
                    showMenu("Biology", "32", "48", R.drawable.biology_topics);
                }

            }
        });
        chembox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "35");
                    intent.putExtra("subjectname", "Chemistry");
                    startActivity(intent);
                }
                else {
                    showMenu("Chemistry", "35", "50", R.drawable.chemistry_topics);
                }

            }
        });

        phybox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "33");
                    intent.putExtra("subjectname", "Physics");
                    startActivity(intent);
                }
                else {
                    showMenu("Physics", "33", "49", R.drawable.physics_topics);
                }

            }
        });

        geobox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "42");
                    intent.putExtra("subjectname", "Geography");
                    startActivity(intent);
                }
                else {
                    showMenu("Geography", "42", "57", R.drawable.geography_topics);
                }

            }
        });

        ecobox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "39");
                    intent.putExtra("subjectname", "Economics");
                    startActivity(intent);
                }
                else {
                    showMenu("Economics", "39", "54", R.drawable.economics_topics);
                }

            }
        });

        acctbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "43");
                    intent.putExtra("subjectname", "Account");
                    startActivity(intent);
                }
                else {
                    showMenu("Account", "43", "58", R.drawable.account_topics);
                }

            }
        });

        combox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "40");
                    intent.putExtra("subjectname", "Commerce");
                    startActivity(intent);
                }
                else {
                    showMenu("Commerce", "40", "55", R.drawable.commerce_topics);
                }

            }
        });

        litbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "41");
                    intent.putExtra("subjectname", "Literature");
                    startActivity(intent);
                }
                else {
                    showMenu("Literature", "41", "56", R.drawable.lit_topics);
                }

            }
        });

        fmathbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "59");
                    intent.putExtra("subjectname", "Futher-Mathematics");
                    startActivity(intent);
                }
                else {
                    showMenu("Futher-Mathematics", "59", "60", R.drawable.fmaths_topics);
                }

            }
        });

        crkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "36");
                    intent.putExtra("subjectname", "C.R.K");
                    startActivity(intent);
                }
                else {
                    showMenu("C.R.K", "36", "51m", R.drawable.crk_topics);
                }

            }
        });

        govbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smode.equals("exam"))
                {
                    Intent intent = new Intent(HomePageActivity.this, ExamPractisePageActivity.class);
                    intent.putExtra("subjectid", "38");
                    intent.putExtra("subjectname", "Government");
                    startActivity(intent);
                }
                else {
                    showMenu("Government", "38", "53", R.drawable.geography_topics);
                }

            }
        });


        home();


    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {

                return true;
            }
        }

        return false;
    }

    //set switch functions
    private void blog() {
        bmode = 1;
        simage.setVisibility(View.INVISIBLE);
        himage.setVisibility(View.INVISIBLE);
        bimage.setVisibility(View.VISIBLE);
        today.setVisibility(View.INVISIBLE);

        footer.setVisibility(View.GONE);
        myback.setVisibility(View.VISIBLE);
        linearLayout3.setVisibility(View.INVISIBLE);
        replacer.setVisibility(View.VISIBLE);
        topicname.setVisibility(View.VISIBLE);
        picture.setVisibility(View.INVISIBLE);
        pbtn.setVisibility(View.INVISIBLE);
        topicname.setText("BLOG");
        TextView tv18 = findViewById(R.id.htextView18);
        tv18.setText("Get familiar with\nupdated\nEducational\nNews");
        home.setVisibility(View.GONE);
        blog.setVisibility(View.VISIBLE);
        subject.setVisibility(View.GONE);
        updateWaecBlog();
        updateJambBlog();
        updateschorlashipbBlog();
        updateadmissionbBlog();
    }

    private void topic(String subjectname) {

        bmode = 1;
        simage.setVisibility(View.VISIBLE);
        himage.setVisibility(View.INVISIBLE);
        bimage.setVisibility(View.INVISIBLE);
        today.setVisibility(View.VISIBLE);

        footer.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.INVISIBLE);
        replacer.setVisibility(View.VISIBLE);
        myback.setVisibility(View.VISIBLE);
        topicname.setText(subjectname);
        topicname.setVisibility(View.VISIBLE);
        picture.setVisibility(View.INVISIBLE);
        pbtn.setVisibility(View.INVISIBLE);

        hbtn.setBackgroundResource(R.drawable.ic_home_bar_blue);
        sbtn.setBackgroundResource(R.drawable.ic_subj_bar_white);
        cbtn.setBackgroundResource(R.drawable.ic_chat_bar);
        mbtn.setBackgroundResource(R.drawable.ic_menu_bar);
        TextView tv18 = findViewById(R.id.htextView18);
        tv18.setText("Kindly choose\nthe topic you\nwant to practice");
        home.setVisibility(View.GONE);
        subject.setVisibility(View.GONE);
        blog.setVisibility(View.GONE);
        topics.setVisibility(View.VISIBLE);
    }

    private void subject() {
        bmode = 0;
        history = "sub";
        simage.setVisibility(View.VISIBLE);
        himage.setVisibility(View.INVISIBLE);
        bimage.setVisibility(View.INVISIBLE);
        today.setVisibility(View.VISIBLE);

        footer.setVisibility(View.VISIBLE);
        replacer.setVisibility(View.INVISIBLE);
        myback.setVisibility(View.INVISIBLE);
        linearLayout3.setVisibility(View.VISIBLE);
        topicname.setVisibility(View.INVISIBLE);
        picture.setVisibility(View.VISIBLE);
        pbtn.setVisibility(View.INVISIBLE);
        hbtn.setBackgroundResource(R.drawable.ic_home_bar_blue);
        sbtn.setBackgroundResource(R.drawable.ic_subj_bar_white);
        cbtn.setBackgroundResource(R.drawable.ic_chat_bar);
        mbtn.setBackgroundResource(R.drawable.ic_menu_bar);
        TextView tv18 = findViewById(R.id.htextView18);
        tv18.setText("Kindly choose\nthe subject you\nwant to practice");
        home.setVisibility(View.GONE);
        blog.setVisibility(View.GONE);
        subject.setVisibility(View.VISIBLE);
    }

    private void home() {
        bmode = 0;
        history = "home";
        simage.setVisibility(View.INVISIBLE);
        himage.setVisibility(View.VISIBLE);
        bimage.setVisibility(View.INVISIBLE);
        today.setVisibility(View.VISIBLE);

        footer.setVisibility(View.VISIBLE);
        replacer.setVisibility(View.INVISIBLE);
        myback.setVisibility(View.INVISIBLE);
        linearLayout3.setVisibility(View.VISIBLE);
        topicname.setVisibility(View.INVISIBLE);
        picture.setVisibility(View.VISIBLE);
        pbtn.setVisibility(View.VISIBLE);
        hbtn.setBackgroundResource(R.drawable.ic_home_bar);
        sbtn.setBackgroundResource(R.drawable.ic_subject_bar);
        cbtn.setBackgroundResource(R.drawable.ic_chat_bar);
        mbtn.setBackgroundResource(R.drawable.ic_menu_bar);
        TextView tv18 = findViewById(R.id.htextView18);
        tv18.setText("What subject \ndo you want to\npractice");
        home.setVisibility(View.VISIBLE);
        subject.setVisibility(View.GONE);
        topics.setVisibility(View.GONE);
        blog.setVisibility(View.GONE);
    }


    //setting subject type popup
    private void showMenu(final String sname, final String obj, final String theory, final int mydraw) {
        final Dialog dialog = new Dialog(HomePageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.subjecttype);
        mainscreen.setAlpha(0.2f);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mainscreen.setAlpha(1f);
            }
        });
        final TextView objbtn = dialog.findViewById(R.id.obj);
        final TextView theorybtn = dialog.findViewById(R.id.theory);
        if (tcontent.getChildCount() > 0) {
            tcontent.removeAllViews();
        }
        objbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //load topic session for obj
                topic(sname);
                dialog.cancel();
                dialog.dismiss();

                //load topic contents
                new datarequest(HomePageActivity.this, new customListener() {
                    @Override
                    public void onResponse(String[] response) {
                        if (response[0].equals("success")) {
                            try {

                                JSONArray tops = new JSONArray(response[1]);
                                for (int i = 0; i < tops.length(); i++) {

                                    final int finalJ = i;
                                    try {

                                        final JSONObject topquery = tops.getJSONObject(finalJ);
                                        //create cardview
                                        CardView mycard = new CardView(HomePageActivity.this);
                                        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                1.0f
                                        );
                                        layoutparams.setMargins(0, 0, 0, 20);
                                        mycard.setLayoutParams(layoutparams);
                                        mycard.setRadius(12);
                                        mycard.setElevation(2);

                                        //create textview
                                        final TextView mytext = new TextView(HomePageActivity.this);
                                        LinearLayout.LayoutParams layoutparams2 = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        );
                                        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
                                        mytext.setTypeface(type);
                                        mytext.setLayoutParams(layoutparams2);
                                        mytext.setText(topquery.getString("name"));
                                        mytext.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                                        mytext.setBackground(getResources().getDrawable(mydraw));
                                        mytext.setPadding(60, 40, 105, 40);
                                        mytext.setTextColor(Color.BLACK);
                                        mytext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                        mytext.setTag(topquery.getString("id"));
                                        //add textview to cardview
                                        mycard.addView(mytext);

                                        //add card to linear
                                        tcontent.addView(mycard);

                                        mytext.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String topicname = mytext.getText().toString();
                                                String topicid = mytext.getTag().toString();
                                                Intent intent = new Intent(HomePageActivity.this, question.class);
                                                intent.putExtra("subjectname", sname);
                                                intent.putExtra("topicname", topicname);
                                                intent.putExtra("topicid", topicid);
                                                intent.putExtra("topictype", "OBJ");
                                                startActivity(intent);

                                            }
                                        });

                                    } catch (Throwable t) {
                                        subject();
                                        new MyFunctions().retryNoExit(HomePageActivity.this, new Runnable() {
                                            @Override
                                            public void run() {
                                                objbtn.performClick();
                                            }
                                        }, mainscreen);
                                    }

                                }


                            } catch (JSONException t) {
                                subject();
                                new MyFunctions().retryNoExit(HomePageActivity.this, new Runnable() {
                                    @Override
                                    public void run() {
                                        objbtn.performClick();
                                    }
                                }, mainscreen);
                            }
                        } else {
                            subject();
                            new MyFunctions().retryNoExit(HomePageActivity.this, new Runnable() {
                                @Override
                                public void run() {
                                    objbtn.performClick();
                                }
                            }, mainscreen);
                        }
                    }
                }).getTopics(obj);

            }
        });
        theorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load topic session theory
                topic(sname);
                dialog.cancel();
                dialog.dismiss();
                //load topic contents
                new datarequest(HomePageActivity.this, new customListener() {
                    @Override
                    public void onResponse(String[] response) {
                        if (response[0].equals("success")) {
                            try {

                                JSONArray tops = new JSONArray(response[1]);
                                for (int i = 0; i < tops.length(); i++) {

                                    final int finalJ = i;
                                    try {

                                        final JSONObject topquery = tops.getJSONObject(finalJ);
                                        //create cardview
                                        CardView mycard = new CardView(HomePageActivity.this);
                                        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                1.0f
                                        );
                                        layoutparams.setMargins(0, 0, 0, 20);
                                        mycard.setLayoutParams(layoutparams);
                                        mycard.setRadius(12);
                                        mycard.setElevation(2);

                                        //create textview
                                        final TextView mytext = new TextView(HomePageActivity.this);
                                        LinearLayout.LayoutParams layoutparams2 = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        );
                                        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
                                        mytext.setTypeface(type);
                                        mytext.setLayoutParams(layoutparams2);
                                        mytext.setText(topquery.getString("name"));
                                        mytext.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                                        mytext.setBackground(getResources().getDrawable(mydraw));
                                        mytext.setPadding(60, 40, 105, 40);
                                        mytext.setTextColor(Color.BLACK);
                                        mytext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                        mytext.setTag(topquery.getString("id"));

                                        //add textview to cardview
                                        mycard.addView(mytext);

                                        //add card to linear
                                        tcontent.addView(mycard);

                                        mytext.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String topicname = mytext.getText().toString();
                                                String topicid = mytext.getTag().toString();
                                                Intent intent = new Intent(HomePageActivity.this, question.class);
                                                intent.putExtra("subjectname", sname);
                                                intent.putExtra("topicname", topicname);
                                                intent.putExtra("topicid", topicid);
                                                intent.putExtra("topictype", "THEORY");
                                                startActivity(intent);

                                            }
                                        });

                                    } catch (Throwable t) {
                                        subject();
                                        new MyFunctions().retryNoExit(HomePageActivity.this, new Runnable() {
                                            @Override
                                            public void run() {
                                                theorybtn.performClick();
                                            }
                                        }, mainscreen);
                                    }

                                }


                            } catch (JSONException t) {
                                subject();
                                new MyFunctions().retryNoExit(HomePageActivity.this, new Runnable() {
                                    @Override
                                    public void run() {
                                        theorybtn.performClick();
                                    }
                                }, mainscreen);
                            }
                        } else {
                            subject();
                            new MyFunctions().retryNoExit(HomePageActivity.this, new Runnable() {
                                @Override
                                public void run() {
                                    theorybtn.performClick();
                                }
                            }, mainscreen);
                        }
                    }
                }).getTopics(theory);
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


    public void updateWaecBlog()
    {
        new datarequest(HomePageActivity.this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {

                    try {

                        JSONArray tops = new JSONArray(response[1]);
                        for (int i = 0; i < tops.length(); i++) {

                            final int finalJ = i;
                            try {

                                isContent = true;
                                final JSONObject topquery = tops.getJSONObject(finalJ);
                                wtext.setText(topquery.getString("post_title"));
                                wauthor.setText("by "+topquery.getString("post_author")+" | "+topquery.getString("post_date"));



                            } catch (Throwable t) {
                                if(isContent == false)
                                {
                                    updateWaecBlog();
                                }
                            }

                            break;

                        }


                    } catch (JSONException t) {
                        if(isContent == false)
                        {
                            updateWaecBlog();
                        }
                    }
                } else {
                    if(isContent == false)
                    {
                        updateWaecBlog();
                    }
                }
            }
        }).sblog(""+waecid);
    }


    public void updateJambBlog()
    {
        new datarequest(HomePageActivity.this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {

                    try {

                        JSONArray tops = new JSONArray(response[1]);
                        for (int i = 0; i < tops.length(); i++) {

                            final int finalJ = i;
                            try {

                                isContentj = true;
                                final JSONObject topquery = tops.getJSONObject(finalJ);
                                jtext.setText(topquery.getString("post_title"));
                                jauthor.setText("by "+topquery.getString("post_author")+" | "+topquery.getString("post_date"));



                            } catch (Throwable t) {
                                if(isContentj == false)
                                {
                                    updateJambBlog();
                                }
                            }

                            break;

                        }


                    } catch (JSONException t) {
                        if(isContentj == false)
                        {
                            updateJambBlog();
                        }
                    }
                } else {
                    if(isContentj == false)
                    {
                        updateJambBlog();
                    }
                }
            }
        }).sblog(""+jambid);
    }



    public void updateschorlashipbBlog()
    {
        new datarequest(HomePageActivity.this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {

                    try {

                        JSONArray tops = new JSONArray(response[1]);
                        for (int i = 0; i < tops.length(); i++) {

                            final int finalJ = i;
                            try {

                                isContents = true;
                                final JSONObject topquery = tops.getJSONObject(finalJ);
                                stext.setText(topquery.getString("post_title"));
                                sauthor.setText("by "+topquery.getString("post_author")+" | "+topquery.getString("post_date"));



                            } catch (Throwable t) {
                                if(isContents == false)
                                {
                                    updateschorlashipbBlog();
                                }
                            }

                            break;

                        }


                    } catch (JSONException t) {
                        if(isContents == false)
                        {
                            updateschorlashipbBlog();
                        }
                    }
                } else {
                    if(isContents == false)
                    {
                        updateschorlashipbBlog();
                    }
                }
            }
        }).sblog(""+scholarid);
    }



    public void updateadmissionbBlog()
    {
        new datarequest(HomePageActivity.this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {

                    try {

                        JSONArray tops = new JSONArray(response[1]);
                        for (int i = 0; i < tops.length(); i++) {

                            final int finalJ = i;
                            try {

                                isContenta = true;
                                final JSONObject topquery = tops.getJSONObject(finalJ);
                                atext.setText(topquery.getString("post_title"));
                                aauthor.setText("by "+topquery.getString("post_author")+" | "+topquery.getString("post_date"));



                            } catch (Throwable t) {
                                if(isContenta == false)
                                {
                                    updateadmissionbBlog();
                                }
                            }

                            break;

                        }


                    } catch (JSONException t) {
                        if(isContenta == false)
                        {
                            updateadmissionbBlog();
                        }
                    }
                } else {
                    if(isContenta == false)
                    {
                        updateadmissionbBlog();
                    }
                }
            }
        }).sblog(""+admisionid);
    }








    public static void Drawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if(bmode == 1)
        {


            if(history.equals("home"))
            {
                home();

            }
            else
            {
                subject();
            }

        }
        else
        {
            if (closeAttempt == 0) {
                Toast.makeText(this, "Press the back button again to exit", Toast.LENGTH_LONG).show();
                closeAttempt = 1;
            } else {
                finish();
            }
        }
    }
}

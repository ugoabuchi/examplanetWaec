package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BlogActivity extends AppCompatActivity {

    private String id, blogcat;
    private int offset;
    private session mySession;
    private ConstraintLayout mainscreen;
    private LinearLayout bcontent;
    private  ImageView topic_back;
    private ScrollView blogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(BlogActivity.this, splashActivity.class);
            startActivity(intent);

        }
        mainscreen = findViewById(R.id.mainscreen);
        bcontent = findViewById(R.id.bcontent);
        topic_back = findViewById(R.id.topic_back);
        blogs = findViewById(R.id.blogs);
        topic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        blogs.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View view = (View)blogs.getChildAt(blogs.getChildCount() - 1);
                int diff = (view.getBottom() - (blogs.getHeight() + blogs.getScrollY()));


                if (diff == 0) {
                   getBlognext();
                }
            }
        });
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        blogcat = intent.getStringExtra("blogcat");
        offset = 0;


        getBlog();
    }



    public void getBlog()
    {
        new datarequest(BlogActivity.this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {

                    try {

                        JSONArray tops = new JSONArray(response[1]);
                        for (int i = 0; i < tops.length(); i++) {

                            final int finalJ = i;
                            try {


                                final JSONObject topquery = tops.getJSONObject(finalJ);

                                //create linearlayout
                                LinearLayout myparent = new LinearLayout(BlogActivity.this);
                                LinearLayout.LayoutParams myparentparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                myparentparams.setMargins(0,0,0, dpToPx(20, BlogActivity.this));
                                myparent.setOrientation(LinearLayout.HORIZONTAL);
                                myparent.setPadding(dpToPx(10, BlogActivity.this), 0, dpToPx(10, BlogActivity.this), 0);
                                myparent.setLayoutParams(myparentparams);

                                //create imageview
                                ImageView guid = new ImageView(BlogActivity.this);
                                LinearLayout.LayoutParams guidparams = new LinearLayout.LayoutParams(dpToPx(80, BlogActivity.this), dpToPx(90, BlogActivity.this));
                                guidparams.setMargins(dpToPx(16, BlogActivity.this),0,0, 0);
                                guidparams.gravity = Gravity.CENTER_VERTICAL;
                                guid.setLayoutParams(guidparams);
                                Picasso.get().load(topquery.getString("guid")).into(guid);

                                //create content Linear Layout
                                LinearLayout cparent = new LinearLayout(BlogActivity.this);
                                LinearLayout.LayoutParams cparentparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F);
                                cparentparams.setMargins(dpToPx(10, BlogActivity.this),0,0, 0);
                                cparent.setOrientation(LinearLayout.VERTICAL);
                                cparent.setPadding(dpToPx(5, BlogActivity.this), dpToPx(5, BlogActivity.this), dpToPx(5, BlogActivity.this), dpToPx(5, BlogActivity.this));
                                cparent.setLayoutParams(cparentparams);

                                //create textviews
                                TextView title = new TextView(BlogActivity.this);
                                LinearLayout.LayoutParams titleparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                titleparams.setMargins(0,dpToPx(10, BlogActivity.this),0, 0);
                                title.setTextColor(Color.parseColor("#3F3D56"));
                                Typeface type = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
                                title.setTypeface(type);
                                title.setText(topquery.getString("post_title"));
                                title.setLayoutParams(titleparams);

                                TextView author = new TextView(BlogActivity.this);
                                LinearLayout.LayoutParams authorparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                authorparams.setMargins(0,dpToPx(10, BlogActivity.this),0, 0);
                                author.setTextColor(Color.parseColor("#3F3D56"));
                                Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
                                author.setTypeface(type1);
                                author.setText("by "+topquery.getString("post_author")+" | "+topquery.getString("post_date"));
                                author.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                author.setLayoutParams(authorparams);

                                cparent.addView(title);
                                cparent.addView(author);

                                myparent.addView(guid);
                                myparent.addView(cparent);

                                bcontent.addView(myparent);
                                offset = 50;
                                myparent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(BlogActivity.this, BlogReadActivity.class);
                                        try {
                                            intent.putExtra("title", topquery.getString("post_title"));
                                            intent.putExtra("url", topquery.getString("guid"));
                                            intent.putExtra("content", topquery.getString("post_content"));
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                        }

                                    }
                                });



                            } catch (Throwable t) {
                                new MyFunctions().retry(BlogActivity.this, new Runnable() {
                                    @Override
                                    public void run() {
                                        getBlog();
                                    }
                                }, mainscreen);
                            }



                        }


                    } catch (JSONException t) {
                        new MyFunctions().retry(BlogActivity.this, new Runnable() {
                            @Override
                            public void run() {
                                getBlog();
                            }
                        }, mainscreen);
                    }
                } else {
                    new MyFunctions().retry(BlogActivity.this, new Runnable() {
                        @Override
                        public void run() {
                            getBlog();
                        }
                    }, mainscreen);
                }
            }
        }).blog(id, ""+offset);
    }

    public void getBlognext()
    {
        new datarequest(BlogActivity.this, new customListener() {
            @Override
            public void onResponse(String[] response) {
                if (response[0].equals("success")) {

                    try {

                        JSONArray tops = new JSONArray(response[1]);
                        for (int i = 0; i < tops.length(); i++) {

                            final int finalJ = i;
                            try {


                                final JSONObject topquery = tops.getJSONObject(finalJ);

                                //create linearlayout
                                LinearLayout myparent = new LinearLayout(BlogActivity.this);
                                LinearLayout.LayoutParams myparentparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                myparentparams.setMargins(0,0,0, dpToPx(20, BlogActivity.this));
                                myparent.setOrientation(LinearLayout.HORIZONTAL);
                                myparent.setPadding(dpToPx(10, BlogActivity.this), 0, dpToPx(10, BlogActivity.this), 0);
                                myparent.setLayoutParams(myparentparams);

                                //create imageview
                                ImageView guid = new ImageView(BlogActivity.this);
                                LinearLayout.LayoutParams guidparams = new LinearLayout.LayoutParams(dpToPx(80, BlogActivity.this), dpToPx(90, BlogActivity.this));
                                guidparams.setMargins(dpToPx(16, BlogActivity.this),0,0, 0);
                                guidparams.gravity = Gravity.CENTER_VERTICAL;
                                guid.setLayoutParams(guidparams);
                                Picasso.get().load(topquery.getString("guid")).into(guid);

                                //create content Linear Layout
                                LinearLayout cparent = new LinearLayout(BlogActivity.this);
                                LinearLayout.LayoutParams cparentparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F);
                                cparentparams.setMargins(dpToPx(10, BlogActivity.this),0,0, 0);
                                cparent.setOrientation(LinearLayout.VERTICAL);
                                cparent.setPadding(dpToPx(5, BlogActivity.this), dpToPx(5, BlogActivity.this), dpToPx(5, BlogActivity.this), dpToPx(5, BlogActivity.this));
                                cparent.setLayoutParams(cparentparams);

                                //create textviews
                                TextView title = new TextView(BlogActivity.this);
                                LinearLayout.LayoutParams titleparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                titleparams.setMargins(0,dpToPx(10, BlogActivity.this),0, 0);
                                title.setTextColor(Color.parseColor("#3F3D56"));
                                Typeface type = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
                                title.setTypeface(type);
                                title.setText(topquery.getString("post_title"));
                                title.setLayoutParams(titleparams);

                                TextView author = new TextView(BlogActivity.this);
                                LinearLayout.LayoutParams authorparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                authorparams.setMargins(0,dpToPx(10, BlogActivity.this),0, 0);
                                author.setTextColor(Color.parseColor("#3F3D56"));
                                Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
                                author.setTypeface(type1);
                                author.setText("by "+topquery.getString("post_author")+" | "+topquery.getString("post_date"));
                                author.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                author.setLayoutParams(authorparams);

                                cparent.addView(title);
                                cparent.addView(author);

                                myparent.addView(guid);
                                myparent.addView(cparent);

                                bcontent.addView(myparent);
                                offset = offset + 50;

                                myparent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(BlogActivity.this, BlogReadActivity.class);
                                        try {
                                            intent.putExtra("title", topquery.getString("post_title"));
                                            intent.putExtra("url", topquery.getString("guid"));
                                            intent.putExtra("content", topquery.getString("post_content"));
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                        }

                                    }
                                });



                            } catch (Throwable t) {
                                getBlog();
                            }



                        }


                    } catch (JSONException t) {
                        getBlog();
                    }
                } else {
                    getBlog();
                }
            }
        }).blognext(id, ""+offset);
    }



    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


}
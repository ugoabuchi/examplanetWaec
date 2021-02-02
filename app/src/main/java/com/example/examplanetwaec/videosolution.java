package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class videosolution extends AppCompatActivity {

    String tops, currentQuestion, sname, type;
    LinearLayout noptions;
    JSONArray mytops;
    TextView tv26;
    PlayerView dvideo, fvideo;
    ImageView fullscreenbutton, back1, prevbutton, nextbutton;
    FloatingActionButton back2;
    RelativeLayout maincont, bot;
    private SimpleExoPlayer player;
    boolean full = false;
    HorizontalScrollView qlist;
    private  session mySession;
    int findex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosolution);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(videosolution.this, splashActivity.class);
            startActivity(intent);

        }

        noptions = findViewById(R.id.listofquestions);
        bot = findViewById(R.id.bot);
        dvideo = findViewById(R.id.dvideo);
        fvideo = findViewById(R.id.fvideo);
        fullscreenbutton = findViewById(R.id.fullscreen);
        maincont = findViewById(R.id.maincont);
        tv26 = findViewById(R.id.textView26);
        back1 = findViewById(R.id.imageView15);
        back2 = findViewById(R.id.back2);
        prevbutton = findViewById(R.id.prevbutton);
        nextbutton = findViewById(R.id.nextbutton);
        qlist = findViewById(R.id.qlist);


        prevbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < tops.length(); i++) {
                    final int finalJ = i;
                    try {
                        final JSONObject sobj = mytops.getJSONObject(finalJ);
                        if (sobj.getString("id").equals(currentQuestion)) {
                            if (i > 0) {
                                final int nfianlj = i - 1;
                                final JSONObject nsobj = mytops.getJSONObject(nfianlj);
                                TextView btn = (TextView) bot.findViewWithTag(currentQuestion);
                                if (!mySession.getAnsweredQuestion(currentQuestion).equals("")) {
                                    if (mySession.getAnsweredQuestion(currentQuestion).equals(mytops.getJSONObject(Integer.parseInt(btn.getText().toString()) - 1).getString("qans"))) {
                                        btn.setBackgroundResource(R.drawable.greencircle);
                                    } else {
                                        btn.setBackgroundResource(R.drawable.redcircle);
                                    }
                                } else {
                                    btn.setBackgroundResource(R.drawable.redcircle);
                                }


                                String userAgent = Util.getUserAgent(videosolution.this, "Examplanet");
                                DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(videosolution.this, userAgent);
                                Uri uriOfContentUrl = Uri.parse(new constants().getAddress() + "/global/tsol/" + nsobj.getString("un") + "." + nsobj.getString("ext"));
                                MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);

                                player.prepare(mediaSource);
                                player.setPlayWhenReady(true);

                                currentQuestion = nsobj.getString("id");
                                TextView btn1 = (TextView) bot.findViewWithTag(currentQuestion);
                                btn1.setBackgroundResource(R.drawable.bluecircle);
                                qlist.scrollTo((int) btn1.getX(), 0);


                            } else {
                                TextView btn = (TextView) bot.findViewWithTag(currentQuestion);
                                if (!mySession.getAnsweredQuestion(currentQuestion).equals("")) {
                                    if (mySession.getAnsweredQuestion(currentQuestion).equals(mytops.getJSONObject(Integer.parseInt(btn.getText().toString()) - 1).getString("qans"))) {
                                        btn.setBackgroundResource(R.drawable.greencircle);
                                    } else {
                                        btn.setBackgroundResource(R.drawable.redcircle);
                                    }
                                } else {
                                    btn.setBackgroundResource(R.drawable.redcircle);
                                }


                                String userAgent = Util.getUserAgent(videosolution.this, "Examplanet");
                                DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(videosolution.this, userAgent);
                                Uri uriOfContentUrl = Uri.parse(new constants().getAddress() + "/global/tsol/" + sobj.getString("un") + "." + sobj.getString("ext"));
                                MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);

                                player.prepare(mediaSource);
                                player.setPlayWhenReady(true);

                                currentQuestion = sobj.getString("id");
                                TextView btn1 = (TextView) bot.findViewWithTag(currentQuestion);
                                btn1.setBackgroundResource(R.drawable.bluecircle);
                                qlist.scrollTo((int) btn1.getX(), 0);


                            }


                            Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrate.vibrate(100);

                            break;
                        }
                    } catch (JSONException e) {

                    }
                }
            }

        });


        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < tops.length(); i++) {
                    final int finalJ = i;
                    try {
                        final JSONObject sobj = mytops.getJSONObject(finalJ);
                        if (sobj.getString("id").equals(currentQuestion)) {
                            if (i < tops.length()) {
                                final int nfianlj = i + 1;
                                final JSONObject nsobj = mytops.getJSONObject(nfianlj);
                                TextView btn = (TextView)bot.findViewWithTag(currentQuestion);
                                if(!mySession.getAnsweredQuestion(currentQuestion).equals(""))
                                {
                                    if(mySession.getAnsweredQuestion(currentQuestion).equals(mytops.getJSONObject(Integer.parseInt(btn.getText().toString()) - 1).getString("qans")))
                                    {
                                        btn.setBackgroundResource(R.drawable.greencircle);
                                    }
                                    else {
                                        btn.setBackgroundResource(R.drawable.redcircle);
                                    }
                                }
                                else {
                                    btn.setBackgroundResource(R.drawable.redcircle);
                                }


                                String userAgent = Util.getUserAgent(videosolution.this, "Examplanet");
                                DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(videosolution.this,userAgent);
                                Uri uriOfContentUrl = Uri.parse(new constants().getAddress()+"/global/tsol/"+nsobj.getString("un")+"."+nsobj.getString("ext"));
                                MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);

                                player.prepare(mediaSource);
                                player.setPlayWhenReady(true);

                                currentQuestion = nsobj.getString("id");
                                TextView btn1 = (TextView)bot.findViewWithTag(currentQuestion);
                                btn1.setBackgroundResource(R.drawable.bluecircle);
                                qlist.scrollTo((int)btn1.getX(),0);


                            } else {
                                TextView btn = (TextView)bot.findViewWithTag(currentQuestion);
                                if(!mySession.getAnsweredQuestion(currentQuestion).equals(""))
                                {
                                    if(mySession.getAnsweredQuestion(currentQuestion).equals(mytops.getJSONObject(Integer.parseInt(btn.getText().toString()) - 1).getString("qans")))
                                    {
                                        btn.setBackgroundResource(R.drawable.greencircle);
                                    }
                                    else {
                                        btn.setBackgroundResource(R.drawable.redcircle);
                                    }
                                }
                                else {
                                    btn.setBackgroundResource(R.drawable.redcircle);
                                }


                                String userAgent = Util.getUserAgent(videosolution.this, "Examplanet");
                                DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(videosolution.this,userAgent);
                                Uri uriOfContentUrl = Uri.parse(new constants().getAddress()+"/global/tsol/"+sobj.getString("un")+"."+sobj.getString("ext"));
                                MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);

                                player.prepare(mediaSource);
                                player.setPlayWhenReady(true);

                                currentQuestion = sobj.getString("id");
                                TextView btn1 = (TextView)bot.findViewWithTag(currentQuestion);
                                btn1.setBackgroundResource(R.drawable.bluecircle);
                                qlist.scrollTo((int)btn1.getX(),0);

                            }

                            Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrate.vibrate(100);




                            break;
                        }
                    } catch (JSONException e) {

                    }
                }


            }
        });


        Intent intent = getIntent();
        tops = intent.getStringExtra("tops");
        sname = intent.getStringExtra("sname");
        type = intent.getStringExtra("type");
        mytops = null;
        tv26.setText(sname);

        fullscreenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleExoPlayerView.switchTargetView(player, dvideo, fvideo);
                fvideo.setVisibility(View.VISIBLE);
                maincont.setVisibility(View.INVISIBLE);
                back2.setVisibility(View.VISIBLE);
                full = true;

                Toast.makeText(videosolution.this, "Press the back button to exit fullscreen mode !", Toast.LENGTH_LONG).show();
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleExoPlayerView.switchTargetView(player, fvideo, dvideo);
                fvideo.setVisibility(View.INVISIBLE);
                maincont.setVisibility(View.VISIBLE);
                back2.setVisibility(View.INVISIBLE);
                full = false;
            }
        });
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                finish();
            }
        });



        //get data needed
        try {

            //set mytops
            if(type.equals("allq"))
            {
                mytops = new JSONArray(tops);
            }
            else
            {
                JSONArray ttops = new JSONArray(tops);
                mytops = new JSONArray(tops);
                for (int i = 0; i < ttops.length(); i++) {
                    JSONObject tobj = ttops.getJSONObject(i);
                      if(!mySession.getAnsweredQuestion(tobj.getString("id")).equals(""))
                        {

                            if(tobj.getString("qans").toUpperCase().equals(mySession.getAnsweredQuestion(tobj.getString("id"))))
                            {
                                mytops.remove(i);
                            }
                        }


                }

            }

            if (mytops.length() > 0) {


                try {
//.replace("width", "style='width:100% !important;' width")
                    Typeface type = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");

                    for (int i = 0; i < mytops.length(); i++) {
                        final int finalJ = i;
                        final JSONObject sobj = mytops.getJSONObject(finalJ);
                        if (i == 0) {
                            currentQuestion = sobj.getString("id");
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMarginStart(dpToPx(45, videosolution.this));
                            TextView btn = new TextView(this);
                            btn.setLayoutParams(params);
                            btn.setTypeface(type);
                            btn.setText((i+1)+"");
                            btn.setTag(sobj.getString("id"));
                            btn.setPadding(0, 12, 0,0);
                            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            btn.setTextColor(Color.parseColor("#FFFFFF"));
                            btn.setBackgroundResource(R.drawable.bluecircle);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setCurrentQuestion(v.getTag().toString(), v);
                                }
                            });
                            noptions.addView(btn);
                            player = new SimpleExoPlayer.Builder(this).build();

                            String userAgent = Util.getUserAgent(this, "Examplanet");

                            DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this,userAgent);
                            Uri uriOfContentUrl = Uri.parse(new constants().getAddress()+"/global/tsol/"+sobj.getString("un")+"."+sobj.getString("ext"));
                            MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);

                            player.prepare(mediaSource);
                            player.setPlayWhenReady(false);
                            player.addListener(new Player.EventListener() {
                                @Override
                                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                                    if (playbackState == ExoPlayer.STATE_ENDED){
                                        //player back ended
                                        player.seekTo(0);
                                        player.setPlayWhenReady(false);
                                    }
                                }
                            });
                            dvideo.setPlayer(player);

                        }
                        else
                        {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMarginStart(dpToPx(45, videosolution.this));
                            TextView btn = new TextView(this);
                            btn.setLayoutParams(params);
                            btn.setTypeface(type);
                            btn.setText((i+1)+"");
                            btn.setTag(sobj.getString("id"));
                            btn.setPadding(0, dpToPx(12, videosolution.this), 0,0);
                            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            btn.setPadding(0, 12, 0,0);

                            if(this.type.equals("allq"))
                            {
                                if(!mySession.getAnsweredQuestion(sobj.getString("id")).equals(""))
                                {
                                    if(mySession.getAnsweredQuestion(sobj.getString("id")).equals(sobj.getString("qans")))
                                    {
                                        //correct
                                        btn.setTextColor(Color.parseColor("#ffffff"));
                                        btn.setBackgroundResource(R.drawable.greencircle);
                                    }
                                    else {
                                        //wrong
                                        btn.setTextColor(Color.parseColor("#ffffff"));
                                        btn.setBackgroundResource(R.drawable.redcircle);
                                    }
                                }
                                else
                                {
                                    //wrong
                                    btn.setTextColor(Color.parseColor("#ffffff"));
                                    btn.setBackgroundResource(R.drawable.redcircle);
                                }
                            }
                            else
                            {
                                //wrong
                                btn.setTextColor(Color.parseColor("#ffffff"));
                                btn.setBackgroundResource(R.drawable.redcircle);
                            }

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setCurrentQuestion(v.getTag().toString(), v);
                                }
                            });

                            noptions.addView(btn);
                        }




                    }


                } catch (Throwable t) {


                }


            }
            else
            {
                new LovelyInfoDialog(videosolution.this).setCancelable(false)
                        .setTitle("Ooops, Can't Load Questions")
                        .setMessage("Seems like you have a poor network connection !!!, try again")
                        .show();
            }


        } catch (JSONException e) {
            new LovelyInfoDialog(videosolution.this).setCancelable(false)
                    .setTitle("Ooops, Can't Load Questions")
                    .setMessage("Seems like you have a poor network connection !!!, try again")
                    .show();
        }


    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private void setCurrentQuestion(String tagid, View qbutton) {
        for (int i = 0; i < mytops.length(); i++) {
            final int finalJ = i;
            try {

                final JSONObject sobj = mytops.getJSONObject(finalJ);
                if (sobj.getString("id").equals(tagid)) {

                    TextView btn = (TextView)bot.findViewWithTag(currentQuestion);
                    if(!mySession.getAnsweredQuestion(currentQuestion).equals(""))
                    {


                        if(mySession.getAnsweredQuestion(currentQuestion).equals(mytops.getJSONObject(Integer.parseInt(btn.getText().toString()) - 1).getString("qans")))
                        {
                            btn.setBackgroundResource(R.drawable.greencircle);
                        }
                        else {
                            btn.setBackgroundResource(R.drawable.redcircle);
                        }
                    }
                    else {
                        btn.setBackgroundResource(R.drawable.redcircle);
                    }


                    String userAgent = Util.getUserAgent(this, "Examplanet");
                    DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this,userAgent);
                    Uri uriOfContentUrl = Uri.parse(new constants().getAddress()+"/global/tsol/"+sobj.getString("un")+"."+sobj.getString("ext"));
                    MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);

                    player.prepare(mediaSource);
                    player.setPlayWhenReady(true);

                    currentQuestion = sobj.getString("id");
                    TextView btn1 = (TextView)bot.findViewWithTag(currentQuestion);
                    btn1.setBackgroundResource(R.drawable.bluecircle);
                    Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrate.vibrate(100);

                    break;
                }
            } catch (JSONException e) {
                setCurrentQuestion(tagid, qbutton);
            }
        }

    }



    @Override
    public void onBackPressed() {

        if(full == true)
        {
            SimpleExoPlayerView.switchTargetView(player, fvideo, dvideo);
            fvideo.setVisibility(View.INVISIBLE);
            maincont.setVisibility(View.VISIBLE);
            back2.setVisibility(View.INVISIBLE);
            full = false;
            Toast.makeText(videosolution.this, "Fullscreen mode exited !", Toast.LENGTH_LONG).show();
        }
        else {
            player.release();
            finish();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        player.release();
        finish();
    }
}
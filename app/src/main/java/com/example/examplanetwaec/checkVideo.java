package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;

public class checkVideo extends AppCompatActivity {
    private session mySession;
    String vlink, sname;
    JSONArray mytops;
    TextView tv26;
    PlayerView dvideo, fvideo;
    private SimpleExoPlayer player;
    ImageView fullscreenbutton, back1;
    Boolean full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_video);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(checkVideo.this, splashActivity.class);
            startActivity(intent);

        }
        full = false;


        dvideo = findViewById(R.id.dvideo);
        fvideo = findViewById(R.id.fvideo);
        tv26 = findViewById(R.id.textView26);
        fullscreenbutton = findViewById(R.id.fullscreen);
        back1 = findViewById(R.id.imageView15);

        Intent intent = getIntent();
        vlink = intent.getStringExtra("vlink");
        sname = intent.getStringExtra("sname");

        tv26.setText(sname);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                finish();
            }
        });

        fullscreenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleExoPlayerView.switchTargetView(player, dvideo, fvideo);
                fvideo.setVisibility(View.VISIBLE);
                dvideo.setVisibility(View.INVISIBLE);
                full = true;
                Toast.makeText(checkVideo.this, "Press the back button to exit fullscreen mode !", Toast.LENGTH_LONG).show();
            }
        });


        //load video

        player = new SimpleExoPlayer.Builder(this).build();

        String userAgent = Util.getUserAgent(this, "Examplanet");

        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this,userAgent);
        Uri uriOfContentUrl = Uri.parse(vlink);
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
        player.setPlayWhenReady(true);
        dvideo.setPlayer(player);



    }

    @Override
    public void onBackPressed() {

        if(full == true)
        {
            SimpleExoPlayerView.switchTargetView(player, fvideo, dvideo);
            fvideo.setVisibility(View.INVISIBLE);
            dvideo.setVisibility(View.VISIBLE);

            full = false;
            Toast.makeText(checkVideo.this, "Fullscreen mode exited !", Toast.LENGTH_LONG).show();
        }
        else {
            player.release();
            finish();
        }

    }
}
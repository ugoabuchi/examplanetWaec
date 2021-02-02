package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AlarmPageActivity extends AppCompatActivity {
    private session mySession;
    MediaPlayer mp;
    String subject = null, time = null;
    int id = 0;
    TextView tview = null, tcont = null;
    Button ok = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_page);
        //get broadcast data
        Intent bdata = getIntent();
        subject = bdata.getStringExtra("subject");
        time = bdata.getStringExtra("time");
        id = Integer.parseInt(bdata.getStringExtra("id"));

        tview = findViewById(R.id.textView35);
        tcont = findViewById(R.id.textView36);
        ok = findViewById(R.id.button17);

        //partial alram beep notification
        mp= MediaPlayer.create(AlarmPageActivity.this, R.raw.alarm   );
        mp.start();

        //start alarm intent
        final Intent alarmIntent = new Intent(AlarmPageActivity.this, MyBroadcastReceiver.class);
        alarmIntent.putExtra("id", id);
        final AlarmManager alarmManager = (AlarmManager)AlarmPageActivity.this.getSystemService(ALARM_SERVICE);


        //check if user is logged in
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            //kill alarm activity
            PendingIntent sender = PendingIntent.getBroadcast(AlarmPageActivity.this, id, alarmIntent, 0);
            alarmManager.cancel(sender);
            sender.cancel();
            mp.stop();
            Intent intent = new Intent(AlarmPageActivity.this, splashActivity.class);
            startActivity(intent);

        }

        tview.setText(time);
        tcont.setText("It's "+time+"\nTime to practice your\n"+subject);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent dashboard = new Intent(AlarmPageActivity.this, HomePageActivity.class);
                startActivity(dashboard);
            }
        });

    }
}
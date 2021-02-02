package com.example.examplanetwaec;

/****************************************************************************************
 This project app was proudly developed by the app_dev team and several educational professionals of
 MAckIV Consult which is a parent company in which Examplanet Services fall under, and all components relating to this
 app should not be reused or duplicated by any external bodies without the prior approval of MackIV Consult.
 Copyright 2020.
 Signed: Management
 Director: Tawede Kehinde
 General Supervisor: Ajayi BabaTosin
 Project Supervisor: Tosere Ojeme (Head of IT)
 Lead Software Developer 1: Mathew Fortune
 Lead Software Developer 2: Oladapo Yusuf
 Blog Developer and Administrator: Oke OluwaTimileyin
 UI/UX: Olawale Damilola
 ***************************************************************************************/
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
public class MyBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

       /* //get subject title, time set, daylight set and say set
        String subject = intent.getStringExtra("subject");
        String time = intent.getStringExtra("time");
        String alarmId = intent.getStringExtra("id");
        //send broadcast data to Alarm screen
        Intent togo = new Intent(context, AlarmPageActivity.class);
        togo.putExtra("subject", subject);
        togo.putExtra("time", time);
        togo.putExtra("id", alarmId);

        //set runnable flag for alram screen
        togo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(togo);
        //start alram screen if(MyReminder.mainActivityIsOpen() == true)
       // Toast.makeText(context, "Alarm recieved", Toast.LENGTH_LONG);

        *//*final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.myalarm);
        dialog.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();*/

        MediaPlayer mp = null;
        mp= MediaPlayer.create(context, R.raw.alarm   );
        mp.start();

    }




}

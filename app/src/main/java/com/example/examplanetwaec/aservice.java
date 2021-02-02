package com.example.examplanetwaec;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

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
public class aservice extends Service {
    private session mySession;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mySession = new session(getApplicationContext());
        //TODO do something useful
        
        Intent dialogIntenta = new Intent(this, HomePageActivity.class);
        dialogIntenta.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(dialogIntenta);

        Intent dialogIntent = new Intent(this, MyReminder.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dialogIntenta.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(dialogIntent);
        //check if alarm exist before popping reminder page
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        if(mySession.alarmsExist() == true)
        {
            Intent dialogIntenta = new Intent(this, HomePageActivity.class);
            dialogIntenta.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(dialogIntenta);

            Intent dialogIntent = new Intent(this, MyReminder.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            dialogIntenta.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(dialogIntent);
            Toast.makeText(this, "App is in Reminder mode, and continously runs in background", Toast.LENGTH_LONG).show();
        }



    }

}
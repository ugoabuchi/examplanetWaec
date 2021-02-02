package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyReminder extends AppCompatActivity {
    private session mySession;
    private LinearLayout contain;
    private ImageView switcher, templatebtn;
    private static boolean mainActivityIsOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reminder);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(MyReminder.this, splashActivity.class);
            startActivity(intent);
        }

        mainActivityIsOpen = true;
        contain = findViewById(R.id.container);
        templatebtn = findViewById(R.id.next);

        //sample reminder
        if(mySession.alarmsExist() == true)
        {
            //create the Constraint and set attributes
            RelativeLayout reminderBox = new RelativeLayout(MyReminder.this);
            RelativeLayout.LayoutParams reminderBoxParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            reminderBox.setPadding(dpToPx(10, MyReminder.this), 10, dpToPx(10, MyReminder.this), dpToPx(10, MyReminder.this));

            reminderBox.setLayoutParams(reminderBoxParams);
            //iterate through alarms, and remove expired alarms
            List<String> AlarmID= mySession.getAlarmsID();
            List<String> AlarmTime= mySession.getAlarmstime();
            List<String> AlarmSubject= mySession.getAlarmsubject();
            List<String> AlarmStime= mySession.getAlarmstimes();
            List<String> AlarmMyday= mySession.getAlarmmyday();
            List<String> Alarmmytime= mySession.getAlarmmytime();
            List<String> Alarmcday= mySession.getAlarmcday();
            for (int i=0; i<AlarmID.size(); i++) {
                Log.e("lists "+i, AlarmID.get(i).toString());
                Log.e("lists "+i, AlarmTime.get(i).toString());
                Log.e("lists "+i, AlarmSubject.get(i).toString());
                Log.e("lists "+i, AlarmStime.get(i).toString());
                Log.e("lists "+i, AlarmMyday.get(i).toString());
                Log.e("lists "+i, Alarmmytime.get(i).toString());
                Log.e("lists "+i, Alarmcday.get(i).toString());

                //contain.addView(reminderSkeleton(AlarmID.get(i), AlarmTime.get(i), AlarmSubject.get(i), AlarmStime.get(i), AlarmMyday.get(i), Alarmmytime.get(i), Alarmcday.get(i)));
            }
        }
        else {
            contain.addView(reminderSkeleton());
        }


        //testing
        templatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contain.addView(reminderSkeleton());
            }
        });


    }

    public static boolean mainActivityIsOpen() {
        return mainActivityIsOpen;
    }

    //create reminder skeleton
    public RelativeLayout reminderSkeleton(String aid, final String time, String subj, String stime, String myday, String mytime, final String cday)
    {


        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        final String generatedString = new String(array, Charset.forName("UTF-8"));
        final boolean isSet[] = { false};
        final int id = Integer.parseInt(aid);
        final Intent alarmIntent = new Intent(MyReminder.this, MyBroadcastReceiver.class);
        alarmIntent.putExtra("id", id+"");
        final AlarmManager alarmManager = (AlarmManager)MyReminder.this.getSystemService(ALARM_SERVICE);

        //create the Constraint and set attributes
        RelativeLayout reminderBox = new RelativeLayout(MyReminder.this);
        RelativeLayout.LayoutParams reminderBoxParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        reminderBox.setPadding(dpToPx(10, MyReminder.this), 10, dpToPx(10, MyReminder.this), dpToPx(10, MyReminder.this));

        reminderBox.setLayoutParams(reminderBoxParams);

        //create Spinner
        Spinner days = new Spinner(MyReminder.this);
        RelativeLayout.LayoutParams daysparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        daysparams.setMargins(0,0,0,0);
        daysparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        days.setDropDownWidth(dpToPx(RelativeLayout.LayoutParams.MATCH_PARENT, MyReminder.this));
        days.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, R.layout.spinnerxml);
        days.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(myday);
        days.setSelection(spinnerPosition);
        days.setId(View.generateViewId());
        days.setTag("days"+generatedString);
        days.setLayoutParams(daysparams);
        reminderBox.addView(days);




        //create Reminder card view
        CardView reminderCard = new CardView(MyReminder.this);
        RelativeLayout.LayoutParams rcParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rcParams.setMargins(dpToPx(10, MyReminder.this),0,dpToPx(10, MyReminder.this),dpToPx(10, MyReminder.this));
        rcParams.addRule(RelativeLayout.BELOW, days.getId());
        reminderCard.setRadius(dpToPx(20, MyReminder.this));
        reminderCard.setElevation(dpToPx(2, MyReminder.this));
        reminderCard.setLayoutParams(rcParams);
        //add card relative child
        RelativeLayout rcardcont = new RelativeLayout(MyReminder.this);
        RelativeLayout.LayoutParams rccparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rcardcont.setPadding(dpToPx(10, MyReminder.this), 10, dpToPx(10, MyReminder.this), dpToPx(10, MyReminder.this));
        //rcardcont.setGravity(Gravity.CENTER_HORIZONTAL);
        rcardcont.setLayoutParams(rccparams);
        //add rcardcont data


        //create Spinner
        Spinner times = new Spinner(MyReminder.this);
        RelativeLayout.LayoutParams timesparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        timesparams.setMargins(dpToPx(0, MyReminder.this),dpToPx(15, MyReminder.this),0,dpToPx(15, MyReminder.this));
        timesparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        times.setDropDownWidth(dpToPx(RelativeLayout.LayoutParams.MATCH_PARENT, MyReminder.this));
        ArrayAdapter<CharSequence> adaptertimes = ArrayAdapter.createFromResource(this, R.array.times, R.layout.spinnerxml);
        times.setAdapter(adaptertimes);
        times.setId(View.generateViewId());
        int spinnerPosition1 = adaptertimes.getPosition(mytime);
        times.setSelection(spinnerPosition1);
        times.setLayoutParams(timesparams);
        times.setTag("times"+generatedString);
        rcardcont.addView(times);

        Spinner subjects = new Spinner(MyReminder.this);
        RelativeLayout.LayoutParams subparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        subparams.setMargins(0,0,0,0);
        subparams.addRule(RelativeLayout.BELOW, times.getId());
        subparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        subjects.setDropDownWidth(dpToPx(RelativeLayout.LayoutParams.MATCH_PARENT, MyReminder.this));

        ArrayAdapter<CharSequence> subadapter = ArrayAdapter.createFromResource(this, R.array.subjects, R.layout.spinnerxml);
        subjects.setAdapter(subadapter);
        subjects.setId(View.generateViewId());
        subjects.setLayoutParams(subparams);
        int spinnerPosition2 = subadapter.getPosition(subj);
        subjects.setSelection(spinnerPosition2);
        subjects.setTag("subjects"+generatedString);
        rcardcont.addView(subjects);

        ImageView imgswitch = new ImageView(MyReminder.this);
        RelativeLayout.LayoutParams imgswitchparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imgswitchparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        imgswitch.setImageResource(R.drawable.ic_toggle_off_btn);
        imgswitch.setId(View.generateViewId());
        imgswitch.setLayoutParams(imgswitchparams);
        rcardcont.addView(imgswitch);


        ImageView imgdel = new ImageView(MyReminder.this);
        RelativeLayout.LayoutParams imgdelparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imgdelparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        imgdelparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        imgdel.setImageResource(R.drawable.ic_delete);
        imgdel.setId(View.generateViewId());
        imgdel.setTag("delete"+generatedString);
        imgdel.setLayoutParams(imgdelparams);
        rcardcont.addView(imgdel);


        reminderCard.addView(rcardcont);
        reminderBox.addView(reminderCard);


        //switcher btn action
        imgswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout maincont = (RelativeLayout) ((((ImageView)v).getParent()).getParent()).getParent();

                if(isSet[0] == true)
                {
                    isSet[0] = false;
                    ((ImageView)v).setImageResource(R.drawable.ic_toggle_off_btn);
                    ((Spinner)maincont.findViewWithTag("days"+generatedString)).setEnabled(true);
                    ((Spinner)maincont.findViewWithTag("times"+generatedString)).setEnabled(true);
                    ((Spinner)maincont.findViewWithTag("subjects"+generatedString)).setEnabled(true);
                    ((ImageView)maincont.findViewWithTag("delete"+generatedString)).setEnabled(true);
                    //to delete main cont view call maincont.setVisibility(View.GONE);
                    PendingIntent sender = PendingIntent.getBroadcast(MyReminder.this, id, alarmIntent, 0);
                    alarmManager.cancel(sender);
                    sender.cancel();

                   Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrate.vibrate(2000);



                    //remove alarm to shared preference
                    mySession.deleteAlarm(id+"");

                }
                else {
                    isSet[0] = true;

                    ((ImageView)v).setImageResource(R.drawable.ic_toggle_on_btn);
                    ((Spinner)maincont.findViewWithTag("days"+generatedString)).setEnabled(false);
                    ((Spinner)maincont.findViewWithTag("times"+generatedString)).setEnabled(false);
                    ((Spinner)maincont.findViewWithTag("subjects"+generatedString)).setEnabled(false);
                    ((ImageView)maincont.findViewWithTag("delete"+generatedString)).setEnabled(false);


                    //initialize alarm values
                    int day = 0;
                    int hour = Integer.parseInt(((Spinner)maincont.findViewWithTag("times"+generatedString)).getSelectedItem().toString().split(":")[0]);
                    String dlight = ((Spinner)maincont.findViewWithTag("times"+generatedString)).getSelectedItem().toString().split(" ")[1];
                    String subject = ((Spinner)maincont.findViewWithTag("subjects"+generatedString)).getSelectedItem().toString();
                    //Toast.makeText(MyReminder.this, ((Spinner)maincont.findViewWithTag("times"+generatedString)).getSelectedItem().toString().split(":")[1], Toast.LENGTH_LONG).show();

                    if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Sunday"))
                        day = 1;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Monday"))
                        day = 2;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Tuesday"))
                        day = 3;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Wednesday"))
                        day = 4;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Thursday"))
                        day = 5;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Friday"))
                        day = 6;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Saturday"))
                        day = 7;
                    else
                        day = 8;


                    //check if alarm is past or present
                    int chosenDay = Integer.parseInt(cday);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

                    if(chosenDay == currentDay)
                    {
                        alarmIntent.putExtra("subject", subject);
                        alarmIntent.putExtra("time", hour+":00 "+dlight);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyReminder.this, id, alarmIntent, 0);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Long.parseLong(time),AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                    }



                    Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrate.vibrate(2000);



                }
            }
        });

        //delte btn action
        imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout maincont = (RelativeLayout) ((((ImageView)v).getParent()).getParent()).getParent();
                maincont.setVisibility(View.GONE);
                mySession.deleteAlarm(id+"");
            }
        });

        imgswitch.performClick();

        //return reminder skeleton
        return reminderBox;
    }




    //create reminder skeleton
    public RelativeLayout reminderSkeleton()
    {


        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        final String generatedString = new String(array, Charset.forName("UTF-8"));
        final boolean isSet[] = { false};
        final int id = new Random().nextInt(3);
        final Intent alarmIntent = new Intent(MyReminder.this, MyBroadcastReceiver.class);
        alarmIntent.putExtra("id", id+"");
        final AlarmManager alarmManager = (AlarmManager)MyReminder.this.getSystemService(ALARM_SERVICE);

        //create the Constraint and set attributes
        RelativeLayout reminderBox = new RelativeLayout(MyReminder.this);
        RelativeLayout.LayoutParams reminderBoxParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        reminderBox.setPadding(dpToPx(10, MyReminder.this), 10, dpToPx(10, MyReminder.this), dpToPx(10, MyReminder.this));

        reminderBox.setLayoutParams(reminderBoxParams);

        //create Spinner
        Spinner days = new Spinner(MyReminder.this);
        RelativeLayout.LayoutParams daysparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        daysparams.setMargins(0,0,0,0);
        daysparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        days.setDropDownWidth(dpToPx(RelativeLayout.LayoutParams.MATCH_PARENT, MyReminder.this));
        days.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, R.layout.spinnerxml);
        days.setAdapter(adapter);
        days.setId(View.generateViewId());
        days.setTag("days"+generatedString);
        days.setLayoutParams(daysparams);
        reminderBox.addView(days);




        //create Reminder card view
        CardView reminderCard = new CardView(MyReminder.this);
        RelativeLayout.LayoutParams rcParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rcParams.setMargins(dpToPx(10, MyReminder.this),0,dpToPx(10, MyReminder.this),dpToPx(10, MyReminder.this));
        rcParams.addRule(RelativeLayout.BELOW, days.getId());
        reminderCard.setRadius(dpToPx(20, MyReminder.this));
        reminderCard.setElevation(dpToPx(2, MyReminder.this));
        reminderCard.setLayoutParams(rcParams);
                            //add card relative child
                            RelativeLayout rcardcont = new RelativeLayout(MyReminder.this);
                            RelativeLayout.LayoutParams rccparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            rcardcont.setPadding(dpToPx(10, MyReminder.this), 10, dpToPx(10, MyReminder.this), dpToPx(10, MyReminder.this));
                            //rcardcont.setGravity(Gravity.CENTER_HORIZONTAL);
                            rcardcont.setLayoutParams(rccparams);
                                        //add rcardcont data


                                        //create Spinner
                                        Spinner times = new Spinner(MyReminder.this);
                                        RelativeLayout.LayoutParams timesparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        timesparams.setMargins(dpToPx(0, MyReminder.this),dpToPx(15, MyReminder.this),0,dpToPx(15, MyReminder.this));
                                        timesparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

                                        times.setDropDownWidth(dpToPx(RelativeLayout.LayoutParams.MATCH_PARENT, MyReminder.this));
                                        ArrayAdapter<CharSequence> adaptertimes = ArrayAdapter.createFromResource(this, R.array.times, R.layout.spinnerxml);
                                        times.setAdapter(adaptertimes);
                                        times.setId(View.generateViewId());
                                        times.setLayoutParams(timesparams);
                                        times.setTag("times"+generatedString);
                                        rcardcont.addView(times);

                                        Spinner subjects = new Spinner(MyReminder.this);
                                        RelativeLayout.LayoutParams subparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                                        subparams.setMargins(0,0,0,0);
                                        subparams.addRule(RelativeLayout.BELOW, times.getId());
                                        subparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                                        subjects.setDropDownWidth(dpToPx(RelativeLayout.LayoutParams.MATCH_PARENT, MyReminder.this));

                                        ArrayAdapter<CharSequence> subadapter = ArrayAdapter.createFromResource(this, R.array.subjects, R.layout.spinnerxml);
                                        subjects.setAdapter(subadapter);
                                        subjects.setId(View.generateViewId());
                                        subjects.setLayoutParams(subparams);
                                        subjects.setTag("subjects"+generatedString);
                                        rcardcont.addView(subjects);

                                        ImageView imgswitch = new ImageView(MyReminder.this);
                                        RelativeLayout.LayoutParams imgswitchparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        imgswitchparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                                        imgswitch.setImageResource(R.drawable.ic_toggle_off_btn);
                                        imgswitch.setId(View.generateViewId());
                                        imgswitch.setLayoutParams(imgswitchparams);
                                        rcardcont.addView(imgswitch);


                                        ImageView imgdel = new ImageView(MyReminder.this);
                                        RelativeLayout.LayoutParams imgdelparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        imgdelparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                                        imgdelparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                                        imgdel.setImageResource(R.drawable.ic_delete);
                                        imgdel.setId(View.generateViewId());
                                        imgdel.setTag("delete"+generatedString);
                                        imgdel.setLayoutParams(imgdelparams);
                                        rcardcont.addView(imgdel);


        reminderCard.addView(rcardcont);
        reminderBox.addView(reminderCard);


        //switcher btn action
        imgswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout maincont = (RelativeLayout) ((((ImageView)v).getParent()).getParent()).getParent();

                if(isSet[0] == true)
                {
                    isSet[0] = false;
                    ((ImageView)v).setImageResource(R.drawable.ic_toggle_off_btn);
                    ((Spinner)maincont.findViewWithTag("days"+generatedString)).setEnabled(true);
                    ((Spinner)maincont.findViewWithTag("times"+generatedString)).setEnabled(true);
                    ((Spinner)maincont.findViewWithTag("subjects"+generatedString)).setEnabled(true);
                    ((ImageView)maincont.findViewWithTag("delete"+generatedString)).setEnabled(true);
                    //to delete main cont view call maincont.setVisibility(View.GONE);
                    PendingIntent sender = PendingIntent.getBroadcast(MyReminder.this, id, alarmIntent, 0);
                    alarmManager.cancel(sender);
                    sender.cancel();

                    Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrate.vibrate(2000);

                    //remove alarm to shared preference
                    mySession.deleteAlarm(id+"");

                }
                else {
                    isSet[0] = true;

                    ((ImageView)v).setImageResource(R.drawable.ic_toggle_on_btn);
                    ((Spinner)maincont.findViewWithTag("days"+generatedString)).setEnabled(false);
                    ((Spinner)maincont.findViewWithTag("times"+generatedString)).setEnabled(false);
                    ((Spinner)maincont.findViewWithTag("subjects"+generatedString)).setEnabled(false);
                    ((ImageView)maincont.findViewWithTag("delete"+generatedString)).setEnabled(false);


                    //initialize alarm values
                    int day = 0;
                    int hour = Integer.parseInt(((Spinner)maincont.findViewWithTag("times"+generatedString)).getSelectedItem().toString().split(":")[0]);
                    String dlight = ((Spinner)maincont.findViewWithTag("times"+generatedString)).getSelectedItem().toString().split(" ")[1];
                    String subject = ((Spinner)maincont.findViewWithTag("subjects"+generatedString)).getSelectedItem().toString();
                    //Toast.makeText(MyReminder.this, ((Spinner)maincont.findViewWithTag("times"+generatedString)).getSelectedItem().toString().split(":")[1], Toast.LENGTH_LONG).show();

                    if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Sunday"))
                        day = 1;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Monday"))
                        day = 2;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Tuesday"))
                        day = 3;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Wednesday"))
                        day = 4;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Thursday"))
                        day = 5;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Friday"))
                        day = 6;
                    else if(((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString().equals("Saturday"))
                        day = 7;
                    else
                        day = 8;

                    //save to preference




                    //text alarm
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.DAY_OF_WEEK,day);
                    calendar.set(Calendar.HOUR,hour);
                    calendar.set(Calendar.MINUTE, 00);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    if(dlight.equals("AM"))
                        calendar.set(Calendar.AM_PM, Calendar.AM);
                    else
                        calendar.set(Calendar.AM_PM, Calendar.PM);

                    //check if alarm is past or present
                    int chosenDay = calendar.get(Calendar.DAY_OF_WEEK);
                    Calendar calendarv = Calendar.getInstance();
                    calendarv.setTimeInMillis(System.currentTimeMillis());
                    int currentDay = calendarv.get(Calendar.DAY_OF_WEEK);

                    if(chosenDay == currentDay)
                    {
                        alarmIntent.putExtra("subject", subject);
                        alarmIntent.putExtra("time", hour+":00 "+dlight);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyReminder.this, id, alarmIntent, 0);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                    }


                    Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrate.vibrate(2000);

                    //add alarm to shared preference
                    mySession.addAlarm(id+"", calendar.getTimeInMillis(), subject, hour+":00 "+dlight, ((Spinner)maincont.findViewWithTag("days"+generatedString)).getSelectedItem().toString(),((Spinner)maincont.findViewWithTag("times"+generatedString)).getSelectedItem().toString(), calendar.get(Calendar.DAY_OF_WEEK)+"");


                }
            }
        });

        //delte btn action
        imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout maincont = (RelativeLayout) ((((ImageView)v).getParent()).getParent()).getParent();
                mySession.deleteAlarm(id+"");
                maincont.setVisibility(View.GONE);
            }
        });

        //return reminder skeleton
        return reminderBox;
    }





    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyReminder.this, HomePageActivity.class);
        startActivity(intent);
    }
}
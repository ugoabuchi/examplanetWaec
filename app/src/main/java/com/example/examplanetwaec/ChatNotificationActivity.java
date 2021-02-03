package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ChatNotificationActivity extends AppCompatActivity {
    private session mySession;
    public LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_notification);

        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(ChatNotificationActivity.this, splashActivity.class);
            startActivity(intent);
        }
        container = findViewById(R.id.lcontainer);

        //update notification every 5 minutes
        Timer _timer = new Timer();

        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // use runOnUiThread(Runnable action)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        container.removeAllViews();


                        if(mySession.notificationExist()==true)
                        {

                            List<String> NID= mySession.getNID();
                            List<String> Time= mySession.getNTime();
                            List<String> Message= mySession.getNmessage();
                            for (int i=0; i<NID.size(); i++) {

                                Long timea = Long.parseLong(Time.get(i).toString());
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                Long currentdate = calendar.getTimeInMillis();


                                int days = Integer.parseInt(TimeUnit.MILLISECONDS.toDays((currentdate - timea))+"");
                                int hours = Integer.parseInt(TimeUnit.MILLISECONDS.toHours((currentdate - timea))+"");
                                int minutes = Integer.parseInt(TimeUnit.MILLISECONDS.toMinutes((currentdate - timea))+"");
                                int seconds = Integer.parseInt(TimeUnit.MILLISECONDS.toSeconds((currentdate - timea))+"");
                                int weeks = days / 7;
                                int months = weeks / 4;
                                int years = months / 12;

                                if(seconds < 60)
                                {
                                    if(seconds < 2)
                                        container.addView(notificationskeleton(NID.get(i).toString(), seconds+" second ago", Message.get(i).toString()));
                                    else
                                        container.addView(notificationskeleton(NID.get(i).toString(), seconds+" seconds ago", Message.get(i).toString()));
                                }
                                else if(minutes < 60)
                                {
                                    if(minutes < 2)
                                        container.addView(notificationskeleton(NID.get(i).toString(), minutes+" minute ago", Message.get(i).toString()));
                                    else
                                        container.addView(notificationskeleton(NID.get(i).toString(), minutes+" minutes ago", Message.get(i).toString()));
                                }
                                else if(hours < 12)
                                {
                                    if(hours < 2)
                                        container.addView(notificationskeleton(NID.get(i).toString(), hours+" hour ago", Message.get(i).toString()));
                                    else
                                        container.addView(notificationskeleton(NID.get(i).toString(), hours+" hours ago", Message.get(i).toString()));
                                }
                                else if(days < 7)
                                {
                                    if(days < 2)
                                        container.addView(notificationskeleton(NID.get(i).toString(), days+" day ago", Message.get(i).toString()));
                                    else
                                        container.addView(notificationskeleton(NID.get(i).toString(), days+" days ago", Message.get(i).toString()));
                                }
                                else if(weeks < 4)
                                {
                                    if(weeks < 2)
                                        container.addView(notificationskeleton(NID.get(i).toString(), weeks+" week ago", Message.get(i).toString()));
                                    else
                                        container.addView(notificationskeleton(NID.get(i).toString(), weeks+" weeks ago", Message.get(i).toString()));
                                }
                                else if(months < 12)
                                {
                                    if(months < 2)
                                        container.addView(notificationskeleton(NID.get(i).toString(), months+" month ago", Message.get(i).toString()));
                                    else
                                        container.addView(notificationskeleton(NID.get(i).toString(), months+" months ago", Message.get(i).toString()));
                                }
                                else
                                {
                                    if(years < 2)
                                        container.addView(notificationskeleton(NID.get(i).toString(), years+" year ago", Message.get(i).toString()));
                                    else
                                        container.addView(notificationskeleton(NID.get(i).toString(), years+" years ago", Message.get(i).toString()));
                                }


                            }

                        }
                        else
                        {
                            //show a refresh button here
                        }









                    }
                });
            }
        }, 30000);







        if(mySession.notificationExist()==true)
        {

            List<String> NID= mySession.getNID();
            List<String> Time= mySession.getNTime();
            List<String> Message= mySession.getNmessage();
            for (int i=0; i<NID.size(); i++) {

                Long timea = Long.parseLong(Time.get(i).toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                Long currentdate = calendar.getTimeInMillis();


                int days = Integer.parseInt(TimeUnit.MILLISECONDS.toDays((currentdate - timea))+"");
                int hours = Integer.parseInt(TimeUnit.MILLISECONDS.toHours((currentdate - timea))+"");
                int minutes = Integer.parseInt(TimeUnit.MILLISECONDS.toMinutes((currentdate - timea))+"");
                int seconds = Integer.parseInt(TimeUnit.MILLISECONDS.toSeconds((currentdate - timea))+"");
                int weeks = days / 7;
                int months = weeks / 4;
                int years = months / 12;

                if(seconds < 60)
                {
                    if(seconds < 2)
                        container.addView(notificationskeleton(NID.get(i).toString(), seconds+" second ago", Message.get(i).toString()));
                    else
                        container.addView(notificationskeleton(NID.get(i).toString(), seconds+" seconds ago", Message.get(i).toString()));
                }
                else if(minutes < 60)
                {
                    if(minutes < 2)
                        container.addView(notificationskeleton(NID.get(i).toString(), minutes+" minute ago", Message.get(i).toString()));
                    else
                        container.addView(notificationskeleton(NID.get(i).toString(), minutes+" minutes ago", Message.get(i).toString()));
                }
                else if(hours < 12)
                {
                    if(hours < 2)
                        container.addView(notificationskeleton(NID.get(i).toString(), hours+" hour ago", Message.get(i).toString()));
                    else
                        container.addView(notificationskeleton(NID.get(i).toString(), hours+" hours ago", Message.get(i).toString()));
                }
                else if(days < 7)
                {
                    if(days < 2)
                        container.addView(notificationskeleton(NID.get(i).toString(), days+" day ago", Message.get(i).toString()));
                    else
                        container.addView(notificationskeleton(NID.get(i).toString(), days+" days ago", Message.get(i).toString()));
                }
                else if(weeks < 4)
                {
                    if(weeks < 2)
                        container.addView(notificationskeleton(NID.get(i).toString(), weeks+" week ago", Message.get(i).toString()));
                    else
                        container.addView(notificationskeleton(NID.get(i).toString(), weeks+" weeks ago", Message.get(i).toString()));
                }
                else if(months < 12)
                {
                    if(months < 2)
                        container.addView(notificationskeleton(NID.get(i).toString(), months+" month ago", Message.get(i).toString()));
                    else
                        container.addView(notificationskeleton(NID.get(i).toString(), months+" months ago", Message.get(i).toString()));
                }
                else
                {
                    if(years < 2)
                        container.addView(notificationskeleton(NID.get(i).toString(), years+" year ago", Message.get(i).toString()));
                    else
                        container.addView(notificationskeleton(NID.get(i).toString(), years+" years ago", Message.get(i).toString()));
                }


            }

        }
        else
        {
            //show a refresh button here
        }












    }


    public LinearLayout notificationskeleton(String NID, String time, String message)
    {
        LinearLayout icontainer = new LinearLayout(ChatNotificationActivity.this);
        LinearLayout.LayoutParams icontainerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        icontainer.setPadding(dpToPx(10, ChatNotificationActivity.this), dpToPx(10, ChatNotificationActivity.this), dpToPx(10, ChatNotificationActivity.this), dpToPx(10, ChatNotificationActivity.this));
        icontainer.setOrientation(LinearLayout.VERTICAL);
        icontainer.setLayoutParams(icontainerParams);

        TextView times = new TextView(ChatNotificationActivity.this);
        LinearLayout.LayoutParams timeparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        timeparams.setMargins(0,0,dpToPx(10, ChatNotificationActivity.this),0);
        timeparams.gravity = Gravity.RIGHT;
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/mali_medium.ttf");
        times.setTypeface(face);
        times.setTextColor(Color.parseColor("#1008B8"));
        times.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        times.setLayoutParams(timeparams);
        times.setText(time);
        icontainer.addView(times);

        //message cardview
        CardView messageCard = new CardView(ChatNotificationActivity.this);
        LinearLayout.LayoutParams rcParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0F);
        rcParams.setMargins(dpToPx(10, ChatNotificationActivity.this),dpToPx(4, ChatNotificationActivity.this),dpToPx(10, ChatNotificationActivity.this),dpToPx(4, ChatNotificationActivity.this));
        messageCard.setRadius(dpToPx(2, ChatNotificationActivity.this));
        messageCard.setBackgroundColor(Color.parseColor("#ffffff"));
        messageCard.setLayoutParams(rcParams);
        //message textview
        TextView messagec = new TextView(ChatNotificationActivity.this);
        LinearLayout.LayoutParams messageparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        messageparams.setMargins(0,0,dpToPx(10, ChatNotificationActivity.this),0);
        Typeface face1 = Typeface.createFromAsset(getAssets(),
                "fonts/mali_medium.ttf");
        messagec.setTypeface(face1);
        messagec.setTextColor(Color.parseColor("#000000"));
        messagec.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        messagec.setLayoutParams(messageparams);
        messagec.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_outline_person_outline_24, 0, 0, 0);
        messagec.setCompoundDrawablePadding(dpToPx(10, ChatNotificationActivity.this));
        messagec.setGravity(Gravity.CENTER_VERTICAL);
        messagec.setPadding(dpToPx(10, ChatNotificationActivity.this), dpToPx(10, ChatNotificationActivity.this), dpToPx(10, ChatNotificationActivity.this), dpToPx(10, ChatNotificationActivity.this));
        messagec.setText(message);
        //messagec.setLineHeight(dpToPx(20, ChatNotificationActivity.this));

        messageCard.addView(messagec);
        icontainer.addView(messageCard);


        return icontainer;
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
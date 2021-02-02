package com.example.examplanetwaec;

import android.content.ContentProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class session {
    SharedPreferences sharedPreferences;
    Context context;

    session(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }


    public boolean checkLog() {
        String logged = sharedPreferences.getString("email", null);
        if (logged != null) {
            return true;

        } else {
            return false;
        }

    }

    public void createlog(String name, String email, String gender, String dob, String picture) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("gender", gender);
        editor.putString("dob", dob);
        editor.putString("picture", picture);
        editor.commit();

    }

    public void addAnsweredQuestion(String qid, String chosenAnswer) {
        SharedPreferences prefs = context.getSharedPreferences("answerList", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(qid, chosenAnswer);
        editor.commit();
    }

    public String getAnsweredQuestion(String qid) {
        SharedPreferences prefs = context.getSharedPreferences("answerList", 0);
        if (prefs.contains(qid) == true) {
            return prefs.getString(qid, null);
        } else {
            return "";
        }

    }

    public void deleteAnsweredQuestion(String qid) {
        SharedPreferences prefs = context.getSharedPreferences("answerList", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(qid);
        editor.commit();
    }

    public void deleteUserLog() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("gender");
        editor.remove("dob");
        editor.remove("picture");
        editor.commit();
    }

    public String getName() {

        if (sharedPreferences.contains("name")){
            return sharedPreferences.getString("name", null);
    }
    else

    {
        return "";
    }

}
    public String getEmail()
    {
        if (sharedPreferences.contains("email")) {
            return sharedPreferences.getString("email", null);
        }
        else
        {
            return "";
        }
    }
    public String getGender()
    {
        if (sharedPreferences.contains("gender")) {
            return sharedPreferences.getString("gender", null);
        }
        else
        {
            return "";
        }
    }
    public String getDob()
    {
        if (sharedPreferences.contains("dob")) {

            return sharedPreferences.getString("dob", null);
        }
        else {
            return "";
        }
    }
    public String getPicture()
    {
        if (sharedPreferences.contains("picture")) {
            return sharedPreferences.getString("picture", null);
        }
        else
        {
            return "";
        }
    }

    public void addAlarm(String alarmID, Long time, String subject, String stime, String myday, String mytime, String cday) {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(alarmID, alarmID+","+time+","+subject+","+stime+","+myday+","+mytime+","+cday);
        editor.commit();

    }

    public void deleteAlarm(String alarmID) {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(alarmID);
        editor.commit();
    }

    public boolean alarmsExist()
    {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        if (prefs.getAll().size() > 0) {
            return true;
        }
        else
        {
            return false;
        }

    }
    public boolean alarmidexist(String alarmid) {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        return prefs.contains(alarmid);

    }

    public List<String> getAlarmsID()
    {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        Map<String,?> keys = prefs.getAll();
        alarm Alarm = new alarm();
        for(Map.Entry<String,?> entry : keys.entrySet()){
                Alarm.addId(entry.getValue().toString().split(",")[0]);

        }

        return Alarm.getAid();
    }

    public List<String> getAlarmstime()
    {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        Map<String,?> keys = prefs.getAll();
        alarm Alarm = new alarm();
        for(Map.Entry<String,?> entry : keys.entrySet()){
                Alarm.addtime(entry.getValue().toString().split(",")[1]);
        }

        return Alarm.getTime();
    }

    public List<String> getAlarmsubject()
    {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        Map<String,?> keys = prefs.getAll();
        alarm Alarm = new alarm();
        for(Map.Entry<String,?> entry : keys.entrySet()){
                Alarm.addSubject(entry.getValue().toString().split(",")[2]);


        }

        return Alarm.getSubject();
    }

    public List<String> getAlarmstimes()
    {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        Map<String,?> keys = prefs.getAll();
        alarm Alarm = new alarm();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Alarm.addStime(entry.getValue().toString().split(",")[3]);
        }

        return Alarm.getSTime();
    }

    public List<String> getAlarmmyday()
    {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        Map<String,?> keys = prefs.getAll();
        alarm Alarm = new alarm();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Alarm.addmyday(entry.getValue().toString().split(",")[4]);
        }

        return Alarm.getmyday();
    }

    public List<String> getAlarmmytime()
    {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        Map<String,?> keys = prefs.getAll();
        alarm Alarm = new alarm();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Alarm.addmytime(entry.getValue().toString().split(",")[5]);
        }

        return Alarm.getmytime();
    }

    public List<String> getAlarmcday()
    {
        SharedPreferences prefs = context.getSharedPreferences("Alarms", 0);
        Map<String,?> keys = prefs.getAll();
        alarm Alarm = new alarm();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Alarm.addcday(entry.getValue().toString().split(",")[6]);
        }

        return Alarm.getcday();
    }


    private class alarm{
        List<String> aid,time,subject,stime,myday, mytime, cday = null;

        alarm()
        {
            aid = new ArrayList();
            time = new ArrayList();
            subject = new ArrayList();
            stime = new ArrayList();
            myday = new ArrayList();
            mytime = new ArrayList();
            cday = new ArrayList();
        }

        public void addId(String id)
        {
            aid.add(id);
        }

        public void addtime(String timeval)
        {
            time.add(timeval);
        }
        public void addSubject(String subj)
        {
            subject.add(subj);
        }

        public void addStime(String timeval)
        {
            stime.add(timeval);
        }

        public void addmyday(String day)
        {
            myday.add(day);
        }
        public void addmytime(String time)
        {
            mytime.add(time);
        }

        public void addcday(String dval)
        {
            cday.add(dval);
        }

        public List<String> getAid()
        {
            return  aid;
        }
        public List<String> getTime()
        {
            return  time;
        }
        public List<String> getSubject()
        {
            return  subject;
        }
        public List<String> getSTime()
        {
            return  stime;
        }
        public List<String> getmyday()
        {
            return  myday;
        }
        public List<String> getmytime()
        {
            return  mytime;
        }
        public List<String> getcday()
        {
            return  cday;
        }

    }

}



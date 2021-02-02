package com.example.examplanetwaec;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

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
public class MyFunctions {





    public void retry(final Context context, final Runnable function, final View MainView)
    {
        final Dialog rdialog = new Dialog(context);
        rdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rdialog.setCancelable(false);
        rdialog.setContentView(R.layout.retryxml);
        MainView.setAlpha(0.2f);
        TextView retry = rdialog.findViewById(R.id.retry);
        TextView exit = rdialog.findViewById(R.id.exit);
        rdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                MainView.setAlpha(1f);
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function.run();
                rdialog.cancel();
                rdialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
                rdialog.cancel();
                rdialog.dismiss();
            }
        });


        rdialog.show();
    }



    public void retryNoExit(final Context context, final Runnable function, final View MainView)
    {
        final Dialog rdialog = new Dialog(context);
        rdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rdialog.setCancelable(false);
        rdialog.setContentView(R.layout.retryxml);
        MainView.setAlpha(0.2f);
        TextView retry = rdialog.findViewById(R.id.retry);
        TextView exit = rdialog.findViewById(R.id.exit);
        rdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                MainView.setAlpha(1f);
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function.run();
                rdialog.cancel();
                rdialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdialog.cancel();
                rdialog.dismiss();
            }
        });


        rdialog.show();
    }




}

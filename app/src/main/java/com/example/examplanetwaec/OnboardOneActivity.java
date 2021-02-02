package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.util.Calendar;

public class OnboardOneActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private session mySession;
    private String gender;
    private TextView vtdate;
    private ImageView next;
    String[] genders = new String[2];
    private int[] dob = new int[3];
    private String email, name, passcode;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboard_one);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == true) {
            Intent intent = new Intent(OnboardOneActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();

        }
        Intent passed = getIntent();
        email = passed.getStringExtra("email");
        name = passed.getStringExtra("name");
        passcode = passed.getStringExtra("passcode");
        next = findViewById(R.id.imageView2);
        gender = "";
        vtdate = findViewById(R.id.editTextDate);
        dob[0] = 0;
        dob[1] = 0;
        dob[2] = 0;
        gender = "Male";
        genders[0] = "Male";
        genders[1] = "Female";
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                vtdate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                dob[0] = year;
                dob[1] = monthOfYear+1;
                dob[2] = dayOfMonth;

            }

        };




        vtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(OnboardOneActivity.this,R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gender.equals(""))
                {
                    new LovelyInfoDialog(OnboardOneActivity.this).setCancelable(false)
                            .setTitle("Compulsory:")
                            .setMessage("Please Select Your Gender")
                            .show();
                }
                else if(dob[0] == 0 || dob[1] == 0 || dob[2] == 0)
                {
                    new LovelyInfoDialog(OnboardOneActivity.this).setCancelable(false)
                            .setTitle("Compulsory:")
                            .setMessage("Please Select Your Date of Birth")
                            .show();
                }
                else
                {
                    int maxYear = Calendar.getInstance().get(Calendar.YEAR) - 14;
                    if(dob[0] > maxYear)
                    {
                        new LovelyInfoDialog(OnboardOneActivity.this).setCancelable(false)
                                .setTitle("Compulsory:")
                                .setMessage("Maximum age Year :"+maxYear)
                                .show();
                    }
                    else
                    {
                        //move to verification
                        Intent intent = new Intent(OnboardOneActivity.this, VerificationPageActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("name", name);
                        intent.putExtra("passcode", passcode);
                        intent.putExtra("gender", gender);
                        intent.putExtra("dob", dob[0]+"-"+dob[1]+"-"+dob[2]);
                        startActivity(intent);
                        finish();
                    }



                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        gender = genders[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        gender = "Male";
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OnboardOneActivity.this, SignUpPageActivity.class);
        startActivity(intent);
        finish();
    }
}
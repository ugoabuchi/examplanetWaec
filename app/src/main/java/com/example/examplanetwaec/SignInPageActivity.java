package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SignInPageActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private Button google;
    private session mySession;
    private TextView signinbtn;
    private ImageView normalsignupBTN;
    private TextView ulabel;
    ProgressDialog dialog = null;
    ConstraintLayout mainscreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in_page);
        dialog = ProgressDialog.show(this, "processing", "Please wait...", true);
        dialog.dismiss();
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == true) {
            Intent intent = new Intent(SignInPageActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();

        }
        mainscreen = findViewById(R.id.mainscreen);
        signinbtn = findViewById(R.id.textView3);
        normalsignupBTN = findViewById(R.id.page_four);
        ulabel = findViewById(R.id.usernameLabel);

        if(mySession.getName().equals("") || mySession.getName().equals(null))
        {
            ulabel.setText("");
        }
        else
        {
            ulabel.setText(mySession.getName().split(" ")[0]);
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();

        google = findViewById(R.id.button3);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);
            }
        });
       normalsignupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalsignin(((EditText)findViewById(R.id.forgetPass_emailField)).getText().toString(), ((EditText)findViewById(R.id.passwordField)).getText().toString());
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInPageActivity.this, SignInPageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        dialog.dismiss();
        googlegignin(completedTask);

    }

    public void googlegignin(final Task<GoogleSignInAccount> completedTask)
    {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            final String email = account.getEmail();
            final String name = account.getDisplayName();
            //check if email exist
            new datarequest(SignInPageActivity.this, new customListener() {
                @Override
                public void onResponse(String[] response) {

                    if (response[0].equals("success")) {
                        if (response[1].equals("success")) {
                            mGoogleSignInClient.signOut();
                            new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                                    .setTitle("Ooops!!!")
                                    .setMessage("Email does not exist!!!")
                                    .show();
                        } else {
                            mGoogleSignInClient.signOut();
                            new datarequest(SignInPageActivity.this, new customListener() {
                                @Override
                                public void onResponse(String[] response) {

                                    if (response[0].equals("success")) {

                                        try {

                                            JSONArray tops = new JSONArray(response[1]);
                                            for (int i = 0; i < tops.length(); i++) {
                                                final int finalJ = i;
                                                final JSONObject sobj = tops.getJSONObject(finalJ);
                                                mySession.createlog(sobj.getString("name"), sobj.getString("email"), sobj.getString("gender"),sobj.getString("dob"), sobj.getString("picture"));
                                            break;
                                            }

                                            Intent intent = new Intent(SignInPageActivity.this, HomePageActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }




                                    } else {

                                        new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                                                .setTitle("Ooops!!!")
                                                .setMessage("Email does not exist!!!")
                                                .show();
                                    }

                                }
                            }).glogin(email);
                        }

                    } else {
                        mGoogleSignInClient.signOut();
                        //Get the name, gender, dob, picture
                        new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                                .setTitle("Ooops!!!")
                                .setMessage("Something has happened, please try again!!!")
                                .show();

                    }
                }
            }).emailExist(email);


        } catch (ApiException e) {
            mGoogleSignInClient.signOut();
            new MyFunctions().retryNoExit(getApplicationContext(), new Runnable() {
                @Override
                public void run() {
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 1);
                }
            }, mainscreen);
        }
    }

    public void normalsignin(final String email, final String passcode)
    {
            if(!email.equals("") && !email.equals(null) && !passcode.equals("") && !passcode.equals(null))
            {
                //check if email exist
                new datarequest(SignInPageActivity.this, new customListener() {
                    @Override
                    public void onResponse(String[] response) {

                        if (response[0].equals("success")) {
                            if (response[1].equals("success")) {
                                mGoogleSignInClient.signOut();
                                new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                                        .setTitle("Ooops!!!")
                                        .setMessage("Email does not exist!!!")
                                        .show();
                            } else {
                                mGoogleSignInClient.signOut();
                                new datarequest(SignInPageActivity.this, new customListener() {
                                    @Override
                                    public void onResponse(String[] response) {

                                        if (response[0].equals("success")) {

                                            try {

                                                JSONArray tops = new JSONArray(response[1]);
                                                for (int i = 0; i < tops.length(); i++) {
                                                    final int finalJ = i;
                                                    final JSONObject sobj = tops.getJSONObject(finalJ);

                                                    if(sobj.getString("passcode").equals(md5(passcode)))
                                                    {
                                                        mySession.createlog(sobj.getString("name"), sobj.getString("email"), sobj.getString("gender"),sobj.getString("dob"), sobj.getString("picture"));
                                                        mySession.createlog(sobj.getString("name"), sobj.getString("email"), sobj.getString("gender"),sobj.getString("dob"), sobj.getString("picture"));
                                                        Intent intent = new Intent(SignInPageActivity.this, HomePageActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    else {
                                                        new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                                                                .setTitle("Ooops!!!")
                                                                .setMessage("Incorrect login details!!!")
                                                                .show();
                                                    }
                                                    break;
                                                }



                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }




                                        } else {

                                            new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                                                    .setTitle("Ooops!!!")
                                                    .setMessage("Email does not exist!!!")
                                                    .show();
                                        }

                                    }
                                }).glogin(email);
                            }

                        } else {
                            mGoogleSignInClient.signOut();
                            //Get the name, gender, dob, picture
                            new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                                    .setTitle("Ooops!!!")
                                    .setMessage("Something has happened, please try again!!!")
                                    .show();

                        }
                    }
                }).emailExist(email);


            }
            else
            {
                new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                        .setTitle("Ooops!!!")
                        .setMessage("A field is empty!!!")
                        .show();
            }

    }
/*
    public void normalSignup(final String email, final String name, final String passcode)
    {
        if(!email.equals("") && !email.equals(null) && !name.equals("") && !name.equals(null) && !passcode.equals("") && !passcode.equals(null))
        {
            //check if email exist
            new datarequest(SignInPageActivity.this, new customListener() {
                @Override
                public void onResponse(String[] response) {

                    if (response[0].equals("success")) {
                        if (response[1].equals("success")) {
                            Random r = new Random( System.currentTimeMillis() );
                            Intent intent = new Intent(SignInPageActivity.this, OnboardOneActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("name", name);
                            intent.putExtra("passcode", passcode);
                            startActivity(intent);
                            finish();
                        } else {
                            mGoogleSignInClient.signOut();
                            new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                                    .setTitle("Ooops!!!")
                                    .setMessage("Email address already exist!!!")
                                    .show();
                        }

                    } else {
                        mGoogleSignInClient.signOut();
                        new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                                .setTitle("Ooops!!!")
                                .setMessage("Something happened, please check your network!!!")
                                .show();
                    }
                }
            }).emailExist(email);
        }
        else {

            new LovelyInfoDialog(SignInPageActivity.this).setCancelable(false)
                    .setTitle("Ooops!!!")
                    .setMessage("You forgot to fill a field")
                    .show();
        }



    }


 */
public static String md5(final String s) {
    final String MD5 = "MD5";
    try {
        // Create MD5 Hash
        MessageDigest digest = java.security.MessageDigest
                .getInstance(MD5);
        digest.update(s.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        return hexString.toString();

    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }
    return "";
}

    @Override
    public void onBackPressed() {
        mGoogleSignInClient.signOut();
        Intent intent = new Intent(SignInPageActivity.this, splashActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleSignInClient.signOut();
    }
}
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

import java.util.Random;

public class SignUpPageActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private Button google;
    private session mySession;
    private TextView logout, signinbtn;
    private ImageView normalsignupBTN;
    ProgressDialog dialog = null;
    ConstraintLayout mainscreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_page);
        dialog = ProgressDialog.show(this, "processing", "Please wait...", true);
        dialog.dismiss();
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == true) {
            Intent intent = new Intent(SignUpPageActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();

        }
        mainscreen = findViewById(R.id.mainscreen);
        signinbtn = findViewById(R.id.textView3);
        normalsignupBTN = findViewById(R.id.page_four);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();

        logout = findViewById(R.id.textView6);
        google = findViewById(R.id.button3);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
            }
        });
        normalsignupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalSignup(((EditText)findViewById(R.id.forgetPass_emailField)).getText().toString(), ((EditText)findViewById(R.id.nameField)).getText().toString(), ((EditText)findViewById(R.id.passwordField)).getText().toString());
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPageActivity.this, SignInPageActivity.class);
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
        googlegignup(completedTask);

    }

    public void googlegignup(final Task<GoogleSignInAccount> completedTask)
    {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            final String email = account.getEmail();
            final String name = account.getDisplayName();
            //check if email exist
            new datarequest(SignUpPageActivity.this, new customListener() {
                @Override
                public void onResponse(String[] response) {

                    if (response[0].equals("success")) {
                        if (response[1].equals("success")) {
                            mGoogleSignInClient.signOut();
                            Random r = new Random( System.currentTimeMillis() );
                            String passcode = ""+((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
                            Intent intent = new Intent(SignUpPageActivity.this, OnboardOneActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("name", name);
                            intent.putExtra("passcode", passcode);
                            startActivity(intent);
                            finish();
                        } else {
                            mGoogleSignInClient.signOut();
                            new LovelyInfoDialog(SignUpPageActivity.this).setCancelable(false)
                                    .setTitle("Ooops!!!")
                                    .setMessage("Email address already exist!!!")
                                    .show();
                        }

                    } else {
                        mGoogleSignInClient.signOut();
                        new LovelyInfoDialog(SignUpPageActivity.this).setCancelable(false)
                                .setTitle("Ooops!!!")
                                .setMessage("Something happened, check your network!!!")
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


    public void normalSignup(final String email, final String name, final String passcode)
    {
        if(!email.equals("") && !email.equals(null) && !name.equals("") && !name.equals(null) && !passcode.equals("") && !passcode.equals(null))
        {
            //check if email exist
            new datarequest(SignUpPageActivity.this, new customListener() {
                @Override
                public void onResponse(String[] response) {

                    if (response[0].equals("success")) {
                        if (response[1].equals("success")) {
                            Random r = new Random( System.currentTimeMillis() );
                            Intent intent = new Intent(SignUpPageActivity.this, OnboardOneActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("name", name);
                            intent.putExtra("passcode", passcode);
                            startActivity(intent);
                            finish();
                        } else {
                            mGoogleSignInClient.signOut();
                            new LovelyInfoDialog(SignUpPageActivity.this).setCancelable(false)
                                    .setTitle("Ooops!!!")
                                    .setMessage("Email address already exist!!!")
                                    .show();
                        }

                    } else {
                        mGoogleSignInClient.signOut();
                        new LovelyInfoDialog(SignUpPageActivity.this).setCancelable(false)
                                .setTitle("Ooops!!!")
                                .setMessage("Something happened, please check your network!!!")
                                .show();
                    }
                }
            }).emailExist(email);
        }
        else {

            new LovelyInfoDialog(SignUpPageActivity.this).setCancelable(false)
                    .setTitle("Ooops!!!")
                    .setMessage("You forgot to fill a field")
                    .show();
        }



    }

    @Override
    public void onBackPressed() {
        mGoogleSignInClient.signOut();
        Intent intent = new Intent(SignUpPageActivity.this, splashActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleSignInClient.signOut();
    }
}
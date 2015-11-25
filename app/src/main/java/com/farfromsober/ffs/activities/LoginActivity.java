package com.farfromsober.ffs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.farfromsober.customviews.CustomFontTextView;
import com.farfromsober.ffs.R;

import butterknife.Bind;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_email) EditText mLoginEmail;
    @Bind(R.id.login_password) EditText mLoginPassword;
    @Bind(R.id.button_login_signin) Button mSigninButton;
    @Bind(R.id.button_fotgot_pass) Button mForgotPassButton;
    @Bind(R.id.button_register) Button mRegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initButtonsListeners();
    }

    
    protected void initButtonsListeners() {
        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                //startActivity(i);
            }
        });

        mForgotPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRegisterButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}

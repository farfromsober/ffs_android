package com.farfromsober.ffs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.farfromsober.ffs.R;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.network.callbacks.OnDataParsedCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements OnDataParsedCallback<User> {

    private APIManager apiManager;
    public WeakReference<OnDataParsedCallback> mOnDataParsedCallback;

    @Bind(R.id.login_email) EditText mLoginEmail;
    @Bind(R.id.login_password) EditText mLoginPassword;
    @Bind(R.id.button_login_signin) Button mSigninButton;
    @Bind(R.id.button_fotgot_pass) Button mForgotPassButton;
    @Bind(R.id.button_register) Button mRegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mOnDataParsedCallback = new WeakReference<>((OnDataParsedCallback)this);
        apiManager = new APIManager();

        initButtonsListeners();
    }


    protected void initButtonsListeners() {
        final WeakReference<LoginActivity> loginActivityWeakReference =  new WeakReference<LoginActivity>(this);

        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mLoginEmail.getText().toString();
                String password = mLoginPassword.getText().toString();
                apiManager.login(email, password, loginActivityWeakReference.get());
            }
        });

        mForgotPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onDataParsed(ArrayList data) {
        Log.i("ffs", data.toString());
        //SharedPreferencesManager.savePrefLoginUser(getApplicationContext(), "");
        //this.showMainActivity();
    }

    @Override
    public void onDataParsed(User data) {
        Log.i("ffs", data.toString());
        SharedPreferencesManager.savePrefLoginUser(getApplicationContext(), data.getEmail());
        this.showMainActivity();
    }

    private void showMainActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        this.finish();
    }
}

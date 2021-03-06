package com.farfromsober.ffs.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.model.LoginData;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.network.APIManager;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.generalutils.SharedPreferencesGeneralManager;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.networkviews.NetworkPreloaderActivity;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends NetworkPreloaderActivity implements OnDataParsedCallback<User> {

    private APIManager apiManager;
    public WeakReference<OnDataParsedCallback> mOnDataParsedCallback;
    private LoginData loginData;


    @Bind(R.id.login_email)
    EditText mLoginEmail;
    @Bind(R.id.login_password)
    EditText mLoginPassword;
    @Bind(R.id.button_login_signin)
    Button mSigninButton;
    @Bind(R.id.button_fotgot_pass)
    Button mForgotPassButton;
    @Bind(R.id.button_register)
    Button mRegisterButton;
    public WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mOnDataParsedCallback = new WeakReference<>((OnDataParsedCallback) this);
        mOnNetworkActivityCallback = new WeakReference<>((OnNetworkActivityCallback) this);

        apiManager = new APIManager(this);

        initButtonsListeners();
    }


    protected void initButtonsListeners() {
        final WeakReference<LoginActivity> loginActivityWeakReference = new WeakReference<LoginActivity>(this);

        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSigninButton.setEnabled(false);
                loginActivityWeakReference.get().showPreloader(getString(R.string.login_accessing));

                String email = mLoginEmail.getText().toString();
                String password = mLoginPassword.getText().toString();
                loginData = new LoginData(email, password);
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
                Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }


    @Override
    public void onDataArrayParsed(int responseCode, ArrayList data) {
        mSigninButton.setEnabled(true);
        if (data != null) {
            Log.i("ffs", data.toString());
        }
    }

    @Override
    public void onDataObjectParsed(int responseCode, User data) {
        mSigninButton.setEnabled(true);
        hidePreloader();
        if (responseCode != HttpsURLConnection.HTTP_OK) {
            return;
        }
        if (data != null) {
            Log.i("ffs", data.toString());
            SharedPreferencesManager.savePrefUserData(getApplicationContext(), data);
            SharedPreferencesManager.savePrefLoginUser(getApplicationContext(), this.loginData);

            this.showMainActivity();
        } else if (data == null) {
            this.showErrorMessage();
        }
    }

    private void showMainActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        this.finish();
    }

    private void showErrorMessage() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.login_error_message_title));
        alertDialog.setMessage(getString(R.string.login_error_message));
        alertDialog.setButton(0, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });
        alertDialog.show();

    }
}

package com.farfromsober.networkviews;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farfromsober.networkviews.callbacks.OnInfoDialogCallback;
import com.farfromsober.networkviews.callbacks.OnNetworkActivityCallback;
import com.farfromsober.networkviews.dialogs.InfoDialogFragment;

import java.lang.ref.WeakReference;

public class NetworkPreloaderActivity extends AppCompatActivity implements OnNetworkActivityCallback, OnInfoDialogCallback {

    public WeakReference<OnNetworkActivityCallback> mOnNetworkActivityCallback;

    private RelativeLayout mLoadingGroup;
    private TextView mLoadingText;
    private ImageView mLoadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOnNetworkActivityCallback = new WeakReference<>((OnNetworkActivityCallback)this);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        mLoadingGroup = (RelativeLayout) findViewById(R.id.loading_group);
        mLoadingText = (TextView) findViewById(R.id.loading_text);
        mLoadingIcon = (ImageView) findViewById(R.id.loading_icon);
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public void onNetworkActivityStarted(String message) {
        showPreloader(message);
    }

    @Override
    public void onNetworkActivityFinished() {
        hidePreloader();
    }

    @Override
    public void onExceptionReceived(Exception e) {
        showInfoDialogFragment(R.string.problems_with_request_dialog_title, R.string.problems_with_request_dialog_message, R.string.problems_with_request_button_text);
    }

    public void showPreloader(String message) {
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_continuously);
        rotation.setFillAfter(true);
        if (mLoadingIcon != null) {
            mLoadingIcon.startAnimation(rotation);
        }
        if (mLoadingText != null) {
            mLoadingText.setText(message);
        }
        if (mLoadingGroup != null) {
            mLoadingGroup.setVisibility(View.VISIBLE);
        }
    }

    public void hidePreloader() {
        if (mLoadingIcon != null) {
            mLoadingIcon.clearAnimation();
        }
        if (mLoadingGroup != null) {
            mLoadingGroup.setVisibility(View.GONE);
        }
    }

    protected void showInfoDialogFragment(int titleId, int messageId, int buttonTextId) {
        InfoDialogFragment dialog = InfoDialogFragment.newInstance(titleId, messageId, buttonTextId);
        dialog.setOnInfoDialogCallback(this);
        dialog.show(getFragmentManager(), null);
    }

    @Override
    public void onInfoDialogClosed(InfoDialogFragment dialog) {
        if (mOnNetworkActivityCallback != null && mOnNetworkActivityCallback.get() != null) {
            mOnNetworkActivityCallback.get().onNetworkActivityFinished();
        }
        dialog.dismiss();
    }
}

package com.farfromsober.networkviews.callbacks;

public interface OnNetworkActivityCallback {
    void onNetworkActivityStarted(String message);
    void onNetworkActivityFinished();
    void onExceptionReceived(Exception e);
}

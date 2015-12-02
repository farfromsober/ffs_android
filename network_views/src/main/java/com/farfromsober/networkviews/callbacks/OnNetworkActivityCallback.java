package com.farfromsober.networkviews.callbacks;

public interface OnNetworkActivityCallback {
    public void onNetworkActivityStarted(String message);
    public void onNetworkActivityFinished();
}

package com.farfromsober.network.callbacks;

public interface OnNetworkActivityCallback {
    public void onNetworkActivityStarted(String message);
    public void onNetworkActivityFinished();
}

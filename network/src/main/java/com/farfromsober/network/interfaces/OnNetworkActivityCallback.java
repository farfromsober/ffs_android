package com.farfromsober.network.interfaces;

public interface OnNetworkActivityCallback {
    public void onNetworkActivityStarted(String message);
    public void onNetworkActivityFinished();
}

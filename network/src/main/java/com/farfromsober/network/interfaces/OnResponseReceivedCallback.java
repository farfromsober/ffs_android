package com.farfromsober.network.interfaces;

import java.lang.ref.WeakReference;

public interface OnResponseReceivedCallback {

    void onResponseReceived(int responseCode, String response, Class<?> modelClass, WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference);

}

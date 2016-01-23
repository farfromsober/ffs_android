package com.farfromsober.network.callbacks;

import java.lang.ref.WeakReference;

public interface OnResponseReceivedCallback {

    void onResponseReceived(int responseCode, String response, Class<?> modelClass, WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference);
    void onResponseReceivedWithNoData(WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference);
    void onExceptionReceived(Exception e, WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference);

}

package com.farfromsober.network.callbacks;

import java.util.ArrayList;

public interface OnDataParsedCallback <T> {
    void onDataArrayParsed(int responseCode, ArrayList<T> data);
    void onDataObjectParsed(int responseCode, T data);
    void onExceptionReceived(Exception e);
}

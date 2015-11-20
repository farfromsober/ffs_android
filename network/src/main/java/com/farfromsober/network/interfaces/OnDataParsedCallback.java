package com.farfromsober.network.interfaces;

import java.util.ArrayList;

public interface OnDataParsedCallback <T> {
    void onDataParsed(ArrayList<T> data);
    void onDataParsed(T data);
}

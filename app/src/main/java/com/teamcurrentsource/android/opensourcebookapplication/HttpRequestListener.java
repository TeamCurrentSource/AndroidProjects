package com.teamcurrentsource.android.opensourcebookapplication;

import com.google.gson.Gson;

import java.io.BufferedReader;


public interface HttpRequestListener {
    void processHttpRequest(Gson data, BufferedReader reader);
}

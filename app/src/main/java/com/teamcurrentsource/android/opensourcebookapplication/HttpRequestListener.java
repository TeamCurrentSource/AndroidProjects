package com.teamcurrentsource.android.opensourcebookapplication;

import com.google.gson.Gson;

import java.io.BufferedReader;

/**
 * Created by Antti on 19.3.2016.
 */
public interface HttpRequestListener {
    void processHttpRequest(Gson data, BufferedReader reader);
}

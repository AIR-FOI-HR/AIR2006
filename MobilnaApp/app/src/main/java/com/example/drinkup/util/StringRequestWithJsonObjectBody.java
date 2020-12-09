package com.example.drinkup.util;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

// reference: https://stackoverflow.com/a/48424181/7519721
public class StringRequestWithJsonObjectBody extends StringRequest {

    private final JSONObject jsonObject;

    public StringRequestWithJsonObjectBody(int method, String url, JSONObject jsonObject, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.jsonObject = jsonObject;
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public byte[] getBody() {
        if (jsonObject == null) {
            return null;
        }
        else {
            return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String responseString = "";
        if (response != null) {
            responseString = new String(response.data);
        }
        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
    }}

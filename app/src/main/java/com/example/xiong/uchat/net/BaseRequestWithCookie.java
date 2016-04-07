package com.example.xiong.uchat.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BaseRequestWithCookie extends StringRequest {

    protected Map<String, String> cookie;
    protected Map<String, String> data;

    public BaseRequestWithCookie(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public BaseRequestWithCookie(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (cookie == null) {
            return super.getHeaders();
        }
        return cookie;
    }

    public void setCookieValue(String cookie) {
        if (this.cookie == null) {
            this.cookie = new HashMap<>();
        }
        this.cookie.put("Cookie", cookie);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (data == null) {
            return super.getParams();
        } else {
            return data;
        }
    }

    public void addParams(String key, String value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }
}

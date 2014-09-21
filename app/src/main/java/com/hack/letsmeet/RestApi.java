package com.hack.letsmeet;

import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by bilal on 2014-09-20.
 */
public class RestApi {
    private static RestApi instance = null;
    private static final String BASE_URL="http://10.21.217.205:8080/";

    public enum Method {
        GET, POST
    };

    private String userid = "";
    private String accessToken = "";

    public static RestApi getInstance() {
        if (instance == null) {
            instance = new RestApi();
        }

        return instance;
    }

    private RestApi() {

    }

    public void request(String endpoint, Method method, JSONObject params, Response.Listener responseHandler) {
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("RestApi", volleyError.getMessage());
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                BASE_URL+endpoint,
                params,
                responseHandler,
                errorListener) {
            @Override
            public HashMap<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<String, String>();
                if (userid.length() > 0 && accessToken.length() > 0) {
                    params.put("X-WWW-Authenticate", userid + " " + accessToken);
                }

                return params;
            }
        };

        if (method == Method.POST) {
            request = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL+endpoint,
                    params,
                    responseHandler,
                    errorListener){
                @Override
                public HashMap<String, String> getHeaders() {
                    HashMap<String, String> params = new HashMap<String, String>();
                    if (userid.length() > 0 && accessToken.length() > 0) {
                        params.put("X-WWW-Authenticate", userid + " " + accessToken);
                    }

                    return params;
                }
            };
        }

    }

    public void setAuth(String userId, String accessToken) {
        this.userid = userId;
        this.accessToken = accessToken;

    }
}

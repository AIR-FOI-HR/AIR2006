package com.example.drinkup.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.drinkup.R;
import com.example.drinkup.models.Korisnik;
import com.example.drinkup.models.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
public class RequestService {

    private Context appContext;
    public RequestService(Context context){
        appContext =  context;
    }
    public void SendRegistrationRequest(Korisnik data){
        String url = appContext.getString(R.string.registration_url);

        RequestQueue queue = Volley.newRequestQueue(appContext);

        JSONObject MyData = new JSONObject();
        try {
            MyData.put("oib", data.OIB);
            MyData.put("email", data.email);
            MyData.put("ime", data.ime);
            MyData.put("prezime", data.prezime);
            MyData.put("spol", data.spol);
            MyData.put("ulogaId", data.ulogaID);
            MyData.put("lozinka", data.lozinka);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, url, MyData,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
            }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void SendLoginRequest(LoginModel login){
        String url = appContext.getString(R.string.login_url);

        RequestQueue queue = Volley.newRequestQueue(appContext);

        JSONObject MyData = new JSONObject();
        try {
            MyData.put("email", login.email);
            MyData.put("lozinka", login.lozinka);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, url, MyData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        queue.add(jsonObjectRequest);
    }
}

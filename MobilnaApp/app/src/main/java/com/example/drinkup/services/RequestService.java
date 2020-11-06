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
import com.example.drinkup.models.Uloga;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

public class RequestService {

    private Context appContext;

    public RequestService(Context context){
        appContext =  context;
    }

    public void fetchAvailableRoles(Consumer<Uloga[]> successObjectConsumer, Consumer<VolleyError> failErrorConsumer) {
        RequestQueue queue = Volley.newRequestQueue(appContext);
        String url = appContext.getString(R.string.get_all_roles_url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Uloga[] roles = new Gson().fromJson(response, Uloga[].class);
                        successObjectConsumer.accept(roles);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        failErrorConsumer.accept(error);
                    }
                });

        queue.add(stringRequest);
    }

    public void SendRegistrationRequest(Korisnik data, Consumer<JSONObject> successObjectConsumer, Consumer<VolleyError> failErrorConsumer){
        String url = appContext.getString(R.string.registration_url);

        RequestQueue queue = Volley.newRequestQueue(appContext);

        JSONObject MyData = new JSONObject();
        try {
            MyData.put("oib", data.getOIB());
            MyData.put("email", data.getEmail());
            MyData.put("ime", data.getIme());
            MyData.put("prezime", data.getPrezime());
            MyData.put("spol", data.getSpol());
            MyData.put("ulogaId", data.getUlogaID());
            MyData.put("lozinka", data.getLozinka());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, url, MyData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                successObjectConsumer.accept(response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                failErrorConsumer.accept(error);
                            }
                        });
        queue.add(jsonObjectRequest);
    }

    public void SendLoginRequest(LoginModel login){
        String url = appContext.getString(R.string.login_url);

        RequestQueue queue = Volley.newRequestQueue(appContext);

        JSONObject MyData = new JSONObject();
        try {
            MyData.put("email", login.getEmail());
            MyData.put("lozinka", login.getLozinka());

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

package com.example.drinkup.services;

import android.content.Context;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.drinkup.R;
import com.example.drinkup.models.Korisnik;
import com.example.drinkup.models.LoginModel;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.Uloga;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
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

    public void SendLoginRequest(LoginModel login, Consumer<JSONObject> successObjectConsumer, Consumer<VolleyError> failErrorConsumer){
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

    public void SendResetPasswordRequest(String email, Consumer<JSONObject> successObjectConsumer, Consumer<VolleyError> failErrorConsumer){
        String url = appContext.getString(R.string.forgot_pass_url);

        RequestQueue queue = Volley.newRequestQueue(appContext);

        JSONObject MyData = new JSONObject();
        try {
            MyData.put("email", email);

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

    public void dohvatiObjekte(Consumer<List<Objekt>> responseConsumer, Consumer<VolleyError> errorConsumer) {
        String urlObjekti = appContext.getString(R.string.caffes_url);

        RequestQueue queue = Volley.newRequestQueue(appContext);

        JsonArrayRequest requestObjekti = new JsonArrayRequest(Request.Method.GET, urlObjekti, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Objekt> listaObjekata = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject objekt = response.getJSONObject(i);

                        int idObjekta = objekt.getInt("id");
                        String naziv = objekt.getString("naziv");
                        String grad = objekt.getString("grad");
                        String ulica = objekt.getString("ulica");
                        String adresa = objekt.getString("adresa");
                        String radnoVrijeme = objekt.getString("radnoVrijeme");
                        String kontakt = objekt.getString("kontakt");
                        Float longituda = (float)objekt.getDouble("longituda");
                        Float latituda = (float)objekt.getDouble("latituda");
                        boolean aktivan = objekt.getBoolean("aktivan");

                        Objekt noviObjekt = new Objekt();
                        noviObjekt.Id = idObjekta;
                        noviObjekt.naziv = naziv;
                        noviObjekt.grad = grad;
                        noviObjekt.ulica = ulica;
                        noviObjekt.adresa = adresa;
                        noviObjekt.radnoVrijeme = radnoVrijeme;
                        noviObjekt.kontakt = kontakt;
                        noviObjekt.longituda = longituda;
                        noviObjekt.latituda = latituda;
                        noviObjekt.aktivan = aktivan;

                        listaObjekata.add(noviObjekt);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                responseConsumer.accept(listaObjekata);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorConsumer.accept(error);
            }
        });

        queue.add(requestObjekti);
    }

    public void dohvatiPonude(Consumer<List<Ponuda>> responseConsumer, Consumer<VolleyError> errorConsumer) {
        String urlPonude = appContext.getString(R.string.offers_url);

        RequestQueue queue = Volley.newRequestQueue(appContext);

        JsonArrayRequest requestPonude = new JsonArrayRequest(Request.Method.GET, urlPonude, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Ponuda> listaPonuda = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject ponuda = response.getJSONObject(i);

                        int id = ponuda.getInt("id");
                        String naslov = ponuda.getString("naslov");
                        String opis = ponuda.getString("opis");
                        Float cijena = (float)ponuda.getDouble("cijena");
                        int brojTokena = ponuda.getInt("brojTokena");
                        int vrstaPonudeId = ponuda.getInt("vrstaPonudeId");
                        int objektId = ponuda.getInt("objektId");

                        Ponuda novaPonuda = new Ponuda();
                        novaPonuda.Id = id;
                        novaPonuda.naslov = naslov;
                        novaPonuda.opis = opis;
                        novaPonuda.cijena = cijena;
                        novaPonuda.brojTokena = brojTokena;
                        novaPonuda.vrstaPonudeId = vrstaPonudeId;
                        novaPonuda.objektId = objektId;

                        listaPonuda.add(novaPonuda);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                responseConsumer.accept(listaPonuda);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorConsumer.accept(error);
            }
        });

        queue.add(requestPonude);
    }
}

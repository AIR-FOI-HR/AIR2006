package com.example.drinkup.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.drinkup.R;
import com.example.drinkup.models.Korisnik;
import com.example.drinkup.models.LoginModel;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.Uloga;
import com.example.drinkup.models.VrstaPonude;
import com.example.drinkup.util.StringRequestWithJsonObjectBody;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RequestService {

    private Context appContext;

    public RequestService(Context context){
        appContext =  context;
    }

    public void fetchAvailableRoles(final Consumer<Uloga[]> successObjectConsumer, final Consumer<VolleyError> failErrorConsumer) {
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

    public void SendRegistrationRequest(Korisnik data, final Consumer<String> successObjectConsumer, final Consumer<VolleyError> failErrorConsumer){
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

        StringRequestWithJsonObjectBody jsonObjectRequest =
                new StringRequestWithJsonObjectBody(Request.Method.POST, url, MyData,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
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

    public void SendLoginRequest(LoginModel login, final Consumer<JSONObject> successObjectConsumer, final Consumer<VolleyError> failErrorConsumer){
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

    public void SendResetPasswordRequest(String email, final Consumer<JSONObject> successObjectConsumer, final Consumer<VolleyError> failErrorConsumer){
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

    public void fetchAllOfferTypes(final Consumer<List<VrstaPonude>> responseConsumer, final Consumer<VolleyError> errorConsumer) {
        String url = appContext.getString(R.string.offer_categories_url);
        RequestQueue queue = Volley.newRequestQueue(appContext);

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                List<VrstaPonude> vrstePonuda = new ArrayList<>();
                                for (int i=0; i<response.length(); i++) {
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        VrstaPonude vrstaPonude = new VrstaPonude();
                                        vrstaPonude.setId(jsonObject.getInt("id"));
                                        vrstaPonude.setNaziv(jsonObject.getString("naziv"));
                                        vrstePonuda.add(vrstaPonude);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                responseConsumer.accept(vrstePonuda);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                errorConsumer.accept(error);
                            }
                        });

        queue.add(jsonArrayRequest);
    }

    public void fetchBars(final Consumer<Objekt[]> successObjectConsumer, final Consumer<VolleyError> failErrorConsumer) {
        RequestQueue queue = Volley.newRequestQueue(appContext);
        String url = appContext.getString(R.string.get_all_bars_url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Objekt[] bars = new Gson().fromJson(response, Objekt[].class);
                        successObjectConsumer.accept(bars);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                failErrorConsumer.accept(error);
            }
        });

        queue.add(stringRequest);
    }

    public void assignEmployeeToBar(int barId, int userId) {
        String url = appContext.getString(R.string.assign_bar_employee_url);
        RequestQueue queue = Volley.newRequestQueue(appContext);

        JSONObject MyData = new JSONObject();
        try {
            MyData.put("objektId", barId);
            MyData.put("korisnikId", userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, MyData, null, null);

        queue.add(jsonObjectRequest);
    }

    public void createOffer(Ponuda ponuda, Integer barId, final Consumer<JSONObject> successObjectConsumer, final Consumer<VolleyError> failErrorConsumer) {
        String url = appContext.getString(R.string.create_offer_url);
        RequestQueue queue = Volley.newRequestQueue(appContext);

        JSONObject MyData = new JSONObject();
        try {
            MyData.put("naslov", ponuda.getNaslov());
            MyData.put("opis", ponuda.getOpis());
            MyData.put("cijena", ponuda.getCijena());
            MyData.put("brojTokena", ponuda.getBrojTokena());
            MyData.put("vrstaPonudeId", ponuda.getVrstaPonudeId());
            MyData.put("objektId", barId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, MyData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        successObjectConsumer.accept(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        failErrorConsumer.accept(error);
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }

    public void fetchWorkplaceOfUser(int userId, final Consumer<Integer> responseConsumer, final Runnable noBarsFoundHandler) {
        String url = MessageFormat.format(appContext.getString(R.string.bar_of_employee_url), userId);

        RequestQueue queue = Volley.newRequestQueue(appContext);

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject userWithBarJsonObject = response.getJSONObject(0);
                                    int barId = userWithBarJsonObject.getInt("objektId");
                                    responseConsumer.accept(barId);
                                } catch (JSONException e) {
                                    noBarsFoundHandler.run();
                                }
                            }
                        }, null);

        queue.add(jsonArrayRequest);
    }

    public void addTokenToUser(int userId, int offerId) {
        String url = appContext.getString(R.string.add_token_url);
        RequestQueue queue = Volley.newRequestQueue(appContext);

        JSONObject MyData = new JSONObject();
        try {
            MyData.put("ponudaId", offerId);
            MyData.put("korisnikId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, MyData, null, null);

        queue.add(jsonObjectRequest);
    }

    public void retrieveOfferDetails(String tokenId, final Consumer<JSONObject> jsonObjectConsumer, final Consumer<VolleyError> errorConsumer) {
        String urlTokenDetails = MessageFormat.format(appContext.getString(R.string.token_details_url), tokenId);
        RequestQueue queue = Volley.newRequestQueue(appContext);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlTokenDetails, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() == 0) {
                    jsonObjectConsumer.accept(null);
                }
                else {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
                        jsonObjectConsumer.accept(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorConsumer.accept(error);
            }
        });

        queue.add(jsonArrayRequest);
    }

    public void redeemToken(String tokenId, final Consumer<JSONObject> jsonObjectConsumer, final Consumer<VolleyError> errorConsumer) {
        String urlTokenDetails = MessageFormat.format(appContext.getString(R.string.token_activation_url), tokenId);
        RequestQueue queue = Volley.newRequestQueue(appContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlTokenDetails, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonObjectConsumer.accept(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorConsumer.accept(error);
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void obrisiToken(String id, final Consumer<JSONObject> successObjectConsumer, final Consumer<VolleyError> errorConsumer) {
        String urlPonude = appContext.getString(R.string.delete_token_url) + id;

        RequestQueue queue = Volley.newRequestQueue(appContext);

        JsonObjectRequest requestDeleteToken = new JsonObjectRequest(Request.Method.DELETE, urlPonude, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                successObjectConsumer.accept(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorConsumer.accept(error);
            }
        });

        queue.add(requestDeleteToken);
    }

    public void editOffer(Ponuda ponuda, Consumer<JSONObject> jsonObjectConsumer, Consumer<VolleyError> volleyErrorConsumer) {
        String url = appContext.getString(R.string.edit_offer_url);
        RequestQueue queue = Volley.newRequestQueue(appContext);

        JSONObject MyData = new JSONObject();
        try {
            MyData.put("id", ponuda.getId());
            MyData.put("naslov", ponuda.getNaslov());
            MyData.put("opis", ponuda.getOpis());
            MyData.put("cijena", ponuda.getCijena());
            MyData.put("brojTokena", ponuda.getBrojTokena());
            MyData.put("vrstaPonudeId", ponuda.getVrstaPonudeId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, MyData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jsonObjectConsumer.accept(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorConsumer.accept(error);
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }

    public void deleteOffer(String id, Consumer<JSONObject> successObjectConsumer, Consumer<VolleyError> errorConsumer) {
        String url = appContext.getString(R.string.delete_offer_url) + id;
        RequestQueue queue = Volley.newRequestQueue(appContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    successObjectConsumer.accept(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorConsumer.accept(error);
            }
        });

        queue.add(jsonObjectRequest);
    }
}

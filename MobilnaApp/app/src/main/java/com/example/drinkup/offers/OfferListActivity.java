package com.example.drinkup.offers;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.drinkup.R;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfferListActivity extends AppCompatActivity {
    private Integer currentUserId;
    private Integer currentRoleId;
    LinearLayout linearLayout;
    private RequestQueue mQueue;
    private List<Ponuda> listaPonuda = new ArrayList<>();
    private List<Objekt> listaObjekata = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.offer_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void handleMenuItemSelection(SubMenu menu, MenuItem selectedItem) {
        for (int i=0; i<menu.size(); i++) {
            MenuItem eachOption = menu.getItem(i);
            if (eachOption == selectedItem) {
                if (selectedItem.isChecked()) {
                    selectedItem.setChecked(false);
                }
                else {
                    selectedItem.setChecked(true);
                }
            } else {
                eachOption.setChecked(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offer_list);

        linearLayout = findViewById(R.id.linear_layout);

        mQueue = Volley.newRequestQueue(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navigation_icon);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(R.string.offer_list_activity_title);

        /*if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                currentUserId = extras.getInt("userId");
                currentRoleId = extras.getInt("roleId");
            }
        }
        else {
            currentUserId = savedInstanceState.getInt("userId");
            currentRoleId = savedInstanceState.getInt("roleId");
        }*/

        dohvatiObjekte();
        dohvatiPonude();
    }

    private void dohvatiObjekte() {
        String urlObjekti = "https://air2006.azurewebsites.net/api/objekt?pageSize=100";

        JsonArrayRequest requestObjekti = new JsonArrayRequest(Request.Method.GET, urlObjekti, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

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
                        noviObjekt.setId(idObjekta);
                        noviObjekt.setNaziv(naziv);
                        noviObjekt.setGrad(grad);
                        noviObjekt.setUlica(ulica);
                        noviObjekt.setAdresa(adresa);
                        noviObjekt.setRadnoVrijeme(radnoVrijeme);
                        noviObjekt.setKontakt(kontakt);
                        noviObjekt.setLongituda(longituda);
                        noviObjekt.setLatituda(latituda);
                        noviObjekt.setAktivan(aktivan);

                        listaObjekata.add(noviObjekt);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(requestObjekti);
    }

    private void dohvatiPonude() {
        String urlPonude = getString(R.string.offers_url);

        JsonArrayRequest requestPonude = new JsonArrayRequest(Request.Method.GET, urlPonude, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject ponuda = response.getJSONObject(i);

                        int id = ponuda.getInt("id");
                        String naslov = ponuda.getString("naslov");
                        String opis = ponuda.getString("opis");
                        Float cijena = (float)ponuda.getDouble("cijena");
                        int brojTokena = ponuda.getInt("brojTokena");
                        int vrstaPonudeId = ponuda.getInt("vrstaPonudeId");
                        int objektId = ponuda.getInt("objektId");

                        Ponuda novaPonuda = new Ponuda();
                        novaPonuda.setId(id);
                        novaPonuda.setNaslov(naslov);
                        novaPonuda.setOpis(opis);
                        novaPonuda.setCijena(cijena);
                        novaPonuda.setBrojTokena(brojTokena);
                        novaPonuda.setVrstaPonude(vrstaPonudeId);
                        novaPonuda.setObjektId(objektId);

                        listaPonuda.add(novaPonuda);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                prikaziPonude();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(requestPonude);
    }

    private void prikaziPonude() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1050, 350);
        params.setMargins(0, 10, 0,0);
        params.gravity = 1;

        for (int i = 0; i < listaPonuda.size(); i++) {
            String opis = listaPonuda.get(i).getOpis();
            Float cijena = listaPonuda.get(i).getCijena();
            int brojTokena = listaPonuda.get(i).getBrojTokena();
            int objektId = listaPonuda.get(i).getObjektId();
            String nazivObjekta = "";

            for (Objekt o: listaObjekata) {
                if(o.getId() == objektId) {
                    nazivObjekta = o.naziv;
                    break;
                }
            }

            TextView textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setPadding(30, 30, 30, 30);
            textView.setBackground(getDrawable(R.drawable.data_containter));
            textView.setTextSize(22);
            textView.setTextColor(Color.WHITE);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            textView.append(opis + " - " + String.valueOf(cijena) + "kn\n");
            textView.append("PREOSTALO " + String.valueOf(brojTokena) + "\n\n");
            textView.append(nazivObjekta);
            linearLayout.addView(textView);
        }
    }
}
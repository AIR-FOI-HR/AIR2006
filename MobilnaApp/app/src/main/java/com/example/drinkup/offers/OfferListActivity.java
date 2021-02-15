package com.example.drinkup.offers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.Gravity;
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
import com.example.drinkup.login.LoginActivity;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.tokens.TokenListActivity;

import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OfferListActivity extends AppCompatActivity {
    private Integer currentUserId;
    private Integer currentRoleId;
    LinearLayout linearLayout;
    private Button MyToken;
    private RequestQueue mQueue;
    private List<Ponuda> listaPonuda = new ArrayList<>();
    private List<Objekt> listaObjekata = new ArrayList<>();
    private List<Objekt> listaObjekata2 = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.offer_list_menu, menu);

        menu.findItem(R.id.sortAscending).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                linearLayout.removeAllViews();
                Collections.sort(listaPonuda, (ponuda, p2) -> ponuda.getCijena().compareTo(p2.getCijena()));
                prikaziPonude(listaPonuda,listaObjekata);
                return true;
            }
        });
        menu.findItem(R.id.sortDescending).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                linearLayout.removeAllViews();
                Collections.sort(listaPonuda, (ponuda, p2) -> ponuda.getCijena().compareTo(p2.getCijena()));
                Collections.reverse(listaPonuda);
                prikaziPonude(listaPonuda,listaObjekata);
                return true;
            }
        });
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

        MyToken =(Button)findViewById(R.id.tokenButton);
        currentUserId = (int) getIntent().getSerializableExtra("userId");

        linearLayout = findViewById(R.id.linear_layout);

        mQueue = Volley.newRequestQueue(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navigation_icon);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(R.string.offer_list_activity_title);

        if (savedInstanceState == null) {
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
        }

        MyToken.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentSucces = new Intent(OfferListActivity.this, TokenListActivity.class);
                intentSucces.putExtra("userId", currentUserId);
                intentSucces.putExtra("roleId", currentRoleId);
                startActivity(intentSucces);
            }
        });

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

                prikaziPonude(listaPonuda, listaObjekata);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(requestPonude);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void prikaziPonude(List<Ponuda> listaPonuda, List<Objekt> listaObjekata) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1025, 350);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.setMargins(10, 20, 10,20);
        params.gravity = 1;
        Integer izborPozadine = 0;

        for (int i = 0; i < listaPonuda.size(); i++) {
            String opis = listaPonuda.get(i).getOpis();
            Float cijena = listaPonuda.get(i).getCijena();
            int brojTokena = listaPonuda.get(i).getBrojTokena();
            int objektId = listaPonuda.get(i).getObjektId();

            String nazivObjekta = "";
            final Ponuda ponuda = listaPonuda.get(i);
            for (Objekt o: listaObjekata) {
                if(o.getId() == objektId) {
                    nazivObjekta = o.naziv;
                    listaPonuda.get(i).setObjekt(o);
                    break;
                }
            }

            final String objekt = nazivObjekta;

            if(brojTokena > 0){
                LinearLayout containerLayout = new LinearLayout(this);
                containerLayout.setLayoutParams(params);
                containerLayout.setOrientation(LinearLayout.VERTICAL);

                TextView textViewPreostalo = new TextView(this);
                textViewPreostalo.setText("PREOSTALO\n" + String.valueOf(brojTokena));
                textViewPreostalo.setTextColor(Color.WHITE);
                textViewPreostalo.setTextSize(25);
                textViewPreostalo.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                textViewPreostalo.setPadding(30, 30, 30, 30);
                textViewPreostalo.setGravity(Gravity.RIGHT);
                containerLayout.addView(textViewPreostalo);

                TextView textViewObjekt = new TextView(this);
                textViewObjekt.setText(nazivObjekta);
                textViewObjekt.setTextColor(Color.WHITE);
                textViewObjekt.setTextSize(18);
                textViewObjekt.setPadding(40,0,0,0);
                containerLayout.addView(textViewObjekt);

                TextView textViewOpisCijena = new TextView(this);
                textViewOpisCijena.setText(opis + " - " + String.valueOf(cijena) + "kn");
                textViewOpisCijena.setTextColor(Color.WHITE);
                textViewOpisCijena.setTextSize(24);
                textViewOpisCijena.setPadding(40,0,0,0);
                containerLayout.addView(textViewOpisCijena);

                switch(izborPozadine){
                    case 0:
                        containerLayout.setBackground(getDrawable(R.drawable.background_offer_blue));
                        izborPozadine++;
                        break;
                    case 1:
                        containerLayout.setBackground(getDrawable(R.drawable.background_offer_orange));
                        izborPozadine++;
                        break;
                    case 2:
                        containerLayout.setBackground(getDrawable(R.drawable.background_offer_pink));
                        izborPozadine++;
                        break;
                    case 3:
                        containerLayout.setBackground(getDrawable(R.drawable.background_offer_purple));
                        izborPozadine++;
                        break;
                    case 4:
                        containerLayout.setBackground(getDrawable(R.drawable.background_offer_green));
                        izborPozadine++;
                        break;
                }

                if(izborPozadine > 4){
                    izborPozadine = 0;
                }

                linearLayout.addView(containerLayout);
                containerLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        offerDetails(ponuda, objekt);
                    }
                });
            }
        }
    }
    private void offerDetails(Ponuda ponuda , String objekt){
        Intent intentOfferDetails= new Intent(OfferListActivity.this, OfferDetailsActivity.class);
        intentOfferDetails.putExtra("ponuda",ponuda);
        intentOfferDetails.putExtra("objekt",objekt);
        intentOfferDetails.putExtra("userId", currentUserId);
        startActivity(intentOfferDetails);
    }
}

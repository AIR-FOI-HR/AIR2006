package com.example.drinkup.tokens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.drinkup.login.ForgotPasswordActivity;
import com.example.drinkup.login.LoginActivity;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.Token;
import com.example.drinkup.offers.OfferListActivity;
import com.example.drinkup.services.RequestService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class TokenListActivity extends AppCompatActivity {
    private Integer currentUserId;
    private Integer currentRoleId;
    LinearLayout linearLayout;
    private RequestQueue mQueue;
    private List<Token> listaTokena = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_token_list);

        linearLayout = findViewById(R.id.linear_layout);

        mQueue = Volley.newRequestQueue(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navigation_icon);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(R.string.token_list_activity_title);

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

        dohvatiTokene();
    }

    private void obrisiTokene(String id){

        RequestService rs = new RequestService(getApplicationContext());
        rs.obrisiToken(id,
                new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject response) {
                        finish();
                        startActivity(getIntent());
                    }
                }, new Consumer<VolleyError>() {
                    @Override
                    public void accept(VolleyError volleyError) {
                        finish();
                        startActivity(getIntent());
                    }
                });



    }

    private void dohvatiTokene() {
        String urlTokeni = "https://air2006.azurewebsites.net/api/token?pageSize=100";

        JsonArrayRequest requestTokeni = new JsonArrayRequest(Request.Method.GET, urlTokeni, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject token = response.getJSONObject(i);

                        String idTokena = token.getString("id");
                        int ponudaId = token.getInt("ponudaId");
                        JSONObject ponuda = token.getJSONObject("ponuda");
                        String datumKreiranja = token.getString("datumKreiranja");
                        Boolean iskoristen = token.getBoolean("iskoristen");
                        int korisnikId = token.getInt("korisnikId");
                        String qr = token.getString("qr");

                        @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(datumKreiranja);

                        int id = ponuda.getInt("id");
                        String naslov = ponuda.getString("naslov");
                        String opis = ponuda.getString("opis");
                        Float cijena = (float)ponuda.getDouble("cijena");
                        int brojTokena = ponuda.getInt("brojTokena");
                        int vrstaPonudeId = ponuda.getInt("vrstaPonudeId");
                        int objektId = ponuda.getInt("objektId");
                        JSONObject objekt = ponuda.getJSONObject("objekt");

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

                        Objekt objektTokena = new Objekt();
                        objektTokena.id = idObjekta;
                        objektTokena.naziv = naziv;
                        objektTokena.grad = grad;
                        objektTokena.ulica = ulica;
                        objektTokena.adresa = adresa;
                        objektTokena.radnoVrijeme = radnoVrijeme;
                        objektTokena.kontakt = kontakt;
                        objektTokena.longituda = longituda;
                        objektTokena.latituda = latituda;
                        objektTokena.aktivan = aktivan;

                        Ponuda ponudaTokena = new Ponuda();
                        ponudaTokena.setId(id);
                        ponudaTokena.setNaslov(naslov);
                        ponudaTokena.setOpis(opis);
                        ponudaTokena.setCijena(cijena);
                        ponudaTokena.setBrojTokena(brojTokena);
                        ponudaTokena.setVrstaPonude(vrstaPonudeId);
                        ponudaTokena.setObjektId(objektId);

                        Token noviToken = new Token();
                        noviToken.id = idTokena;
                        noviToken.ponudaId = ponudaId;
                        noviToken.ponuda = ponudaTokena;
                        noviToken.datumKreiranja = date;
                        noviToken.iskoristen = iskoristen;
                        noviToken.korisnikId = korisnikId;
                        noviToken.qr = qr;

                        listaTokena.add(noviToken);
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }

                prikaziTokene();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(requestTokeni);
    }

    private void prikaziTokene() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1050, 250);
        params.gravity = 1;
        Integer izborPozadine = 0;

        for (int i = 0; i < listaTokena.size(); i++) {
            if (listaTokena.get(i).korisnikId == currentUserId) {
                String opis = listaTokena.get(i).ponuda.getOpis();
                Float cijena = listaTokena.get(i).ponuda.getCijena();
                Date datumKreiranja = listaTokena.get(i).datumKreiranja;

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(datumKreiranja);
                calendar.add(Calendar.MINUTE, 150);

                Date istekTokena = calendar.getTime();

                long istekVremena = ((istekTokena.getTime() - System.currentTimeMillis()) / (1000 * 60)) % 60;

                if (istekVremena > 0) {

                    TextView textView = new TextView(this);
                    textView.setLayoutParams(params);

                    switch(izborPozadine){
                        case 0:
                            textView.setBackground(getDrawable(R.drawable.background_token_blue));
                            izborPozadine++;
                            break;
                        case 1:
                            textView.setBackground(getDrawable(R.drawable.background_token_orange));
                            izborPozadine++;
                            break;
                        case 2:
                            textView.setBackground(getDrawable(R.drawable.background_token_pink));
                            izborPozadine++;
                            break;
                        case 3:
                            textView.setBackground(getDrawable(R.drawable.background_token_purple));
                            izborPozadine++;
                            break;
                        case 4:
                            textView.setBackground(getDrawable(R.drawable.background_token_green));
                            izborPozadine++;
                            break;
                    }

                    if(izborPozadine > 4){
                        izborPozadine = 0;
                    }

                    textView.setTextSize(25);
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
                    textView.setPadding(50,40,0,40);
                    textView.setCompoundDrawablePadding(5);
                    textView.setTextColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.append(istekVremena + " m " + opis + " - " + String.valueOf(cijena) + "kn\n");
                    linearLayout.addView(textView);

                    Button button = new Button(this);
                    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(10, 10);
                    buttonParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    buttonParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    buttonParams.gravity = 1;
                    buttonParams.setMargins(0,20,0,20);
                    button.setText("Obriši!");
                    button.setTextColor(Color.rgb(51,51,51));
                    button.setLayoutParams(buttonParams);
                    button.setBackgroundColor(Color.WHITE);

                    final int index = i;
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            obrisiTokene(listaTokena.get(index).id);
                        }
                    });
                    linearLayout.addView(button);
                }
            }
        }
    }
}
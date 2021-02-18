package com.example.drinkup.tokens;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.drinkup.R;
import com.example.drinkup.employee.ui.list.OfferEditActivity;
import com.example.drinkup.guest.GuestMainActivity;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.Token;
import com.example.drinkup.services.RequestService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class TokenListFragment extends Fragment {
    private LinearLayout linearLayout;
    private RequestQueue mQueue;
    private List<Token> listaTokena;

    private GuestMainActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_token_list, container, false);
        activity = (GuestMainActivity) getActivity();
        activity.customizeActionBar(getString(R.string.token_list_fragment_title));

        linearLayout = root.findViewById(R.id.token_linear_layout);

        mQueue = Volley.newRequestQueue(getContext());

        return root;
    }

    @Override
    public void onResume(){
        dohvatiTokene();
        super.onResume();
    }

    private void dohvatiTokene() {
        String urlTokeni = MessageFormat.format("https://air2006.azurewebsites.net/api/token?filterColumn=korisnikId&filterValue={0}&filterOption=11", activity.getCurrentUserId());

        JsonArrayRequest requestTokeni = new JsonArrayRequest(Request.Method.GET, urlTokeni, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    listaTokena = new ArrayList<>();
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
                        ponudaTokena.setVrstaPonudeId(vrstaPonudeId);
                        ponudaTokena.setObjektId(objektId);
                        ponudaTokena.setObjekt(objektTokena);

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

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1050, 260);
        params.setMargins(10, 40, 10,40);
        params.gravity = 1;
        int brojTokena = 0;
        Integer izborPozadine = 0;
        linearLayout.removeAllViews();

        for (int i = 0; i < listaTokena.size(); i++) {
            Token token = listaTokena.get(i);
            String opis = token.ponuda.getOpis();
            float cijena = token.ponuda.getCijena();

            Date datumKreiranja = token.getDatumKreiranja();
            String naziv = token.ponuda.getObjekt().getNaziv();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(datumKreiranja);
            calendar.add(Calendar.MINUTE, 90);

            Date istekTokena = calendar.getTime();

            long istekVremena = ((istekTokena.getTime() - System.currentTimeMillis()) / (1000 * 60));

            if (istekVremena > 0) {
                brojTokena++;
                TextView textView = new TextView(activity);
                textView.setLayoutParams(params);

                switch(izborPozadine){
                    case 0:
                        textView.setBackground(getContext().getDrawable(R.drawable.background_token_blue));
                        izborPozadine++;
                        break;
                    case 1:
                        textView.setBackground(getContext().getDrawable(R.drawable.background_token_orange));
                        izborPozadine++;
                        break;
                    case 2:
                        textView.setBackground(getContext().getDrawable(R.drawable.background_token_pink));
                        izborPozadine++;
                        break;
                    case 3:
                        textView.setBackground(getContext().getDrawable(R.drawable.background_token_purple));
                        izborPozadine++;
                        break;
                    case 4:
                        textView.setBackground(getContext().getDrawable(R.drawable.background_token_green));
                        izborPozadine++;
                        break;
                }

                if(izborPozadine > 4){
                    izborPozadine = 0;
                }

                textView.setTextSize(22);
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_time, 0, 0, 0);
                textView.setCompoundDrawablePadding(40);
                textView.setPadding(50,40,0,40);
                textView.setTextColor(Color.WHITE);
                textView.append(opis + " - " + cijena + "kn" + "\n" + naziv);

                linearLayout.addView(textView);

                TextView leftTimeTextView = new TextView(activity);
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(10, 10);
                textViewParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                textViewParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                textViewParams.gravity = Gravity.LEFT;
                textViewParams.setMargins(86,-110,0,4);
                leftTimeTextView.setText(istekVremena + " m");
                leftTimeTextView.setTextColor(Color.WHITE);
                leftTimeTextView.setLayoutParams(textViewParams);

                linearLayout.addView(leftTimeTextView);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tokenDetails(token);
                    }
                });

            }
        }

        if (brojTokena == 0){
            TextView emptyTextView = new TextView(activity);
            emptyTextView.setLayoutParams(params);
            emptyTextView.setGravity(Gravity.CENTER);
            emptyTextView.setText("Nema aktivnih tokena");
            emptyTextView.setTextSize(22);
            emptyTextView.setTextColor(Color.WHITE);
            linearLayout.addView(emptyTextView);
        }
    }

    private void tokenDetails(Token token) {
        Intent intentTokenDetails = new Intent(activity, TokenDetailsActivity.class);
        intentTokenDetails.putExtra("token", token);
        intentTokenDetails.putExtra("userId", activity.getCurrentUserId());
        startActivity(intentTokenDetails);
        }
}

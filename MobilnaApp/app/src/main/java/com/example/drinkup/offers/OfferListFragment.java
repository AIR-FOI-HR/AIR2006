package com.example.drinkup.offers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.drinkup.R;
import com.example.drinkup.guest.GuestMainActivity;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfferListFragment extends Fragment {
    private LinearLayout linearLayout;
    private RequestQueue mQueue;
    private List<Ponuda> listaPonuda = new ArrayList<>();
    private List<Objekt> listaObjekata = new ArrayList<>();

    private GuestMainActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offer_list, container, false);
        activity = (GuestMainActivity) getActivity();
        activity.customizeActionBar(getString(R.string.offer_list_fragment_title));

        setHasOptionsMenu(true);

        linearLayout = root.findViewById(R.id.offer_linear_layout);

        mQueue = Volley.newRequestQueue(getContext());

        dohvatiObjekte();
        dohvatiPonude();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.offer_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
                LinearLayout containerLayout = new LinearLayout(activity);
                containerLayout.setLayoutParams(params);
                containerLayout.setOrientation(LinearLayout.VERTICAL);

                TextView textViewPreostalo = new TextView(activity);
                textViewPreostalo.setText("PREOSTALO\n" + String.valueOf(brojTokena));
                textViewPreostalo.setTextColor(Color.WHITE);
                textViewPreostalo.setTextSize(25);
                textViewPreostalo.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                textViewPreostalo.setPadding(30, 30, 30, 30);
                textViewPreostalo.setGravity(Gravity.RIGHT);
                containerLayout.addView(textViewPreostalo);

                TextView textViewObjekt = new TextView(activity);
                textViewObjekt.setText(nazivObjekta);
                textViewObjekt.setTextColor(Color.WHITE);
                textViewObjekt.setTextSize(18);
                textViewObjekt.setPadding(40,0,0,0);
                containerLayout.addView(textViewObjekt);

                TextView textViewOpisCijena = new TextView(activity);
                textViewOpisCijena.setText(opis + " - " + String.format("%.2f", cijena) + "kn");
                textViewOpisCijena.setTextColor(Color.WHITE);
                textViewOpisCijena.setTextSize(24);
                textViewOpisCijena.setPadding(40,0,0,0);
                containerLayout.addView(textViewOpisCijena);

                switch(izborPozadine){
                    case 0:
                        containerLayout.setBackground(getContext().getDrawable(R.drawable.background_offer_blue));
                        izborPozadine++;
                        break;
                    case 1:
                        containerLayout.setBackground(getContext().getDrawable(R.drawable.background_offer_orange));
                        izborPozadine++;
                        break;
                    case 2:
                        containerLayout.setBackground(getContext().getDrawable(R.drawable.background_offer_pink));
                        izborPozadine++;
                        break;
                    case 3:
                        containerLayout.setBackground(getContext().getDrawable(R.drawable.background_offer_purple));
                        izborPozadine++;
                        break;
                    case 4:
                        containerLayout.setBackground(getContext().getDrawable(R.drawable.background_offer_green));
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
        Intent intentOfferDetails= new Intent(activity, OfferDetailsActivity.class);
        intentOfferDetails.putExtra("ponuda",ponuda);
        intentOfferDetails.putExtra("objekt",objekt);
        intentOfferDetails.putExtra("userId", activity.getCurrentUserId());
        startActivity(intentOfferDetails);
    }
}

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
import com.example.drinkup.models.VrstaPonude;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OfferListFragment extends Fragment {
    private LinearLayout linearLayout;
    private RequestQueue mQueue;
    private List<Ponuda> listaPonuda = new ArrayList<>();
    private List<Ponuda> novaLista = new ArrayList<>();

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

        dohvatiPonude();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.offer_list_menu, menu);
        menu.findItem(R.id.sortAscending).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                linearLayout.removeAllViews();
                Collections.sort(listaPonuda, (ponuda, p2) -> ponuda.getCijena().compareTo(p2.getCijena()));
                prikaziPonude(listaPonuda);
                return true;
            }
        });
        menu.findItem(R.id.sortDescending).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                linearLayout.removeAllViews();
                Collections.sort(listaPonuda, (ponuda, p2) -> ponuda.getCijena().compareTo(p2.getCijena()));
                Collections.reverse(listaPonuda);
                prikaziPonude(listaPonuda);
                return true;
            }
        });
        menu.findItem(R.id.typeKombinacija).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                linearLayout.removeAllViews();
                novaLista.clear();
                for(int i=0; i<listaPonuda.size(); i++)
                    if(listaPonuda.get(i).getVrstaPonudeId() == 0)
                        novaLista.add(listaPonuda.get(i));
                prikaziPonude(novaLista);
                return true;
            }
        });
        menu.findItem(R.id.typeAkcija).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                linearLayout.removeAllViews();
                novaLista.clear();
                for(int i=0; i<listaPonuda.size(); i++)
                    if(listaPonuda.get(i).getVrstaPonudeId() == 1)
                        novaLista.add(listaPonuda.get(i));
                prikaziPonude(novaLista);
                return true;
            }
        });
        menu.findItem(R.id.typeGratis).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                linearLayout.removeAllViews();
                novaLista.clear();
                for(int i=0; i<listaPonuda.size(); i++)
                    if(listaPonuda.get(i).getVrstaPonudeId() == 2)
                        novaLista.add(listaPonuda.get(i));
                prikaziPonude(novaLista);
                return true;
            }
        });
        menu.findItem(R.id.typeKolac).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                linearLayout.removeAllViews();
                novaLista.clear();
                for(int i=0; i<listaPonuda.size(); i++)
                    if(listaPonuda.get(i).getVrstaPonudeId() == 3)
                        novaLista.add(listaPonuda.get(i));
                prikaziPonude(novaLista);
                return true;
            }
        });
        menu.findItem(R.id.typeSve).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                linearLayout.removeAllViews();
                prikaziPonude(listaPonuda);
                return true;
            }
        });
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

    private void dohvatiPonude() {
        String urlPonude = getString(R.string.offers_url);

        JsonArrayRequest requestPonude = new JsonArrayRequest(Request.Method.GET, urlPonude, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject ponuda = response.getJSONObject(i);
                        Gson gson = new Gson();

                        int id = ponuda.getInt("id");
                        String naslov = ponuda.getString("naslov");
                        String opis = ponuda.getString("opis");
                        Float cijena = (float)ponuda.getDouble("cijena");
                        int brojTokena = ponuda.getInt("brojTokena");
                        int vrstaPonudeId = ponuda.getInt("vrstaPonudeId");
                        int objektId = ponuda.getInt("objektId");
                        Objekt objekt = gson.fromJson(ponuda.getString("objekt"), Objekt.class);
                        VrstaPonude vrstaPonude = gson.fromJson(ponuda.getString("vrstaPonude"), VrstaPonude.class);

                        Ponuda novaPonuda = new Ponuda();
                        novaPonuda.setId(id);
                        novaPonuda.setNaslov(naslov);
                        novaPonuda.setOpis(opis);
                        novaPonuda.setCijena(cijena);
                        novaPonuda.setBrojTokena(brojTokena);
                        novaPonuda.setVrstaPonudeId(vrstaPonudeId);
                        novaPonuda.setObjektId(objektId);
                        novaPonuda.setObjekt(objekt);
                        novaPonuda.setVrstaPonude(vrstaPonude);

                        listaPonuda.add(novaPonuda);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                prikaziPonude(listaPonuda);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(requestPonude);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void prikaziPonude(List<Ponuda> listaPonuda) {

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
            String nazivObjekta = listaPonuda.get(i).getObjekt().getNaziv();
            final Ponuda ponuda = listaPonuda.get(i);

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
                        offerDetails(ponuda, nazivObjekta);
                    }
                });
            }
        }
    }
    private void offerDetails(Ponuda ponuda, String nazivObjekta) {
        Intent intentOfferDetails= new Intent(activity, OfferDetailsActivity.class);
        intentOfferDetails.putExtra("ponuda", ponuda);
        intentOfferDetails.putExtra("nazivObjekta", nazivObjekta);
        intentOfferDetails.putExtra("userId", activity.getCurrentUserId());
        startActivity(intentOfferDetails);
    }
}

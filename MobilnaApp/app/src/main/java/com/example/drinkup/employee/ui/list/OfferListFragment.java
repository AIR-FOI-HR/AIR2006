package com.example.drinkup.employee.ui.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.example.drinkup.employee.EmployeeMainActivity;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.VrstaPonude;
import com.example.drinkup.offers.OfferDetailsActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getDrawable;

public class OfferListFragment extends Fragment {
    LinearLayout linearLayout;
    private RequestQueue mQueue;
    private List<Ponuda> listaPonuda;
    private EmployeeMainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offer_list, container, false);
        activity = (EmployeeMainActivity) getActivity();
        activity.customizeActionBar(getString(R.string.offer_list_fragment_title));
        mQueue = Volley.newRequestQueue(this.getContext());
        linearLayout = root.findViewById(R.id.offer_linear_layout);

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        dohvatiPonude(activity.getWorkingBarId());
    }

    public void dohvatiPonude(int objektId) {
        String urlPonude = getString(R.string.offers_url);
        urlPonude += "&filterOption=11&filterColumn=ObjektId&filterValue=" + objektId;
        listaPonuda = new ArrayList<>();
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
        linearLayout.removeAllViewsInLayout();

        for (int i = 0; i < listaPonuda.size(); i++) {
            String opis = listaPonuda.get(i).getOpis();
            Float cijena = listaPonuda.get(i).getCijena();
            int brojTokena = listaPonuda.get(i).getBrojTokena();
            int objektId = listaPonuda.get(i).getObjektId();

            final Ponuda ponuda = listaPonuda.get(i);
            final String nazivObjekta = ponuda.getObjekt().getNaziv();

            LinearLayout containerLayout = new LinearLayout(this.getContext());
            containerLayout.setLayoutParams(params);
            containerLayout.setOrientation(LinearLayout.VERTICAL);

            TextView textViewPreostalo = new TextView(this.getContext());
            textViewPreostalo.setText("PREOSTALO\n" + String.valueOf(brojTokena));
            textViewPreostalo.setTextColor(Color.WHITE);
            textViewPreostalo.setTextSize(25);
            textViewPreostalo.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            textViewPreostalo.setPadding(30, 30, 30, 30);
            textViewPreostalo.setGravity(Gravity.RIGHT);
            containerLayout.addView(textViewPreostalo);

            TextView textViewObjekt = new TextView(this.getContext());
            textViewObjekt.setText(nazivObjekta);
            textViewObjekt.setTextColor(Color.WHITE);
            textViewObjekt.setTextSize(18);
            textViewObjekt.setPadding(40,0,0,0);
            containerLayout.addView(textViewObjekt);

            TextView textViewOpisCijena = new TextView(this.getContext());
            textViewOpisCijena.setText(opis + " - " + String.valueOf(cijena) + "kn");
            textViewOpisCijena.setTextColor(Color.WHITE);
            textViewOpisCijena.setTextSize(24);
            textViewOpisCijena.setPadding(40,0,0,0);
            containerLayout.addView(textViewOpisCijena);

            switch(izborPozadine){
                case 0:
                    containerLayout.setBackground(getDrawable(this.getContext(), R.drawable.background_offer_blue));
                    izborPozadine++;
                    break;
                case 1:
                    containerLayout.setBackground(getDrawable(this.getContext(), R.drawable.background_offer_orange));
                    izborPozadine++;
                    break;
                case 2:
                    containerLayout.setBackground(getDrawable(this.getContext(), R.drawable.background_offer_pink));
                    izborPozadine++;
                    break;
                case 3:
                    containerLayout.setBackground(getDrawable(this.getContext(), R.drawable.background_offer_purple));
                    izborPozadine++;
                    break;
                case 4:
                    containerLayout.setBackground(getDrawable(this.getContext(), R.drawable.background_offer_green));
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
                    offerDetails(ponuda);
                }
            });
        }
    }

    private void offerDetails(Ponuda ponuda) {
        Intent intentOfferDetails= new Intent(activity, OfferEditActivity.class);
        intentOfferDetails.putExtra("ponuda", ponuda);
        intentOfferDetails.putExtra("userId", activity.getCurrentUserId());
        startActivity(intentOfferDetails);
    }
}

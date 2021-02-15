package com.example.drinkup.offers;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.drinkup.R;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.services.RequestService;

import hr.foi.air.core.TokenJudge;
import hr.foi.air.location.LocationJudge;

public class OfferDetailsActivity extends AppCompatActivity {

    RequestService service;
    int currentUserId;
    Ponuda ponuda;
    TokenJudge tokenJudge = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponuda_detalji);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(R.string.offer_details_activity_title);

        service = new RequestService(getApplicationContext());

        ponuda = (Ponuda) getIntent().getSerializableExtra("ponuda");
        currentUserId = (int) getIntent().getSerializableExtra("userId");
        String nazivObjekta = (String) getIntent().getSerializableExtra("nazivObjekta");
        TextView textViewNaslov = findViewById(R.id.textViewNaslov);
        textViewNaslov.setText(ponuda.getNaslov());

        TextView textViewOpis = findViewById(R.id.textViewOpis);
        textViewOpis.setText(ponuda.getOpis());

        TextView textViewCijena = findViewById(R.id.textViewCijena);
        textViewCijena.setText(String.format("%.2f", ponuda.getCijena()));

        TextView textViewTokeni = findViewById(R.id.textViewTokeni);
        textViewTokeni.setText(String.valueOf(ponuda.getBrojTokena()));

        TextView textViewObjekt = findViewById(R.id.textViewObjekt);
        textViewObjekt.setText(nazivObjekta);


        Objekt objektPonude = ponuda.getObjekt();
        tokenJudge = new LocationJudge(this, objektPonude.getLatituda(), objektPonude.getLongituda());
    }

    public void onButtonClick(View view) {
        if (tokenJudge != null){
            if (tokenJudge.canGetToken()){
                service.addTokenToUser(currentUserId, ponuda.getId());
                Toast.makeText(this, "Token uspješno preuzet.", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, tokenJudge.getDenialMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
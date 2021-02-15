package com.example.drinkup.offers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.drinkup.R;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.services.RequestService;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import hr.foi.air.core.GenerousJudge;
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

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navigation_icon);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(R.string.offer_details_activity_title);

        service = new RequestService(getApplicationContext());

        ponuda = (Ponuda) getIntent().getSerializableExtra("ponuda");
        currentUserId = (int) getIntent().getSerializableExtra("userId");
        String objekt = (String) getIntent().getSerializableExtra("objekt");
        TextView textViewNaslov = (TextView) findViewById(R.id.textViewNaslov);
        textViewNaslov.setText(""+ponuda.getNaslov());

        TextView textViewOpis = (TextView) findViewById(R.id.textViewOpis);
        textViewOpis.setText(""+ponuda.getOpis());

        TextView textViewCijena = (TextView) findViewById(R.id.textViewCijena);
        textViewCijena.setText(""+ponuda.getCijena());

        TextView textViewTokeni = (TextView) findViewById(R.id.textViewTokeni);
        textViewTokeni.setText(""+ponuda.getBrojTokena());

        TextView textViewObjekt = (TextView) findViewById(R.id.textViewObjekt);
        textViewObjekt.setText(objekt);


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
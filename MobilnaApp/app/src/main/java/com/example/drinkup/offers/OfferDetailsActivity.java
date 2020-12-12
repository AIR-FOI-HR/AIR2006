package com.example.drinkup.offers;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.VrstaPonude;
import com.example.drinkup.services.RequestService;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.function.Consumer;

public class OfferDetailsActivity extends AppCompatActivity {

    RequestService service;
    int currentUserId;
    Ponuda ponuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponuda_detalji);

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
    }

    public void onButtonClick(View view) {
        service.addTokenToUser(currentUserId, ponuda.getId());
    }
}
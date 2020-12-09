package com.example.drinkup.offers;

import androidx.appcompat.app.AppCompatActivity;
import com.example.drinkup.R;
import com.example.drinkup.models.Ponuda;

import android.os.Bundle;
import android.widget.TextView;

public class OfferDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponuda_detalji);

        Ponuda ponuda = (Ponuda) getIntent().getSerializableExtra("ponuda");
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
}
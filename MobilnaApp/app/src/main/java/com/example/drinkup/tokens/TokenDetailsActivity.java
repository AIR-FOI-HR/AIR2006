package com.example.drinkup.tokens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.employee.ui.list.OfferEditActivity;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.Token;
import com.example.drinkup.services.RequestService;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

public class TokenDetailsActivity extends AppCompatActivity {

    RequestService service;
    int currentUserId;
    Token token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_detalji);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(R.string.token_details_activity_title);

        service = new RequestService(getApplicationContext());

        token = (Token) getIntent().getSerializableExtra("token");

        currentUserId = (int) getIntent().getSerializableExtra("userId");

        Ponuda ponuda = token.getPonuda();
        Objekt objektPonude = ponuda.getObjekt();

        TextView textViewNaslovPonude = findViewById(R.id.textViewPonudaNaslov);
        textViewNaslovPonude.setText(ponuda.getNaslov());

        Bitmap bitmapaQrKodSlike = getBitmapFromBase64String(token.getQr());
        ImageView imageViewQrKodTokena = findViewById(R.id.slikaQrKoda);
        imageViewQrKodTokena.setImageBitmap(bitmapaQrKodSlike);

        TextView textViewOpisPonude = findViewById(R.id.textViewPonudaOpis);
        textViewOpisPonude.setText(ponuda.getOpis());

        TextView textViewNazivObjekta = findViewById(R.id.textViewPonudaObjektNaziv);
        textViewNazivObjekta.setText(objektPonude.getNaziv());

        TextView textViewAdresaObjekta = findViewById(R.id.textViewPonudaObjektAdresa);
        textViewAdresaObjekta.setText(objektPonude.getAdresa());

        TextView textViewVrijemeIstekaTokena = findViewById(R.id.textViewTokenVrijemeIsteka);
        textViewVrijemeIstekaTokena.setText(getExpirationDate(token.getDatumKreiranja()));

        TextView textViewCijenaPonude = findViewById(R.id.textViewPonudaCijena);
        textViewCijenaPonude.setText(String.format("%.2f kn", ponuda.getCijena()));

        Button deleteTokenButton = findViewById(R.id.deleteToken);
        deleteTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TokenDetailsActivity.this)
                        .setTitle("Obriši token")
                        .setMessage("Sigurno želite obrisati token?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                obrisiToken();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void obrisiToken(){
        RequestService rs = new RequestService(this);
        rs.obrisiToken(token.getId(),
                new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject response) {
                        onBackPressed();
                    }
                }, new Consumer<VolleyError>() {
                    @Override
                    public void accept(VolleyError volleyError) {
                    }
                });
    }

    private String getExpirationDate(Date datumKreiranjaTokena) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datumKreiranjaTokena);
        calendar.add(Calendar.MINUTE, 150);
        Date datumIstekaTokena = calendar.getTime();
        return dateFormat.format(datumIstekaTokena);
    }

    private Bitmap getBitmapFromBase64String(String base64EncodedString) {
        byte[] decodedString = Base64.decode(base64EncodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
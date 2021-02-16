package com.example.drinkup.employee.ui.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.VrstaPonude;
import com.example.drinkup.services.RequestService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class OfferEditActivity extends AppCompatActivity {
    private Ponuda ponuda;
    private final Map<String, Integer> offerTypeIdsByTheirNames = new HashMap<>();
    private int barTitle = R.string.offer_edit_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_edit);

        ponuda = (Ponuda) getIntent().getSerializableExtra("ponuda");

        new RequestService(this).fetchAllOfferTypes(
                new Consumer<List<VrstaPonude>>() {
                    @Override
                    public void accept(List<VrstaPonude> vrstePonuda) {
                        OfferEditActivity.this.displayFetchedOfferTypes(vrstePonuda);
                    }
                },
                new Consumer<VolleyError>() {
                    @Override
                    public void accept(VolleyError volleyError) {
                        Toast.makeText(OfferEditActivity.this, R.string.offer_types_retrieval_error_on_offer_creation, Toast.LENGTH_LONG).show();
                        findViewById(R.id.createOffer).setClickable(false);
                    }
                }
        );

        final EditText title = findViewById(R.id.title);
        title.setText(ponuda.getNaslov());
        final EditText description = findViewById(R.id.description);
        description.setText(ponuda.getOpis());
        final EditText price = findViewById(R.id.price);
        price.setText(""+ponuda.getCijena());
        final EditText numberOfTokens = findViewById(R.id.numberOfTokens);
        numberOfTokens.setText(""+ponuda.getBrojTokena());
        final Spinner offerTypeOptions = findViewById(R.id.offerTypeOptions);
        final Button editOfferButton = findViewById(R.id.editOffer);
        final Button cancelOfferButton = findViewById(R.id.cancelOffer);
        final Button deleteOfferButton = findViewById(R.id.deleteOffer);

        editOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !numberOfTokens.getText().toString().isEmpty()) {
                    String selectedOfferTypeName = offerTypeOptions.getSelectedItem().toString();
                    Integer selectedOfferTypeId = offerTypeIdsByTheirNames.get(selectedOfferTypeName);
                    ponuda.setNaslov(title.getText().toString());
                    ponuda.setOpis(description.getText().toString());
                    ponuda.setCijena(Float.parseFloat(price.getText().toString()));
                    ponuda.setBrojTokena(Integer.parseInt(numberOfTokens.getText().toString()));
                    ponuda.setVrstaPonudeId(selectedOfferTypeId);
                    new RequestService(OfferEditActivity.this).editOffer(ponuda,
                            new Consumer<JSONObject>() {
                                @Override
                                public void accept(JSONObject jsonObject) {
                                    Toast.makeText(OfferEditActivity.this, getString(R.string.offer_update_successful), Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                }
                            }, new Consumer<VolleyError>() {
                                @Override
                                public void accept(VolleyError volleyError) {
                                    Toast.makeText(OfferEditActivity.this, getString(R.string.offer_update_failed), Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else {
                    Toast.makeText(OfferEditActivity.this, R.string.undefined_fields_exist, Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        deleteOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(OfferEditActivity.this)
                        .setTitle("Obriši ponudu")
                        .setMessage("Sigurno želite obrisati ponudu?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new RequestService(OfferEditActivity.this).deleteOffer(""+ponuda.getId(),
                                        new Consumer<JSONObject>() {
                                            @Override
                                            public void accept(JSONObject jsonObject) {
                                                Toast.makeText(OfferEditActivity.this, getString(R.string.offer_delete_successful), Toast.LENGTH_LONG).show();
                                                onBackPressed();
                                            }
                                        }, new Consumer<VolleyError>() {
                                            @Override
                                            public void accept(VolleyError volleyError) {
                                                Toast.makeText(OfferEditActivity.this, getString(R.string.offer_delete_failed), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void displayFetchedOfferTypes(List<VrstaPonude> vrstePonuda) {
        for (VrstaPonude vrstaPonude : vrstePonuda) {
            offerTypeIdsByTheirNames.put(vrstaPonude.getNaziv(), vrstaPonude.getId());
        }
        Object[] offerTypeNames = offerTypeIdsByTheirNames.keySet().toArray();
        Spinner offerTypesDropdown = findViewById(R.id.offerTypeOptions);
        SpinnerAdapter offerTypesSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, offerTypeNames);
        offerTypesDropdown.setAdapter(offerTypesSpinnerAdapter);
        Integer selectedOfferTypeId = offerTypeIdsByTheirNames.get(ponuda.getVrstaPonude().getNaziv());
        offerTypesDropdown.setSelection(selectedOfferTypeId);
    }
}

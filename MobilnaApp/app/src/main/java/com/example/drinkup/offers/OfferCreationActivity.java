package com.example.drinkup.offers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.login.LoginActivity;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.VrstaPonude;
import com.example.drinkup.services.RequestService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class OfferCreationActivity extends AppCompatActivity {

    private Integer currentUserId;

    private Integer currentRoleId;

    private Integer workingBarId;

    private final Map<String, Integer> offerTypeIdsByTheirNames = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offer_creation);

        customizeActionBar();
        loadState(savedInstanceState);

        new RequestService(getApplicationContext()).fetchAllOfferTypes(
                new Consumer<List<VrstaPonude>>() {
                    @Override
                    public void accept(List<VrstaPonude> vrstePonuda) {
                        OfferCreationActivity.this.displayFetchedOfferTypes(vrstePonuda);
                    }
                },
                new Consumer<VolleyError>() {
                    @Override
                    public void accept(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), R.string.offer_types_retrieval_error_on_offer_creation, Toast.LENGTH_LONG).show();
                        findViewById(R.id.createOffer).setClickable(false);
                    }
                }
        );

        final EditText title = findViewById(R.id.title);
        final EditText description = findViewById(R.id.description);
        final EditText price = findViewById(R.id.price);
        final EditText numberOfTokens = findViewById(R.id.numberOfTokens);
        final Spinner offerTypeOptions = findViewById(R.id.offerTypeOptions);
        final Button createOfferButton = findViewById(R.id.createOffer);

        createOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !numberOfTokens.getText().toString().isEmpty()) {
                    String selectedOfferTypeName = offerTypeOptions.getSelectedItem().toString();
                    Integer selectedOfferTypeId = offerTypeIdsByTheirNames.get(selectedOfferTypeName);
                    Ponuda ponuda = new Ponuda();
                    ponuda.setNaslov(title.getText().toString());
                    ponuda.setOpis(description.getText().toString());
                    ponuda.setCijena(Float.parseFloat(price.getText().toString()));
                    ponuda.setBrojTokena(Integer.parseInt(numberOfTokens.getText().toString()));
                    ponuda.setVrstaPonude(selectedOfferTypeId);
                    new RequestService(getApplicationContext()).createOffer(ponuda, workingBarId,
                            new Consumer<JSONObject>() {
                                @Override
                                public void accept(JSONObject jsonObject) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.offer_creation_successful), Toast.LENGTH_LONG).show();
                                    title.getText().clear();
                                    description.getText().clear();
                                    price.getText().clear();
                                    numberOfTokens.getText().clear();
                                }
                            }, new Consumer<VolleyError>() {
                                @Override
                                public void accept(VolleyError volleyError) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.offer_creation_failed), Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.undefined_fields_exist, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                currentUserId = extras.getInt("userId");
                currentRoleId = extras.getInt("roleId");
                workingBarId = extras.getInt("barId");
            }
        }
        else {
            currentUserId = savedInstanceState.getInt("userId");
            currentRoleId = savedInstanceState.getInt("roleId");
            workingBarId = savedInstanceState.getInt("barId");
        }
    }

    private void customizeActionBar() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navigation_icon);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(R.string.offer_creation_activity_title);
    }

    private void displayFetchedOfferTypes(List<VrstaPonude> vrstePonuda) {
        for (VrstaPonude vrstaPonude : vrstePonuda) {
            offerTypeIdsByTheirNames.put(vrstaPonude.getNaziv(), vrstaPonude.getId());
        }
        Object[] offerTypeNames = offerTypeIdsByTheirNames.keySet().toArray();
        Spinner offerTypesDropdown = findViewById(R.id.offerTypeOptions);
        SpinnerAdapter offerTypesSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, offerTypeNames);
        offerTypesDropdown.setAdapter(offerTypesSpinnerAdapter);
    }
}
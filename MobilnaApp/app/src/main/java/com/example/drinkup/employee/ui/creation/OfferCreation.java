package com.example.drinkup.employee.ui.creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.employee.EmployeeMainActivity;
import com.example.drinkup.models.Ponuda;
import com.example.drinkup.models.VrstaPonude;
import com.example.drinkup.services.RequestService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class OfferCreation extends Fragment {

    private EmployeeMainActivity activity;

    private final Map<String, Integer> offerTypeIdsByTheirNames = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offer_creation, container, false);
        activity = (EmployeeMainActivity) getActivity();
        activity.customizeActionBar(getString(R.string.offer_creation_fragment_title));
        new RequestService(getContext()).fetchAllOfferTypes(
                new Consumer<List<VrstaPonude>>() {
                    @Override
                    public void accept(List<VrstaPonude> vrstePonuda) {
                        OfferCreation.this.displayFetchedOfferTypes(root, vrstePonuda);
                    }
                },
                new Consumer<VolleyError>() {
                    @Override
                    public void accept(VolleyError volleyError) {
                        Toast.makeText(getContext(), R.string.offer_types_retrieval_error_on_offer_creation, Toast.LENGTH_LONG).show();
                        root.findViewById(R.id.createOffer).setClickable(false);
                    }
                }
        );

        final EditText title = root.findViewById(R.id.title);
        final EditText description = root.findViewById(R.id.description);
        final EditText price = root.findViewById(R.id.price);
        final EditText numberOfTokens = root.findViewById(R.id.numberOfTokens);
        final Spinner offerTypeOptions = root.findViewById(R.id.offerTypeOptions);
        final Button createOfferButton = root.findViewById(R.id.createOffer);

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
                    new RequestService(root.getContext()).createOffer(ponuda, activity.getWorkingBarId(),
                            new Consumer<JSONObject>() {
                                @Override
                                public void accept(JSONObject jsonObject) {
                                    Toast.makeText(getContext(), getString(R.string.offer_creation_successful), Toast.LENGTH_LONG).show();
                                    title.getText().clear();
                                    description.getText().clear();
                                    price.getText().clear();
                                    numberOfTokens.getText().clear();
                                }
                            }, new Consumer<VolleyError>() {
                                @Override
                                public void accept(VolleyError volleyError) {
                                    Toast.makeText(getContext(), getString(R.string.offer_creation_failed), Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else {
                    Toast.makeText(getContext(), R.string.undefined_fields_exist, Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }

    private void displayFetchedOfferTypes(View root, List<VrstaPonude> vrstePonuda) {
        for (VrstaPonude vrstaPonude : vrstePonuda) {
            offerTypeIdsByTheirNames.put(vrstaPonude.getNaziv(), vrstaPonude.getId());
        }
        Object[] offerTypeNames = offerTypeIdsByTheirNames.keySet().toArray();
        Spinner offerTypesDropdown = root.findViewById(R.id.offerTypeOptions);
        SpinnerAdapter offerTypesSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, offerTypeNames);
        offerTypesDropdown.setAdapter(offerTypesSpinnerAdapter);
    }
}
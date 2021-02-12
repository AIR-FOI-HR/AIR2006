package com.example.drinkup.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.login.LoginActivity;
import com.example.drinkup.models.Korisnik;
import com.example.drinkup.models.Objekt;
import com.example.drinkup.models.Uloga;
import com.example.drinkup.services.RequestService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class RegistrationActivity extends AppCompatActivity {

    Map<String, Integer> roleIdsByTheirNames = new HashMap<>();

    Map<String, Integer> barIdsByTheirNames = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(R.string.registration_activity_title);

        final EditText emailEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText passwordRepeatEditText = findViewById(R.id.passwordRepeat);
        final Button registrationButton = findViewById(R.id.createOffer);
        final EditText oibEditText = findViewById(R.id.naslov);
        final EditText firstNameEditText = findViewById(R.id.firstName);
        final EditText lastNameEditText = findViewById(R.id.lastName);
        final RadioButton maleGenderRadio = findViewById(R.id.radioOptionMale);

        oibEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String oib = oibEditText.getText().toString();
                    if (!UserInputValidation.isOibInValidFormat(oib)) {
                        oibEditText.setError(getString(R.string.invalid_oib_format));
                    }
                }
            }
        });

        firstNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String firstName = firstNameEditText.getText().toString();
                    if (!UserInputValidation.isNameDefined(firstName)) {
                        firstNameEditText.setError(getString(R.string.undefined_first_name));
                    }
                }
            }
        });

        lastNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String lastName = lastNameEditText.getText().toString();
                    if (!UserInputValidation.isNameDefined(lastName)) {
                        lastNameEditText.setError(getString(R.string.undefined_last_name));
                    }
                }
            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String email = emailEditText.getText().toString();
                    if (!UserInputValidation.isEmailInValidFormat(email)) {
                        emailEditText.setError(getString(R.string.invalid_email_format));
                    }
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = passwordEditText.getText().toString();
                    if (!UserInputValidation.isPasswordValid(password)) {
                        passwordEditText.setError(getString(R.string.invalid_password));
                    }
                }
            }
        });

        passwordRepeatEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = passwordEditText.getText().toString();
                    String repeatedPassword = passwordRepeatEditText.getText().toString();
                    if (!UserInputValidation.arePasswordsSame(password, repeatedPassword)) {
                        passwordRepeatEditText.setError(getString(R.string.password_mismatch));
                    }
                }
            }
        });

        RequestService rs = new RequestService(getApplicationContext());
        rs.fetchAvailableRoles(new Consumer<Uloga[]>() {
            @Override
            public void accept(Uloga[] roles) {
                RegistrationActivity.this.displayFetchedRoles(roles);
            }
        }, new Consumer<VolleyError>() {
            @Override
            public void accept(VolleyError volleyError) {
                Toast.makeText(RegistrationActivity.this.getApplicationContext(), R.string.role_retrieval_failed, Toast.LENGTH_LONG).show();
                Intent intentRegisterLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intentRegisterLogin);
            }
        });
        rs.fetchBars(new Consumer<Objekt[]>() {
            @Override
            public void accept(Objekt[] bars) {
                RegistrationActivity.this.displayFetchedBars(bars);
            }
        }, new Consumer<VolleyError>() {
            @Override
            public void accept(VolleyError volleyError) {
                Toast.makeText(RegistrationActivity.this.getApplicationContext(), R.string.role_retrieval_failed, Toast.LENGTH_LONG).show();
                Intent intentRegisterLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intentRegisterLogin);
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!oibEditText.getText().toString().isEmpty() && !firstNameEditText.getText().toString().isEmpty() && !lastNameEditText.getText().toString().isEmpty() && !emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty() && !passwordRepeatEditText.getText().toString().isEmpty()) {
                    if (oibEditText.getError() == null && firstNameEditText.getError() == null && lastNameEditText.getError() == null && emailEditText.getError() == null && passwordEditText.getError() == null && passwordRepeatEditText.getError() == null) {
//                        final FrameLayout progressBarHolder = findViewById(R.id.loading);
//                        progressBarHolder.setVisibility(View.VISIBLE);
                        RequestService rs = new RequestService(getApplicationContext());
                        Korisnik registriraniKorisnik = new Korisnik();
                        registriraniKorisnik.setOIB(oibEditText.getText().toString());
                        registriraniKorisnik.setEmail(emailEditText.getText().toString());
                        registriraniKorisnik.setIme(firstNameEditText.getText().toString());
                        registriraniKorisnik.setPrezime(lastNameEditText.getText().toString());
                        registriraniKorisnik.setLozinka(passwordEditText.getText().toString());
                        registriraniKorisnik.setSpol(maleGenderRadio.isChecked() ? 0 : 1);
                        registriraniKorisnik.setUlogaID(readSelectedRoleId());
                        rs.SendRegistrationRequest(registriraniKorisnik,
                                new Consumer<String>() {
                                    @Override
                                    public void accept(String response) {
                                        if (registriraniKorisnik.getUlogaID() != 0) {
                                            int generatedUserId = Integer.parseInt(response);
                                            Spinner barDropdown = findViewById(R.id.barOptions);
                                            String selectedBarName = barDropdown.getSelectedItem().toString();
                                            Integer selectedBarId = barIdsByTheirNames.get(selectedBarName);
                                            rs.assignEmployeeToBar(selectedBarId, generatedUserId);
                                        }
                                        Toast.makeText(getApplicationContext(), R.string.registration_successful, Toast.LENGTH_LONG).show();
                                        Intent intentRegisterLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                                        startActivity(intentRegisterLogin);
                                    }
                                }, new Consumer<VolleyError>() {
                                    @Override
                                    public void accept(VolleyError volleyError) {
                                        if (volleyError.networkResponse != null) {
                                            Toast.makeText(getApplicationContext(), R.string.duplicate_email_or_oib, Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), R.string.registration_failed, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.some_errors_exist, Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.undefined_fields_exist, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void displayFetchedBars(Objekt[] bars) {
        for (Objekt bar : bars) {
            barIdsByTheirNames.put(bar.getNaziv(), bar.getId());
        }
        Object[] barNames = barIdsByTheirNames.keySet().toArray();
        Spinner barsDropdown = findViewById(R.id.barOptions);
        SpinnerAdapter barsSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, barNames);
        barsDropdown.setAdapter(barsSpinnerAdapter);
    }

    private int readSelectedRoleId() {
        final String selectedRoleName;
        if (findViewById(R.id.roleOptionsV1).getVisibility() == View.VISIBLE) {
            RadioGroup roleRadioGroup = findViewById(R.id.roleOptionsV1);
            RadioButton checkedRadioButton = findViewById(roleRadioGroup.getCheckedRadioButtonId());
            selectedRoleName = checkedRadioButton.getText().toString();
        }
        else {
            Spinner roleDropdown = findViewById(R.id.roleOptionsV2);
            selectedRoleName = roleDropdown.getSelectedItem().toString();
        }
        return roleIdsByTheirNames.get(selectedRoleName);
    }

    private void displayFetchedRoles(Uloga[] roles) {
        for (Uloga role : roles) {
            roleIdsByTheirNames.put(role.getNaziv(), role.getId());
        }
        Object[] roleNames = roleIdsByTheirNames.keySet().toArray();
        final View roleOptionsViewElement;
        if (roles.length == 2) {
            RadioGroup roleRadioGroup = findViewById(R.id.roleOptionsV1);
            RadioButton roleOptionRadioButton1 = findViewById(R.id.roleOptionRadioButton1);
            roleOptionRadioButton1.setText(roleNames[0].toString());
            RadioButton roleOptionRadioButton2 = findViewById(R.id.roleOptionRadioButton2);
            roleOptionRadioButton2.setText(roleNames[1].toString());
            roleOptionsViewElement = roleRadioGroup;
            roleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    toggleBarsDropdownVisibilityBasedOnCurrentRoleSelection();
                }
            });
        }
        else {
            Spinner roleDropdown = findViewById(R.id.roleOptionsV2);
            SpinnerAdapter roleSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, roleNames);
            roleDropdown.setAdapter(roleSpinnerAdapter);
            roleOptionsViewElement = roleDropdown;
            roleDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    toggleBarsDropdownVisibilityBasedOnCurrentRoleSelection();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        roleOptionsViewElement.setVisibility(View.VISIBLE);
        toggleBarsDropdownVisibilityBasedOnCurrentRoleSelection();
    }

    private void toggleBarsDropdownVisibilityBasedOnCurrentRoleSelection() {
        View barDropdown = findViewById(R.id.barOptions);
        if (readSelectedRoleId() == 0) {  // if user is guest
            barDropdown.setVisibility(View.GONE);
        } else {
            barDropdown.setVisibility(View.VISIBLE);
        }
    }
}
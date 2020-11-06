package com.example.drinkup.registration;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drinkup.models.Korisnik;
import com.example.drinkup.R;
import com.example.drinkup.services.RequestService;
import com.example.drinkup.services.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText emailEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText passwordRepeatEditText = findViewById(R.id.passwordRepeat);
        final Button registrationButton = findViewById(R.id.register);
        final EditText oibEditText = findViewById(R.id.oib);
        final EditText firstNameEditText = findViewById(R.id.firstName);
        final EditText lastNameEditText = findViewById(R.id.lastName);
        final RadioButton maleGenderRadio = findViewById(R.id.radio1);
        final RadioButton femaleGenderRadio = findViewById(R.id.radio2);
        final RadioButton ownerRoleRadio = findViewById(R.id.radioRoleOwner);
        final RadioButton guestRoleRadio = findViewById(R.id.radioRoleOwner);

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

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!oibEditText.getText().toString().isEmpty() && !firstNameEditText.getText().toString().isEmpty() && !lastNameEditText.getText().toString().isEmpty() && !emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty() && !passwordRepeatEditText.getText().toString().isEmpty()) {
                    if (oibEditText.getError() == null && firstNameEditText.getError() == null && lastNameEditText.getError() == null && emailEditText.getError() == null && passwordEditText.getError() == null && passwordRepeatEditText.getError() == null) {
//                        final FrameLayout progressBarHolder = findViewById(R.id.loading);
//                        progressBarHolder.setVisibility(View.VISIBLE);
                        // TODO implementirati slanje registracijskih podataka na web-servis
                        RequestService rs = new RequestService(getApplicationContext());
                        Korisnik registriraniKorisnik = new Korisnik();
                        registriraniKorisnik.OIB = oibEditText.getText().toString();
                        registriraniKorisnik.email = emailEditText.getText().toString();
                        registriraniKorisnik.ime = firstNameEditText.getText().toString();
                        registriraniKorisnik.prezime = lastNameEditText.getText().toString();
                        registriraniKorisnik.lozinka = passwordEditText.getText().toString();
                        registriraniKorisnik.spol = maleGenderRadio.isChecked() ? 0 : 1;
                        registriraniKorisnik.ulogaID = ownerRoleRadio.isChecked() ? 1 : 0;
                        rs.SendRegistrationRequest(registriraniKorisnik);
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

    public void registration(String oib, String ime, String prezime, String email, int spol, int ulogaId, boolean status) {
        Korisnik korisnik = new Korisnik();
        korisnik.OIB = oib;
        korisnik.ime = ime;
        korisnik.prezime = prezime;
        korisnik.email = email;
        korisnik.spol = spol;
        korisnik.ulogaID = ulogaId;
        korisnik.status = status;

        //HttpClient httpclient = HttpClients.createDefault();
        //HttpPost httppost = new HttpPost("http://www.a-domain.com/foo/");

    }

}
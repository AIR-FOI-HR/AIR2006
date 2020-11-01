package com.example.drinkup.registration;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drinkup.R;

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
                        if (emailEditText.getText().toString().equals("admin@foi.hr")) {
                            Toast.makeText(getApplicationContext(), R.string.registration_failed, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), R.string.registration_successful, Toast.LENGTH_LONG).show();
                        }
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
}
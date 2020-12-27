package com.example.drinkup.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.models.LoginModel;
import com.example.drinkup.offers.OfferListActivity;
import com.example.drinkup.registration.RegistrationActivity;
import com.example.drinkup.services.RequestService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Error;
    private Button Login;
    private TextView ForgotPassword;
    private TextView LoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText)findViewById(R.id.loginUsername);
        Password = (EditText)findViewById(R.id.loginPassword);
        Login =(Button)findViewById(R.id.loginButton);
        ForgotPassword =(TextView)findViewById(R.id.loginForgotPassword);
        LoginRegister=(TextView)findViewById(R.id.loginRegister);

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });
        LoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRegister();
            }
        });

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RequestService rs = new RequestService(getApplicationContext());
                LoginModel korisnik = new LoginModel();
                korisnik.setEmail(Name.getText().toString());
                korisnik.setLozinka(Password.getText().toString());

                rs.SendLoginRequest(korisnik,
                        new Consumer<JSONObject>() {
                            @Override
                            public void accept(JSONObject response) {
                                try {
                                    //ovdje se dohvaca neki cudni ID korisnika, moj ID u bazi je 3, a tu je dohvacen 153 ??????wtf
                                    int userId = response.getInt("id");
                                    int roleId = response.getInt("ulogaId");
                                    Intent intentSucces = new Intent(LoginActivity.this, OfferListActivity.class);
                                    intentSucces.putExtra("userId", userId);
                                    intentSucces.putExtra("roleId", roleId);
                                    startActivity(intentSucces);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Consumer<VolleyError>() {
                            @Override
                            public void accept(VolleyError volleyError) {
                                Error=(TextView)findViewById(R.id.loginError);
                                Error.setText(R.string.login_failed);
                            }
                        });
            }
        });
    }
    private void forgotPassword(){
        Intent intentForgotPassword= new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intentForgotPassword);
    }
    private void loginRegister(){
        Intent intentLoginRegister= new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intentLoginRegister);
    }
}
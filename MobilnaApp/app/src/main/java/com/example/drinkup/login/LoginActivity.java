package com.example.drinkup.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.models.LoginModel;
import com.example.drinkup.offers.OfferCreationActivity;
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

        Name.setText("lidijapitic@gmail.com");
        Password.setText("123456789m");

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
                                    int userId = response.getInt("id");
                                    int roleId = response.getInt("ulogaId");
                                    if (roleId == 0) {
                                        Intent intentSuccess = new Intent(LoginActivity.this, OfferListActivity.class);
                                        intentSuccess.putExtra("userId", userId);
                                        intentSuccess.putExtra("roleId", roleId);
                                        startActivity(intentSuccess);
                                        finish();
                                    }
                                    else {
                                        rs.fetchWorkplaceOfUser(userId,
                                                new Consumer<Integer>() {
                                                    @Override
                                                    public void accept(Integer barId) {
                                                        Intent intentSuccess = new Intent(LoginActivity.this, OfferCreationActivity.class);
                                                        intentSuccess.putExtra("userId", userId);
                                                        intentSuccess.putExtra("roleId", roleId);
                                                        intentSuccess.putExtra("barId", barId);
                                                        startActivity(intentSuccess);
                                                        finish();
                                                    }
                                                }, new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(getApplicationContext(), getString(R.string.no_bars_for_user_found), Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    }
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
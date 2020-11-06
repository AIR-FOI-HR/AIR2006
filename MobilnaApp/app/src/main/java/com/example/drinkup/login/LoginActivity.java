package com.example.drinkup.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drinkup.R;
import com.example.drinkup.models.LoginModel;
import com.example.drinkup.registration.RegistrationActivity;
import com.example.drinkup.services.RequestService;

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
                rs.SendLoginRequest(korisnik);
                //validate(Name.getText().toString(), Password.getText().toString());
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

    private void validate(String userName, String userPassword){

        if((userName.equals("admin")) && (userPassword.equals("admin"))){

            Intent intentSucces= new Intent(LoginActivity.this, SecondActivity.class);
            startActivity(intentSucces);

        }else{
            Error=(TextView)findViewById(R.id.loginError);
            Error.setText("Pogrešno upisani podaci! Molimo pokušajte ponovo.");
        }
    }
}
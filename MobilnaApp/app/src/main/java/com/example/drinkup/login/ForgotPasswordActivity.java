package com.example.drinkup.login;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.models.LoginModel;
import com.example.drinkup.registration.RegistrationActivity;
import com.example.drinkup.services.RequestService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

public class ForgotPasswordActivity extends AppCompatActivity {


    private TextView ForgotPasswordSendConfirmation;
    private TextView ForgotPasswordEmail;
    private Button ForgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ForgotPasswordEmail = (TextView)findViewById(R.id.editTextTextPersonName);
        ForgotPasswordButton =(Button)findViewById(R.id.buttonForgotPassword);


        ForgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                emailSent();
            }
        });
    }
    private void emailSent(){
        RequestService rs = new RequestService(getApplicationContext());

        rs.SendResetPasswordRequest(ForgotPasswordEmail.getText().toString(),
                new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject response) {
                        try {
                            if (response.getInt("statusCode") == 400) {
                                ForgotPasswordSendConfirmation=(TextView)findViewById(R.id.forgotPasswordSendConfirmation);
                                ForgotPasswordSendConfirmation.setText(R.string.reset_password_failed);
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.reset_password_success, Toast.LENGTH_LONG).show();
                                Intent intentSucces = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                startActivity(intentSucces);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Consumer<VolleyError>() {
                    @Override
                    public void accept(VolleyError volleyError) {
                        ForgotPasswordSendConfirmation=(TextView)findViewById(R.id.forgotPasswordSendConfirmation);
                        ForgotPasswordSendConfirmation.setText(R.string.reset_password_failed);
                    }
                });

    }
}
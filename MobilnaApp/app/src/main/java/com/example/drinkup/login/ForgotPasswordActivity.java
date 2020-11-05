package com.example.drinkup.login;

import androidx.appcompat.app.AppCompatActivity;
import com.example.drinkup.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;

public class ForgotPasswordActivity extends AppCompatActivity {


    private TextView ForgotPasswordSendConfirmation;
    private Button ForgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ForgotPasswordButton =(Button)findViewById(R.id.buttonForgotPassword);


        ForgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                emailSent();
            }
        });
    }
    private void emailSent(){
        ForgotPasswordSendConfirmation=(TextView)findViewById(R.id.forgotPasswordSendConfirmation);
        ForgotPasswordSendConfirmation.setText("Lozinka je uspješno poslana na vašu email adresu.");
    }
}
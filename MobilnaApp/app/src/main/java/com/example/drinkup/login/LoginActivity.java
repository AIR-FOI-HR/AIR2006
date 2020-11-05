package com.example.drinkup.login;

import androidx.appcompat.app.AppCompatActivity;
import com.example.drinkup.R;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Error;
    private Button Login;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText)findViewById(R.id.loginUsername);
        Password = (EditText)findViewById(R.id.loginPassword);
        Login =(Button)findViewById(R.id.loginButton);
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
    }

    private void validate(String userName, String userPassword){
        if((userName.equals("admin")) && (userPassword.equals("admin"))){

            Intent intent= new Intent(LoginActivity.this, SecondActivity.class);
            startActivity(intent);

        }else{
            Error=(TextView)findViewById(R.id.loginError);
            Error.setText("Greška");
        }
    }
}
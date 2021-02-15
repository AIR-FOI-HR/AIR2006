package com.example.drinkup.login;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.employee.EmployeeMainActivity;
import com.example.drinkup.guest.GuestMainActivity;
import com.example.drinkup.models.LoginModel;
import com.example.drinkup.registration.RegistrationActivity;
import com.example.drinkup.services.RequestService;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Error;
    private Button Login;
    private TextView ForgotPassword;
    private TextView LoginRegister;

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        ((AppCompatTextView) findViewById(R.id.actionBarTitle)).setText(R.string.login_activity_title);

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
                                        Intent intentSuccess = new Intent(LoginActivity.this, GuestMainActivity.class);
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
                                                        Intent intentSuccess = new Intent(LoginActivity.this, EmployeeMainActivity.class);
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

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();
        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}

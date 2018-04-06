package com.quicksuntech.barcodedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText user_name, password;
    Button btnLogin;
    SharedPrefQRCode sharedPrefILumy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefILumy = SharedPrefQRCode.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitle("");

            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setPadding(20, 0, 0, 0);
            mTitle.setText("Login");
            setSupportActionBar(toolbar);

        } else {
            Toast.makeText(LoginActivity.this, "toolbar is null", Toast.LENGTH_SHORT).show();
        }

        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    if (user_name.getText().toString().equals("admin") &&
                            password.getText().toString().equals("12345")) {

                        Intent intent = new Intent(LoginActivity.this, BarcodeExampleActivity.class);
                        startActivity(intent);
                        sharedPrefILumy.saveISLogged_IN(LoginActivity.this, true);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect User Name Or Password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private boolean isValid() {

        if (user_name.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "Enter User Name", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

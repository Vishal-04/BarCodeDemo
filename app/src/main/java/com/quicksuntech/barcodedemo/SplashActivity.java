package com.quicksuntech.barcodedemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SplashActivity extends Activity {
    public static final String TAG = "ActivitySplash";
    private CountDownTimer contador;
    SharedPrefQRCode sharedPrefQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPrefQRCode = SharedPrefQRCode.getInstance();
        splashTime();


    }


    public void splashTime() {
        contador = new CountDownTimer(2000, 1000) {
            @Override
            public void onFinish() {

                if (sharedPrefQRCode.getISLogged_IN(SplashActivity.this)) {
                    Intent intent = new Intent(SplashActivity.this, BarcodeExampleActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

}

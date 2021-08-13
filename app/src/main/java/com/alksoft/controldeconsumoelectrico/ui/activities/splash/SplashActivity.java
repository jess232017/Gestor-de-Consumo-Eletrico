package com.alksoft.controldeconsumoelectrico.ui.activities.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.alksoft.controldeconsumoelectrico.ui.activities.main.MainActivity;
import com.alksoft.controldeconsumoelectrico.ui.activities.register.RegisterActivity;
import com.alksoft.controldeconsumoelectrico.utils.PrefManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (PrefManager.getInstance(SplashActivity.this).getProfileExist()) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }else {
            Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(i);
            finish();
        }
    }
}
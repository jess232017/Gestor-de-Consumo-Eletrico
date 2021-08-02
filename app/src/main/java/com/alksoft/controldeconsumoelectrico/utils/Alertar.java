package com.alksoft.controldeconsumoelectrico.utils;

import android.app.Activity;

import com.alksoft.controldeconsumoelectrico.R;
import com.tapadoo.alerter.Alerter;

import static java.security.AccessController.getContext;

public class Alertar {
    public static void successSave(Activity ctx, String Title, String Subtitle){
        Alerter.create(ctx)
                .setTitle(Title)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setIcon(R.drawable.ic_baseline_save_24)
                .setText(Subtitle)
                .setDuration(1000)
                .show();
    }

    public static void loading(Activity ctx, String Title, String Subtitle){
        Alerter.create(ctx)
                .setTitle(Title)
                .setBackgroundColorRes(R.color.colorPrimary)
                .enableProgress(true)
                .setText(Subtitle)
                .show();
    }

    public static void error(Activity ctx, String Title, String Subtitle){
        Alerter.create(ctx)
                .setTitle(Title)
                .setBackgroundColorRes(R.color.red_1)
                .setIcon(R.drawable.ic_baseline_error_24)
                .setText(Subtitle)
                .setDuration(1000)
                .show();
    }
}

package com.alksoft.controldeconsumoelectrico.utils;

import android.content.Context;

import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;
import com.alksoft.controldeconsumoelectrico.ui.dialogs.InsertRegisterDialog;
import com.alksoft.controldeconsumoelectrico.vm.ProfileViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class JvUtils {

    public static String splitName(String fullName){
        int idx = fullName.indexOf(" ");

        if (idx == -1)
            return fullName;

        String firstName = fullName.substring(0, idx);
        String lastName  = fullName.substring(idx + 1);
        lastName = lastName.substring(lastName.indexOf(" "), lastName.lastIndexOf(" "));

        return firstName + lastName;
    }

}

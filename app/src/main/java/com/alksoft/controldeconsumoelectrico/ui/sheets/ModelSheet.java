package com.alksoft.controldeconsumoelectrico.ui.sheets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alksoft.controldeconsumoelectrico.databinding.SheetEditionBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ModelSheet extends BottomSheetDialog {
    private SheetEditionBinding view;

    public ModelSheet(@NonNull Context context) {
        super(context);
    }

    public ModelSheet(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ModelSheet(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = SheetEditionBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());
    }

}

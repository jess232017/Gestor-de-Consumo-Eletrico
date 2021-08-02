package com.alksoft.controldeconsumoelectrico.ui.sheets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alksoft.controldeconsumoelectrico.R;
import com.alksoft.controldeconsumoelectrico.data.Repository;
import com.alksoft.controldeconsumoelectrico.databinding.SheetEditionBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

public class EditionSheet extends BottomSheetDialog {
    private Repository repository;
    private SheetEditionBinding view;
    private View.OnClickListener clickVer;
    private View.OnClickListener clickEditar;
    private View.OnClickListener clickBorrar;
    private View.OnClickListener clickAgregar;


    public EditionSheet(@NonNull Context context) {
        super(context);
    }

    public EditionSheet(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected EditionSheet(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = SheetEditionBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        setListeners();
    }

    private void setListeners(){
        if(clickVer != null){
            view.btnVer.setOnClickListener(clickVer);
        }else{
            view.btnVer.setVisibility(View.GONE);
        }

        if(clickEditar != null){
            view.btnEditar.setOnClickListener(clickEditar);
        }else{
            view.btnEditar.setVisibility(View.GONE);
        }

        if(clickBorrar != null){
            view.btnBorrar.setOnClickListener(clickBorrar);
        }else{
            view.btnBorrar.setVisibility(View.GONE);
        }

        if(clickAgregar != null){
            view.btnAgregar.setOnClickListener(clickAgregar);
        }else{
            view.btnAgregar.setVisibility(View.GONE);
        }
    }

    public void setClickVer(View.OnClickListener clickVer) {
        this.clickVer = clickVer;
    }

    public void setClickEditar(View.OnClickListener clickEditar) {
        this.clickEditar = clickEditar;
    }

    public void setClickBorrar(View.OnClickListener clickBorrar) {
        this.clickBorrar = clickBorrar;
    }

    public void setClickAgregar(View.OnClickListener clickAgregar) {
        this.clickAgregar = clickAgregar;
    }
}

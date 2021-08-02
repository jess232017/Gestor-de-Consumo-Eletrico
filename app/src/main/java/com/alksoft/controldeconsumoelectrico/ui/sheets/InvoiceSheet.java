package com.alksoft.controldeconsumoelectrico.ui.sheets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alksoft.controldeconsumoelectrico.data.local.entity.Calculado;
import com.alksoft.controldeconsumoelectrico.databinding.SheetInvoicesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class InvoiceSheet extends BottomSheetDialog {
    private Calculado calculado;
    private View.OnClickListener clickAgregar;
    private SheetInvoicesBinding view;
    private float totalKwh;

    public InvoiceSheet(@NonNull Context context) {
        super(context);
    }

    public InvoiceSheet(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected InvoiceSheet(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = SheetInvoicesBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        view.txtEnergia.setText(calculado.getEnergia());
        view.txtAlumbrado.setText(calculado.getAlumbrado());
        view.txtComercia.setText(calculado.getComercializacion());
        view.txtSubsidio.setText(calculado.getSubsidioConsumo());
        view.txtSubsidio2.setText(calculado.getSubsidioComercia());
        view.txtIva.setText(calculado.getIva());
        view.txtTotal.setText(calculado.getTotal());
        view.txtTotalkWh.setText(String.format("%.2f", totalKwh) + " kWh");
        view.btnAgregar.setOnClickListener(clickAgregar);

    }

    public void setClickAgregar(View.OnClickListener clickAgregar) {
        this.clickAgregar = clickAgregar;
    }

    public void setTotalKwh(float totalKwh) {
        this.totalKwh = totalKwh;
    }

    public void setCalculado(Calculado calculado) {
        this.calculado = calculado;
    }
}

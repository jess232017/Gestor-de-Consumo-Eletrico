package com.alksoft.controldeconsumoelectrico.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alksoft.controldeconsumoelectrico.data.local.entity.Daily;
import com.alksoft.controldeconsumoelectrico.databinding.DialogInsertDailyBinding;
import com.alksoft.controldeconsumoelectrico.utils.Alertar;
import com.alksoft.controldeconsumoelectrico.vm.DailyViewModel;

import java.text.DecimalFormat;
import java.util.Objects;

public class InsertReadingDialog extends Dialog {
    private DialogInsertDailyBinding binding;
    private DailyViewModel mdailyViewModel;
    private float ConsumoInicial;
    private float ConsumoUltimo;
    private float ConsumoHoy;
    private long idFactura;
    private Context ctx;

    public InsertReadingDialog(@NonNull Context context) {
        super(context);
        ctx = context;
    }

    public InsertReadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        ctx = context;
    }

    protected InsertReadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        ctx = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DialogInsertDailyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(Objects.requireNonNull(getWindow()).getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;
        getWindow().setAttributes(layoutParams);

        binding.Title.setText("Registrar Consumo");
        binding.TxtConsumoInicial.setText(String.valueOf(ConsumoInicial));
        binding.BtnCerrar.setOnClickListener(v -> dismiss());

        binding.BtnConfirmar.setOnClickListener(v -> {
            if(isValido()){
                String Fecha = DateFormat.format("dd/MM/yyyy", new java.util.Date()).toString();
                String Hora = DateFormat.format("kk:mm", new java.util.Date()).toString();

                mdailyViewModel.insert((new Daily(Fecha, Hora, ConsumoHoy, idFactura)));

                Alertar.successSave((Activity) ctx,"Guardando", "El consumo de hoy ("+ ConsumoHoy +") se ha guardado con exito!");

                dismiss();
            }
        });
    }

    private boolean isValido(){
        if (TextUtils.isEmpty(binding.TxtConsumoInicial.getText())) {
            binding.lytConsumoInicial.setError("Requerido");
            return false;
        }

        if (TextUtils.isEmpty(binding.TxtConsumoActual.getText())) {
            binding.lytConsumoActual.setError("Requerido");
            return false;
        }

        float ConsumoIngresado = Float.parseFloat(Objects.requireNonNull(binding.TxtConsumoActual.getText()).toString());
        if(!(Float.compare(ConsumoIngresado, ConsumoInicial) > 0)){
            binding.lytConsumoActual.setError("El consumo de hoy ("+ ConsumoIngresado +") debe ser mayor al inicial (" + ConsumoInicial + ")");
            return false;
        }

        ConsumoHoy = ConsumoIngresado - ConsumoInicial;
        ConsumoHoy = Float.parseFloat(new DecimalFormat("#.#f").format(ConsumoHoy));
        if(!(Float.compare(ConsumoHoy, ConsumoUltimo) > 0)){
            binding.lytConsumoActual.setError("El consumo de hoy ("+ ConsumoHoy +") no puede ser menor al ultimo registrado ("+ ConsumoUltimo + ")");
            return false;
        }

        return true;
    }

    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }

    public void setConsumoUltimo(float consumoUltimo) {
        ConsumoUltimo = consumoUltimo;
    }

    public void setConsumoInicial(float ConsumoInicial) {
        this.ConsumoInicial = ConsumoInicial;
    }

    public void setmdailyViewModel(DailyViewModel mdailyViewModel) {
        this.mdailyViewModel = mdailyViewModel;
    }
}

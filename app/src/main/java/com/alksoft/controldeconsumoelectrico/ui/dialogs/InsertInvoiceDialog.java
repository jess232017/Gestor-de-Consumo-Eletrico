package com.alksoft.controldeconsumoelectrico.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alksoft.controldeconsumoelectrico.data.local.entity.Invoice;
import com.alksoft.controldeconsumoelectrico.databinding.DialogInsertInvoiceBinding;
import com.alksoft.controldeconsumoelectrico.utils.Alertar;
import com.alksoft.controldeconsumoelectrico.vm.InvoiceViewModel;

import java.util.Calendar;
import java.util.Objects;

public class InsertInvoiceDialog extends Dialog {
    private DialogInsertInvoiceBinding binding;
    private InvoiceViewModel mInvoiceViewModel;
    private float ConsumoUltimoAnt;
    private float ConsumoInicial;
    private Context ctx;

    public InsertInvoiceDialog(@NonNull Context context) {
        super(context);
        ctx = context;
    }

    public InsertInvoiceDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected InsertInvoiceDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DialogInsertInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(Objects.requireNonNull(getWindow()).getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;
        getWindow().setAttributes(layoutParams);

        binding.Title.setText("Registrar Recibo");
        binding.BtnConfirmar.setText("Registrar Recibo");
        binding.BtnCerrar.setOnClickListener(v -> dismiss());

        //Establecer fecha actual como Fecha Inicio
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        binding.TxtFechaInicio.setText(mDay  + "/"+ (mMonth + 1) + "/"+ mYear);

        if(ConsumoUltimoAnt != 0){
            binding.TxtConsumoInicial.setText(String.valueOf(ConsumoUltimoAnt));
        }

        //Escuchar el click del usuario para mostrar un picker dialog
        binding.TxtFechaInicio.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(ctx,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view1, int year,
                                              int monthOfYear, int dayOfMonth) {
                            binding.TxtFechaInicio.setText(dayOfMonth  + "/"+ (monthOfYear + 1) + "/"+ year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        binding.BtnConfirmar.setOnClickListener(v -> {
            if(isValido()){
                Alertar.successSave((Activity) ctx,"Guardando", "El recibo ha sido guardado");
                String FechaInicio = Objects.requireNonNull(binding.TxtFechaInicio.getText()).toString();
                mInvoiceViewModel.insert((new Invoice(ConsumoInicial, FechaInicio)));
                dismiss();
            }
        });
    }

    private boolean isValido(){
        if (TextUtils.isEmpty(binding.TxtConsumoInicial.getText())) {
            binding.lytConsumoInicial.setError("Requerido");
            return false;
        }

        if (TextUtils.isEmpty(binding.TxtFechaInicio.getText())) {
            binding.lytFechaInicio.setError("Requerido");
            return false;
        }

        ConsumoInicial = Float.parseFloat(Objects.requireNonNull(binding.TxtConsumoInicial.getText()).toString());
        if(Float.compare(ConsumoInicial, ConsumoUltimoAnt) < 0){
            binding.lytConsumoInicial.setError("El consumo inical ("+ ConsumoInicial +") no puede ser menor al consumo final de su factura anterior ("+ ConsumoUltimoAnt + ")");
            return false;
        }

        return true;
    }

    public void setmInvoiceViewModel(InvoiceViewModel mInvoiceViewModel) {
        this.mInvoiceViewModel = mInvoiceViewModel;
    }

    public void setConsumoUltimoAnt(float consumoUltimoAnt) {
        ConsumoUltimoAnt = consumoUltimoAnt;
    }
}

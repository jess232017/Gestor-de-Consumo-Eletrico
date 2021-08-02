package com.alksoft.controldeconsumoelectrico.ui.activities.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.alksoft.controldeconsumoelectrico.R;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Daily;
import com.alksoft.controldeconsumoelectrico.databinding.ItemReadingBinding;
import com.alksoft.controldeconsumoelectrico.databinding.SheetEditionBinding;
import com.alksoft.controldeconsumoelectrico.ui.sheets.EditionSheet;
import com.alksoft.controldeconsumoelectrico.utils.Alertar;
import com.alksoft.controldeconsumoelectrico.vm.DailyViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;


public class ReadingListAdapter extends RecyclerView.Adapter<ReadingListAdapter.DailyViewHolder > {
    private final Context mContext;
    private final DailyViewModel mdailyViewModel;
    private final float CsInicial;
    private List<Daily> mDailies;
    private final LocalDate mFechaInicio;
    private final LocalDate mPbFechaFin;

    public ReadingListAdapter(Context context, DailyViewModel viewModel, float cSInicial, String FechaInicio)
    {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yy");
        mFechaInicio = dtf.parseLocalDate(FechaInicio);
        mPbFechaFin = mFechaInicio.plusDays(30);

        mdailyViewModel = viewModel;
        CsInicial = cSInicial;
        mContext = context;
    }

    @NotNull
    @Override
    public DailyViewHolder  onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemReadingBinding itemBinding = ItemReadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DailyViewHolder(itemBinding);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NotNull DailyViewHolder holder, int position) {
        Daily current = mDailies.get(position);

        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yy");
        LocalDate mFechaFin = dtf.parseLocalDate(current.getFecha());

        //Consumo promedio por dia
        int days = Days.daysBetween(mFechaInicio, mFechaFin).getDays() + 1;
        float ConsumoPerDia = current.getConsumo() / days;

        //Consumo registrado en la lectura
        float ConsumoActual = CsInicial + current.getConsumo();

        //Consumo final probable
        days = Days.daysBetween(mFechaFin, mPbFechaFin).getDays() + 1;
        float ConsumoPromedio = current.getConsumo() + (ConsumoPerDia * days);

        holder.view.txtFechaLectura.setText("Realizada el " + current.getFecha() +" " + current.getHora());
        holder.view.txtConsumoLeido.setText(current.getConsumo() + " kWh");
        holder.view.TxtConsumoInicial.setText(String.format("%.2f",CsInicial));
        holder.view.TxtConsumoActual.setText(String.format("%.2f",ConsumoActual));
        holder.view.txtConsumoPerDia.setText(String.format("%.2f", ConsumoPerDia));
        holder.view.txtConsumoPromedio.setText(String.format("%.2f", ConsumoPromedio));

        holder.view.card.setOnClickListener( v ->{
            mostrarEdicion(current);
        });
    }

    public void mostrarEdicion(Daily current){
        EditionSheet editionSheet = new EditionSheet(mContext);
        editionSheet.setClickEditar(view -> {
            editionSheet.dismiss();
        });
        editionSheet.setClickBorrar(view -> {
            editionSheet.dismiss();
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setCancelable(true)
                    .setTitle("Eliminar Registro")
                    .setMessage("¿Realmente Desea Eliminar este Registro?")
                    // .setAnimation(R.raw.warning)
                    .setPositiveButton("Eliminar", R.drawable.ic_baseline_delete_forever_24, (dialogInterface, which) -> {
                        Alertar.successSave((Activity) mContext, "¡Eliminado!", "Los datos se han elimanado");
                        mdailyViewModel.delete(current);
                        //Toast.makeText(getContext(), "¡Eliminado!", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    })
                    .setNegativeButton("Cancelar", R.drawable.ic_close_black_24dp, (dialogInterface, which) -> {
                        //Toast.makeText(getContext(), "¡Cancelado!", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    })
                    .build();
            // Show Dialog
            mBottomSheetDialog.show();
        });
        editionSheet.show();
    }

    public void setDailies(List<Daily> Dailies){
        mDailies = Dailies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDailies != null)
            return mDailies.size();
        else return 0;
    }

     static class DailyViewHolder extends RecyclerView.ViewHolder {
        ItemReadingBinding view;

        private DailyViewHolder(ItemReadingBinding itemView) {
            super(itemView.getRoot());
            this.view = itemView;
        }
    }
}
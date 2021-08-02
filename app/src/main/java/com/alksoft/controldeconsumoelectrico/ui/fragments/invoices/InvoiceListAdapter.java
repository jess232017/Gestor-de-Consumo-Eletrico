package com.alksoft.controldeconsumoelectrico.ui.fragments.invoices;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alksoft.controldeconsumoelectrico.R;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Calculado;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaCalculada;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Invoice;
import com.alksoft.controldeconsumoelectrico.databinding.ItemInvoicesBinding;
import com.alksoft.controldeconsumoelectrico.databinding.SheetEditionBinding;
import com.alksoft.controldeconsumoelectrico.databinding.SheetInvoicesBinding;
import com.alksoft.controldeconsumoelectrico.ui.activities.detail.DetailActivity;
import com.alksoft.controldeconsumoelectrico.ui.sheets.EditionSheet;
import com.alksoft.controldeconsumoelectrico.ui.sheets.InvoiceSheet;
import com.alksoft.controldeconsumoelectrico.utils.Alertar;
import com.alksoft.controldeconsumoelectrico.vm.InvoiceViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.InvoiceViewHolder > {
    private final Context mContext;
    private final InvoiceViewModel mInvoiceViewModel;
    private List<FacturaCalculada> mInvoices;

    public InvoiceListAdapter(Context context, InvoiceViewModel InvoiceViewModel) {
        mContext = context;
        mInvoiceViewModel = InvoiceViewModel;
    }

    @NotNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemInvoicesBinding itemBinding = ItemInvoicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InvoiceViewHolder(itemBinding);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NotNull InvoiceViewHolder holder, int position) {
        Invoice invoice = mInvoices.get(position).invoice;
        Calculado calculado = mInvoices.get(position).calculado;
        float ApCz = intentarCalcular(calculado.getAlumbrado(), calculado.getComercializacion());
        float consumoFinal = invoice.getConsumoFinal();
        float totalkWh = (consumoFinal == 0 ) ? 0 : (invoice.getConsumoFinal() - invoice.getConsumoInicial());

        holder.view.txtInvoiceNumber.setText("INV-000" + invoice.getIdFactura());
        holder.view.txtInitDate.setText(invoice.getFechaInicio());
        holder.view.txtEndDate.setText(invoice.getFechaFin());
        holder.view.txtApCz.setText(String.format("%.2f", ApCz));
        holder.view.txtEnergiaAmt.setText(calculado.getEnergia());
        holder.view.txtRegINE.setText(calculado.getRegulacion());
        holder.view.txtTotal.setText("C$ " + calculado.getTotal());
        holder.view.txtTotalkWh.setText(String.format("%.2f", totalkWh) + "kWh");
        holder.view.lytInvoice.setOnClickListener( v ->{
            mostrarEdicion(position, totalkWh);
        });
    }

    private float intentarCalcular(String alumbrado, String comercialz){
        try {
            return (Float.parseFloat(alumbrado) + Float.parseFloat(comercialz));
        }catch (Exception e){
            return .0f;
        }
    }

    private void mostrarEdicion(int pos, float totalKwh){
        EditionSheet editionSheet = new EditionSheet(mContext);

        //Clicks events
        editionSheet.setClickVer(view -> {
           mostrarRecibo(pos, totalKwh);
        });

        editionSheet.setClickEditar(view -> {

        });

        editionSheet.setClickBorrar(view -> {
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setCancelable(true)
                    .setTitle("Eliminar Registro")
                    .setMessage("¿Realmente Desea Eliminar este Registro?")
                    //.setAnimation(R.raw.warning)
                    .setPositiveButton("Eliminar", R.drawable.ic_baseline_delete_forever_24, (dialogInterface, which) -> {
                        mInvoiceViewModel.delete(mInvoices.get(pos).invoice);
                        dialogInterface.dismiss();
                        editionSheet.dismiss();

                        Alertar.successSave((Activity) mContext, "¡Eliminado!", "Los datos se han elimanado");
                    })
                    .setNegativeButton("Cancelar", R.drawable.ic_baseline_close_24, (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                        editionSheet.dismiss();
                    })
                    .build();
            // Show Dialog
            mBottomSheetDialog.show();
        });

        editionSheet.setClickAgregar(view -> {
            agregarLectura(mInvoices.get(pos).invoice);
            editionSheet.dismiss();
        });

        editionSheet.show();
    }

    private void agregarLectura(Invoice invoice){
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra("IdFactura", invoice.getIdFactura());
        intent.putExtra("ConsumoInicial", invoice.getConsumoInicial());
        intent.putExtra("FechaInicio", invoice.getFechaInicio());
        mContext.startActivity(intent);
    }

    private void mostrarRecibo(int pos, float totalKwh){
        InvoiceSheet invoiceSheet = new InvoiceSheet(mContext);
        invoiceSheet.setCalculado(mInvoices.get(pos).calculado);
        invoiceSheet.setClickAgregar(view -> {
            agregarLectura(mInvoices.get(pos).invoice);
        });
        invoiceSheet.setTotalKwh(totalKwh);
        invoiceSheet.show();
    }

    public void setInvoices(List<FacturaCalculada> invoices){
        mInvoices = invoices;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mInvoices != null)
            return mInvoices.size();
        else return 0;
    }

    static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        ItemInvoicesBinding view;

        private InvoiceViewHolder(ItemInvoicesBinding view) {
            super(view.getRoot());
            this.view = view;
        }
    }
}
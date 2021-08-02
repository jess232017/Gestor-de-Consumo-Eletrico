package com.alksoft.controldeconsumoelectrico.vm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alksoft.controldeconsumoelectrico.data.InvoiceRepository;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaCalculada;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Invoice;

import java.util.List;

public class InvoiceViewModel extends AndroidViewModel{
    private final InvoiceRepository mRepository;
    private final LiveData<List<Invoice>> mAllInvoices;

    public InvoiceViewModel (Application application) {
        super(application);
        mRepository = new InvoiceRepository(application);
        mAllInvoices = mRepository.getAllInvoice();
    }

    public LiveData<List<Invoice>> getAllInvoice() { return mAllInvoices; }

    public LiveData<List<FacturaCalculada>> getRecibosCalculado() { return mRepository.getRecibosCalculado(); }

    public LiveData<FacturaCalculada> getReciboCalculado(Invoice invoice)
    {
        return mRepository.getReciboCalculado(invoice);
    }

    public LiveData<Integer> getRecibosCalculado(Invoice invoice)
    {
        return mRepository.isCalculated(invoice);
    }

    public void insert(Invoice invoice) { mRepository.insert(invoice); }

    public void update(Invoice invoice) {
        mRepository.update(invoice);
    }

    public void delete(Invoice invoice) {
        mRepository.delete(invoice);
    }
}
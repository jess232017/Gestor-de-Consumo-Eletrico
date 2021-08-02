package com.alksoft.controldeconsumoelectrico.data;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.alksoft.controldeconsumoelectrico.data.local.dao.InvoiceDao;
import com.alksoft.controldeconsumoelectrico.data.local.database.AppDatabase;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Calculado;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaCalculada;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Invoice;

import java.util.List;

public class InvoiceRepository {
    private InvoiceDao mInvoiceDao;
    private LiveData<List<Invoice>> mAllInvoice;

    public InvoiceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mInvoiceDao = db.facturaDao();
        mAllInvoice = mInvoiceDao.getAll();
    }

    public LiveData<List<Invoice>> getAllInvoice() {
        return mAllInvoice;
    }

    public LiveData<List<FacturaCalculada>> getRecibosCalculado() {
        return mInvoiceDao.getRecibosCalculado();
    }

    public LiveData<FacturaCalculada> getReciboCalculado(Invoice invoice) {
        return mInvoiceDao.getReciboCalculado(invoice.getIdFactura());
    }

    public LiveData<Integer> isCalculated(Invoice invoice) {
        return mInvoiceDao.isCalculated(invoice.getIdFactura());
    }

    public void insert(Invoice invoice) {
        AppDatabase.dbExecutor.execute(
                () ->{
                    long idFactura = mInvoiceDao.insert(invoice);
                    insert(new Calculado(idFactura, "", "", "",
                            "", "", "", "", ""));
                }
        );
    }

    public void update(Invoice invoice) {
        AppDatabase.dbExecutor.execute(
                () -> mInvoiceDao.update(invoice)
        );
    }

    public void delete(Invoice invoice) {
        AppDatabase.dbExecutor.execute(
                () -> mInvoiceDao.delete(invoice)
        );
    }

    //Calculado CRUD
    public void insert(Calculado calculado) {
        AppDatabase.dbExecutor.execute(
                () -> mInvoiceDao.insert(calculado)
        );
    }

    public void update(Calculado calculado) {
        AppDatabase.dbExecutor.execute(
                () -> mInvoiceDao.update(calculado)
        );
    }

    public void delete(Calculado calculado) {
        AppDatabase.dbExecutor.execute(
                () -> mInvoiceDao.delete(calculado)
        );
    }
}



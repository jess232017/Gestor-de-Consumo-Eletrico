package com.alksoft.controldeconsumoelectrico.data;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.alksoft.controldeconsumoelectrico.data.local.database.AppDatabase;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaDiario;

import java.util.List;

public class DesgloseRepository {
    private final LiveData<List<FacturaDiario>> desgloselist;

    public DesgloseRepository(Context context, int IdFactura) {
        AppDatabase db = AppDatabase.getDatabase(context);
        desgloselist = db.facturaDao().getKwhDiarios();
    }

    public LiveData<List<FacturaDiario>> getAllFacturaDiaria() {
        return desgloselist;
    }

    /*public void insert(Desglose desglose) {
        AppDatabase.dbExecutor.execute(
                () -> desgloseDao.insert(desglose)
        );
    }*/
}

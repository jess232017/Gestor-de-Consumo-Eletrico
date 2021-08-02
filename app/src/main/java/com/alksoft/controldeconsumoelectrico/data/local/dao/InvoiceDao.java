package com.alksoft.controldeconsumoelectrico.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.alksoft.controldeconsumoelectrico.data.local.entity.Calculado;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaCalculada;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaDiario;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Invoice;

import java.util.List;

@Dao
public interface InvoiceDao {
    @Query("SELECT * FROM Invoice ORDER BY IdFactura DESC")
    LiveData<List<Invoice>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Invoice invoice);

    @Update
    void update(Invoice invoice);

    @Delete
    void delete(Invoice invoice);

    @Transaction
    @Query("SELECT * FROM Invoice, Daily")
    LiveData<List<FacturaDiario>> getKwhDiarios();

    @Transaction
    @Query("SELECT * FROM Invoice I INNER JOIN Calculado C ON C.IdFactura = I.IdFactura ORDER BY IdFactura DESC")
    LiveData<List<FacturaCalculada>> getRecibosCalculado();

    //Calculado CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Calculado calculado);

    @Update
    void update(Calculado calculado);

    @Delete
    void delete(Calculado calculado);

    @Transaction
    @Query("SELECT COUNT(*)  FROM Calculado WHERE IdFactura = :IdFactura")
    LiveData<Integer> isCalculated(int IdFactura);

    @Transaction
    @Query("SELECT * FROM Invoice I INNER JOIN Calculado C ON C.IdFactura = I.IdFactura WHERE C.IdFactura = :IdFactura")
    LiveData<FacturaCalculada> getReciboCalculado(int IdFactura);
}

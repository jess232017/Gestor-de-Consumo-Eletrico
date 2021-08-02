package com.alksoft.controldeconsumoelectrico.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.alksoft.controldeconsumoelectrico.data.local.LocalConstanst;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Calculado;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Daily;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaCalculada;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaPerfil;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Invoice;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;

import java.util.List;


@Dao
public interface DailyDao {
    @Query("SELECT * FROM Daily WHERE IdFactura = :IdFactura ORDER BY Consumo DESC")
    LiveData<List<Daily>> getAll(int IdFactura);

    @Query("SELECT CAST(Consumo AS VARCHAR(20)) FROM Daily WHERE IdFactura = :IdFactura ORDER BY Consumo DESC")
    LiveData<List<String>> getConsumos(int IdFactura);

    @Query("SELECT * FROM DAILY WHERE IdDia = :IdDia")
    LiveData<Daily> getDaily(int IdDia);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Daily DiarioKwh);

    @Update
    void update(Daily DiarioKwh);

    @Delete
    void delete(Daily DiarioKwh);

    @Transaction
    @Query("UPDATE " + LocalConstanst.NAME_TABLE_FACTURA + " SET ConsumoFinal = ((SELECT Consumo FROM Daily ORDER BY IdDia DESC LIMIT 1 ) + (SELECT ConsumoInicial FROM Invoice WHERE IdFactura = :IdFactura))," +
            "FechaFin = (SELECT Fecha FROM Daily ORDER BY IdDia DESC LIMIT 1) WHERE IdFactura = :IdFactura")
    void updateConsumo(int IdFactura);

    @Query("SELECT * FROM profile LIMIT 1")
    Profile getProfile();

    @Query("SELECT * FROM invoice WHERE IdFactura = :IdFactura")
    Invoice getInvoice(int IdFactura);

    @Transaction
    @Query("SELECT * FROM Invoice I INNER JOIN Calculado C ON C.IdFactura = I.IdFactura WHERE C.IdFactura = :IdFactura")
    FacturaCalculada getReciboCalculado(int IdFactura);

    //Actualizar Calculado
    @Update
    void update(Calculado calculado);
}

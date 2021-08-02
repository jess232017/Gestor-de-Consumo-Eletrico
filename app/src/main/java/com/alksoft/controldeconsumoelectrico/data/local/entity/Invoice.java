package com.alksoft.controldeconsumoelectrico.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Invoice {
    @PrimaryKey(autoGenerate = true)
    private int IdFactura;
    private float ConsumoInicial;
    private float ConsumoFinal;
    private String FechaInicio;
    private String FechaFin;

    public Invoice(){

    }

    public Invoice(float consumoInical, float consumoFinal, String fechaInicio, String fechaFin) {
        ConsumoInicial = consumoInical;
        ConsumoFinal = consumoFinal;
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
    }

    public Invoice(float consumoInicial,String fechaInicio) {
        ConsumoInicial = consumoInicial;
        FechaInicio = fechaInicio;
    }

    public int getIdFactura() {
        return IdFactura;
    }

    public void setIdFactura(int idFactura) {
        IdFactura = idFactura;
    }

    public float getConsumoInicial() {
        return ConsumoInicial;
    }

    public void setConsumoInicial(float consumoInicial) {
        ConsumoInicial = consumoInicial;
    }

    public float getConsumoFinal() {
        return ConsumoFinal;
    }

    public void setConsumoFinal(float consumoFinal) {
        ConsumoFinal = consumoFinal;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
    }
}
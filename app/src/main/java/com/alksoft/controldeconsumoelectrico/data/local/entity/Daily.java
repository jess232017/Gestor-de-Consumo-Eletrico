package com.alksoft.controldeconsumoelectrico.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Daily {
    @PrimaryKey(autoGenerate = true)
    private int IdDia;
    private String Hora;
    private String Fecha;
    private float Consumo;
    private long IdFactura;

    public Daily(){}

    public Daily(String fecha, String hora, float consumo, long idFactura) {
        Hora = hora;
        Fecha = fecha;
        Consumo = consumo;
        IdFactura = idFactura;
    }

    public int getIdDia() {
        return IdDia;
    }

    public void setIdDia(int idDia) {
        IdDia = idDia;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public float getConsumo() {
        return Consumo;
    }

    public void setConsumo(float consumo) {
        Consumo = consumo;
    }

    public long getIdFactura() {
        return IdFactura;
    }

    public void setIdFactura(long idFactura) {
        IdFactura = idFactura;
    }
}
package com.alksoft.controldeconsumoelectrico.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Municipio {
    @SerializedName("valor")
    @Expose
    private String valor;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

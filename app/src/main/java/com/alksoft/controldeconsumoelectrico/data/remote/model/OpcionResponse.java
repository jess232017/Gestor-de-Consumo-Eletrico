package com.alksoft.controldeconsumoelectrico.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpcionResponse {

    @SerializedName("resultado")
    @Expose
    private List<Opcion> resultado = null;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public List<Opcion> getResultado() {
        return resultado;
    }

    public void setResultado(List<Opcion> resultado) {
        this.resultado = resultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

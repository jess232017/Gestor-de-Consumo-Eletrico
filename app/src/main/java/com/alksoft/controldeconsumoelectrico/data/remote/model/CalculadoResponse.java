package com.alksoft.controldeconsumoelectrico.data.remote.model;

import com.alksoft.controldeconsumoelectrico.data.local.entity.Calculado;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculadoResponse {

    @SerializedName("resultado")
    @Expose
    private Calculado resultado;

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public Calculado getResultado() {
        return resultado;
    }

    public void setResultado(Calculado resultado) {
        this.resultado = resultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

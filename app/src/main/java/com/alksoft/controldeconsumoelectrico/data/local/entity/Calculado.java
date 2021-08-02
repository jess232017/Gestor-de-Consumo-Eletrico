package com.alksoft.controldeconsumoelectrico.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Calculado {

    //Room
    @PrimaryKey(autoGenerate = true)
    private int IdCalculado;
    private long IdFactura;

    //API
    @SerializedName("Energia")
    @Expose
    private String energia;
    @SerializedName("Alumbrado")
    @Expose
    private String alumbrado;
    @SerializedName("Comercializacion")
    @Expose
    private String comercializacion;
    @SerializedName("Subsidio_Consumo")
    @Expose
    private String subsidioConsumo;
    @SerializedName("Subsidio_Comercia")
    @Expose
    private String subsidioComercia;
    @SerializedName("Regulacion")
    @Expose
    private String regulacion;
    @SerializedName("IVA")
    @Expose
    private String iva;
    @SerializedName("Total")
    @Expose
    private String total;

    public  Calculado(){

    }

    public Calculado(long idFactura, String energia, String alumbrado, String comercializacion, String regulacion, String iva, String total) {
        IdFactura = idFactura;
        this.energia = energia;
        this.alumbrado = alumbrado;
        this.comercializacion = comercializacion;
        this.regulacion = regulacion;
        this.iva = iva;
        this.total = total;
    }

    public Calculado(long idFactura, String energia, String alumbrado, String comercializacion, String subsidioConsumo, String subsidioComercia, String regulacion, String iva, String total) {
        IdFactura = idFactura;
        this.energia = energia;
        this.alumbrado = alumbrado;
        this.comercializacion = comercializacion;
        this.subsidioConsumo = subsidioConsumo;
        this.subsidioComercia = subsidioComercia;
        this.regulacion = regulacion;
        this.iva = iva;
        this.total = total;
    }

    public int getIdCalculado() {
        return IdCalculado;
    }

    public void setIdCalculado(int idCalculado) {
        IdCalculado = idCalculado;
    }

    public long getIdFactura() {
        return IdFactura;
    }

    public void setIdFactura(long idFactura) {
        IdFactura = idFactura;
    }

    public String getEnergia() {
        return energia;
    }

    public void setEnergia(String energia) {
        this.energia = energia;
    }

    public String getAlumbrado() {
        return alumbrado;
    }

    public void setAlumbrado(String alumbrado) {
        this.alumbrado = alumbrado;
    }

    public String getComercializacion() {
        return comercializacion;
    }

    public void setComercializacion(String comercializacion) {
        this.comercializacion = comercializacion;
    }

    public String getSubsidioConsumo() {
        return subsidioConsumo;
    }

    public void setSubsidioConsumo(String subsidioConsumo) {
        this.subsidioConsumo = subsidioConsumo;
    }

    public String getSubsidioComercia() {
        return subsidioComercia;
    }

    public void setSubsidioComercia(String subsidioComercia) {
        this.subsidioComercia = subsidioComercia;
    }

    public String getRegulacion() {
        return regulacion;
    }

    public void setRegulacion(String regulacion) {
        this.regulacion = regulacion;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
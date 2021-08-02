package com.alksoft.controldeconsumoelectrico.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Profile {
    @PrimaryKey(autoGenerate = true)
    private int IdProfile;

    private String nombreUsuario;

    @SerializedName("RdbCalcularAP")
    @Expose
    private String rdbCalcularAP;
    @SerializedName("CboTarifa")
    @Expose
    private String cboTarifa;
    @SerializedName("CboDepartamento")
    @Expose
    private String cboDepartamento;
    @SerializedName("CboMunicipio")
    @Expose
    private String cboMunicipio;

    public Profile(String nombreUsuario, String rdbCalcularAP, String cboTarifa, String cboDepartamento, String cboMunicipio) {
        this.nombreUsuario = nombreUsuario;
        this.rdbCalcularAP = rdbCalcularAP;
        this.cboTarifa = cboTarifa;
        this.cboDepartamento = cboDepartamento;
        this.cboMunicipio = cboMunicipio;
    }

    public int getIdProfile() {
        return IdProfile;
    }

    public void setIdProfile(int idProfile) {
        IdProfile = idProfile;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRdbCalcularAP() {
        return rdbCalcularAP;
    }

    public void setRdbCalcularAP(String rdbCalcularAP) {
        this.rdbCalcularAP = rdbCalcularAP;
    }

    public String getCboTarifa() {
        return cboTarifa;
    }

    public void setCboTarifa(String cboTarifa) {
        this.cboTarifa = cboTarifa;
    }

    public String getCboDepartamento() {
        return cboDepartamento;
    }

    public void setCboDepartamento(String cboDepartamento) {
        this.cboDepartamento = cboDepartamento;
    }

    public String getCboMunicipio() {
        return cboMunicipio;
    }

    public void setCboMunicipio(String cboMunicipio) {
        this.cboMunicipio = cboMunicipio;
    }
}

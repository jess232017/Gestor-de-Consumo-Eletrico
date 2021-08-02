package com.alksoft.controldeconsumoelectrico.data.remote;

public class ApiConstants {
    public static String getURLBASE(){
        return "https://aqueous-citadel-59203.herokuapp.com/";
    }

    public static final String calcularRecibo = "/calcular-recibo";
    public static final String obtenerTarifas = "/obtener-tarifas";
    public static final String obtenerDepartaments = "/obtener-departamentos";
    public static final String obtenerMunicipios = "/obtener-municipios";
}
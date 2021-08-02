package com.alksoft.controldeconsumoelectrico.data.remote.model;

import com.alksoft.controldeconsumoelectrico.data.remote.ApiConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MunicipioApiService {
    @POST(ApiConstants.calcularRecibo)
    @FormUrlEncoded
    Call<CalculadoResponse> calcularFactura(@Field("TxtActiva") float KwhTotal,
                                         @Field("TxtFechaFacturaAnterior") String FechaInit,
                                         @Field("TxtFechaFactura") String FechaFin,
                                         @Field("RdbCalcularAP") String CalcularAP,
                                         @Field("CboTarifa") String Tarifa,
                                         @Field("CboDepartamento") String Departamento,
                                         @Field("CboMunicipio") String Municipio);

}

package com.alksoft.controldeconsumoelectrico.data.remote.model;

import com.alksoft.controldeconsumoelectrico.data.remote.ApiConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OpcionApiService {
    @GET(ApiConstants.obtenerTarifas)
    Call<OpcionResponse> obtenerTarifas();

    @GET(ApiConstants.obtenerDepartaments)
    Call<OpcionResponse> obtenerDepartments();

    @POST(ApiConstants.obtenerMunicipios)
    @FormUrlEncoded
    Call<OpcionResponse> obtenerMunicipios(@Field("department") String Departments);

}

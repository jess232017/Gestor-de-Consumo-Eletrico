package com.alksoft.controldeconsumoelectrico.data;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.alksoft.controldeconsumoelectrico.utils.Alertar;
import com.alksoft.controldeconsumoelectrico.utils.JvUtils;
import com.alksoft.controldeconsumoelectrico.R;
import com.alksoft.controldeconsumoelectrico.data.remote.RetrofitApi;
import com.alksoft.controldeconsumoelectrico.data.remote.model.Opcion;
import com.alksoft.controldeconsumoelectrico.data.remote.model.OpcionApiService;
import com.alksoft.controldeconsumoelectrico.data.remote.model.OpcionResponse;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.tapadoo.alerter.Alerter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OptionRepository {
    public List<Opcion> tarifasList;
    public List<Opcion> departmentsList;
    public List<Opcion> municipiosList;
    private final OpcionApiService opcionApiService;
    private final Context ctx;

    public OptionRepository(Context ctx){
        //Local > Room
        //deviceDAO = AppDatabase.getAppDb(App.instance.getApplicationContext()).deviceDAO();
        //Remote > Retrofit
        this.ctx = ctx;
        opcionApiService = RetrofitApi.getInstance().getRetrofit().create(OpcionApiService.class);
    }

    public void obtenerTarifas(MaterialAutoCompleteTextView viewTarifas) {
        Call<OpcionResponse> responseCall = opcionApiService.obtenerTarifas();
        responseCall.enqueue(new Callback<OpcionResponse>() {
            @Override
            public void onResponse(@NotNull Call<OpcionResponse> call, @NotNull Response<OpcionResponse> response) {
                if (response.body() != null) {
                    Log.e("debugginRF", "La operacion fue un exito");

                    tarifasList = response.body().getResultado();
                    String[] opcionArray = new String[tarifasList.size()];

                    for (int i = 0; i < tarifasList.size(); i++) {
                        Opcion opcion = tarifasList.get(i);
                        opcionArray[i] = opcion.getNombre();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, R.layout.dropdown, opcionArray);

                    viewTarifas.setThreshold(1);//will start working from first character
                    viewTarifas.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                }
            }

            @Override
            public void onFailure(@NotNull Call<OpcionResponse> call, @NotNull Throwable t) {
                Alertar.error((Activity) ctx, "Error", "No se pudieron obtener las tarifas");
            }
        });
    }

    public void obtenerDepartments(MaterialAutoCompleteTextView viewDepartments) {
        Call<OpcionResponse> responseCall = opcionApiService.obtenerDepartments();
        responseCall.enqueue(new Callback<OpcionResponse>() {
            @Override
            public void onResponse(@NotNull Call<OpcionResponse> call, @NotNull Response<OpcionResponse> response) {
                if (response.body() != null) {
                    Log.e("debugginRF", "La operacion fue un exito");

                    departmentsList = response.body().getResultado();
                    String[] opcionArray = new String[departmentsList.size()];

                    for (int i = 0; i < departmentsList.size(); i++) {
                        Opcion opcion = departmentsList.get(i);
                        opcionArray[i] = opcion.getNombre();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, R.layout.dropdown, opcionArray);

                    viewDepartments.setThreshold(1);//will start working from first character
                    viewDepartments.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                }
            }

            @Override
            public void onFailure(@NotNull Call<OpcionResponse> call, @NotNull Throwable t) {
                Alertar.error((Activity) ctx, "Error", "No se pudieron obtener los departamentos");

            }
        });
    }

    public void obtenerMunicipios(int pos, MaterialAutoCompleteTextView viewMunicipios, CircularProgressIndicator indicator) {
        indicator.setVisibility(View.VISIBLE);
        String Department = departmentsList.get(pos).getValor();

        Call<OpcionResponse> responseCall = opcionApiService.obtenerMunicipios(Department);
        responseCall.enqueue(new Callback<OpcionResponse>() {
            @Override
            public void onResponse(@NotNull Call<OpcionResponse> call, @NotNull Response<OpcionResponse> response) {
                if (response.body() != null) {
                    Log.e("debugginRF", "La operacion fue un exito");

                    municipiosList = response.body().getResultado();
                    String[] opcionArray = new String[municipiosList.size()];

                    for (int i = 0; i < municipiosList.size(); i++) {
                        Opcion opcion = municipiosList.get(i);
                        opcionArray[i] = opcion.getNombre();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, R.layout.dropdown, opcionArray);

                    viewMunicipios.setThreshold(1);//will start working from first character
                    viewMunicipios.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                    indicator.setVisibility(View.GONE);
                    Alerter.hide();
                }
            }

            @Override
            public void onFailure(@NotNull Call<OpcionResponse> call, @NotNull Throwable t) {
                indicator.setVisibility(View.GONE);
                Alertar.error((Activity) ctx, "Error", "No se pudieron obtener los municipios");
            }
        });
    }
}

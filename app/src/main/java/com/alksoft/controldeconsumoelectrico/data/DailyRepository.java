package com.alksoft.controldeconsumoelectrico.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.alksoft.controldeconsumoelectrico.data.local.dao.DailyDao;
import com.alksoft.controldeconsumoelectrico.data.local.database.AppDatabase;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Calculado;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Daily;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaCalculada;
import com.alksoft.controldeconsumoelectrico.data.local.entity.FacturaPerfil;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Invoice;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;
import com.alksoft.controldeconsumoelectrico.data.remote.RetrofitApi;
import com.alksoft.controldeconsumoelectrico.data.remote.model.CalculadoApiService;
import com.alksoft.controldeconsumoelectrico.data.remote.model.CalculadoResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyRepository {
    private final DailyDao mDailyDao;
    private final Application application;

    public DailyRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.application = application;
        mDailyDao = db.diarioKwhDao();
    }

    public LiveData<List<Daily>> getmAllDaily(int IdFactura) {
        return mDailyDao.getAll(IdFactura);
    }

    public LiveData<List<String>> getmAllConsumo(int IdFactura) {
        return mDailyDao.getConsumos(IdFactura);
    }

    public LiveData<Daily> getmDaily(int IdDaily){
        return mDailyDao.getDaily(IdDaily);
    }

    public void insert(Daily daily) {
        AppDatabase.dbExecutor.execute(
                () -> mDailyDao.insert(daily)
        );
    }

    public void UpdateConsumo(int IdFactura) {
        AppDatabase.dbExecutor.execute(() -> {
            mDailyDao.updateConsumo(IdFactura);
            Log.e("debugginRF", "HOLA DESDE REPOSITORU");
            getCalcularConsumo(IdFactura);
        });
    }

    public void getCalcularConsumo(int IdFactura){
        Thread thread = new Thread(() -> {
            //Obtener recibo y su calculo previo
            FacturaCalculada facturaCalculada = mDailyDao.getReciboCalculado(IdFactura);
            final Calculado calculado = facturaCalculada.calculado;
            Invoice invoice = facturaCalculada.invoice;

            //Obtener perfil de la factura
            Profile profile = mDailyDao.getProfile();

            //Formatear los datos
            float KwhTotal = invoice.getConsumoFinal() - invoice.getConsumoInicial();
            String FechaInicio = invoice.getFechaInicio().replace("/", "-");
            String FechaFin = invoice.getFechaFin().replace("/", "-");

            //Instanciar llamdo a la Api
            Call<CalculadoResponse> responseCall = RetrofitApi.getInstance().getRetrofit().create(CalculadoApiService.class).
                    calcularFactura(KwhTotal, FechaInicio, FechaFin,
                            profile.getRdbCalcularAP(), profile.getCboTarifa(), profile.getCboDepartamento(),
                            profile.getCboMunicipio());

            responseCall.enqueue(new Callback<CalculadoResponse>() {
                @Override
                public void onResponse(@NotNull Call<CalculadoResponse> call, @NotNull Response<CalculadoResponse> response) {
                    if (response.body() != null) {
                        Calculado respuesta = response.body().getResultado();
                        respuesta.setIdFactura(IdFactura);
                        respuesta.setIdCalculado(calculado.getIdCalculado());

                        /*
                        "Energia": "1,581.47",
                        "Alumbrado": "186.31",
                        "Comercializacion": "100.85",
                        "Regulacion": "18.69",
                        "Total": "2,170.41"

                        "IVA": "283.10",                Nullable
                        "Subsidio_Consumo": "-329.51",  Nullable
                        "Subsidio_Comercia": "-1.73",   Nullable
                        * */
                        /*calculado.setTotal(respuesta.getTotal());
                        calculado.setAlumbrado(respuesta.getAlumbrado());
                        calculado.setComercializacion(respuesta.getComercializacion());
                        calculado.setRegulacion(respuesta.getRegulacion());

                        if(respuesta.getIva() != null){
                            calculado.setIva(respuesta.getIva());
                        }

                        if(respuesta.getSubsidioComercia() != null){
                            calculado.setSubsidioComercia(respuesta.getSubsidioComercia());
                        }

                        if(respuesta.getSubsidioConsumo() != null){
                            calculado.setSubsidioConsumo(respuesta.getSubsidioConsumo());
                        }*/

                        AppDatabase.dbExecutor.execute(
                                () -> mDailyDao.update(respuesta)
                        );
                    }else{
                        Log.e("debugginRF", "La operacion a fallado");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculadoResponse> call, @NotNull Throwable t) {
                    Log.e("debugginRF", "La operacion a fallado");
                }
            });
        });
        thread.start();
    }

    public void update(Daily daily) {
        AppDatabase.dbExecutor.execute(
                () -> mDailyDao.update(daily)
        );
    }

    public void delete(Daily daily) {
        AppDatabase.dbExecutor.execute(
                () -> mDailyDao.delete(daily)
        );
    }
}



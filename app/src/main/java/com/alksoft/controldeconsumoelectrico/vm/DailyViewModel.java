package com.alksoft.controldeconsumoelectrico.vm;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alksoft.controldeconsumoelectrico.data.DailyRepository;
import com.alksoft.controldeconsumoelectrico.data.InvoiceRepository;
import com.alksoft.controldeconsumoelectrico.data.ProfileRepository;
import com.alksoft.controldeconsumoelectrico.data.Repository;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Daily;
import com.alksoft.controldeconsumoelectrico.data.remote.RetrofitApi;
import com.alksoft.controldeconsumoelectrico.data.remote.model.CalculadoApiService;
import com.alksoft.controldeconsumoelectrico.data.remote.model.CalculadoResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyViewModel extends AndroidViewModel implements Repository {
    private final DailyRepository mRepository;


    public DailyViewModel(Application application) {
        super(application);
        mRepository = new DailyRepository(application);
    }

    public LiveData<List<Daily>> getAllDaily(int IdInvoice) {
        return mRepository.getmAllDaily(IdInvoice);
    }

    public LiveData<List<String>> getAllConsumo(int IdInvoice)
    {
        return mRepository.getmAllConsumo(IdInvoice);
    }

    public LiveData<Daily> getDaily(int IdDaily)
    {
        return  mRepository.getmDaily(IdDaily);
    }

    public void insert(Daily daily)
    {
        mRepository.insert(daily);
        mRepository.UpdateConsumo((int) daily.getIdFactura());
    }

    public void update(Daily daily) {
        mRepository.update(daily);
        mRepository.UpdateConsumo((int) daily.getIdFactura());
    }

    public void delete(Daily daily) {
        mRepository.delete(daily);
        mRepository.UpdateConsumo((int) daily.getIdFactura());
    }

    @Override
    public void insert() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
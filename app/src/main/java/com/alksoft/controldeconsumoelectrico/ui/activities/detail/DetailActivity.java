package com.alksoft.controldeconsumoelectrico.ui.activities.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alksoft.controldeconsumoelectrico.R;
import com.alksoft.controldeconsumoelectrico.databinding.ActivityDetailBinding;
import com.alksoft.controldeconsumoelectrico.ui.dialogs.InsertReadingDialog;
import com.alksoft.controldeconsumoelectrico.vm.DailyViewModel;

public class DetailActivity extends AppCompatActivity{
    private DailyViewModel mdailyViewModel;
    private ActivityDetailBinding binding;

    private int idFactura;
    private float ConsumoInicial;
    private float ConsumoUltimo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.AppBar.toolbar.setTitle("");
        setSupportActionBar(binding.AppBar.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idFactura = getIntent().getExtras().getInt("IdFactura");
        String fechaInicio = getIntent().getExtras().getString("FechaInicio");
        ConsumoInicial = getIntent().getExtras().getFloat("ConsumoInicial");

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        mdailyViewModel = new ViewModelProvider(this, factory).get(DailyViewModel.class);

        final ReadingListAdapter adapter = new ReadingListAdapter(this, mdailyViewModel, ConsumoInicial, fechaInicio);
        binding.listInvoice.setAdapter(adapter);
        binding.listInvoice.setLayoutManager(new LinearLayoutManager(this));


        mdailyViewModel.getAllDaily(idFactura).observe(this, dailies -> {
            adapter.setDailies(dailies);
            try{
                ConsumoUltimo = dailies.get(0).getConsumo();
            }catch (Exception e){
                ConsumoUltimo = 0;
            }
        });

        binding.fabAgregar.setOnClickListener(v -> Dialog_InsertDaily());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        binding.AppBar.searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void Dialog_InsertDaily() {
        InsertReadingDialog insertReading = new InsertReadingDialog(this);
        insertReading.setIdFactura(idFactura);
        insertReading.setConsumoUltimo(ConsumoUltimo);
        insertReading.setConsumoInicial(ConsumoInicial);
        insertReading.setmdailyViewModel(mdailyViewModel);
        insertReading.show();
    }
}
package com.alksoft.controldeconsumoelectrico.ui.activities.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alksoft.controldeconsumoelectrico.R;
import com.alksoft.controldeconsumoelectrico.databinding.ActivityDetailBinding;
import com.alksoft.controldeconsumoelectrico.ui.dialogs.InsertReadingDialog;
import com.alksoft.controldeconsumoelectrico.utils.Alertar;
import com.alksoft.controldeconsumoelectrico.vm.DailyViewModel;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

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

        ReadingListAdapter adapter = new ReadingListAdapter(this, mdailyViewModel, ConsumoInicial, fechaInicio);
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
        setCalendar();
    }

    private void setCalendar(){
        binding.calendarView.addEventTag(2021, 7, 29);
        binding.calendarView.addEventTag(2021, 7, 30);
        binding.calendarView.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
           @Override
           public void onDayChanged() {
               Day day = binding.calendarView.getSelectedDay();
               String subtitle = "Selected Day: "
                       + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay();
               //Alertar.loading(DetailActivity.this, "Calendar", subtitle);
               Toast.makeText(DetailActivity.this, subtitle, Toast.LENGTH_LONG).show();
           }

           @Override
           public void onClickListener() {
               Day day = binding.calendarView.getSelectedDay();
               String subtitle = "Selected Day: "
                       + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay();
               //Alertar.loading(DetailActivity.this, "Calendar", subtitle);
               Toast.makeText(DetailActivity.this, subtitle, Toast.LENGTH_LONG).show();
           }

           @Override
            public void onDaySelect() {
                Day day = binding.calendarView.getSelectedDay();
                String subtitle = "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay();
                //Alertar.loading(DetailActivity.this, "Calendar", subtitle);
                Toast.makeText(DetailActivity.this, subtitle, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemClick(View view) {
                Day day = binding.calendarView.getSelectedDay();
                String subtitle = "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay();
                //Alertar.loading(DetailActivity.this, "Calendar", subtitle);
                Toast.makeText(DetailActivity.this, subtitle, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDataUpdate() {
                Day day = binding.calendarView.getSelectedDay();
                String subtitle = "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay();
                //Alertar.loading(DetailActivity.this, "Calendar", subtitle);
                Toast.makeText(DetailActivity.this, subtitle, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMonthChange() {
                Day day = binding.calendarView.getSelectedDay();
                String subtitle = "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay();
                //Alertar.loading(DetailActivity.this, "Calendar", subtitle);
                Toast.makeText(DetailActivity.this, subtitle, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onWeekChange(int i) {
                Day day = binding.calendarView.getSelectedDay();
                String subtitle = "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay();
                //Alertar.loading(DetailActivity.this, "Calendar", subtitle);
                Toast.makeText(DetailActivity.this, subtitle, Toast.LENGTH_LONG).show();
            }
        });
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
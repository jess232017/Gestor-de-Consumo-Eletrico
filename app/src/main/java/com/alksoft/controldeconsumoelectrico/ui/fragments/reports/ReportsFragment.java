package com.alksoft.controldeconsumoelectrico.ui.fragments.reports;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alksoft.controldeconsumoelectrico.databinding.FragmentReportsBinding;
import com.alksoft.controldeconsumoelectrico.utils.JvUtils;
import com.alksoft.controldeconsumoelectrico.vm.ProfileViewModel;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReportsFragment extends Fragment {
    private ProfileViewModel vmProfile;
    private FragmentReportsBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReportsBinding.inflate(inflater, container, false);

        ///Inicializar ViewModel
        vmProfile = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        vmProfile.getProfile().observe(requireActivity(), profile -> {
            if(profile != null){
                binding.toolbar.setTitle("Hola, " + JvUtils.splitName(profile.getNombreUsuario()));
            }
        });

        BarData data = setDataInBarData();
        binding.chart1.setData(data);
        binding.chart1.animateXY(2000, 2000);
        binding.chart1.invalidate();

        binding.chart2.getDescription().setEnabled(false);
        binding.chart2.setBackgroundColor(Color.WHITE);
        binding.chart2.setDrawGridBackground(false);
        binding.chart2.setDrawBarShadow(false);
        binding.chart2.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        binding.chart2.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.LINE
        });

        Legend l =  binding.chart2.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(setDataInBarData());
        combinedData.setData(generateLineData());

        binding.chart2.setData(combinedData);
        binding.chart2.invalidate();

        LineData lineData = generateLineData();

        binding.chart3.getDescription().setEnabled(false);
        binding.chart3.setBackgroundColor(Color.WHITE);
        binding.chart3.setDrawGridBackground(false);

        binding.chart3.setData(lineData);
        binding.chart3.invalidate();

        return binding.getRoot();
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < 12; index++)
            entries.add(new Entry(index + 0.5f, (float) (Math.random() * 15) + 5));

        LineDataSet set = new LineDataSet(entries, "Promedio diario según mes");
        set.setColor(Color.rgb(54, 93, 195));
        set.setLineWidth(2.5f);

        //54,93,195
        set.setCircleColor(Color.rgb(54, 93, 195));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(54, 93, 195));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(54, 93, 195));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData setDataInBarData() {

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(0, 138, "JL"));
        entries.add(new BarEntry(1, 120, "AG"));
        entries.add(new BarEntry(2, 141, "SP"));
        entries.add(new BarEntry(3, 150, "OC"));
        entries.add(new BarEntry(4, 150, "NO"));
        entries.add(new BarEntry(5, 118,"DI"));
        entries.add(new BarEntry(6, 120, "EN"));
        entries.add(new BarEntry(7, 130, "FE"));
        entries.add(new BarEntry(8, 110, "MZ"));
        entries.add(new BarEntry(9, 120, "AB"));
        entries.add(new BarEntry(10, 139, "MY"));
        entries.add(new BarEntry(11, 141, "JN"));

        BarDataSet d = new BarDataSet(entries, "Consumo según mes");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
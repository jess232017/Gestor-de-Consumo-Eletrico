package com.alksoft.controldeconsumoelectrico.ui.activities.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alksoft.controldeconsumoelectrico.R;
import com.alksoft.controldeconsumoelectrico.data.OptionRepository;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;
import com.alksoft.controldeconsumoelectrico.databinding.ActivityRegisterBinding;
import com.alksoft.controldeconsumoelectrico.ui.activities.main.MainActivity;
import com.alksoft.controldeconsumoelectrico.utils.Alertar;
import com.alksoft.controldeconsumoelectrico.utils.PrefManager;
import com.alksoft.controldeconsumoelectrico.vm.ProfileViewModel;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding view;
    private ProfileViewModel mProfileViewModel;
    private OptionRepository optionRepository;
    private PrefManager prefManager;
    private int indexDepartment = -1;
    private int indexMunicipio = -1;
    private int indexTarifa = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefManager = PrefManager.getInstance(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        view = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        optionRepository = new OptionRepository(this);
        mProfileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        //Rellenar "Spinner" con lista remota
        optionRepository.obtenerTarifas(view.txtTarifa);
        optionRepository.obtenerDepartments(view.txtDepartamento);

        //Escucha de eventos
        view.txtTarifa.setOnItemClickListener((parent, arg1, pos, id) -> indexTarifa = pos);

        view.txtDepartamento.setOnItemClickListener((parent, arg1, pos, id) -> {
            Alertar.loading(this, "Cargando", "Munipios de " + view.txtDepartamento.getText());
            indexDepartment = pos;
            view.txtMunicipio.setText("");
            optionRepository.obtenerMunicipios(indexDepartment, view.txtMunicipio, view.cpiMunicipio);
        });

        view.txtMunicipio.setOnItemClickListener((parent, arg1, pos, id) -> indexMunicipio = pos);

        //Evento click de Registrar perfil
        view.BtnConfirmar.setOnClickListener(this::registrarPerfil);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PrefManager.getInstance(RegisterActivity.this).getProfileExist()) {
            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @SuppressLint("SetTextI18n")
    private void registrarPerfil(View view1){
        String calcularAp = (view.switchAP.isChecked()) ? "Si" : "No";
        String NombreUsuario = Objects.requireNonNull(view.txtNombre.getText()).toString();

        if(NombreUsuario.length() < 3){
            view.lytNombre.setError("Nombre no valido");
            return;
        }else{
            view.lytNombre.setError(null);
        }

        if(indexTarifa == -1){
            view.lytTarifa.setError("Tarifa no valida");
            return;
        }else{
            view.lytTarifa.setError(null);
        }

        if(indexDepartment == -1){
            view.lytDepartamento.setError("Departamento no valido");
            return;
        }else{
            view.lytDepartamento.setError(null);
        }

        if(indexMunicipio == -1){
            view.lytMunicipio.setError("Municipio no valido");
            return;
        }else{
            view.lytMunicipio.setError(null);
        }

        String Tarifa = optionRepository.tarifasList.get(indexTarifa).getValor();
        String Departamento =optionRepository.departmentsList.get(indexDepartment).getValor();
        String Municipio = optionRepository.municipiosList.get(indexMunicipio).getValor();

        Alertar.loading(this, "Guardando","Guardando su perfil");
        Profile profile = new Profile(NombreUsuario, calcularAp, Tarifa, Departamento, Municipio);
        mProfileViewModel.insert(profile);
        prefManager.setProfileExist(true);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
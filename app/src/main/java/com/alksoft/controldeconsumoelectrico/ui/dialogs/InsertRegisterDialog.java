package com.alksoft.controldeconsumoelectrico.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alksoft.controldeconsumoelectrico.data.OptionRepository;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;
import com.alksoft.controldeconsumoelectrico.data.remote.RetrofitApi;
import com.alksoft.controldeconsumoelectrico.databinding.DialogInsertRegisterBinding;
import com.alksoft.controldeconsumoelectrico.utils.Alertar;
import com.alksoft.controldeconsumoelectrico.utils.PrefManager;
import com.alksoft.controldeconsumoelectrico.vm.ProfileViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InsertRegisterDialog extends BottomSheetDialog {
    private DialogInsertRegisterBinding view;
    private OptionRepository optionRepository;
    private int indexDepartment = -1;
    private int indexMunicipio = -1;
    private int indexTarifa = -1;
    private Profile profile;
    private final Context ctx;

    public InsertRegisterDialog(@NonNull @NotNull Context context) {
        super(context);
        optionRepository = new OptionRepository(context);
        ctx = context;
    }

    private InsertRegisterDialog(@NonNull @NotNull Context context, int theme) {
        super(context, theme);
        ctx = context;
    }

    private InsertRegisterDialog(@NonNull @NotNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        ctx = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = DialogInsertRegisterBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        //Rellenar "Spinner" con lista remota
        optionRepository.obtenerTarifas(view.txtTarifa);
        optionRepository.obtenerDepartments(view.txtDepartamento);

        //Escucha de eventos
        view.txtTarifa.setOnItemClickListener((parent, arg1, pos, id) -> {
            indexTarifa = pos;
        });

        view.txtDepartamento.setOnItemClickListener((parent, arg1, pos, id) -> {
            Alertar.loading((Activity) ctx, "Cargando", "Munipios de " + view.txtDepartamento.getText());
            indexDepartment = pos;
            view.txtMunicipio.setText("");
            optionRepository.obtenerMunicipios(indexDepartment, view.txtMunicipio, view.cpiMunicipio);
        });

        view.txtMunicipio.setOnItemClickListener((parent, arg1, pos, id) -> {
            indexMunicipio = pos;
        });

        //Evento click de Registrar perfil
        view.BtnConfirmar.setOnClickListener(this::registrarPerfil);
        editarPerfil();
    }

    private void editarPerfil(){
        if(profile != null){
            view.txtTitulo.setText("EDITAR PERFIL");
        }
    }

    private void registrarPerfil(View view1){
        String calcularAp = (view.switchAP.isChecked()) ? "Si" : "No";
        String NombreUsuario = Objects.requireNonNull(view.txtNombre.getText()).toString();

        if(NombreUsuario.length() < 3){
            Toast.makeText(getContext(), "Nombre no valido", Toast.LENGTH_SHORT).show();
            return;
        }

        if(indexTarifa == -1){
            Toast.makeText(getContext(), "Tarifa no valida", Toast.LENGTH_SHORT).show();
            return;
        }

        if(indexDepartment == -1){
            Toast.makeText(getContext(), "Departamento no valido", Toast.LENGTH_SHORT).show();
            return;
        }

        if(indexMunicipio == -1){
            Toast.makeText(getContext(), "Municipio no valido", Toast.LENGTH_SHORT).show();
            return;
        }

        String Tarifa = optionRepository.tarifasList.get(indexTarifa).getValor();
        String Departamento =optionRepository.departmentsList.get(indexDepartment).getValor();
        String Municipio = optionRepository.municipiosList.get(indexMunicipio).getValor();

        profile = new Profile(NombreUsuario, calcularAp, Tarifa, Departamento, Municipio);
        PrefManager prefManager = PrefManager.getInstance(ctx);
        prefManager.setProfileExist(true);
        prefManager.setDepartment(view.txtDepartamento.getText().toString());
        prefManager.setMunicipio(view.txtMunicipio.getText().toString());
        dismiss();
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setOptionRepository(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }
}

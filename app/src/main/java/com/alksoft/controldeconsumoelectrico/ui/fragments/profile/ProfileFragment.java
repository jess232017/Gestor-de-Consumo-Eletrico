package com.alksoft.controldeconsumoelectrico.ui.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alksoft.controldeconsumoelectrico.databinding.FragmentProfileBinding;
import com.alksoft.controldeconsumoelectrico.ui.dialogs.InsertRegisterDialog;
import com.alksoft.controldeconsumoelectrico.utils.Alertar;
import com.alksoft.controldeconsumoelectrico.utils.PrefManager;
import com.alksoft.controldeconsumoelectrico.vm.ProfileViewModel;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel vmProfile;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.AppBar.title.setText("Perfil");

        PrefManager prefManager = PrefManager.getInstance(getContext());


        //ViewModel de Profile
        vmProfile = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        vmProfile.getProfile().observe(requireActivity(), profile -> {
            if(profile != null){
                binding.txtNombre.setText(profile.getNombreUsuario());
                binding.txtNombre2.setText(profile.getNombreUsuario());
                binding.txtDepartamento.setText(prefManager.getDepartment() + ", " + prefManager.getMunicipio());
                //binding.toolbar.setTitle(JvUtils.splitName(profile.getNombreUsuario()));
                binding.txtAlumbrado.setText(profile.getRdbCalcularAP());
                binding.txtDepartamento2.setText(prefManager.getDepartment());
                binding.txtMunicipio.setText(prefManager.getMunicipio());
                binding.txtTarifa.setText(profile.getCboTarifa());
            }
        });

        binding.fabEditar.setOnClickListener(this::editarPerfil);

        return binding.getRoot();
    }


    private void editarPerfil(View view){
        InsertRegisterDialog registerDialog = new InsertRegisterDialog(requireActivity());

        if(registerDialog.getProfile() != null){
            vmProfile.insert(registerDialog.getProfile());
            Alertar.successSave(requireActivity(), "Guardando","Guardando su perfil");
        }

        registerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        vmProfile = null;
        binding = null;
    }
}
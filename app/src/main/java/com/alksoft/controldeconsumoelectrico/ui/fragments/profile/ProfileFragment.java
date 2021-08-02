package com.alksoft.controldeconsumoelectrico.ui.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alksoft.controldeconsumoelectrico.databinding.FragmentProfileBinding;
import com.alksoft.controldeconsumoelectrico.ui.dialogs.InsertRegisterDialog;
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

        //ViewModel de Profile
        vmProfile = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        vmProfile.getProfile().observe(requireActivity(), profile -> {
            if(profile != null){
                /*binding.toolbar.setTitle(JvUtils.splitName(profile.getNombreUsuario()));
                binding.switchAP.setChecked(profile.getRdbCalcularAP().equals("Si"));
                binding.txtDepartamento.setText(profile.getCboDepartamento());
                binding.txtMunicipio.setText(profile.getCboMunicipio());
                binding.txtTarifa.setText(profile.getCboTarifa());*/
            }
        });

        return binding.getRoot();
    }

    private void editarPerfil(){
        InsertRegisterDialog registerDialog = InsertRegisterDialog.getInstance(requireContext());
        registerDialog.setmProfileViewModel(vmProfile);
        registerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
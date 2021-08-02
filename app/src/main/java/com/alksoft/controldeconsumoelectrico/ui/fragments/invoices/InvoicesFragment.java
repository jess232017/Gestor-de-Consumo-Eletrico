package com.alksoft.controldeconsumoelectrico.ui.fragments.invoices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;
import com.alksoft.controldeconsumoelectrico.databinding.FragmentInvoicesBinding;
import com.alksoft.controldeconsumoelectrico.ui.dialogs.InsertInvoiceDialog;
import com.alksoft.controldeconsumoelectrico.utils.JvUtils;
import com.alksoft.controldeconsumoelectrico.vm.InvoiceViewModel;
import com.alksoft.controldeconsumoelectrico.vm.ProfileViewModel;

public class InvoicesFragment extends Fragment {
    private InvoiceViewModel mInvoiceViewModel;
    private FragmentInvoicesBinding binding;
    private ProfileViewModel vmProfile;
    private float ConsumoUltimoAnt;
    private Profile profile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoicesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ///Inicializar ViewModel
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        mInvoiceViewModel = new ViewModelProvider(this, factory).get(InvoiceViewModel.class);

        //Inicialiazar Adaptador
        final InvoiceListAdapter adapter = new InvoiceListAdapter(getActivity(), mInvoiceViewModel);
        binding.listInvoice.setAdapter(adapter);
        binding.listInvoice.setLayoutManager(new LinearLayoutManager(getContext()));

        //Obervar datos
        mInvoiceViewModel.getRecibosCalculado().observe(requireActivity(), invoices -> {
            if(invoices.size() == 0){
                binding.lytList.setVisibility(View.GONE);
                binding.noItem.getRoot().setVisibility(View.VISIBLE);
            }else{
                binding.noItem.getRoot().setVisibility(View.GONE);
                binding.lytList.setVisibility(View.VISIBLE);
            }

            adapter.setInvoices(invoices);
            try{
                ConsumoUltimoAnt = invoices.get(0).invoice.getConsumoFinal();
            }catch (Exception e){
                ConsumoUltimoAnt = 0;
            }
        });

        //ViewModel de Profile
        vmProfile = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        vmProfile.getProfile().observe(requireActivity(), item -> {
            profile = item;
            if(profile != null){
                binding.AppBar.title.setText(JvUtils.splitName(item.getNombreUsuario()));
            }
        });

        binding.fabAgregar.setOnClickListener(this::insertarRecibo);
        binding.noItem.BtnAccion.setOnClickListener(this::insertarRecibo);

        return root;
    }

    private void insertarRecibo(View view) {
        if(profile != null){
            InsertInvoiceDialog insertInvoice = new InsertInvoiceDialog(requireContext());
            insertInvoice.setmInvoiceViewModel(mInvoiceViewModel);
            insertInvoice.setConsumoUltimoAnt(ConsumoUltimoAnt);
            insertInvoice.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
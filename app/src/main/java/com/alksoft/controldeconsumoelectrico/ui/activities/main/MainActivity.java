package com.alksoft.controldeconsumoelectrico.ui.activities.main;

import android.os.Bundle;
import android.view.Menu;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.alksoft.controldeconsumoelectrico.R;
import com.alksoft.controldeconsumoelectrico.databinding.ActivityMainBinding;
import com.alksoft.controldeconsumoelectrico.vm.ProfileViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    private ProfileViewModel vmProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.main_fragment);
        setupSmoothBottomMenu();

        vmProfile = new ViewModelProvider(this).get(ProfileViewModel.class);
        vmProfile.getProfile().observe(this, item -> {
        });
    }

    private void setupSmoothBottomMenu() {
        PopupMenu popupMenu = new PopupMenu(this, null);
        popupMenu.inflate(R.menu.bottom_nav_menu);
        Menu menu = popupMenu.getMenu();
        binding.bottomBar.setupWithNavController(menu, navController);
    }
}
package com.alksoft.controldeconsumoelectrico.vm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alksoft.controldeconsumoelectrico.data.ProfileRepository;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;

public class ProfileViewModel extends AndroidViewModel{
    private final ProfileRepository mRepository;

    public ProfileViewModel(Application application) {
        super(application);
        mRepository = new ProfileRepository(application);
    }

    public LiveData<Profile> getProfile() {
        return mRepository.getProfile();
    }

    public void insert(Profile Profile)
    {
        mRepository.insert(Profile);
    }

    public void update(Profile Profile) {
        mRepository.update(Profile);
    }

    public void delete(Profile Profile) {
        mRepository.delete(Profile);
    }

    /*public boolean checkExist(Context ctx){
        if(profile == null){
            new MaterialAlertDialogBuilder(ctx)
                    .setTitle("Se requiere de un Perfil")
                    .setMessage("El perfil permite realizar diversos calculos para obtener el recibo.")
                    .setPositiveButton("Ok", (dialogInterface, i) -> {
                        //InsertRegisterDialog registerDialog = InsertRegisterDialog.getInstance(ctx);
                        //registerDialog.setmProfileViewModel(this);
                        //registerDialog.show();
                        //dialogInterface.dismiss();
                    }).setNegativeButton("No", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    }
            ).show();
            return false;
        }
        return true;
    }*/
}
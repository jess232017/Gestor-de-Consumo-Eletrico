package com.alksoft.controldeconsumoelectrico.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alksoft.controldeconsumoelectrico.data.local.dao.ProfileDao;
import com.alksoft.controldeconsumoelectrico.data.local.dao.ProfileDao;
import com.alksoft.controldeconsumoelectrico.data.local.database.AppDatabase;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;

import java.util.List;

public class ProfileRepository {
    private ProfileDao mProfileDao;
    private LiveData<Profile> mAllProfile;

    public ProfileRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mProfileDao = db.profileDao();
        mAllProfile = mProfileDao.getProfile();
    }

    public LiveData<Profile> getProfile() {
        return mAllProfile;
    }

    public LiveData<Integer> existProfile() {
        return mProfileDao.existProfile();
    }

    public void insert(Profile Profile) {
        AppDatabase.dbExecutor.execute(
                () -> mProfileDao.insert(Profile)
        );
    }

    public void update(Profile Profile) {
        AppDatabase.dbExecutor.execute(
                () -> mProfileDao.update(Profile)
        );
    }

    public void delete(Profile Profile) {
        AppDatabase.dbExecutor.execute(
                () -> mProfileDao.delete(Profile)
        );
    }
}



package com.alksoft.controldeconsumoelectrico.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alksoft.controldeconsumoelectrico.data.local.entity.Daily;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Invoice;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;

import java.util.List;

@Dao
public interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Profile profile);

    @Update
    void update(Profile profile);

    @Delete
    void delete(Profile profile);

    @Query("SELECT * FROM profile LIMIT 1")
    LiveData<Profile> getProfile();

    @Query("SELECT COUNT(*) FROM profile")
    LiveData<Integer> existProfile();
}

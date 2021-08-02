package com.alksoft.controldeconsumoelectrico.data.local.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alksoft.controldeconsumoelectrico.data.local.LocalConstanst;
import com.alksoft.controldeconsumoelectrico.data.local.dao.DailyDao;
import com.alksoft.controldeconsumoelectrico.data.local.dao.InvoiceDao;
import com.alksoft.controldeconsumoelectrico.data.local.dao.ProfileDao;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Calculado;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Daily;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Invoice;
import com.alksoft.controldeconsumoelectrico.data.local.entity.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Invoice.class, Daily.class, Calculado.class, Profile.class} ,  version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    //our app database object
    private static AppDatabase INSTANCE;
    public abstract InvoiceDao facturaDao();
    public abstract DailyDao diarioKwhDao();
    public abstract ProfileDao profileDao();

    private static final int THREADS = 2;
    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, LocalConstanst.NAME_DATABASE)
                            .addCallback(mRoomCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    // Prepoblar base de datos con callback
    private static final RoomDatabase.Callback mRoomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            dbExecutor.execute(() -> {
                /*long idFactura = INSTANCE.facturaDao().insert(new Invoice(13361, "17/11/2020"));
                long idCalculado = INSTANCE.facturaDao().insert(new Calculado(idFactura, "0", "0", "0",
                        "0", "0", "0", "0", "0"));
                DailyDao dailyDao = INSTANCE.diarioKwhDao();
                //dd/MM/yy
                dailyDao.insert(new Daily("27/11/2020","8:30", 38, idFactura));
                dailyDao.insert(new Daily("28/11/2020","10:30", 43.3f, idFactura));
                dailyDao.insert(new Daily("29/11/2020","7:42", 46.7f, idFactura));
                dailyDao.insert(new Daily("30/11/2020","7:10", 50.3f, idFactura));
                dailyDao.insert(new Daily("01/12/2020","8:31", 53, idFactura));

                dailyDao.insert(new Daily("02/12/2020","9:19", 56.9f, idFactura));
                dailyDao.insert(new Daily("03/12/2020","8:00", 59.4f, idFactura));
                dailyDao.insert(new Daily("04/12/2020","8:00", 63.4f, idFactura));
                dailyDao.insert(new Daily("05/12/2020","8:31", 66.4f, idFactura));
                dailyDao.insert(new Daily("06/12/2020","7:17", 70.4f, idFactura));
                dailyDao.insert(new Daily("07/12/2020","9:36", 74.3f, idFactura));
                dailyDao.insert(new Daily("08/12/2020","8:15", 78.6f, idFactura));
                dailyDao.insert(new Daily("09/12/2020","10:30", 82.2f, idFactura));
                dailyDao.insert(new Daily("10/12/2020","8:04", 84.5f, idFactura));
                dailyDao.insert(new Daily("11/12/2020","8:33", 88, idFactura));
                dailyDao.insert(new Daily("12/12/2020","10:11", 92.5f, idFactura));
                dailyDao.insert(new Daily("13/12/2020","08:51", 95.6f, idFactura));*/
            });
        }
    };
}
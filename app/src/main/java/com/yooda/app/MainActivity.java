package com.yooda.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.yooda.app.adapter.HeadCategoryAdapter;
import com.yooda.app.fragment.StoreFragment;
import com.yooda.app.model.HeadCategory;
import com.yooda.app.services.FloatCallerService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    private static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 0;

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;

    RecyclerView headCategoryRecycler;
    HeadCategoryAdapter headCategoryAdapter;
    SharedPreferences sharedpreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takeAllPermissions();

        // Запуск сервиса окна вызова поверх окон
        if (Settings.canDrawOverlays(this)) {
            startService(new Intent(getApplication(), FloatCallerService.class));
        }
        
        // Local Storage
        sharedpreferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        Log.d("LOL", sharedpreferences.getAll().toString()); // Local Storage

        // Табы
        TabLayout tabLayout = findViewById(R.id.header_tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                gotoFragment(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Фрагменты приложения
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_view, StoreFragment.class, null)
                .commit();
        }


        //Списки
        List<HeadCategory> list = new ArrayList<>();
        list.add(new HeadCategory(1,"Склад", "storage"));
        list.add(new HeadCategory(2,"Финансы", "cash"));
        list.add(new HeadCategory(3,"Контакты", "contacts"));
        list.add(new HeadCategory(4,"Планировщик", "schedule"));
        list.add(new HeadCategory(5,"История", "history"));
        list.add(new HeadCategory(6,"Статистика", "stats"));
        //setHeadCategoryRecycler(list);
    }

    public void setHeadCategoryRecycler(List<HeadCategory> list){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        //headCategoryRecycler = findViewById(R.id.header_recycler);
        headCategoryRecycler.setLayoutManager(layoutManager);

        headCategoryAdapter = new HeadCategoryAdapter(this, list);

        headCategoryRecycler.setAdapter(headCategoryAdapter);
    }


    public void gotoFragment(TabLayout.Tab tab) {
        String fr = "StoreFragment";
        switch (tab.getPosition()){
            case 0: fr = "StoreFragment"; break;
            case 1: fr = "CashFragment"; break;
            case 2: fr = "ContactFragment"; break;
            case 3: fr = "ScheduleFragment"; break;
            case 4: fr = "HistoryFragment"; break;
            case 5: fr = "StatisticFragment"; break;
        }

        try{
            if(fr.equals("StatisticFragment")){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("cac", "1");
                editor.commit();
            }

            Fragment f = (Fragment)(Class.forName("com.yooda.app.fragment." + fr).newInstance());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            FragmentTransaction replace = transaction.replace(R.id.fragment_container_view, f);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch(ClassNotFoundException e){
            Log.e("Loading","Класс не найден",e);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void takeAllPermissions()
    {
        int readPhonePermissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (readPhonePermissionState == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Разрешение на чтение контактов есть", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Разрешение на чтение контактов нет", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }

        int readCallLogPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);

        if (readCallLogPermission == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Разрешение на чтение READ_CALL_LOG есть", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Разрешение на чтение READ_CALL_LOG нет", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CALL_LOG},
                    MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

}
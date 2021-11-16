package com.yooda.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.yooda.app.adapter.HeadCategoryAdapter;
import com.yooda.app.fragment.CashFragment;
import com.yooda.app.fragment.StoreFragment;
import com.yooda.app.model.HeadCategory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView headCategoryRecycler;
    HeadCategoryAdapter headCategoryAdapter;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences("App", Context.MODE_PRIVATE);

        Log.d("LOL", sharedpreferences.getAll().toString());

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


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_view, StoreFragment.class, null)
                .commit();
        }

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

}
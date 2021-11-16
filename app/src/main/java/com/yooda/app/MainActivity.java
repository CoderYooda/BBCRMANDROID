package com.yooda.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gotoFragment();
            }
        });


        TabLayout tabLayout = findViewById(R.id.header_tab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        gotoFragment1();
                    case 1:
                        gotoFragment();
                }
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


    public void gotoFragment(){
        CashFragment fragment = new CashFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void gotoFragment1(){
        StoreFragment fragment = new StoreFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}